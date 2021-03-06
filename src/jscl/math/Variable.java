package jscl.math;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import jscl.math.function.Algebraic;
import jscl.math.function.Constant;
import jscl.math.function.Frac;
import jscl.math.function.Function;
import jscl.math.function.ImplicitFunction;
import jscl.math.function.Root;
import jscl.math.function.Sqrt;
import jscl.math.operator.Factorial;
import jscl.math.operator.Operator;
import jscl.text.ParseException;
import jscl.editor.rendering.MathObject;

public abstract class Variable implements Comparable, MathObject {
    protected final String name;

    public Variable(final String name) {
        this.name = name;
    }

    public abstract Generic antiderivative(Variable variable) throws NotIntegrableException;
    public abstract Generic derivative(Variable variable);
    public abstract Generic substitute(Variable variable, Generic generic);
    public abstract Generic function(Variable variable);
    public abstract Generic eval();
    public abstract Generic expand();
    public abstract Generic factorize();
    public abstract Generic elementary();
    public abstract Generic simplify();
    public abstract Generic numeric();

    public Expression expressionValue() {
        return Expression.valueOf(this);
    }

    public abstract boolean isConstant(Variable variable);

    public boolean isIdentity(Variable variable) {
        return compareTo(variable)==0;
    }

    public int compareTo(Variable variable) {
        if(this==variable) return 0;
        int c=comparator.compare(this,variable);
        if(c<0) return -1;
        else if(c>0) return 1;
        else {
            return variableCompareTo(variable);
        }
    }

    public abstract int variableCompareTo(Variable variable);

    public int compareTo(Object o) {
        return compareTo((Variable)o);
    }

    public boolean equals(Object obj) {
        if(obj instanceof Variable) {
            return compareTo((Variable)obj)==0;
        } else return false;
    }

    public String toString() {
        return name;
    }

    protected String nameToMathML() {
	return special.containsKey(name)?(String)special.get(name):name;
    }

    protected abstract Variable newinstance();

    private static final Map special=new HashMap();
    static {
        special.put("Alpha","\u0391");
        special.put("Beta","\u0392");
        special.put("Gamma","\u0393");
        special.put("Delta","\u0394");
        special.put("Epsilon","\u0395");
        special.put("Zeta","\u0396");
        special.put("Eta","\u0397");
        special.put("Theta","\u0398");
        special.put("Iota","\u0399");
        special.put("Kappa","\u039A");
        special.put("Lambda","\u039B");
        special.put("Mu","\u039C");
        special.put("Nu","\u039D");
        special.put("Xi","\u039E");
        special.put("Pi","\u03A0");
        special.put("Rho","\u03A1");
        special.put("Sigma","\u03A3");
        special.put("Tau","\u03A4");
        special.put("Upsilon","\u03A5");
        special.put("Phi","\u03A6");
        special.put("Chi","\u03A7");
        special.put("Psi","\u03A8");
        special.put("Omega","\u03A9");
        special.put("alpha","\u03B1");
        special.put("beta","\u03B2");
        special.put("gamma","\u03B3");
        special.put("delta","\u03B4");
        special.put("epsilon","\u03B5");
        special.put("zeta","\u03B6");
        special.put("eta","\u03B7");
        special.put("theta","\u03B8");
        special.put("iota","\u03B9");
        special.put("kappa","\u03BA");
        special.put("lambda","\u03BB");
        special.put("mu","\u03BC");
        special.put("nu","\u03BD");
        special.put("xi","\u03BE");
        special.put("pi","\u03C0");
        special.put("rho","\u03C1");
        special.put("sigma","\u03C3");
        special.put("tau","\u03C4");
        special.put("upsilon","\u03C5");
        special.put("phi","\u03C6");
        special.put("chi","\u03C7");
        special.put("psi","\u03C8");
        special.put("omega","\u03C9");
        special.put("nabla","\u2207");
        special.put("aleph","\u2135");
        special.put("hbar","\u210F");
        special.put("hamilt","\u210B");
        special.put("lagran","\u2112");
        special.put("square","\u25A1");
    }

    protected static final Comparator comparator = new Comparator() {
        public int compare(Object o1, Object o2) {
            return value((Variable)o1)-value((Variable)o2);
        }
    };

    private static int value(Variable v) {
        int n;
        if(v instanceof TechnicalVariable) n=0;
        else if(v instanceof DoubleVariable) n=1;
        else if(v instanceof Frac && ((Frac)v).integer()) n=2;
        else if(v instanceof Sqrt && ((Sqrt)v).imaginary()) n=3;
        else if(v instanceof Constant) n=4;
        else if(v instanceof Root) n=5;
        else if(v instanceof Algebraic) n=6;
        else if(v instanceof ImplicitFunction) n=7;
        else if(v instanceof Function) n=8;
        else if(v instanceof Factorial) n=9;
        else if(v instanceof Operator) n=10;
        else if(v instanceof ExpressionVariable) n=11;
        else if(v instanceof VectorVariable) n=12;
        else if(v instanceof MatrixVariable) n=13;
        else throw new ArithmeticException();
        return n;
    }
}
