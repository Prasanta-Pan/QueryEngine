package org.pp.qry;

import org.pp.qry.interfaces.Node;

class ParameterNode extends AbstractNode {
	/**
	 * Parameter index value
	 */
	private int index;
	
	/**
	 * Create a parameter node
	 * @param index
	 */
	ParameterNode(int index) {
		this.index = index;
		this.type = TYPE_PRM;
	}

	@Override
	public boolean isParameter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object value() {
		// TODO Auto-generated method stub
		return index;
	}
	
	@Override
	public int intValue() {
		return index;
	}
	
	@Override
	public Node eq(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate ? new BooleanNode(true) : super.eq(othr, validate);
	}

	@Override
	public Node neq(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate ? new BooleanNode(true) : super.neq(othr, validate);
	}

	@Override
	public Node gt(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && isValidCond(othr) ?
			    new BooleanNode(true) : super.gt(othr, validate);
	}

	@Override
	public Node ge(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && isValidCond(othr) ?
			    new BooleanNode(true) : super.ge(othr, validate);
	}

	@Override
	public Node lt(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && isValidCond(othr) ?
			    new BooleanNode(true) : super.lt(othr, validate);
	}

	@Override
	public Node le(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && isValidCond(othr) ?
			    new BooleanNode(true) : super.le(othr, validate);
	}

	@Override
	public Node and(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && isValidRel(othr) ? 
					new BooleanNode(true) : super.and(othr, validate);
	}

	@Override
	public Node or(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && isValidRel(othr) ? 
					new BooleanNode(true) : super.or(othr, validate);
	}

	@Override
	public Node bet(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && (othr instanceof ListNode ||
							othr instanceof ParameterNode) ? 
				new BooleanNode(true) : super.bet(othr, validate);
	}

	@Override
	public Node nbet(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && (othr instanceof ListNode ||
							othr instanceof ParameterNode) ? 
				new BooleanNode(true) : super.nbet(othr, validate);
	}

	@Override
	public Node in(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && (othr instanceof SetNode || 
							othr instanceof ParameterNode) ?
				new BooleanNode(true) : super.in(othr, validate);
	}

	@Override
	public Node nin(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && (othr instanceof SetNode || 
							othr instanceof ParameterNode) ?
				new BooleanNode(true) : super.nin(othr, validate);
	}

	@Override
	public Node plus(Node othr, boolean validate) {
		// during validation
		if(validate) {
			// if string type
			if (othr instanceof StringNode)
				return new StringNode("");
			// if number type and other acceptable types
			if (isValidArith(othr))
				return new IntNode(0);			
		} 
		// else throw error
		return super.plus(othr, validate);
	}

	@Override
	public Node minus(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && isValidArith(othr) ?
					new IntNode(0) : super.minus(othr, validate);
	}

	@Override
	public Node mul(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && isValidArith(othr) ?
					new IntNode(0) : super.mul(othr, validate);
	}

	@Override
	public Node div(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && isValidArith(othr) ?
					new IntNode(0) : super.div(othr, validate);
	}

	@Override
	public Node rem(Node othr, boolean validate) {
		// TODO Auto-generated method stub
		return validate && isValidArith(othr) ?
					new IntNode(0) : super.rem(othr, validate);
	}
	
	@Override
	public String toString() {
		return "ParameterNode@[Index: " + index + ", Priority: " + priority + "]";
	}
}
