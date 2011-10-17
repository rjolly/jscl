/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jscl.math;

import jscl.math.function.*;
import jscl.math.function.trigonometric.*;
import jscl.math.function.hyperbolic.*;

/**
 *
 * @author raphael
 */
public class Predef {
	public static final Generic pi=Constant.pi;
	public static final Generic oo=Constant.infinity;

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

	public static Generic power(Generic a, Generic b) {
		return new Pow(a, b).expressionValue();
	}

	public static Generic variable(String name) {
		return new Constant(name).expressionValue();
	}

	public static Generic variable(String name, Generic subscript[]) {
		return new Constant(name, 0, subscript).expressionValue();
	}

	public static Generic elementary(Generic a) {
		return a.elementary();
	}

	public static Generic expand(Generic a) {
		return a.expand();
	}

	public static Generic factorize(Generic a) {
		return a.factorize();
	}

	public static Generic numeric(Generic a) {
		return a.numeric();
	}

	public static Generic simplify(Generic a) {
		return a.simplify();
	}
}
