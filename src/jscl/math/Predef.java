package jscl.math;

import jscl.math.function.*;
import jscl.math.function.trigonometric.*;
import jscl.math.function.hyperbolic.*;

public class Predef {
	public static final Generic pi=Constant.pi;
	public static final Generic oo=Constant.infinity;

	public static Generic root(Generic[] a, Generic b) {
		return new Root(a, b).expressionValue();
	}

	public static Generic cubic(Generic a) {
		return new Cubic(a).expressionValue();
	}

	public static Generic sqrt(Generic a) {
		return new Sqrt(a).expressionValue();
	}

	public static Generic sin(Generic a) {
		return new Sin(a).expressionValue();
	}

	public static Generic cos(Generic a) {
		return new Cos(a).expressionValue();
	}

	public static Generic tan(Generic a) {
		return new Tan(a).expressionValue();
	}

	public static Generic cot(Generic a) {
		return new Cot(a).expressionValue();
	}

	public static Generic arcsin(Generic a) {
		return new Asin(a).expressionValue();
	}

	public static Generic arccos(Generic a) {
		return new Acos(a).expressionValue();
	}

	public static Generic arctan(Generic a) {
		return new Atan(a).expressionValue();
	}

	public static Generic arccot(Generic a) {
		return new Acot(a).expressionValue();
	}

	public static Generic sinh(Generic a) {
		return new Sinh(a).expressionValue();
	}

	public static Generic cosh(Generic a) {
		return new Cosh(a).expressionValue();
	}

	public static Generic tanh(Generic a) {
		return new Tanh(a).expressionValue();
	}

	public static Generic coth(Generic a) {
		return new Coth(a).expressionValue();
	}

	public static Generic arcsinh(Generic a) {
		return new Asinh(a).expressionValue();
	}

	public static Generic arccosh(Generic a) {
		return new Acosh(a).expressionValue();
	}

	public static Generic arctanh(Generic a) {
		return new Atanh(a).expressionValue();
	}

	public static Generic arccoth(Generic a) {
		return new Acoth(a).expressionValue();
	}

	public static Generic exp(Generic a) {
		return new Exp(a).expressionValue();
	}

	public static Generic ln(Generic a) {
		return new Log(a).expressionValue();
	}

	public static Generic power(Generic a, Generic b) {
		return new Pow(a, b).expressionValue();
	}

	public static Generic vector(String name, int n) {
		return new JSCLVector(name, n);
	}

	public static Generic matrix(String name, int n, int p) {
		return new Matrix(name, n, p);
	}

	public static Generic matrix(String name, int n) {
		return new Matrix(name, n, n);
	}

	public static Generic variable(String name) {
		return new Constant(name).expressionValue();
	}

	public static Generic variable(String name, Generic subscript[]) {
		return new Constant(name, 0, subscript).expressionValue();
	}
}
