package org.pp.qry;

import org.pp.qry.interfaces.Node;

class AbstractNode implements Node {
    /**
     * Node type
     */
	protected int type;
	/**
	 * Node priority
	 */
	protected int priority = -1;
	
	/**
	 * Return a concrete Node implementation
	 * @param val
	 * @return
	 */
	static Node toNode(Object val) {
		// return null node
		if (val == null)
			return new NullNode();
		// return String Node
		if (val instanceof String)
			return new StringNode((String) val);
		// return boolean node
		if (val instanceof Boolean) 
			return new BooleanNode((boolean) val);
		// if number instance
		if (val instanceof Number) {
			// if integer instance
			if (val instanceof Integer)
				return new IntNode((int) val);
			// if long instance
			if (val instanceof Long)
				return new LongNode((long) val);
			// if float instance
			if (val instanceof Float)
				return new FloatNode((float) val);
			// if double instance
			if (val instanceof Double)
				return new DoubleNode((double) val);
			// no other number type is supported
			else
				throw new RuntimeException("Unsupported number type: " + val);
		}
		// if everything else fail...
		else
			throw new RuntimeException("Unsupported data type: " + val);
	}
	
	/**
	 * Check acceptable type for conditional operator during validation
	 * @param othr
	 * @return
	 */
	protected boolean isValidCond(Node othr) {
		return (othr instanceof NumberNode || 
				 othr instanceof StringNode ||
				 othr instanceof ParameterNode ||
				 othr instanceof IDNode);
	}
	
	/**
	 * Check acceptable type for relational operator during validation
	 * @param othr
	 * @return
	 */
	protected boolean isValidRel(Node othr) {
		return (othr instanceof BooleanNode ||
				 othr instanceof ParameterNode ||
				 othr instanceof IDNode);
	}
	
	/**
	 * Check acceptable type for arithmetic operator during validation
	 * @param othr
	 * @return
	 */
	protected boolean isValidArith(Node othr) {
		return (othr instanceof NumberNode ||
				 othr instanceof ParameterNode ||
				 othr instanceof IDNode);
	}	
		
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return priority;
	}

	@Override
	public Node left() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Node right() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setPriority(int priority) {
		// TODO Auto-generated method stub
		this.priority = priority;
	}
	
	@Override
	public boolean isID() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOperator() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isParameter() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isNull() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public void setLeft(Node left) {
		throw new UnsupportedOperationException("setLeft() not supported by this node: " + toString());
	}
	
	@Override 
	public void setRight(Node right) {
		throw new UnsupportedOperationException("setRight() not supported by this node: " + toString());
	}
	
	@Override
	public int compare(Node othr) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("compare method is not implemneted by this node: " + toString());
	}

	@Override
	public Object value() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("value method is not implemneted by this node: " + toString());
	}

	@Override
	public Object higherValue() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("higherValue method is not implemneted by this node: " + toString());
	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("intValue method is not implemneted by this node: " + toString());
	}

	@Override
	public boolean boolValue() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("boolValue method is not implemneted by this node: " + toString());
	}

	@Override
	public String stringValue() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("stringValue method is not implemneted by this node: " + toString());
	}	
	
	@Override
	public Node eq(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '=' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node neq(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '!=' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node gt(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '>' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node ge(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '>=' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node lt(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '<' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node le(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '<=' is undefined for datatypes, left: " + toString() + ", toString: " + othr.toString());
	}

	@Override
	public Node and(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '&&' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node or(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '||' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node bet(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator 'between' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node nbet(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator 'not between' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node in(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator 'in' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node nin(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator 'not in' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node plus(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '+' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node minus(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '-' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node mul(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '*' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node div(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '/' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}

	@Override
	public Node rem(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Operator '%' is undefined for datatypes, left: " + toString() + ", right: " + othr.toString());
	}	
}
