package org.pp.qry;

import java.util.Set;

import org.pp.qry.interfaces.Node;

/**
 * To support IN/NOT-IN operator
 * 
 * @author prasantsmac
 *
 */
class SetNode extends AbstractNode {
	/**
	 * Backing set of values
	 */
	private Set<Node> set;
    /**
     * Indicate if its a number set
     */
	private boolean number = false;
	/**
	 * Create a set node
	 * 
	 * @param set
	 */
	SetNode(Set<Node> set, boolean nummber) {
       this.set = set;
       this.number = nummber;
	}
	
	public boolean isNumberNode() {
		return number;
	}

	@Override
	public Object value() {
		// TODO Auto-generated method stub
		return set;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SetNode@[{");
		// get value string
		for (Node node : set)
			sb.append(node.value() + ",");
		// remove last comma
		sb.deleteCharAt(sb.length() - 1);
		// append priority value as well
		sb.append("}, Priority: " + priority + "]");
		// return result
		return sb.toString();
	}
}
