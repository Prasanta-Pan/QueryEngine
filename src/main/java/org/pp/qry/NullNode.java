package org.pp.qry;

import org.pp.qry.interfaces.Node;

class NullNode extends AbstractNode {
     
	/**
	 * Create a Null Node
	 */
	NullNode() {
		this.type = TYPE_NULL;
	}		

	/**
	 * Check if it is acceptable valid type
	 * @param othr
	 * @return
	 */
	private boolean isValid(Node othr) {
		return (othr instanceof StringNode || 
				 othr instanceof ParameterNode || 
				 othr instanceof IDNode ||
				 othr instanceof NullNode);
	}
	
	@Override
	public Node eq(Node othr, boolean validate) {
		// during validation
		if (validate && isValid(othr))
				new BooleanNode(true);
		// in runtime
		if (!validate) {
			// if null node 
			if (othr instanceof NullNode)
				return new BooleanNode(true);
			// if string node
			if (othr instanceof StringNode)
				return new BooleanNode(false);
		}		
		// throw exception ...
		return super.eq(othr, validate);
	}

	@Override
	public Node neq(Node othr, boolean validate) {
		// during validation
		if (validate && isValid(othr))
				new BooleanNode(true);
		// in runtime
		if (!validate) {
			// if null node 
			if (othr instanceof NullNode)
				return new BooleanNode(false);
			// if string node
			if (othr instanceof StringNode)
				return new BooleanNode(true);
		}		
		// throw exception ...
		return super.neq(othr, validate);
	}	

	@Override
	public Object value() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isNull() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public String toString() {
		return "NullNode@[Value: " + null + ", Priority: " + priority + "]";
	}

}
