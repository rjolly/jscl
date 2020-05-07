package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JSCLInteger;
import jscl.math.NotIntegerException;
import jscl.math.NotIntegrableException;
import jscl.math.NotVariableException;
import jscl.math.NumericWrapper;
import jscl.math.Variable;

public class Abs extends Function {
    public Abs(Generic generic) {
        super("abs",new Generic[] {generic});
    }

    public Generic antiderivative(int n) throws NotIntegrableException {
        return Constant.half.multiply(parameter[0]).multiply(new Abs(parameter[0]).evaluate());
    }

    public Generic derivative(int n) {
        return new Sgn(parameter[0]).evaluate();
    }

    public Generic evaluate() {
        if(parameter[0].signum()<0) {
            return new Abs(parameter[0].negate()).evaluate();
        }
        try {
            return parameter[0].integerValue().abs();
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Generic evalelem() {
        return new Sqrt(
            parameter[0].pow(2)
        ).evalelem();
    }

    public Generic evalsimp() {
        if(parameter[0].signum()<0) {
            return new Abs(parameter[0].negate()).evalsimp();
        }
        try {
            return parameter[0].integerValue().abs();
        } catch (NotIntegerException e) {}
        try {
            Variable v=parameter[0].variableValue();
            if(v instanceof Abs) {
                Function f=(Function)v;
                return f.evalsimp();
            } else if(v instanceof Sgn) {
                return JSCLInteger.valueOf(1);
            }
        } catch (NotVariableException e) {}
        return expressionValue();
    }

    public Generic evalfunc() {
        return ((jscl.math.Function)parameter[0]).abs();
    }

    public Generic evalnum() {
        return ((NumericWrapper)parameter[0]).abs();
    }

    public String toJava() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(parameter[0].toJava());
        buffer.append(".abs()");
        return buffer.toString();
    }

    public String toMathML(Object data) {
	StringBuffer b = new StringBuffer();
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) b.append(bodyToMathML());
        else {
		b.append("<msup>");
		b.append(bodyToMathML());
		b.append("<mn>" + String.valueOf(exponent) + "</mn>");
		b.append("</msup>");
        }
	return b.toString();
    }

    String bodyToMathML() {
	StringBuffer b = new StringBuffer();
	b.append("<mfenced open=\"|\" close=\"|\">");
        b.append(parameter[0].toMathML(null));
	b.append("</mfenced>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new Abs(null);
    }
}
