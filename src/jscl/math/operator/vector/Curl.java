package jscl.math.operator.vector;

import jscl.math.Generic;
import jscl.math.JSCLVector;
import jscl.math.Variable;
import jscl.math.operator.VectorOperator;

public class Curl extends VectorOperator {
    public Curl(Generic vector, Generic variable) {
        super("curl",new Generic[] {vector,variable});
    }

    public Generic compute() {
        Variable variable[]=variables(parameter[1]);
        if(parameter[0] instanceof JSCLVector) {
            JSCLVector vector=(JSCLVector)parameter[0];
            return vector.curl(variable);
        }
        return expressionValue();
    }

    protected String bodyToMathML() {
        return operator("nabla") + "<mo>" + "\u2227" + "</mo>" + parameter[0].toMathML(null);
    }

    protected Variable newinstance() {
        return new Curl(null,null);
    }
}
