package jscl.math;

public class ExpressionVariable extends GenericVariable {
    public ExpressionVariable(Generic generic) {
        super(generic);
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        buffer.append("(").append(content).append(")");
        return buffer.toString();
    }

    protected Variable newinstance() {
        return new ExpressionVariable(null);
    }
}
