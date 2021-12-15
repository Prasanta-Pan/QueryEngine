package org.pp.qry;

import org.pp.qry.interfaces.Node;

class BooleanNode extends AbstractNode {
	/**
	 * Boolean value
	 */
	private boolean val;
	/**
	 * 
	 * @param val
	 */
	BooleanNode(boolean val) {
		super.type = TYPE_BOOL;
		this.val = val;
	}
	
	@Override
	public Node eq(Node othr, boolean validate) {
		// during validation 
		if (validate && isValidRel(othr)) 
			return new BooleanNode(true);
		// in runtime
		else if (!validate && othr instanceof BooleanNode) {
			// cast to boolean type
			BooleanNode o = (BooleanNode) othr;
			// return the result
			return new BooleanNode(val == o.val);
		}
		// or throw error
		return super.eq(othr, validate);
	}

	@Override
	public Node neq(Node othr, boolean validate) {
		// during validation 
		if (validate && isValidRel(othr)) 
			return new BooleanNode(true);
		// in runtime
		else if (!validate && othr instanceof BooleanNode) {
			// cast to boolean type
			BooleanNode o = (BooleanNode) othr;
			// return the result
			return new BooleanNode(val != o.val);
		}
		// or throw error
		return super.neq(othr, validate);
	}

	@Override
	public Node and(Node othr, boolean validate) {
		// during validation 
		if (validate && isValidRel(othr)) 
			return new BooleanNode(true);
		// in runtime
		else if (!validate && othr instanceof BooleanNode) {
			// cast to boolean type
			BooleanNode o = (BooleanNode) othr;
			// return the result
			return new BooleanNode(val && o.val);
		}
		// or throw error
		return super.and(othr, validate);
	}

	@Override
	public Node or(Node othr, boolean validate) {
		// during validation 
		if (validate && isValidRel(othr)) 
			return new BooleanNode(true);
		// in runtime
		else if (!validate && othr instanceof BooleanNode) {
			// cast to boolean type
			BooleanNode o = (BooleanNode) othr;
			// return the result
			return new BooleanNode(val || o.val);
		}
		// or throw error
		return super.or(othr, validate);
	}

	@Override
	public Object value() {
		// TODO Auto-generated method stub
		return val;
	}

	@Override
	public Object higherValue() {
		// TODO Auto-generated method stub
		return val ? val : !val;
	}

	@Override
	public boolean boolValue() {
		// TODO Auto-generated method stub
		return val;
	}

	@Override
	public String toString() {
		return "BooleanNode@[Value: " + val + ", Priority: " + priority + "]";
	}
}
