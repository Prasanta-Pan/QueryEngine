package org.pp.qry;

import java.util.List;

import org.pp.qry.interfaces.Node;
/**
 * To support BETWEEN/NOT-BETWEEN operator
 * @author prasantsmac
 *
 */
class ListNode extends AbstractNode {
	/**
	 * Backing list of nodes (2 only)
	 */
    private List<Node> list;
    
    /**
     * Create a List Node object
     * @param list
     */
    ListNode(List<Node> list) {
    	this.list = list;
    	this.type = list.get(0).getType();
    }	

	@Override
	public Object value() {
		// TODO Auto-generated method stub
		return list;
	}	
	
	@Override
	public String toString()  {
		StringBuilder sb = new StringBuilder("ListNode@[{");
		// get value string
		for (Node node : list)
			sb.append(node.value() + ",");
		// remove last comma
		sb.deleteCharAt(sb.length() - 1);
		// append priority value as well
		sb.append("}, Priority: " + priority + "]");
		// return result
		return sb.toString();
	}

}
