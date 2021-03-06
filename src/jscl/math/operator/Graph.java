package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.Variable;

public class Graph extends Operator {
    public Graph(Generic expression, Generic variable) {
        super("graph",new Generic[] {expression,variable});
    }

    public Object apply() {
        return jscl.engine.Graph.apply(eval());
    }

    public Generic compute() {
        Variable variable=parameter[1].variableValue();
        return parameter[0].function(variable);
    }

    protected Variable newinstance() {
        return new Graph(null,null);
    }
}
