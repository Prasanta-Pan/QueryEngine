package org.pp.qry;

import org.pp.qry.interfaces.Node;

class IntNode extends NumberNode {
	/**
	 * Backing INT value
	 */
	private int val;
	
	/**
	 * Setup a integer value node
	 * @param val
	 */
	IntNode(int val) {
		this.val = val;
		this.type = TYPE_NUM;
		this.numType = INT;
	}	
	/**
	 * Parse integer and setup a value node
	 * @param val
	 */
	IntNode(String val) {
		this.val = Integer.parseInt(val);
		this.type = TYPE_NUM;
		this.numType = INT;
	}
	
	@Override
	public int compare(Node othr) {
		// cast to number node
		NumberNode o = (NumberNode) othr;
		// if both INT.Which is most likely the case
		switch (o.numType) {
			// if other type is integer
			case INT:
				int oval = o.intValue();
				return val != oval ? val > oval ? 1 : -1 : 0;
			// if other type is long
			case LNG:
				long loval = o.longValue();
				long lnewVal = (long) val;
				return lnewVal != loval ? lnewVal > loval ? 1 : -1 : 0;
			// if other type is float
			case FLT:
				float foval = o.floatValue();
				float newVal = (float) val;
				return newVal != foval ? newVal > foval ? 1 : -1 : 0;
			// it must be double than
			default:
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return dnewVal != doval ? dnewVal > doval ? 1 : -1 : 0;
		}		
		
	}
	
	@Override
	protected NumberNode add(NumberNode o) {
		// check the other type and make appropriate action
		switch (o.numType) {
			// if other type is integer
			case INT:
				int oval = o.intValue();
				return new IntNode(val + oval);
			// if other type is long
			case LNG:
				long loval = o.longValue();
				long lnewVal = (long) val;
				return new LongNode(loval + lnewVal);
			// if other type is float
			case FLT:
				float foval = o.floatValue();
				float fnewVal = (float) val;
				return new FloatNode(foval + fnewVal);
			// it must be double than
			default:
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal + doval);
		}	
		
	}
	
	@Override
	protected NumberNode sub(NumberNode o) {
		// check the other type and make appropriate action
		switch (o.numType) {
			// if other type is integer
			case INT:
				int oval = o.intValue();
				return new IntNode(val - oval);
			// if other type is long
			case LNG:
				long loval = o.longValue();
				long lnewVal = (long) val;
				return new LongNode(lnewVal - loval);
			// if other type is float
			case FLT:
				float foval = o.floatValue();
				float fnewVal = (float) val;
				return new FloatNode(fnewVal - foval);
			// it must be double than
			default:
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal - doval);
		}
	}
	
	@Override
	protected NumberNode mul(NumberNode o) {
		// check the other type and make appropriate action
		switch (o.numType) {
			// if other type is integer
			case INT:
				int oval = o.intValue();
				return new IntNode(val * oval);
			// if other type is long
			case LNG:
				long loval = o.longValue();
				long lnewVal = (long) val;
				return new LongNode(lnewVal * loval);
			// if other type is float
			case FLT:
				float foval = o.floatValue();
				float fnewVal = (float) val;
				return new FloatNode(fnewVal * foval);
			// it must be double than
			default:
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal * doval);
		}
	}
	
	@Override
	protected NumberNode div(NumberNode o) {
		// check the other type and make appropriate action
		switch (o.numType) {
			// if other type is integer
			case INT:
				int oval = o.intValue();
				return new IntNode(val / oval);
			// if other type is long
			case LNG:
				long loval = o.longValue();
				long lnewVal = (long) val;
				return new LongNode(lnewVal / loval);
			// if other type is float
			case FLT:
				float foval = o.floatValue();
				float fnewVal = (float) val;
				return new FloatNode(fnewVal / foval);
			// it must be double than
			default:
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal / doval);
		}
	}
	
	@Override
	protected NumberNode rem(NumberNode o) {
		// check the other type and make appropriate action
		switch (o.numType) {
			// if other type is integer
			case INT:
				int oval = o.intValue();
				return new IntNode(val % oval);
			// if other type is long
			case LNG:
				long loval = o.longValue();
				long lnewVal = (long) val;
				return new LongNode(lnewVal % loval);
			// if other type is float
			case FLT:
				float foval = o.floatValue();
				float fnewVal = (float) val;
				return new FloatNode(fnewVal % foval);
			// it must be double than
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
		return val + 1;
	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return val;
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
		return val;
	}
	
	@Override
	public int hashCode() {
		return val;
	}	
	
	@Override
	public String toString() {
		return "IntNode@[Value: " + val + ",Priority: " + priority + "]";
	}

}
