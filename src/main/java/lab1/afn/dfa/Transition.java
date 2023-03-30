package lab1.afn.dfa;

import lab1.afn.dfa.State2;
import lab1.afn.thompson.Thompson;

public class Transition {
    private State2 initialState;
    private State2 finalState;
    private String transitionSymbol;

    public Transition(String transitionSymbol) {
        this.transitionSymbol = transitionSymbol;
        this.initialState = new State2(Thompson.stateCount);
        this.finalState = new State2(Thompson.stateCount);

        
        this.initialState.addNextState2(this.finalState);
        this.finalState.addPreviousState2(this.initialState);
    }

    
    public Transition(String transitionSymbol, State2 initialState, State2 finalState) {
        this.transitionSymbol = transitionSymbol;
        this.initialState = initialState;
        this.finalState = finalState;

        /* add link to states */
        this.initialState.addNextState2(this.finalState);
        this.finalState.addPreviousState2(this.initialState);
    }

    public State2 getInitialState() {
        return this.initialState;
    }

    public State2 getFinalState() {
        return this.finalState;
    }

    public String toString() {
        return initialState.toString() + " - " + transitionSymbol + " - " + finalState.toString();
    }

    public String getTransitionSymbol() { return this.transitionSymbol; }
}
