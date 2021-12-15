package org.pp.qry;

import org.pp.qry.interfaces.Node;

class OperatorNode extends AbstractNode {
	/**
	 * Backing operator value
	 */
    private int opVal;
    /**
     * Left node
     */
    private Node left;
    /**
     * Right node
     */
    private Node right;    
    
    /**
     * Create a operator node
     * @param val
     * @param priority
     */
	public OperatorNode(int val, int type, int priority) {
		opVal = val;
		this.type = type;
		this.priority = priority;
	}	

	@Override
	public Node left() {
		// TODO Auto-generated method stub
		return left;
	}
	
	@Override
	public void setLeft(Node left) {
		this.left = left;
	}

	@Override
	public Node right() {
		// TODO Auto-generated method stub
		return right;
	}	
	
	@Override
	public void setRight(Node right) {
		// TODO Auto-generated method stub
		this.right = right;
	}

	@Override
	public boolean isOperator() {
		// TODO Auto-generated method stub
		return true;
	}	

	@Override
	public Object value() {
		// TODO Auto-generated method stub
		return opVal;
	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return opVal;
	}

	@Override
	public void setPriority(int priority) {
		// TODO Auto-generated method stub
		this.priority = priority;
	}
	
	private String opString() {
		switch (opVal) {
			case OP_GT:
				return STR_GT;
			case OP_LT:
				return STR_LT;
			case OP_GTE:
				return STR_GTE;
			case OP_LTE:
				return STR_LTE;
			case OP_EQ:
				return STR_EQ;
			case OP_NEQ:
				return STR_NEQ;
			case OP_AND:
				return STR_AND;
			case OP_OR:
				return STR_OR;
			case OP_ADD:
				return STR_ADD;
			case OP_SUB:
				return STR_SUB;
			case OP_MUL:
				return STR_MUL;
			case OP_DIV:
				return STR_DIV;
			case OP_MOD:
				return STR_MOD;
			case OP_IN:
				return STR_IN;
			case OP_BET:
				return STR_BET;
			case OP_PRN:
				return STR_PRN;
			case OP_NIN:
				return STR_NOT + " " + STR_IN;
			case OP_NBET:
				return STR_NOT + " " + STR_BET;
			default:
				throw new RuntimeException("Unknown operator");
		}
	}
	
	@Override
	public String toString() {
		return "OperatorNode@[Value:" + opString() + ", Priority:" + priority + "]";
	}
	
	
}
