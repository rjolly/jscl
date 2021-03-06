package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.JSCLInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;

public class Product extends Operator {
    public Product(Generic expression, Generic variable, Generic n1, Generic n2) {
        super("product",new Generic[] {expression,variable,n1,n2});
    }

    public Generic compute() {
        Variable variable=parameter[1].variableValue();
        try {
            int n1=parameter[2].integerValue().intValue();
            int n2=parameter[3].integerValue().intValue();
            Generic a=JSCLInteger.valueOf(1);
            for(int i=n1;i<=n2;i++) {
                a=a.multiply(parameter[0].substitute(variable,JSCLInteger.valueOf(i)));
            }
            return a;
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public String toMathML() {
        Variable v=parameter[1].variableValue();
        StringBuffer b = new StringBuffer();
        b.append("<apply><product/><lowlimit>");
        b.append(parameter[2].toMathML());
        b.append("</lowlimit><uplimit>");
        b.append(parameter[3].toMathML());
        b.append("</uplimit><bvar>");
        b.append(v.toMathML());
        b.append("</bvar>");
        b.append(parameter[0].toMathML());
        b.append("</apply>");
        return b.toString();
     }

    protected Variable newinstance() {
        return new Product(null,null,null,null);
    }
}
