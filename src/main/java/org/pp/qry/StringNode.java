package org.pp.qry;

import java.util.List;
import java.util.Set;

import org.pp.qry.interfaces.Node;

class StringNode extends AbstractNode {
    /**
     * Backing String value
     */
	private String val;
	
	/**
	 * Create a new String Node
	 * @param val
	 */
	StringNode(String val) {
		this.val = val;
		this.type = TYPE_STR;
	}
	
	/**
	 * Check acceptable data type during validation
	 * @param othr
	 * @return
	 */
	private boolean isValid(Node othr) {
		return (othr instanceof StringNode ||
				 othr instanceof NullNode ||
				 othr instanceof ParameterNode ||
				 othr instanceof IDNode) ? true : false;
	}
	
	/**
	 * Check valid data type for String condition
	 * @param othr
	 * @return
	 */
	private boolean isValidStringCond(Node othr) {
		return (othr instanceof StringNode ||
				 othr instanceof ParameterNode ||
				 othr instanceof IDNode) ? true : false;
	}
	
	@Override
	public Node eq(Node othr, boolean validate) {
		// during validation
		if (validate && isValid(othr))
				return new BooleanNode(true);
		// real action 
		if (!validate) {
			// if null node
			if (othr instanceof NullNode)
			   return new BooleanNode(false);
			// if String 
			if (othr instanceof StringNode)
		       return new BooleanNode(compare(othr) == 0);			
		}
		// else return error
		return super.eq(othr, validate);
	}

	@Override
	public Node neq(Node othr, boolean validate) {
		// during validation
		if (validate && isValid(othr))
				return new BooleanNode(true);
		// in runtime
		if (!validate) {
			// if null node
			if (othr instanceof NullNode)
			   return new BooleanNode(true);
			// if String 
			if (othr instanceof StringNode)
		       return new BooleanNode(compare(othr) != 0);			
		}		
		// else return error
		return super.neq(othr, validate);
	}

	@Override
	public Node gt(Node othr, boolean validate) {
		// during validation
		if (validate && isValidStringCond(othr))
					return new BooleanNode(true);
		
		// in real action, compare strings or return error
		return !validate && othr instanceof StringNode ? 
				new BooleanNode(compare(othr) > 0) :super.gt(othr, validate);
	}

	@Override
	public Node ge(Node othr, boolean validate) {
		// during validation
		if (validate && isValidStringCond(othr))
			return new BooleanNode(true);
		
		// in real action, compare strings or return error
		return !validate && othr instanceof StringNode ? 
			new BooleanNode(compare(othr) >= 0) : super.ge(othr, validate);
	}

	@Override
	public Node lt(Node othr, boolean validate) {
		// during validation
		if (validate && isValidStringCond(othr))
			return new BooleanNode(true);
		
		// in real action, compare strings or return error
		return !validate && othr instanceof StringNode ? 
				new BooleanNode(compare(othr) < 0) :super.lt(othr, validate);
	}

	@Override
	public Node le(Node othr, boolean validate) {
		// during validation
		if (validate && isValidStringCond(othr))
			return new BooleanNode(true);
		
		// in real action, compare strings or return error
		return !validate && othr instanceof StringNode ? 
				new BooleanNode(compare(othr) <= 0) :super.le(othr, validate);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Node bet(Node othr, boolean validate) {
		// during validation
		if (validate && othr instanceof ListNode) {
			// cast to ListNode
			ListNode o = (ListNode) othr;
			// get list of node
			List<Node> list = (List<Node>) o.value();
			// ensure the list contains only StringNode instances
			return list.get(0) instanceof StringNode ? 
				   new BooleanNode(true) : new BooleanNode(false);
		}		
		// ensure we are dealing with List node
		if (!validate && othr instanceof ListNode) {
			// cast to ListNode
			ListNode o = (ListNode) othr;
			// get list of node
			List<Node> list = (List<Node>) o.value();
			// ensure all note types are String
			if (list.get(0) instanceof StringNode) {
				// compare strings and return result
				return (compare(list.get(0)) >= 0 && compare(list.get(1)) <= 0) ? 
					new BooleanNode(true) : new BooleanNode(false);
			}
						
		}
		// either throw exception
		return super.bet(othr, validate);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Node nbet(Node othr, boolean validate) {
		// during validation
		if (validate && othr instanceof ListNode) {
			// cast to ListNode
			ListNode o = (ListNode) othr;
			// get list of node
			List<Node> list = (List<Node>) o.value();
			// ensure the list contains only StringNode instances
			return list.get(0) instanceof StringNode ? 
				   new BooleanNode(true) : new BooleanNode(false);
		}		
		// ensure we are dealing with List node
		if (!validate && othr instanceof ListNode) {
			// cast to ListNode
			ListNode o = (ListNode) othr;
			// get list of node
			List<Node> list = (List<Node>) o.value();
			// ensure all note types are String
			if (list.get(0) instanceof StringNode) {
				// compare strings and return result
				return (compare(list.get(0)) < 0 || compare(list.get(1)) > 0) ? 
					new BooleanNode(true) : new BooleanNode(false);
			}
						
		}
		// either throw exception
		return super.nbet(othr, validate);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Node in(Node othr, boolean validate) {
		// during validation	
		if (validate && othr instanceof SetNode) {
			// cast to set node
			SetNode o = (SetNode) othr;
			// verify if all nodes are string type
			if (!o.isNumberNode())
				return new BooleanNode(true);
		}	
		// ensure we are dealing with set of nodes
		if (!validate && othr instanceof SetNode) {
			// cast to set node
			SetNode o = (SetNode) othr;
			// verify if all nodes are string type
			if (!o.isNumberNode()) {
				// get corresponding set of nodes
				Set<Node> set = (Set<Node>) o.value();
				// check if value is present
				return set.contains(this) ? 
					new BooleanNode(true) : new BooleanNode(false);
			}
			
		}
		// either throw exception
		return super.in(othr, validate);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Node nin(Node othr, boolean validate) {
		// during validation	
		if (validate && othr instanceof SetNode) {
			// cast to set node
			SetNode o = (SetNode) othr;
			// verify if all nodes are string type
			if (!o.isNumberNode())
				return new BooleanNode(true);
		}	
		// ensure we are dealing with set of nodes
		if (!validate && othr instanceof SetNode) {
			// cast to set node
			SetNode o = (SetNode) othr;
			// verify if all nodes are string type
			if (!o.isNumberNode()) {
				// get corresponding set of nodes
				Set<Node> set = (Set<Node>) o.value();
				// check if value is present
				return !set.contains(this) ? 
					new BooleanNode(true) : new BooleanNode(false);
			}
			
		}
		// either throw exception
		return super.nin(othr, validate);
	}

	@Override
	public Node plus(Node othr, boolean validate) {
		// add string value of other node	
		return new StringNode(val + othr.value() + "");
	}

	@Override
	public int compare(Node othr) {
		// compare two strings
		StringNode o = (StringNode) othr;
		// return result
		return val.compareTo(o.val);		
	}

	@Override
	public Object value() {
		// TODO Auto-generated method stub
		return val;
	}

	@Override
	public Object higherValue() {
		// convert string char array
		char[] chars = val.toCharArray();
		// copy entire chars except last to string builder
		int len = chars.length - 1;
		StringBuilder sbLocal = new StringBuilder();
		// copy all chars
		for (int i = 0; i < len; i++)
			sbLocal.append(chars[i]);
		// check if their is room to increment
		if (chars[len] < Character.MAX_VALUE) {
			chars[len] = (char) (chars[len] + 1);
			sbLocal.append(chars[len]);
		} else { // if not append a minimum character value
			sbLocal.append(chars[len]);
			sbLocal.append(Character.MIN_VALUE);
		}
		return sbLocal.toString();
	}
	
	@Override
	public int hashCode() {
		return val.hashCode();
	}
	
	@Override
	public boolean equals(Object othr) {
		// ensure we are dealing with right node
		if (othr instanceof StringNode) {
			StringNode o = (StringNode) othr;
			return val.equals(o.val);
		}
		// throw error
		return super.equals(othr);
	}
	
	@Override
	public String toString() {
		return "StringNode@[Value: " + val + ", Priority: " + priority + "]";
	}
}
