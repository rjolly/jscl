package jscl.math.operator.number;

import jscl.math.Generic;
import jscl.math.JSCLInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;
import jscl.math.operator.Operator;

public class EulerPhi extends Operator {
    public EulerPhi(Generic integer) {
        super("eulerphi",new Generic[] {integer});
    }

    public Generic compute() {
        try {
            JSCLInteger en=parameter[0].integerValue();
            return en.phi();
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    protected Variable newinstance() {
        return new EulerPhi(null);
    }
}
