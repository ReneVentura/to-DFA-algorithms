package lab1.afn.thompson;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.IOUtils;
public class NfaToDfaConverter {
    private final Thompson nfa;
    private final List<Edges> dfaEdges;
    public final List<State> dfaStates;
    public final Map<Set<Integer>, State> dfaStateMap;

    public NfaToDfaConverter(Thompson nfa) {
        this.nfa = nfa;
        this.dfaEdges = new ArrayList<>();
        this.dfaStates = new ArrayList<>();
        this.dfaStateMap = new HashMap<>();
    }

    public List<Edges> convert() {
     // Create the initial state for the DFA, which is the epsilon closure of the NFA's initial state
    Set<Integer> initialStates = epsilonClosure(nfa.getInitialState().states);
    State dfaInitialState = new State(initialStates);
    dfaStates.add(dfaInitialState);
    dfaStateMap.put(initialStates, dfaInitialState);

    // Process each state in the DFA
    int i = 0;
    while (i < dfaStates.size()) {
        State dfaState = dfaStates.get(i);
        Set<Integer> dfaStateSet = dfaState.states;

        // For each input symbol, compute the set of states that can be reached from this state
        for (char inputSymbol : getInputSymbols()) {
            Set<Integer> nfaTargetStates = new HashSet<>();
            for (int nfaState : dfaStateSet) {
                Set<Integer> targets = getTargetStates(nfaState, inputSymbol);
                nfaTargetStates.addAll(targets);
            }

            // Compute the epsilon closure of the target states
            Set<Integer> epsilonClosure = epsilonClosure(nfaTargetStates);

            // If the target states haven't been processed yet, add them to the DFA
            if (!dfaStateMap.containsKey(epsilonClosure)) {
                State dfaTargetState = new State(epsilonClosure);
                dfaStates.add(dfaTargetState);
                dfaStateMap.put(epsilonClosure, dfaTargetState);
            }

            // Add an edge to the DFA for this input symbol
            int sourceIndex = dfaStates.indexOf(dfaState);
            int targetIndex = dfaStates.indexOf(dfaStateMap.get(epsilonClosure));
            dfaEdges.add(new Edges(sourceIndex, targetIndex, inputSymbol));
        }
        i++;
    }

    return dfaEdges;
    }

    public List<State> convert2() {
        // Create the initial state for the DFA, which is the epsilon closure of the NFA's initial state
        Set<Integer> initialStates = epsilonClosure(nfa.getInitialState().states);
        State dfaInitialState = new State(initialStates);
        dfaStates.add(dfaInitialState);
        dfaStateMap.put(initialStates, dfaInitialState);

        // Process each state in the DFA
        int i = 0;
        while (i < dfaStates.size()) {
            State dfaState = dfaStates.get(i);
            Set<Integer> dfaStateSet = dfaState.states;

            // For each input symbol, compute the set of states that can be reached from this state
            for (char inputSymbol : getInputSymbols()) {
                Set<Integer> nfaTargetStates = new HashSet<>();
                for (int nfaState : dfaStateSet) {
                    Set<Integer> targets = getTargetStates(nfaState, inputSymbol);
                    nfaTargetStates.addAll(targets);
                }

                // Compute the epsilon closure of the target states
                Set<Integer> epsilonClosure = epsilonClosure(nfaTargetStates);

                // If the target states haven't been processed yet, add them to the DFA
                if (!dfaStateMap.containsKey(epsilonClosure)) {
                    State dfaTargetState = new State(epsilonClosure);
                    dfaStates.add(dfaTargetState);
                    dfaStateMap.put(epsilonClosure, dfaTargetState);
                }

                // Add an edge to the DFA for this input symbol
                int sourceIndex = dfaStates.indexOf(dfaState);
                int targetIndex = dfaStates.indexOf(dfaStateMap.get(epsilonClosure));
                dfaEdges.add(new Edges(sourceIndex, targetIndex, inputSymbol));
            }
            i++;
        }

        return dfaStates;
    }

    private Set<Integer> getTargetStates(int sourceState, char inputSymbol) {
        Set<Integer> targets = new HashSet<>();
        for (Edges edge : nfa.getTransitionList()) {
            if (edge.start == sourceState && edge.label == inputSymbol) {
                targets.add(edge.end);
            }
        }
        return targets;
    }

    private Set<Integer> epsilonClosure(Set<Integer> states) {
        Stack<Integer> stack = new Stack<>();
        Set<Integer> visited = new HashSet<>();
    
        stack.addAll(states);
        visited.addAll(states);
    
        while (!stack.isEmpty()) {
            int state = stack.pop();
            for (Edges edge : nfa.getTransitionList()) {
                if (edge.start == state && edge.label == 'ε' && !visited.contains(edge.end)) {
                    stack.push(edge.end);
                    visited.add(edge.end);
                }
            }
        }
    
        return visited;
    }

    private Set<Character> getInputSymbols() {
        Set<Character> symbols = new HashSet<>();
        for (Edges edge : nfa.getTransitionList()) {
            if (edge.label != 'ε') {
                symbols.add(edge.label);
            }
        }
        return symbols;
    }

    public void toGraph(String filename) throws InterruptedException, IOException {
        List<Edges> dfaEdges = convert();
        String dot = "digraph {\n";
    for (int i = 0; i < dfaStates.size(); i++) {
        lab1.afn.thompson.State state = dfaStates.get(i);
        dot += "  " + i + " [label=\"" + state.states + "\",";
        if (nfa.isFinalState(state.states)) {
            dot += "shape=doublecircle";    
        } else {
            dot += "shape=circle";
        }
        dot += "]\n";
    }
    for (Edges edge : dfaEdges) {
        dot += "  " + edge.start + " -> " + edge.end + " [label=\"" + edge.label + "\"]\n";
    }
    dot += "}\n";

    ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng");
    Process process = pb.start();
    java.io.OutputStream stdin = process.getOutputStream();
    IOUtils.write(dot.toString(), stdin, "UTF-8");
    stdin.close();
    java.io.InputStream stdout = process.getInputStream();
    FileOutputStream output = new FileOutputStream(filename+".png");
    IOUtils.copy(stdout, output);
    output.close();
    stdout.close();

    // Wait for the process to finish
    process.waitFor(); 
    }
    public boolean isAcceptState(State state) {
        List<State> acceptingStates = nfa.getAcceptingStates();
        for (State acceptingState : acceptingStates) {
            if (acceptingState.states.equals(state.states)) {
                return true;
            }
        }
        return false;
    }
    public boolean accepts(String input) {
        State currentState = dfaStates.get(0);
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int targetIndex = -1;
            for (Edges edge : dfaEdges) {
                if (edge.getSource() == dfaStates.indexOf(currentState) && edge.label == c) {
                    targetIndex = edge.getTarget();
                    break;
                }
            }
            if (targetIndex == -1) {
                // no matching edge found, input is not accepted
                return false;
            }
            currentState = dfaStates.get(targetIndex);
        }
        // input consumed completely, check if current state is an accept state
        return isAcceptState(currentState);
    }


   
}