package jscl.math;

import org.jdesktop.swingx.JXGraph;

public class Graph extends JXGraph.Plot {
	private final Function f;

	private Graph(final Function f) {
		this.f = f;
	}

	public double compute(final double value) {
		return f.apply(value);
	}

	public static Object apply(Generic expr) {
		if (expr instanceof Function) {
			return new Graph((Function) expr);
		} else if (expr instanceof JSCLVector) {
			Generic a[] = ((JSCLVector) expr).elements();
			Graph s[] = new Graph[a.length];
			for(int i=0;i<a.length;i++) {
				if (a[i] instanceof Function) {
					s[i] = new Graph((Function) a[i]);
				} else throw new NotFunctionException();
			}
			return s;
		} else throw new NotFunctionException();
	}
}
