package jscl.math.polynomial;

import jscl.math.Generic;
import jscl.math.Variable;

class NestedPolynomial extends UnivariatePolynomial {
    NestedPolynomial(Variable variable[]) {
        this(variable[0],PolynomialWrapper.factory(variable));
    }

    NestedPolynomial(Variable variable, Generic coefFactory) {
        super(variable,coefFactory);
    }

    protected UnivariatePolynomial newinstance() {
        return new NestedPolynomial(variable,coefFactory);
    }
}
