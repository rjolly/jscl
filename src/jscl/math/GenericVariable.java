package jscl.math;

public abstract class GenericVariable extends Variable {
    Generic content;

    GenericVariable(Generic generic) {
        super("");
        content=generic;
    }

    public static Generic content(Generic generic) {
        try {
            Variable v=generic.variableValue();
            if(v instanceof GenericVariable) generic=((GenericVariable)v).content;
        } catch (NotVariableException e) {}
        return generic;
    }

    public Generic antiderivative(Variable variable) throws NotIntegrableException {
        return content.antiderivative(variable);
    }

    public Generic derivative(Variable variable) {
        return content.derivative(variable);
    }

    public Generic substitute(Variable variable, Generic generic) {
        GenericVariable v=(GenericVariable)newinstance();
        v.content=content.substitute(variable,generic);
        if(v.isIdentity(variable)) return generic;
        else return v.expressionValue();
    }

    public Generic eval() {
        GenericVariable v=(GenericVariable)newinstance();
        v.content=content.eval();
        return v.expressionValue();
    }

    public Generic expand() {
        return content.expand();
    }

    public Generic factorize() {
        GenericVariable v=(GenericVariable)newinstance();
        v.content=content.factorize();
        return v.expressionValue();
    }

    public Generic elementary() {
        GenericVariable v=(GenericVariable)newinstance();
        v.content=content.elementary();
        return v.expressionValue();
    }

    public Generic simplify() {
        GenericVariable v=(GenericVariable)newinstance();
        v.content=content.simplify();
        return v.expressionValue();
    }

    public Generic function(Variable variable) {
        if(isIdentity(variable)) return Function.identity;
        else return content.function(variable);
    }

    public Generic numeric() {
        return content.numeric();
    }

    public boolean isConstant(Variable variable) {
        return content.isConstant(variable);
    }

    public int variableCompareTo(Variable variable) {
        GenericVariable v=(GenericVariable)variable;
        return content.compareTo(v.content);
    }

    public String toString() {
        return content.toString();
    }

    public String toMathML() {
        return content.toMathML();
    }
}
