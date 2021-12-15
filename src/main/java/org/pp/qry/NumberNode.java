package org.pp.qry;

import java.util.List;
import java.util.Set;

import org.pp.qry.interfaces.Node;

abstract class NumberNode extends AbstractNode {
    /**
     * Number type
     * INT 		- 0
     * LONG 	- 1
     * Float 	- 2
     * Double 	- 3
     */
	protected int numType = -1;
	
	static final int INT = 0;
	static final int LNG = 1;
	static final int FLT = 2;
	static final int DBL = 3;
	/**
	 * Return double value
	 * @return
	 */
	protected abstract double doubleValue();
	/**
	 * Return float value 
	 * @return
	 */
	protected abstract float floatValue();
	/**
	 * Return long value
	 * @return
	 */
	protected abstract long longValue();
	/**
	 * Add 2 number nodes and return result
	 * @param o
	 * @return
	 */
	protected abstract NumberNode add(NumberNode o);
    /**
     * Subtract 2 number nodes and return result
     * @param o
     * @return
     */
	protected abstract NumberNode sub(NumberNode o);
	/**
	 * Multiply 2 number nodes and return result
	 * @param o
	 * @return
	 */
	protected abstract NumberNode mul(NumberNode o);
	/**
	 * Divide 2 number nodes and return result
	 * @param o
	 * @return
	 */
	protected abstract NumberNode div(NumberNode o);
    /**
     *  2 number nodes and return result
     * @param o
     * @return
     */
	protected abstract NumberNode rem(NumberNode o);
		
	@Override
	public Node plus(Node othr, boolean validate) {
		// return true if valid operation
		if (validate) {
			// if String type
			if (othr instanceof StringNode)
				return new StringNode("");
			// if number or other acceptable type
			if (isValidArith(othr))
				return new IntNode(0);
		}			
		// in runtime 
		if (!validate) {
			// if number type
			if (othr instanceof NumberNode) {
				// cast to number node
				NumberNode o = (NumberNode) othr;
				// return result
				return add(o);
			}
			// if String type 
			if (othr instanceof StringNode)
				return new StringNode("" + value() + othr.value());
		}
		// else throw error
		return super.plus(othr, validate);
	}

	@Override
	public Node minus(Node othr, boolean validate) {
		// return true if valid operation
		if (validate && isValidArith(othr))
			return new IntNode(0);
		// in runtime
		if (!validate && othr instanceof NumberNode) {
			// cast to number node
			NumberNode o = (NumberNode) othr;
			// return result
			return sub(o);
		}
		// else throw error
		return super.minus(othr, validate);
	}

	@Override
	public Node mul(Node othr, boolean validate) {
		// return true if valid operation
		if (validate && isValidArith(othr))
			return new IntNode(0);
		// in runtime
		if (!validate && othr instanceof NumberNode) {
			// cast to number node
			NumberNode o = (NumberNode) othr;
			// return result
			return mul(o);
		}
		// else throw error
		return super.mul(othr, validate);
	}

	@Override
	public Node div(Node othr, boolean validate) {
		// return true if valid operation
		if (validate && isValidArith(othr))
			return new IntNode(0);
		// in runtime
		if (!validate && othr instanceof NumberNode) {
			// cast to number node
			NumberNode o = (NumberNode) othr;
			// return result
			return div(o);
		}
		// else throw error
		return super.div(othr, validate);
	}

	@Override
	public Node rem(Node othr, boolean validate) {
		// return true if valid operation
		if (validate && isValidArith(othr))
			return new IntNode(0);
		// in runtime
		if (!validate && othr instanceof NumberNode) {
			// cast to number node
			NumberNode o = (NumberNode) othr;
			// return result
			return rem(o);
		}
		// else throw error
		return super.rem(othr, validate);
	}
	
	@Override
	public Node eq(Node othr, boolean validate) {
		// return true if valid operation
		if (validate && isValidArith(othr))
			return new BooleanNode(true);
		// in runtime
		if (!validate && othr instanceof NumberNode) {
			// return result
			return new BooleanNode(compare(othr) == 0);
		}
		// else throw error
		return super.eq(othr, validate);	
	}

	@Override
	public Node neq(Node othr, boolean validate) {
		// return true if valid operation
		if (validate && isValidArith(othr))
			return new BooleanNode(true);
		// in runtime
		if (!validate && othr instanceof NumberNode) {
			// return result
			return new BooleanNode(compare(othr) != 0);
		}
		// else throw error
		return super.eq(othr, validate);	
	}

	@Override
	public Node gt(Node othr, boolean validate) {
		// return true if valid operation
		if (validate && isValidArith(othr))
			return new BooleanNode(true);
		// in runtime
		if (!validate && othr instanceof NumberNode) {
			// return result
			return new BooleanNode(compare(othr) > 0);
		}
		// else throw error
		return super.gt(othr, validate);	
	}

	@Override
	public Node ge(Node othr, boolean validate) {
		// return true if valid operation
		if (validate && isValidArith(othr))
			return new BooleanNode(true);
		// in runtime
		if (!validate && othr instanceof NumberNode) {
			// return result
			return new BooleanNode(compare(othr) >= 0);
		}
		// else throw error
		return super.ge(othr, validate);	
	}

	@Override
	public Node lt(Node othr, boolean validate) {
		// return true if valid operation
		if (validate && isValidArith(othr))
			return new BooleanNode(true);
		// in runtime
		if (!validate && othr instanceof NumberNode) {
			// return result
			return new BooleanNode(compare(othr) < 0);
		}
		// else throw error
		return super.lt(othr, validate);	
	}

	@Override
	public Node le(Node othr, boolean validate) {
		// return true if valid operation
		if (validate && isValidArith(othr))
			return new BooleanNode(true);
		// in runtime
		if (!validate && othr instanceof NumberNode) {
			// return result
			return new BooleanNode(compare(othr) <= 0);
		}
		// else throw error
		return super.le(othr, validate);	
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Node bet(Node othr, boolean validate) {
		// during validation
		if (validate) {
			// valid 
			if (othr instanceof ParameterNode)
				return new BooleanNode(true);
			// ensure nodes in set are number type
			if (othr instanceof ListNode) {
				// cast to list node
				ListNode o = (ListNode) othr;
				// get list of Node
				List<Node> list = (List<Node>) o.value();
				// check if Nodes are number type
				if (list.get(0) instanceof NumberNode) {
					return new BooleanNode(true);
				}
			}
		}
		// in runtime
		if (!validate && othr instanceof ListNode) {
			// cast to list node
			ListNode o = (ListNode) othr;
			// get list of Node
			List<Node> list = (List<Node>) o.value();
			// check if Nodes are number type
			if (list.get(0) instanceof NumberNode) {
				// compare now
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
		if (validate) {
			// valid 
			if (othr instanceof ParameterNode)
				return new BooleanNode(true);
			// ensure nodes in set are number type
			if (othr instanceof ListNode) {
				// cast to list node
				ListNode o = (ListNode) othr;
				// get list of Node
				List<Node> list = (List<Node>) o.value();
				// check if Nodes are number type
				if (list.get(0) instanceof NumberNode) {
					return new BooleanNode(true);
				}
			}
		}
		// in runtime
		if (!validate && othr instanceof ListNode) {
			// cast to list node
			ListNode o = (ListNode) othr;
			// get list of Node
			List<Node> list = (List<Node>) o.value();
			// check if Nodes are number type
			if (list.get(0) instanceof NumberNode) {
				// compare now
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
		if (validate) {
			// valid 
			if (othr instanceof ParameterNode)
				return new BooleanNode(true);
			// ensure nodes in set are number type
			if (othr instanceof SetNode) {
				// cast to set node
				SetNode o = (SetNode) othr;
				// check if Nodes are number type
				if (o.isNumberNode())
					return new BooleanNode(true);
			}
		}
		// in runtime
		if (!validate && othr instanceof SetNode) {
			// cast to set node
			SetNode o = (SetNode) othr;
			// get set of Nodes
			Set<Node> set = (Set<Node>) o.value();
			// check if Nodes are number type
			if (o.isNumberNode()) {
				// compare now
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
		if (validate) {
			// valid 
			if (othr instanceof ParameterNode)
				return new BooleanNode(true);
			// ensure nodes in set are number type
			if (othr instanceof SetNode) {
				// cast to set node
				SetNode o = (SetNode) othr;
				// check if Nodes are number type
				if (o.isNumberNode())
					return new BooleanNode(true);
			}
		}
		// in runtime
		if (!validate && othr instanceof SetNode) {
			// cast to set node
			SetNode o = (SetNode) othr;
			// get set of Nodes
			Set<Node> set = (Set<Node>) o.value();
			// check if Nodes are number type
			if (o.isNumberNode()) {
				// compare now
				return !set.contains(this) ?
					 new BooleanNode(true) : new BooleanNode(false);				
			}
		}
		// either throw exception
		return super.nin(othr, validate);
	}
	
	@Override
	public boolean equals(Object o) {
		// must of number type
		if (o instanceof NumberNode) {
			return compare((Node) o) == 0 ? true : false;
		}
		// false otherwise 
		return false;
	}
		
}
