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
        Variable v=parameter[1].variableValue();
	StringBuffer b = new StringBuffer();
	b.append("<apply><int/><lowlimit>");
        b.append(parameter[2].toMathML(null));
        b.append("</lowlimit><uplimit>");
        b.append(parameter[3].toMathML(null));
        b.append("</uplimit><bvar>");
        b.append(v.toMathML(null));
        b.append("</bvar>");
        b.append(parameter[0].toMathML(null));
	b.append("</apply>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new Integral(null,null,null,null);
    }
}
