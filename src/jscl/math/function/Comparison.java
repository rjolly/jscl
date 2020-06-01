package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JSCLBoolean;
import jscl.math.JSCLInteger;
import jscl.math.NotIntegerException;
import jscl.math.NotIntegrableException;
import jscl.math.NumericWrapper;
import jscl.math.Variable;

public class Comparison extends Function {
    int operator;

    public Comparison(String name, Generic expression1, Generic expression2) {
        super(name,new Generic[] {expression1,expression2});
        for(int i=0;i<easo.length;i++) if(name.compareTo(easo[i])==0) operator=i;
    }

    public Generic antiderivative(int n) throws NotIntegrableException {
        throw new NotIntegrableException();
    }

    public Generic derivative(int n) {
        return JSCLInteger.valueOf(0);
    }

    public Generic evaluate() {
        try {
            return compare(parameter[0].integerValue(),parameter[1].integerValue());
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Generic evalelem() {
        return expressionValue();
    }

    public Generic evalsimp() {
        return expressionValue();
    }

    public Generic evalfunc() {
        return compare((jscl.math.Function)parameter[0],(jscl.math.Function)parameter[1]);
    }

    public Generic evalnum() {
        return compare((NumericWrapper)parameter[0],(NumericWrapper)parameter[1]);
    }

    private JSCLBoolean compare(JSCLInteger a1, JSCLInteger a2) {
        return JSCLBoolean.valueOf(compare((Generic)a1,(Generic)a2));
    }

    private jscl.math.Function compare(final jscl.math.Function a1, final jscl.math.Function a2) {
        return new jscl.math.Function() {
            public double apply(double value) {
                return JSCLBoolean.valueOf(compare(Double.compare(a1.apply(value),a2.apply(value)))).content();
            }
        };
    }

    private NumericWrapper compare(NumericWrapper a1, NumericWrapper a2) {
        return new NumericWrapper(JSCLBoolean.valueOf(compare((Generic)a1,(Generic)a2)));
    }

    private boolean compare(Generic a1, Generic a2) {
        return compare(a1.compareTo(a2));
    }

    private boolean compare(int n) {
        switch(operator) {
            case 0:
                return n==0;
            case 1:
                return n!=0;
            case 2:
                return n<=0;
            case 3:
                return n<0;
            case 4:
                return n>=0;
            case 5:
                return n>0;
            case 6:
                return n==0;
            default:
                return false;
        }
    }

    public String toJava() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(parameter[0].toJava()).append(easj[operator]).append(parameter[1].toJava());
        return buffer.toString();
    }

    protected Variable newinstance() {
        return new Comparison(name,null,null);
    }

    private static final String eass[]={"=","<>","<=","<",">=",">","~"};
    private static final String easj[]={"==","!=","<=","<",">=",">","=="};
    private static final String easo[]={"eq","neq","leq","lt","geq","gt","approx"};
}
