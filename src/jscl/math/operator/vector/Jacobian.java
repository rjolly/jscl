package jscl.math.operator.vector;

import jscl.math.Generic;
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

    @Override
    public String toMathML(Object data) {
        StringBuffer b = new StringBuffer();
        b.append("<apply>");
	b.append("<apply><transpose/>");
        b.append(operator("nabla").toMathML(null));
        b.append("</apply>");
	b.append("<apply><transpose/>");
        b.append(parameter[0].toMathML(null));
        b.append("</apply>");
        b.append("</apply>");
        return b.toString();
    }

    protected Variable newinstance() {
        return new Jacobian(null,null);
    }
}
