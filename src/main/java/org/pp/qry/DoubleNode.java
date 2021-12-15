package org.pp.qry;

import org.pp.qry.interfaces.Node;

class DoubleNode extends NumberNode {
	/**
	 * Backing double value
	 */
	private double val;
	
    /**
     * Setup Double node
     * @param val
     */
	DoubleNode (double val) {
		this.val = val;
		this.type = TYPE_NUM;
		this.numType = DBL;
	}
	/**
	 * Parse string and generate double
	 * @param val
	 */
	DoubleNode (String val) {
		this.val = Double.parseDouble(val);
		this.type = TYPE_NUM;
		this.numType = DBL;
	}	

	@Override
	public int compare(Node othr) {
		// cast to number node
		NumberNode o = (NumberNode) othr;
		// must raise to double
		double oval = o.doubleValue();
		// compare and return result
		return val != oval ? val > oval ? 1 : -1 : 0;			
		
	}
	
	@Override
	protected NumberNode add(NumberNode o) {
		// cast to double
		double oval = o.doubleValue();
		// return result
		return new DoubleNode(val + oval);
	}
	
	@Override
	protected NumberNode sub(NumberNode o) {
		// cast to double
		double oval = o.doubleValue();
		// return result
		return new DoubleNode(val - oval);
	}
	
	@Override
	protected NumberNode mul(NumberNode o) {
		// cast to double
		double oval = o.doubleValue();
		// return result
		return new DoubleNode(val * oval);
	}
	
	@Override
	protected NumberNode div(NumberNode o) {
		// cast to double
		double oval = o.doubleValue();
		// return result
		return new DoubleNode(val / oval);
	}
	
	@Override
	protected NumberNode rem(NumberNode o) {
		// cast to double
		double oval = o.doubleValue();
		// return result
		return new DoubleNode(val % oval);
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
		return (float) val;
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
		return "DoubleNode@[Value: " + val + ", Priority: " + priority + "]";
	}
}
