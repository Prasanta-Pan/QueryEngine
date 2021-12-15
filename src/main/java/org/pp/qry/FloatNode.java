package org.pp.qry;

import org.pp.qry.interfaces.Node;

class FloatNode extends NumberNode {
    /**
     * Backing float value
     */
	private float val;
	
	/**
	 * Setup float node
	 * @param val
	 */
	FloatNode(float val) {
		this.val = val;
		this.type = TYPE_NUM;
		this.numType = FLT;
	}
	/**
	 * Parse string and generate float value
	 * @param val
	 */
	FloatNode(String val) {
		this.val = Float.parseFloat(val);
		this.type = TYPE_NUM;
		this.numType = FLT;
	}

	@Override
	public int compare(Node othr) {
		// cast to number
		NumberNode o = (NumberNode) othr;
		switch (o.numType) {
			// raised other to float
			case FLT :
			case INT :
				float oval = o.floatValue();
				return val != oval ? val > oval ? 1 : -1 : 0;
			// otherwise raise both type to double
			default :
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return dnewVal != doval ? dnewVal > doval ? 1 : -1 : 0;
		}		
	}
	
	@Override
	protected NumberNode add(NumberNode o) {
		// TODO Auto-generated method stub
		switch (o.numType) {
			// raised other to float
			case FLT:
			case INT:
				float oval = o.floatValue();
				return new FloatNode(val + oval);
			// otherwise raise both type to double
			default:
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal + doval);
		}
	}
	
	@Override
	protected NumberNode sub(NumberNode o) {
		// TODO Auto-generated method stub
		switch (o.numType) {
			// raised other to float
			case FLT:
			case INT:
				float oval = o.floatValue();
				return new FloatNode(val - oval);
			// otherwise raise both type to double
			default:
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal - doval);
		}
	}
	
	@Override
	protected NumberNode mul(NumberNode o) {
		// TODO Auto-generated method stub
		switch (o.numType) {
			// raised other to float
			case FLT:
			case INT:
				float oval = o.floatValue();
				return new FloatNode(val * oval);
			// otherwise raise both type to double
			default:
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal * doval);
		}
	}
	
	@Override
	protected NumberNode div(NumberNode o) {
		// TODO Auto-generated method stub
		switch (o.numType) {
			// raised other to float
			case FLT:
			case INT:
				float oval = o.floatValue();
				return new FloatNode(val / oval);
			// otherwise raise both type to double
			default:
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal / doval);
		}
	}
	
	@Override
	protected NumberNode rem(NumberNode o) {
		// TODO Auto-generated method stub
		switch (o.numType) {
			// raised other to float
			case FLT:
			case INT:
				float oval = o.floatValue();
				return new FloatNode(val % oval);
			// otherwise raise both type to double
			default:
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal % doval);
		}
	}

	@Override
	public Object value() {
		// TODO Auto-generated method stub
		return val;
	}

	@Override
	public Object higherValue() {
		// TODO Auto-generated method stub
		return Math.nextUp(val);
	}

	@Override
	protected double doubleValue() {
		// TODO Auto-generated method stub
		return val;
	}

	@Override
	protected float floatValue() {
		// TODO Auto-generated method stub
		return val;
	}

	@Override
	protected long longValue() {
		// TODO Auto-generated method stub
		return (long) val;
	}
	
	@Override
	public int hashCode() {
		return (int) val;
	}
		
	@Override
	public String toString() {
		return "FloatNode@[Value: " + val + ", Priority: " + priority + "]";
	}
	
}
