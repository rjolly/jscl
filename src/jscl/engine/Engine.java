package jscl.engine;

import java.io.Reader;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import jscl.math.Expression;
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
			return Expression.valueOf(script);
		} catch (final ParseException e) {
			throw new ScriptException(e);
		}
	}

	public Object eval(final Reader reader, final ScriptContext context) {
		throw new UnsupportedOperationException();
	}
}
