package jscl.math;

import jscl.math.function.Constant;

public class TechnicalVariable extends Constant {
    public TechnicalVariable(String name) {
        super(name);
    }

    public TechnicalVariable(String name, Generic subscript[]) {
        super(name,0,subscript);
    }

    protected Variable newinstance() {
        return new TechnicalVariable(name, new Generic[subscript.length]);
    }
}
