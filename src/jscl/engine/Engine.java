package jscl.engine;

import java.io.Reader;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import jscl.math.Expression;
import jscl.math.Function;
import jscl.math.Generic;
import jscl.math.JSCLVector;
import jscl.text.ParseException;

public class Engine extends AbstractScriptEngine {
	private final ScriptEngineFactory factory;

	public Engine(final ScriptEngineFactory factory) {
		this.factory = factory;
	}

	public ScriptEngineFactory getFactory() {
		return factory;
	}

	public Bindings createBindings() {
		return new SimpleBindings();
	}

	public Object eval(final String script, final ScriptContext context) throws ScriptException {
		try {
			final Generic expr = Expression.valueOf(script).expand();
			if (expr instanceof Function) {
				return Graph.apply((Function) expr);
			} else if (expr instanceof JSCLVector) {
				Generic a[] = ((JSCLVector) expr).elements();
				Function s[] = new Function[a.length];
				boolean flag = true;
				for(int i=0;i<a.length;i++) {
					if (a[i] instanceof Function) {
						s[i] = (Function) a[i];
					} else {
						flag = false;
					}
				}
				if (flag) {
					return Graph.apply(s);
				}
			}
			return expr;
		} catch (final ParseException e) {
			throw new ScriptException(e);
		}
	}

	public Object eval(final Reader reader, final ScriptContext context) {
		throw new UnsupportedOperationException();
	}
}
