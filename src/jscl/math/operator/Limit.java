package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.Variable;

public class Limit extends Operator {
    public Limit(Generic expression, Generic variable, Generic limit, Generic direction) {
        super("lim",new Generic[] {expression,variable,limit,direction});
    }

    public Generic compute() {
        return expressionValue();
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        int n=4;
        if(parameter[3].signum()==0) n=3;
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
        int c=parameter[3].signum();
	StringBuffer b = new StringBuffer();
	b.append("<mrow>");
	b.append("<munder>");
	b.append("<mo>" + "lim" + "</mo>");
	b.append("<mrow>");
        b.append(parameter[1].toMathML(null));
	b.append("<mo>" + "\u2192" + "</mo>");
        if(c==0) b.append(parameter[2].toMathML(null));
        else {
	    b.append("<msup>");
            b.append(parameter[2].toMathML(null));
	    b.append("<mo>");
            b.append(c<0?"-":c>0?"+":"");
	    b.append("</mo>");
	    b.append("</msup>");
        }
	b.append("</mrow>");
	b.append("</munder>");
        b.append(parameter[0].toMathML(null));
	b.append("</mrow>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new Limit(null,null,null,null);
    }
}
