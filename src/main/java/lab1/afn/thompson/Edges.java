package lab1.afn.thompson;
public class Edges {
    public int start;
    public int end;
    public char label;

    public Edges(int start, int end, char label) {
        this.start = start;
        this.end = end;
        this.label = label;
        start2=null;
        end2=null;
    }
    private final State start2;
    private final State end2;
    
    
    public Edges(State start, State end, char label) {
        this.start2 = start;
        this.end2 = end;
        this.label = label;
    }
    public int getSource() {
        return start;
    }

    public int getTarget() {
        return end;
    }

    public char getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return start + " --" + label + "--> " + end;
    }
}