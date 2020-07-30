package jscl.math;

public class VectorVariable extends GenericVariable {
    public VectorVariable(Generic generic) {
        super(generic);
    }

    public Generic eval() {
        return content.eval();
    }

    protected Variable newinstance() {
        return new VectorVariable(null);
    }
}
