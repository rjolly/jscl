package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.Variable;
import jscl.math.function.Constant;

public abstract class VectorOperator extends Operator {
    public VectorOperator(String name, Generic parameter[]) {
        super(name,parameter);
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

    protected abstract String bodyToMathML();

    protected String operator(String name) {
        Variable variable[]=variables(GenericVariable.content(parameter[1]));
	StringBuffer b = new StringBuffer();
	b.append("<msub>");
        b.append(new Constant(name).toMathML(null));
	b.append("<mrow>");
        for(int i=0;i<variable.length;i++) b.append(variable[i].expressionValue().toMathML(null));
	b.append("</mrow>");
	b.append("</msub>");
	return b.toString();
    }
}
