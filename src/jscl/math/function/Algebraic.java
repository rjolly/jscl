package jscl.math.function;

import jscl.math.Generic;
import jscl.math.NotIntegrableException;

public abstract class Algebraic extends Function {
    public Algebraic(String name, Generic parameter[]) {
        super(name,parameter);
    }

    public abstract Root rootValue() throws NotRootException;

    public Generic antiderivative(int n) throws NotIntegrableException {
        return null;
    }

    public String toMathML(Object data) {
	StringBuffer b = new StringBuffer();
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) b.append(bodyToMathML(false));
        else {
		b.append("<msup>");
		b.append(bodyToMathML(true));
		b.append("<mn>" + String.valueOf(exponent) + "</mn>");
		b.append("</msup>");
        }
	return b.toString();
    }

    abstract String bodyToMathML(boolean fenced);
}
