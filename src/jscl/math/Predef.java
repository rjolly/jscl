package jscl.math;

import jscl.math.function.*;
import jscl.math.function.trigonometric.*;
import jscl.math.function.hyperbolic.*;
import jscl.math.operator.*;
import jscl.math.numeric.*;

public class Predef {
	public static final Generic pi=Constant.pi;
	public static final Generic oo=Constant.infinity;

	public static ImplicitFunction.Curried function(String name, int derivation) {
		return ImplicitFunction.apply(name, new int[] {derivation});
	}

	public static ImplicitFunction.Curried[] function(String name, int derivation, int n) {
		return ImplicitFunction.apply(name, new int[] {derivation}, n);
	}

	public static ImplicitFunction.Curried function(String name, int derivation[]) {
		return ImplicitFunction.apply(name, derivation);
	}

	public static ImplicitFunction.Curried[] function(String name, int derivation[], int n) {
		return ImplicitFunction.apply(name, derivation, n);
	}

	public static Generic[] root(Generic parameter[]) {
		return Root.apply(parameter);
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

	public static Generic[] vector(String name, int prime, int n) {
		return new JSCLVector(name, prime, n).elements();
	}

	public static Generic[] vector(String name, int n) {
		return new JSCLVector(name, 0, n).elements();
	}

	public static Generic[][] matrix(String name, int n, int p) {
		return new Matrix(name, 0, n, p).elements();
	}

	public static Generic[][] matrix(String name, int n) {
		return new Matrix(name, 0, n, n).elements();
	}

	public static Generic variable(String name) {
		return new Constant(name).expressionValue();
	}

	public static Generic variable(String name, int prime) {
		return new Constant(name, prime).expressionValue();
	}

	public static Generic integral(Generic expression, Generic variable) {
		return new IndefiniteIntegral(expression, variable).expressionValue();
	}

	public static Generic integral(Generic expression, Generic variable, Generic n1, Generic n2) {
		return new Integral(expression, variable, n1, n2).expressionValue();
	}

	public static Generic d(Generic expression, Generic variable) {
		return new Derivative(expression, variable, variable, JSCLInteger.valueOf(1)).expressionValue();
	}

	public static Generic d(Generic expression, Generic variable, Generic value) {
		return new Derivative(expression, variable, value, JSCLInteger.valueOf(1)).expressionValue();
	}

	public static Generic d(Generic expression, Generic variable, Generic value, Generic order) {
		return new Derivative(expression, variable, value, order).expressionValue();
	}

	public static Generic modint(int content, int modulo) {
		return ModularInteger.valueOf(content, modulo);
	}

	public static Generic integer(String str) {
		return JSCLInteger.valueOf(str);
	}

	public static Generic rational(String n, String d) {
		return Rational.valueOf(n, d);
	}

	public static Generic real(double val) {
		return new NumericWrapper(JSCLDouble.valueOf(val));
	}

	public static Generic complex(double real, double imag) {
		return new NumericWrapper(Complex.valueOf(real, imag));
	}

	public static Generic bool(boolean value) {
		return JSCLBoolean.valueOf(value);
	}

	public static Generic vector(Generic element[]) {
		return new JSCLVector(element);
	}

	public static Generic matrix(Generic element[][]) {
		return new Matrix(element);
	}

	public static Object graph(Generic a) {
		return Graph.apply(a.expand());
	}

	public static Object graph(Generic a[]) {
		return Graph.apply(new JSCLVector(a).expand());
	}

	public static Generic function(Generic expression, Generic variable) {
		return new jscl.math.operator.Graph(expression, variable).expressionValue();
	}

	public static Generic elementary(Generic expression) {
		return new Elementary(expression).expressionValue();
	}

	public static Generic factorize(Generic expression) {
		return new Factorize(expression).expressionValue();
	}

	public static Generic simplify(Generic expression) {
		return new Simplify(expression).expressionValue();
	}

	public static Generic numeric(Generic expression) {
		return new jscl.math.operator.Numeric(expression).expressionValue();
	}

	public static Generic quote(Generic expression) {
		return new Quote(expression).expressionValue();
	}

	public static Generic sum(Generic expression, Generic variable, Generic n1, Generic n2) {
		return new Sum(expression, variable, n1, n2).expressionValue();
	}

	public static Generic product(Generic expression, Generic variable, Generic n1, Generic n2) {
		return new Product(expression, variable, n1, n2).expressionValue();
	}

	public static Generic limit(Generic expression, Generic variable, Generic limit) {
		return new Limit(expression, variable, limit, JSCLInteger.valueOf(0)).expressionValue();
	}

	public static Generic limit(Generic expression, Generic variable, Generic limit, Generic direction) {
		return new Limit(expression, variable, limit, direction).expressionValue();
	}

	public static Generic factorial(Generic expression) {
		return new Factorial(expression).expressionValue();
	}
}
