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

    @Override
    public String toMathML(Object data) {
        StringBuffer b = new StringBuffer();
        b.append("<apply><curl/>");
        b.append(parameter[0].toMathML(null));
        b.append(parameter[1].toMathML(null));
        b.append("</apply>");
        return b.toString();
    }

    protected Variable newinstance() {
        return new Curl(null,null);
    }
}
