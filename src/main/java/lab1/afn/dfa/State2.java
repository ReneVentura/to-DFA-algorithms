package lab1.afn.dfa;

import java.util.LinkedList;
import java.util.List;

import lab1.afn.thompson.Thompson;


public class State2 {
    private List<State2> previousState2s;
    private List<State2> nextState2s;
    private int State2Id;

    private boolean isInitial;
    private boolean isFinal;

    public State2(int State2Id) {
        this.State2Id = State2Id;
        this.previousState2s = new LinkedList<State2>();
        this.nextState2s = new LinkedList<State2>();
        Thompson.stateCount++; // sets a unique ID which comes from AFN's total State2Count
    }

    public State2(int State2Id, List<State2> previousState2, List<State2> nextState2) {
        this.State2Id = State2Id;
        this.previousState2s = previousState2;
        this.nextState2s = nextState2;
        Thompson.stateCount++;
    }

    public State2(int State2Id, boolean dfa) {
        this.State2Id = State2Id;
        this.previousState2s = new LinkedList<>();
        this.nextState2s = new LinkedList<>();
    }

    public void addPreviousState2(State2 previousState2) {
        this.previousState2s.add(previousState2);
    }

    public void addNextState2(State2 nextState2) {
        this.nextState2s.add(nextState2);
    }

    public List<State2> getPreviousState2s() {
        return this.previousState2s;
    }

    public List<State2> getNextState2s() { return this.nextState2s; }

    public String toString() {
        return String.valueOf(this.State2Id);
    }

    public int getState2Id() { return this.State2Id; }

    public void setInitial(boolean isInitial) { this.isInitial = isInitial; }

    public boolean getInitial() { return this.isInitial; }

    public void setFinal(boolean isFinal) { this.isFinal = isFinal; }

    public boolean getFinal() { return this.isFinal; }
}