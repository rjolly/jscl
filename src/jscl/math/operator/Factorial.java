package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.JSCLInteger;
import jscl.math.NotIntegerException;
import jscl.math.NotVariableException;
import jscl.math.Variable;
import jscl.math.function.Frac;
import jscl.math.function.Pow;
import jscl.util.ArrayComparator;

public class Factorial extends Operator {
    public Factorial(Generic expression) {
        super("",new Generic[] {expression});
    }

    public Generic compute() {
        try {
            int n=parameter[0].integerValue().intValue();
            Generic a=JSCLInteger.valueOf(1);
            for(int i=0;i<n;i++) {
                a=a.multiply(JSCLInteger.valueOf(i+1));
            }
            return a;
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public int compareTo(Variable variable) {
        if(this==variable) return 0;
        int c=comparator.compare(this,variable);
        if(c<0) return -1;
        else if(c>0) return 1;
        else {
            Factorial v=(Factorial)variable;
            return ArrayComparator.comparator.compare(parameter,v.parameter);
        }
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        try {
            JSCLInteger en=parameter[0].integerValue();
            buffer.append(en);
        } catch (NotIntegerException e) {
            try {
                Variable v=parameter[0].variableValue();
                if(v instanceof Frac || v instanceof Pow) {
                    buffer.append(GenericVariable.valueOf(parameter[0]));
                } else buffer.append(v);
            } catch (NotVariableException e2) {
                buffer.append(GenericVariable.valueOf(parameter[0]));
            }
        }
        buffer.append("!");
        return buffer.toString();
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
	b.append("<mrow>");
        try {
            JSCLInteger en=parameter[0].integerValue();
            b.append(en.toMathML(null));
        } catch (NotIntegerException e) {
            try {
                Variable v=parameter[0].variableValue();
                if(v instanceof Pow) {
                    b.append(GenericVariable.valueOf(parameter[0]).toMathML(null));
                } else b.append(v.toMathML(null));
            } catch (NotVariableException e2) {
                b.append(GenericVariable.valueOf(parameter[0]).toMathML(null));
            }
        }
	b.append("<mo>" + "!" + "</mo>");
	b.append("</mrow>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new Factorial(null);
    }
}
