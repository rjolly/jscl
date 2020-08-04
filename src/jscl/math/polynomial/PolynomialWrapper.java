package jscl.math.polynomial;

import jscl.math.Expression;
import jscl.math.Generic;
import jscl.math.JSCLInteger;
import jscl.math.Power;
import jscl.math.Variable;

final class PolynomialWrapper extends Generic {
    final Polynomial content;

    PolynomialWrapper(Polynomial polynomial) {
        content=polynomial;
    }

    Polynomial content() {
        return content;
    }

    public PolynomialWrapper add(PolynomialWrapper wrapper) {
        return new PolynomialWrapper(content.add(wrapper.content));
    }

    public Generic add(Generic generic) {
        if(generic instanceof PolynomialWrapper) {
            return add((PolynomialWrapper)generic);
        } else {
            return add(valueof(generic));
        }
    }

    public PolynomialWrapper subtract(PolynomialWrapper wrapper) {
        return new PolynomialWrapper(content.subtract(wrapper.content));
    }

    public Generic subtract(Generic generic) {
        if(generic instanceof PolynomialWrapper) {
            return subtract((PolynomialWrapper)generic);
        } else {
            return subtract(valueof(generic));
        }
    }

    public PolynomialWrapper multiply(PolynomialWrapper wrapper) {
        return new PolynomialWrapper(content.multiply(wrapper.content));
    }

    public Generic multiply(Generic generic) {
        if(generic instanceof PolynomialWrapper) {
            return multiply((PolynomialWrapper)generic);
        } else {
            return multiply(valueof(generic));
        }
    }

    public PolynomialWrapper divide(PolynomialWrapper wrapper) throws ArithmeticException {
        return new PolynomialWrapper(content.divide(wrapper.content));
    }

    public Generic divide(Generic generic) throws ArithmeticException {
        if(generic instanceof PolynomialWrapper) {
            return divide((PolynomialWrapper)generic);
        } else {
            return divide(valueof(generic));
        }
    }

    public PolynomialWrapper gcd(PolynomialWrapper wrapper) {
        return new PolynomialWrapper(content.gcd(wrapper.content));
    }

    public Generic gcd(Generic generic) {
        if(generic instanceof PolynomialWrapper) {
            return gcd((PolynomialWrapper)generic);
        } else {
            return gcd(valueof(generic));
        }
    }

    public Generic gcd() {
        return content.gcd();
    }

    public Generic negate() {
        return new PolynomialWrapper(content.negate());
    }

    public int signum() {
        return content.signum();
    }

    public int degree() {
        return content.degree();
    }

    public Generic antiderivative(Variable variable) {
        return null;
    }

    public Generic derivative(Variable variable) {
        return null;
    }

    public Generic substitute(Variable variable, Generic generic) {
        return null;
    }

    public Generic function(Variable variable) {
        return null;
    }

    public Generic eval() {
        return null;
    }

    public Generic expand() {
        return null;
    }

    public Generic factorize() {
        return null;
    }

    public Generic elementary() {
        return null;
    }

    public Generic simplify() {
        return null;
    }

    public Generic numeric() {
        return null;
    }

    public Generic valueof(Generic generic) {
        if(generic instanceof PolynomialWrapper) {
            return new PolynomialWrapper(content.valueof(((PolynomialWrapper)generic).content));
        } else {
            return new PolynomialWrapper(content.valueof(generic));
        }
    }

    public Generic[] sumValue() {
        return null;
    }

    public Generic[] productValue() {
        return null;
    }

    public Power powerValue() {
        return null;
    }

    public Expression expressionValue() {
        return null;
    }

    public JSCLInteger integerValue() {
        return null;
    }

    public Variable variableValue() {
        return null;
    }

    public Variable[] variables() {
        return new Variable[0];
    }

    public boolean isPolynomial(Variable variable) {
        return false;
    }

    public boolean isConstant(Variable variable) {
        return false;
    }

    public int compareTo(PolynomialWrapper wrapper) {
        return content.compareTo(wrapper.content);
    }

    public int compareTo(Generic generic) {
        if(generic instanceof PolynomialWrapper) {
            return compareTo((PolynomialWrapper)generic);
        } else {
            return compareTo(valueof(generic));
        }
    }

    public static Generic factory(Variable variable[]) {
        if(variable.length>1) {
            Variable var[]=new Variable[variable.length-1];
            for(int i=0;i<var.length;i++) var[i]=variable[i+1];
            return new PolynomialWrapper(NestedPolynomial.factory(var));
        } else return null;
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        if(signum()<0) buffer.append("-").append(negate());
        else buffer.append("(").append(content).append(")");
        return buffer.toString();
    }

    @Override
    public String toMathML() {
	    return content.toMathML();
    }
}
