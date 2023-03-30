package lab1.afn.thompson;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lab1.afn.preafn.infpos;

public class Thompson {
    public static int stateCount = 0;
    private int flag=-1;
    public List<Edges> transitionList;
    public State initialState;
    public State finalState;

    public Thompson(String infixRegex) {
        String postfixRegex = infpos.infixToPostfix(infixRegex);
        Stack<State> stack = new Stack<>();
        transitionList = new ArrayList<>();

        for (int i = 0; i < postfixRegex.length(); i++) {
            char c = postfixRegex.charAt(i);

            if (isOperator(c)) {
                State s2 = stack.pop();
                State s1 = null;

                if (c != '*') {
                    s1 = stack.pop();
                }

                if (c == '|') {
                    State union = union(s1, s2);
                    stack.push(union);
                } else if (c == '.') {
                    State concatenation = concatenate(s1, s2);
                    stack.push(concatenation);
                } else if (c == '*') {
                    State kleeneClosure = kleeneClosure(s2);
                    stack.push(kleeneClosure);
                }
            } else {
                State s = new State(new HashSet<>(Arrays.asList(stateCount, stateCount+1)));
                transitionList.add(new Edges(stateCount, stateCount+1, c));
                stack.push(s);
                stateCount += 2;
            }
        }

        finalState = stack.pop();
        initialState = new State(new HashSet<>(Arrays.asList(0)));
        }


    private boolean isOperator(char c) {
        return c == '|' || c == '.' || c == '*';
    }

    private State union(State s1, State s2) {
        State s = new State(new HashSet<>(Arrays.asList(stateCount, stateCount+1)));
        transitionList.add(new Edges(stateCount, s1.states.iterator().next(), 'ε'));
        transitionList.add(new Edges(stateCount, s2.states.iterator().next(), 'ε'));
        transitionList.add(new Edges(s1.states.iterator().next()+1, stateCount+1, 'ε'));
        transitionList.add(new Edges(s2.states.iterator().next()+1, stateCount+1, 'ε'));
        stateCount += 2;
        return s;
    }

    private State concatenate(State s1, State s2) {
        for (int state : s1.states) {
            if (s2.states.contains(state+1)) {
                s2.states.remove(state+1);
            }
        }
        State s = new State(new HashSet<>(s1.states));
        s.states.addAll(s2.states);
        int lastStateS1;
        int firstStateS2;
        int lastElement=0;
        if(s1.states.size()>0){
            // get last state in s1 and first state in s2
            lastStateS1 = s1.states.iterator().next() + s1.states.size() - 1;
            firstStateS2 = s2.states.iterator().next();
        }
        else
        {
            for(int state: s1.states) {
                lastElement = state;
            }
            lastStateS1 = lastElement;
            firstStateS2 = s2.states.iterator().next();
        }
    
        // add transition from last state in s1 to first state in s2
        transitionList.add(new Edges(lastStateS1, firstStateS2, 'ε'));
    
        return s;
    }

    private State kleeneClosure(State s) {
        State newInitial = new State(new HashSet<>(Arrays.asList(stateCount, stateCount+1)));
        transitionList.add(new Edges(stateCount, s.states.iterator().next(), 'ε'));
        transitionList.add(new Edges(stateCount, stateCount+1, 'ε'));
        transitionList.add(new Edges(s.states.iterator().next()+1, s.states.iterator().next(), 'ε'));
        transitionList.add(new Edges(s.states.iterator().next()+1, stateCount+1, 'ε'));
        
        stateCount += 2;
    
       
        // Add the new initial state to the stack
        return newInitial;
    }

    public List<Edges> getTransitionList() {
        return transitionList;
    }

    public State getInitialState() {
        return initialState;
    }

    public State getFinalState() {
        return finalState;
    }
    public boolean isFinalState(Set<Integer> states) {
        for (int state : states) {
            //System.out.println(finalState.states.iterator().next());
            if (finalState.contains(state)) {
                return true;
            }
        }
        return false;
    }
    public List<State> getAcceptingStates() {
        List<State> acceptingStates = new ArrayList<>();
        for (int i = 0; i < stateCount; i++) {
            State state = new State(new HashSet<>(Arrays.asList(i)));
            if (isFinalState(state.states)) {
                acceptingStates.add(state);
            }
        }
        return acceptingStates;
    }

    public boolean accepts(String input) {
        Set<Integer> currentStates = initialState.states;
    
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            Set<Integer> nextStates = new HashSet<>();
    
            for (Edges edge : transitionList) {
                if (currentStates.contains(edge.getSource()) && edge.getLabel() == c) {
                    nextStates.add(edge.getTarget());
                }
            }
    
            currentStates = nextStates;
        }
    
        return isFinalState(currentStates);
    }
}
