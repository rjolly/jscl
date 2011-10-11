package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.NotIntegrableException;
import jscl.math.Variable;

public class Integral extends Operator {
    public Integral(Generic expression, Generic variable, Generic n1, Generic n2) {
        super("integral",new Generic[] {expression,variable,n1,n2});
    }

    public Generic compute() {
        Variable variable=parameter[1].variableValue();
        try {
            Generic a=parameter[0].antiderivative(variable);
            return a.substitute(variable,parameter[3]).subtract(a.substitute(variable,parameter[2]));
        } catch (NotIntegrableException e) {}
        return expressionValue();
    }

    public String toMathML(Object data) {
	StringBuffer b = new StringBuffer();
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) b.append(bodyToMathML());
        else {
		b.append("<msup>");
		b.append("<mfenced>" + bodyToMathML() + "</mfenced>");
		b.append("<mn>" + String.valueOf(exponent) + "</mn>");
		b.append("</msup>");
        }
	return b.toString();
    }

    String bodyToMathML() {
        Variable v=parameter[1].variableValue();
	StringBuffer b = new StringBuffer();
	b.append("<mrow>");
	b.append("<msubsup>");
	b.append("<mo>" + "\u222B" + "</mo>");
        b.append(parameter[2].toMathML(null));
        b.append(parameter[3].toMathML(null));
	b.append("</msubsup>");
        b.append(parameter[0].toMathML(null));
	b.append("<mo>" + /*"\u2146"*/"d" + "</mo>");
        b.append(v.toMathML(null));
	b.append("</mrow>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new Integral(null,null,null,null);
    }
}
