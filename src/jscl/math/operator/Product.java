package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.JSCLInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;

public class Product extends Operator {
    public Product(Generic expression, Generic variable, Generic n1, Generic n2) {
        super("prod",new Generic[] {expression,variable,n1,n2});
    }

    public Generic compute() {
        Variable variable=parameter[1].variableValue();
        try {
            int n1=parameter[2].integerValue().intValue();
            int n2=parameter[3].integerValue().intValue();
            Generic a=JSCLInteger.valueOf(1);
            for(int i=n1;i<=n2;i++) {
                a=a.multiply(parameter[0].substitute(variable,JSCLInteger.valueOf(i)));
            }
            return a;
        } catch (NotIntegerException e) {}
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
	StringBuffer b = new StringBuffer();
	b.append("<mrow>");
	b.append("<munderover>");
	b.append("<mo>" + "\u220F" + "</mo>");
	b.append("<mrow>");
        b.append(parameter[1].toMathML(null));
	b.append("<mo>" + "=" + "</mo>");
        b.append(parameter[2].toMathML(null));
	b.append("</mrow>");
        b.append(parameter[3].toMathML(null));
	b.append("</munderover>");
        b.append(parameter[0].toMathML(null));
	b.append("</mrow>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new Product(null,null,null,null);
    }
}
