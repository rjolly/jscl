package jscl.math.operator.vector;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.JSCLVector;
import jscl.math.Variable;
import jscl.math.function.Constant;
import jscl.math.operator.VectorOperator;

public class Jacobian extends VectorOperator {
    public Jacobian(Generic vector, Generic variable) {
        super("jacobian",new Generic[] {vector,variable});
    }

    public Generic compute() {
        Variable variable[]=variables(parameter[1]);
        if(parameter[0] instanceof JSCLVector) {
            JSCLVector vector=(JSCLVector)parameter[0];
            return vector.jacobian(variable);
        }
        return expressionValue();
    }

    protected String bodyToMathML() {
	StringBuffer b = new StringBuffer();
        b.append(operator("nabla"));
	b.append("<msup>");
        b.append(parameter[0].toMathML(null));
	b.append("<mo>" + "T" + "</mo>");
	b.append("</msup>");
	return b.toString();
    }

    protected String operator(String name) {
        Variable variable[]=variables(GenericVariable.content(parameter[1]));
	StringBuffer b = new StringBuffer();
	b.append("<msubsup>");
        b.append(new Constant(name).toMathML(null));
	b.append("<mrow>");
        for(int i=0;i<variable.length;i++) b.append(variable[i].expressionValue().toMathML(null));
	b.append("</mrow>");
	b.append("<mo>" + "T" + "</mo>");
	b.append("</msubsup>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new Jacobian(null,null);
    }
}
