package jscl.math.operator.matrix;

import jscl.math.Generic;
import jscl.math.Matrix;
import jscl.math.Variable;
import jscl.math.operator.Operator;

public class Trace extends Operator {
    public Trace(Generic matrix) {
        super("trace",new Generic[] {matrix});
    }

    public Generic compute() {
        if(parameter[0] instanceof Matrix) {
            Matrix matrix=(Matrix)parameter[0];
            return matrix.trace();
        }
        return expressionValue();
    }

    public String toMathML(Object data) {
	StringBuffer b = new StringBuffer();
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) {
		b.append("<mo>" + "tr" + "</mo>");
        }
        else {
		b.append("<msup>");
		b.append("<mo>" + "tr" + "</mo>");
		b.append("<mn>" + String.valueOf(exponent) + "</mn>");
		b.append("</msup>");
        }
        b.append(parameter[0].toMathML(null));
	return b.toString();
    }

    protected Variable newinstance() {
        return new Trace(null);
    }
}
