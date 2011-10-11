package jscl.math;

public class ExpressionVariable extends GenericVariable {
        public ExpressionVariable(Generic generic) {
                super(generic);
        }

        public Generic substitute(Variable variable, Generic generic) {
                if(isIdentity(variable)) return generic;
                else return content.substitute(variable,generic);
        }

        public Generic elementary() {
                return content.elementary();
        }

        public Generic simplify() {
                return content.simplify();
        }

        public String toString() {
                StringBuffer buffer=new StringBuffer();
                buffer.append("(").append(content).append(")");
                return buffer.toString();
        }

        public String toJava() {
                StringBuffer buffer=new StringBuffer();
                buffer.append("(").append(content.toJava()).append(")");
                return buffer.toString();
        }

    public String toMathML(Object data) {
	StringBuffer b = new StringBuffer();
        int exponent=data instanceof Integer?((Integer)data).intValue():1;
        if(exponent==1) b.append(bodyToMathML());
        else {
		b.append("<msup>");
		b.append(bodyToMathML());
		b.append("<mn>" + String.valueOf(exponent) + "</mn>");
		b.append("</msup>");
        }
	return b.toString();
    }

    String bodyToMathML() {
	return "<mfenced>" + content.toMathML(null) + "</mfenced>";
    }

    protected Variable newinstance() {
        return new ExpressionVariable(null);
    }
}
