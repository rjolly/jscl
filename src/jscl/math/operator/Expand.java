package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.Variable;

public class Expand extends Operator {
    public Expand(Generic expression) {
        super("expand",new Generic[] {expression});
    }

    public Generic compute() {
        return parameter[0].expand();
    }

    protected Variable newinstance() {
        return new Expand(null);
    }
}
