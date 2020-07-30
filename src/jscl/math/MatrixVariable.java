package jscl.math;

public class MatrixVariable extends GenericVariable {
    public MatrixVariable(Generic generic) {
        super(generic);
    }

    public Generic eval() {
        return content.eval();
    }

    protected Variable newinstance() {
        return new MatrixVariable(null);
    }
}
