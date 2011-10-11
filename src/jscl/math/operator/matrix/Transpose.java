package jscl.math.operator.matrix;

import jscl.math.Generic;
import jscl.math.Matrix;
import jscl.math.Variable;
import jscl.math.operator.Operator;

public class Transpose extends Operator {
    public Transpose(Generic matrix) {
        super("tran",new Generic[] {matrix});
    }

    public Generic compute() {
        if(parameter[0] instanceof Matrix) {
            Matrix matrix=(Matrix)parameter[0];
            return matrix.transpose();
        }
        return expressionValue();
    }

    public String toMathML(Object data) {
	StringBuffer b = new StringBuffer();
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) b.append(bodyToMathML());
        else {
		b.append("<msup>");
		b.append(bodyToMathML());
		b.append("<mn>" + String.valueOf(exponent) + "</mn>");
		b.append("</msup>");
        }
	return b.toString();
    }

    String bodyToMathML() {
	StringBuffer b = new StringBuffer();
	b.append("<msup>");
        b.append(parameter[0].toMathML(null));
	b.append("<mo>" + "T" + "</mo>");
	b.append("</msup>");
    	return b.toString();
}

    protected Variable newinstance() {
        return new Transpose(null);
    }
}
