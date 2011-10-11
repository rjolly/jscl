package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.JSCLInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;

public class Derivative extends Operator {
    public Derivative(Generic expression, Generic variable, Generic value, Generic order) {
        super("d",new Generic[] {expression,variable,value,order});
    }

    public Generic compute() {
        Variable variable=parameter[1].variableValue();
        try {
            int n=parameter[3].integerValue().intValue();
            Generic a=parameter[0];
            for(int i=0;i<n;i++) {
                a=a.derivative(variable);
            }
            return a.substitute(variable,parameter[2]);
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        int n=4;
        if(parameter[3].compareTo(JSCLInteger.valueOf(1))==0) {
            n=3;
            if(parameter[2].compareTo(parameter[1])==0) n=2;
        }
        buffer.append(name);
        buffer.append("(");
        for(int i=0;i<n;i++) {
            buffer.append(parameter[i]).append(i<n-1?", ":"");
        }
        buffer.append(")");
        return buffer.toString();
    }

    public String toMathML(Object data) {
	StringBuffer b = new StringBuffer();
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) b.append(derivationToMathML(false));
        else {
	    b.append("<msup>");
            b.append(derivationToMathML(true));
	    b.append("<mn>" + String.valueOf(exponent) + "</mn>");
	    b.append("</msup>");
        }
	b.append("<mfenced>");
        b.append(parameter[0].toMathML(null));
        if(parameter[2].compareTo(parameter[1])!=0) b.append(parameter[2].toMathML(null));
	b.append("</mfenced>");
	return b.toString();
     }

    String derivationToMathML(boolean fenced) {
	return fenced?"<mfenced>" + derivationToMathML() + "</mfenced>":derivationToMathML();
    }

    String derivationToMathML() {
        Variable v=parameter[1].variableValue();
	StringBuffer b = new StringBuffer();
        int n=0;
        try {
            n=parameter[3].integerValue().intValue();
        } catch (NotIntegerException e) {}
        if(n==1) {
	    b.append("<mfrac>");
	    b.append("<mo>" + /*"\u2146"*/"d" + "</mo>");
	    b.append("<mrow>");
	    b.append("<mo>" + /*"\u2146"*/"d" + "</mo>");
            b.append(v.toMathML(null));
	    b.append("</mrow>");
	    b.append("</mfrac>");
        } else {
	    b.append("<mfrac>");
	    b.append("<msup>");
	    b.append("<mo>" + /*"\u2146"*/"d" + "</mo>");
            b.append(parameter[3].toMathML(null));
	    b.append("</msup>");
	    b.append("<mrow>");
	    b.append("<mo>" + /*"\u2146"*/"d" + "</mo>");
	    b.append("<msup>");
            b.append(parameter[1].toMathML(null));
            b.append(parameter[3].toMathML(null));
	    b.append("</msup>");
	    b.append("</mrow>");
	    b.append("</mfrac>");
        }
	return b.toString();
    }

    protected Variable newinstance() {
        return new Derivative(null,null,null,null);
    }
}
