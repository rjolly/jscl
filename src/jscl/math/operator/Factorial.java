package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.JSCLInteger;
import jscl.math.NotIntegerException;
import jscl.math.NotVariableException;
import jscl.math.Variable;
import jscl.math.function.Frac;
import jscl.math.function.Pow;
import jscl.util.ArrayComparator;

public class Factorial extends Operator {
    public Factorial(Generic expression) {
        super("",new Generic[] {expression});
    }

    public Generic compute() {
        try {
            return parameter[0].integerValue().factorial();
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public int compareTo(Variable variable) {
        if(this==variable) return 0;
        int c=comparator.compare(this,variable);
        if(c<0) return -1;
        else if(c>0) return 1;
        else {
            Factorial v=(Factorial)variable;
            return ArrayComparator.comparator.compare(parameter,v.parameter);
        }
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        try {
            JSCLInteger en=parameter[0].integerValue();
            buffer.append(en);
        } catch (NotIntegerException e) {
            try {
                Variable v=parameter[0].variableValue();
                if(v instanceof Frac || v instanceof Pow) {
                    buffer.append(GenericVariable.valueOf(parameter[0]));
                } else buffer.append(v);
            } catch (NotVariableException e2) {
                buffer.append(GenericVariable.valueOf(parameter[0]));
            }
        }
        buffer.append("!");
        return buffer.toString();
    }

    public String toMathML() {
        StringBuffer b = new StringBuffer();
        b.append("<apply><factorial/>");
        b.append(parameter[0].toMathML());
        b.append("</apply>");
        return b.toString();
    }

    protected Variable newinstance() {
        return new Factorial(null);
    }
}
