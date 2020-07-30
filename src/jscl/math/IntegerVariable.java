package jscl.math;

class IntegerVariable extends GenericVariable {
    IntegerVariable(Generic generic) {
        super(generic);
    }

    public Generic eval() {
        return content.eval();
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        buffer.append("(").append(content).append(")");
        return buffer.toString();
    }

    protected Variable newinstance() {
        return new IntegerVariable(null);
    }
}
