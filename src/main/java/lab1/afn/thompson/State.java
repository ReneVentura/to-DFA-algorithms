    package lab1.afn.thompson;

    import java.util.HashSet;
    import java.util.Objects;
    import java.util.Set;
    import java.util.*;
    public class State {
        public Set<Integer> states;
        public List<State> next;
        public List<State> prev;
        private boolean accepting;
        public State(Set<Integer> states) {
            this.states = states;
        }

        public State(int state) {
            this.states = new HashSet<>();
            this.states.add(state);
            this.prev = new LinkedList<State>();
            this.next = new LinkedList<State>();
            Thompson.stateCount++;
        }
        

        public boolean contains(int state) {
            return states.contains(state);
        }

        public boolean contains(State otherState) {
            return states.containsAll(otherState.states);
        }
        
        
        public void addPreviousStates(State previousState) {
            this.prev.add(previousState);
        }

        public void addNextStates(State nextState) {
            this.next.add(nextState);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            State state = (State) other;
            return Objects.equals(states, state.states);
        }
        

        @Override
        public int hashCode() {
            return Objects.hash(states);
        }

        @Override
        public String toString() {
            return states.toString();
        }
        public void setAccepting(boolean accepting) {
            this.accepting = accepting;
        }
    
        public boolean isAccepting() {
            return accepting;
        }
    }
