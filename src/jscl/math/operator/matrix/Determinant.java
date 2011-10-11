package jscl.math.operator.matrix;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.Matrix;
import jscl.math.Variable;
import jscl.math.operator.Operator;

public class Determinant extends Operator {
    public Determinant(Generic matrix) {
        super("det",new Generic[] {matrix});
    }

    public Generic compute() {
        if(parameter[0] instanceof Matrix) {
            Matrix matrix=(Matrix)parameter[0];
            return matrix.determinant();
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
        Generic m=GenericVariable.content(parameter[0]);
	StringBuffer b = new StringBuffer();
	b.append("<mfenced open=\"|\" close=\"|\">");
        if(m instanceof Matrix) {
            Generic element[][]=((Matrix)m).elements();
	    b.append("<mtable>");
            for(int i=0;i<element.length;i++) {
		b.append("<mtr>");
                for(int j=0;j<element.length;j++) {
		    b.append("<mtd>");
                    b.append(element[i][j].toMathML(null));
		    b.append("</mtd>");
                }
		b.append("</mtr>");
            }
	    b.append("</mtable>");
        } else b.append(m.toMathML(null));
	b.append("</mfenced>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new Determinant(null);
    }
}
