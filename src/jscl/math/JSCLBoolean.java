package jscl.math;

import java.math.BigInteger;

public class JSCLBoolean extends ModularInteger {
    public JSCLBoolean(BigInteger content) {
        super(content,BigInteger.valueOf(2));
    }

    public static JSCLBoolean valueOf(boolean value) {
        return new JSCLBoolean(BigInteger.valueOf(value?1:0));
    }
}
