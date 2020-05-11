package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.GenericVariable;
import jscl.math.JSCLInteger;
import jscl.math.JSCLVector;
import jscl.math.Variable;
import jscl.math.function.Constant;
import jscl.math.function.ImplicitFunction;
import jscl.math.polynomial.Basis;
import jscl.math.polynomial.Monomial;
import jscl.math.polynomial.Ordering;
import jscl.math.polynomial.Polynomial;

public class Groebner extends Operator {
    public Groebner(Generic generic, Generic variable, Generic ordering, Generic modulo) {
        super("groebner",new Generic[] {generic,variable,ordering,modulo});
    }

    public Generic compute() {
        Generic generic[]=((JSCLVector)parameter[0]).elements();
        Variable variable[]=variables(parameter[1]);
        Ordering ord=ordering(parameter[2]);
        int m=parameter[3].integerValue().intValue();
        return new PolynomialVector(Basis.compute(generic,variable,ord,m));
    }

    public Operator transmute() {
        Generic p[]=new Generic[] {GenericVariable.content(parameter[0]),GenericVariable.content(parameter[1])};
        if(p[0] instanceof JSCLVector && p[1] instanceof JSCLVector) {
            Generic generic[]=((JSCLVector)p[0]).elements();
            Variable variable[]=variables(p[1]);
            Ordering ord=ordering(parameter[2]);
            int m=parameter[3].integerValue().intValue();
            return new Groebner(new PolynomialVector(new Basis(generic,Polynomial.factory(variable,ord,m))),p[1],parameter[2],parameter[3]);
        }
        return this;
    }

    static Ordering ordering(Generic generic) {
        Variable v=generic.variableValue();
        if(v.compareTo(new Constant("lex"))==0) return Monomial.lexicographic;
        else if(v.compareTo(new Constant("tdl"))==0) return Monomial.totalDegreeLexicographic;
        else if(v.compareTo(new Constant("drl"))==0) return Monomial.degreeReverseLexicographic;
        else if(v instanceof ImplicitFunction) {
            Generic g[]=((ImplicitFunction)v).parameters();
            int k=g[0].integerValue().intValue();
            if(v.compareTo(new ImplicitFunction("elim",new Generic[] {JSCLInteger.valueOf(k)}))==0) return Monomial.kthElimination(k);
        }
        throw new ArithmeticException();
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        int n=4;
        if(parameter[3].signum()==0) {
            n=3;
            if(ordering(parameter[2])==Monomial.lexicographic) n=2;
        }
        buffer.append(name);
        buffer.append("(");
        for(int i=0;i<n;i++) {
            buffer.append(parameter[i]).append(i<n-1?", ":"");
        }
        buffer.append(")");
        return buffer.toString();
    }

    public String toMathML(Object data) {
	StringBuffer b = new StringBuffer();
        int n=4;
        if(parameter[3].signum()==0) {
            n=3;
            if(ordering(parameter[2])==Monomial.lexicographic) n=2;
        }
	b.append("<apply>");
	b.append("<ci>" + nameToMathML() + "</ci>");
        for(int i=0;i<n;i++) {
            b.append(parameter[i].toMathML(null));
        }
	b.append("</apply>");
	return b.toString();
    }

    protected Variable newinstance() {
        return new Groebner(null,null,null,null);
    }
}

class PolynomialVector extends JSCLVector {
    final Basis basis;

    PolynomialVector(Basis basis) {
        this(basis,basis.elements());
    }

    PolynomialVector(Basis basis, Generic generic[]) {
        super(generic.length>0?generic:new Generic[] {JSCLInteger.valueOf(0)});
        this.basis=basis;
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        buffer.append("{");
        for(int i=0;i<n;i++) {
            buffer.append(basis.polynomial(element[i])).append(i<n-1?", ":"");
        }
        buffer.append("}");
        return buffer.toString();
    }

    protected String bodyToMathML() {
	StringBuffer b = new StringBuffer();
	b.append("<mfenced>");
	b.append("<mtable>");
        for(int i=0;i<n;i++) {
	    b.append("<mtr>");
	    b.append("<mtd>");
            b.append(basis.polynomial(element[i]).toMathML(null));
	    b.append("</mtd>");
	    b.append("</mtr>");
        }
	b.append("</mtable>");
	b.append("</mfenced>");
	return b.toString();
    }

    protected Generic newinstance(Generic element[]) {
        return new PolynomialVector(basis,element);
    }
}
