package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.NotIntegrableException;
import jscl.math.Variable;

public class IndefiniteIntegral extends Operator {
    public IndefiniteIntegral(Generic expression, Generic variable) {
        super("integral",new Generic[] {expression,variable});
    }

    public Generic compute() {
        Variable variable=parameter[1].variableValue();
        try {
            return parameter[0].antiderivative(variable);
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
	b.append("<mo>" + "\u222B" + "</mo>");
        b.append(parameter[0].toMathML(null));
	b.append("<mo>" + /*"\u2146"*/"d" + "</mo>");
        b.append(v.toMathML(null));
	b.append("</mrow>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new IndefiniteIntegral(null,null);
    }
}
