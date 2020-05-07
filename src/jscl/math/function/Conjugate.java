package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JSCLInteger;
import jscl.math.JSCLVector;
import jscl.math.Matrix;
import jscl.math.NotIntegerException;
import jscl.math.NotIntegrableException;
import jscl.math.NotVariableException;
import jscl.math.NumericWrapper;
import jscl.math.Power;
import jscl.math.Variable;

public class Conjugate extends Function {
    public Conjugate(Generic generic) {
        super("conjugate",new Generic[] {generic});
    }

    public Generic antiderivative(int n) throws NotIntegrableException {
        return Constant.half.multiply(evaluate().pow(2));
    }

    public Generic derivative(int n) {
        return JSCLInteger.valueOf(1);
    }

    public Generic evaluate() {
        try {
            return parameter[0].integerValue();
        } catch (NotIntegerException e) {}
        if(parameter[0] instanceof Matrix) {
            return ((Matrix)parameter[0]).conjugate();
        } else if(parameter[0] instanceof JSCLVector) {
            return ((JSCLVector)parameter[0]).conjugate();
        }
        return expressionValue();
    }

    public Generic evalelem() {
        try {
            return parameter[0].integerValue();
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Generic evalsimp() {
        try {
            return parameter[0].integerValue();
        } catch (NotIntegerException e) {}
        if(parameter[0].signum()<0) {
            return new Conjugate(parameter[0].negate()).evalsimp().negate();
        } else if(parameter[0].compareTo(Constant.i)==0) {
            return Constant.i.negate();
        }
        try {
            Variable v=parameter[0].variableValue();
            if(v instanceof Conjugate) {
                Generic g[]=((Conjugate)v).parameters();
                return g[0];
            } else if(v instanceof Exp) {
                Generic g[]=((Exp)v).parameters();
                return new Exp(new Conjugate(g[0]).evalsimp()).evalsimp();
            } else if(v instanceof Log) {
                Generic g[]=((Log)v).parameters();
                return new Log(new Conjugate(g[0]).evalsimp()).evalsimp();
            }
        } catch (NotVariableException e) {
            Generic a[]=parameter[0].sumValue();
            if(a.length>1) {
                Generic s=JSCLInteger.valueOf(0);
                for(int i=0;i<a.length;i++) {
                    s=s.add(new Conjugate(a[i]).evalsimp());
                }
                return s;
            } else {
                Generic p[]=a[0].productValue();
                Generic s=JSCLInteger.valueOf(1);
                for(int i=0;i<p.length;i++) {
                    Power o=p[i].powerValue();
                    s=s.multiply(new Conjugate(o.value()).evalsimp().pow(o.exponent()));
                }
                return s;
            }
        }
        Generic n[]=Frac.separateCoefficient(parameter[0]);
        if(n[0].compareTo(JSCLInteger.valueOf(1))==0 && n[1].compareTo(JSCLInteger.valueOf(1))==0);
        else return new Conjugate(n[2]).evalsimp().multiply(
            new Frac(n[0],n[1]).evalsimp()
        );
        return expressionValue();
    }

    public Generic evalfunc() {
        return ((jscl.math.Function)parameter[0]).conjugate();
    }

    public Generic evalnum() {
        return ((NumericWrapper)parameter[0]).conjugate();
    }

    public String toJava() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(parameter[0].toJava());
        buffer.append(".conjugate()");
        return buffer.toString();
    }

    public String toMathML(Object data) {
	StringBuffer b = new StringBuffer();
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) b.append(bodyToMathML());
        else {
		b.append("<msup>");
		b.append("<mfenced>" + bodyToMathML() + "</mfenced>");
		b.append("<mn>" + String.valueOf(exponent) + "</mn>");
		b.append("</msup>");
        }
	return b.toString();
    }

    String bodyToMathML() {
	StringBuffer b = new StringBuffer();
	b.append("<mover>");
        b.append(parameter[0].toMathML(null));
	b.append("<mo>" + "-" + "</mo>");
	b.append("</mover>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new Conjugate(null);
    }
}
