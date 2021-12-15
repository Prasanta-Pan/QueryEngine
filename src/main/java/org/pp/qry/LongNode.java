package org.pp.qry;

import org.pp.qry.interfaces.Node;

class LongNode extends NumberNode {
    /**
     * Backing long value
     */
	private long val;
	
	/**
	 * Create a new Long Node
	 * @param val
	 */
	LongNode(long val) {
		this.val = val;
		this.type = TYPE_NUM;
		this.numType = LNG;
	}
	/**
	 * Parse String to long value and create long node
	 * @param val
	 */
	LongNode(String val) {
		this.val = Long.parseLong(val);
		this.type = TYPE_NUM;
		this.numType = LNG;
	}

	@Override
	public int compare(Node othr) {
		// cast to number type
		NumberNode o = (NumberNode) othr;
		// raise to appropriate type
		switch (o.numType) {
		    // raise to long type
			case INT :
			case LNG :
				long oval = o.longValue();
				return val != oval ? val > oval ? 1 : -1 : 0;
			// otherwise raised both to double type
			default :
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return dnewVal != doval ? dnewVal > doval ? 1 : -1 : 0;		
		}
		
	}
	
	@Override
	protected NumberNode add(NumberNode o) {
		// raise to appropriate type
		switch (o.numType) {
		    // raise to long type
			case INT :
			case LNG :
				long oval = o.longValue();
				return new LongNode(val + oval);
			// otherwise raised both to double type
			default :
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal + doval);	
		}
	}
	
	@Override
	protected NumberNode sub(NumberNode o) {
		// raise to appropriate type
		switch (o.numType) {
		    // raise to long type
			case INT :
			case LNG :
				long oval = o.longValue();
				return new LongNode(val - oval);
			// otherwise raised both to double type
			default :
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal - doval);	
		}
	}
	
	@Override
	protected NumberNode mul(NumberNode o) {
		// raise to appropriate type
		switch (o.numType) {
		    // raise to long type
			case INT :
			case LNG :
				long oval = o.longValue();
				return new LongNode(val * oval);
			// otherwise raised both to double type
			default :
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal * doval);	
		}
	}
	
	@Override
	protected NumberNode div(NumberNode o) {
		// raise to appropriate type
		switch (o.numType) {
		    // raise to long type
			case INT :
			case LNG :
				long oval = o.longValue();
				return new LongNode(val / oval);
			// otherwise raised both to double type
			default :
				double doval = o.doubleValue();
				double dnewVal = (double) val;
				return new DoubleNode(dnewVal / doval);	
		}
	}
	
	@Override
	protected NumberNode rem(NumberNode o) {
		// raise to appropriate type
		switch (o.numType) {
		    // raise to long type
			case INT :
			case LNG :
				long oval = o.longValue();
				return new LongNode(val % oval);
			// otherwise raised both to double type
			default :
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
		return (int) val;
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
		return (int) val;
	}
		
	@Override
	public String toString() {
		return "LongNode@[Value: " + val + ", Priority: " + priority + "]";
	}
	
}
