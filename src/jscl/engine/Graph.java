package jscl.engine;

import jscl.math.Function;
import org.jdesktop.swingx.JXGraph;

public class Graph extends JXGraph.Plot {
	private final Function f;

	private Graph(final Function f) {
		this.f = f;
	}

	public double compute(final double value) {
		return f.apply(value);
	}

	public static Graph[] apply(final Function ... fs) {
		final Graph gs[] = new Graph[fs.length];
		for (int i=0;i<fs.length;i++) gs[i] = new Graph(fs[i]);
		return gs;
	}
}
