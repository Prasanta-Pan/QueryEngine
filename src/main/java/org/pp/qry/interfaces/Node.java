package org.pp.qry.interfaces;

public interface Node {
	/** declare operator cOonstant */
	public static final int OP_MUL 
							= 0; 	// '*'
	public static final int OP_DIV  
							= 1;	// '/'
	public static final int OP_MOD 
							= 2;	// '%'
	public static final int OP_ADD 
							= 3;	// '+'
	public static final int OP_SUB 
							= 4;	// '-'
	public static final int OP_LT 
							= 5;	// '<'
	public static final int OP_LTE 
							= 6;	// '<='
	public static final int OP_GTE 
							= 7;	// '>='
	public static final int OP_GT  
							= 8;	// '>'
	public static final int OP_EQ 
							= 9;	// '='
	public static final int OP_NEQ 
							= 10;	// '!='
	public static final int OP_AND 
							= 11;	// '&&'
	public static final int OP_OR 
							= 12;	// '||'
	public static final int OP_BET 
							= 13;	// 'bet' || 'between'
	public static final int OP_NBET 
							= 14;	// 'not between' || 'nbet'
	public static final int OP_IN 
							= 15;	// 'in' 
	public static final int OP_NIN 
							= 16;	// 'not in' || 'nin'
	public static final int OP_PRN 
							= 17;	// '(' || ')'

	/** Declares operator's priority */
	public static final int PRI_PARAN 
							= 99; // '(' expression ')'
	public static final int PRI_MATH_HIGH 
							= 95; // '*', '/' , '%'
	public static final int PRI_MATH_LOW 
							= 80; // '+' , '-'
	public static final int PRI_COND_OP 
							= 75; // '>', '>=', '<', '<=', '=' , '!=', 'in', 'between'
	public static final int PRI_AND 
							= 70; // '&&'
	public static final int PRI_OR 
							= 65; // '||'

	/** Declare value type */
	public static final int TYPE_PRM 
							= 7; // Type Parameter
	public static final int TYPE_OP 
							= 6; // Type operator
	public static final int TYPE_ID 
							= 5; // Value type is ID
	public static final int TYPE_STR 
							= 4; // Value type is String value
	public static final int TYPE_NUM 
							= 3; // Value type is number
	public static final int TYPE_BOOL 
							= 2; // Value type is boolean
	public static final int TYPE_NULL 
							= 1; // Value type is null
	public static final int TYPE_PRN 
							= 0;

	/** Constant for debugging purpose */
	public static final String STR_ADD 
						= "+";
	public static final String STR_SUB 
						= "-";
	public static final String STR_MUL 
						= "*";
	public static final String STR_DIV 
						= "/";
	public static final String STR_MOD 
						= "%";
	public static final String STR_GT 
						= ">";
	public static final String STR_GTE 
						= ">=";
	public static final String STR_LT 
						= "<";
	public static final String STR_LTE 
						= "<=";
	public static final String STR_EQ 
						= "=";
	public static final String STR_NEQ 
						= "!=";
	public static final String STR_AND 
						= "&&";
	public static final String STR_OR 
						= "||";
	public static final String STR_IN 
						= "in";
	public static final String STR_NOT 
						= "not";
	public static final String STR_NIN 
						= "nin";
	public static final String STR_BET 
						= "between";
	public static final String STR_NBET 
						= "nbet";
	public static final String STR_PRN 
						= "(";
	public static final String STR_PRN_END 
						= ")";
    /**
     * Get Node type
     * @return
     */
	public int getType();
	/**
	 * Get Node priority
	 * @return
	 */
	public int getPriority();
	/**
	 * Get left Node
	 * @return
	 */
	public Node left();
	/**
	 * Get right Node
	 * @return
	 */
	public Node right();
	/**
	 * Set right node
	 * @param node
	 */
	public void setRight(Node node);
	/**
	 * Set left node
	 * @param node
	 */
	public void setLeft(Node node);
	/**
	 * Perform equal action
	 * @param othr
	 * @return
	 */
	public Node eq(Node othr, boolean validate);
	/**
	 * Perform not equal action
	 * @param othr
	 * @return
	 */
	public Node neq(Node othr, boolean validate);
    /**
     * Perform greater than action
     * @param othr
     * @return
     */
	public Node gt(Node othr, boolean validate);
	/**
	 * Perform greater than equal to action
	 * @param othr
	 * @return
	 */
	public Node ge(Node othr, boolean validate);
	/**
	 * Perform less than action
	 * @param othr
	 * @return
	 */
	public Node lt(Node othr, boolean validate);
	/**
	 * Perform less than and equal action
	 * @param othr
	 * @return
	 */
	public Node le(Node othr, boolean validate);
	/**
	 * Perform && action
	 * @param othr
	 * @return
	 */
	public Node and(Node othr, boolean validate);
	/**
	 * perform || action
	 * @param othr
	 * @return
	 */
	public Node or(Node othr, boolean validate);
	/**
	 * Perform between action
	 * @param othr
	 * @return
	 */
	public Node bet(Node othr, boolean validate);
	/**
	 * Perform not between action
	 * @param othr
	 * @return
	 */
	public Node nbet(Node othr, boolean validate);
	/**
	 * Perform IN action
	 * @param othr
	 * @return
	 */
	public Node in(Node othr, boolean validate);
	/**
	 * Perform not in action
	 * @param othr
	 * @return
	 */
	public Node nin(Node othr, boolean validate);
	/**
	 * Perform plus action
	 * @param othr
	 * @return
	 */
	public Node plus(Node othr, boolean validate);
	/**
	 * Perform minus action
	 * @param othr
	 * @return
	 */
	public Node minus(Node othr, boolean validate);
	/**
	 * Perform multiplication action
	 * @param othr
	 * @return
	 */
	public Node mul(Node othr, boolean validate);
    /**
     * Perform Division action
     * @param othr
     * @return
     */
	public Node div(Node othr, boolean validate);
	/**
	 * Perform remainder action
	 * @param othr
	 * @return
	 */
	public Node rem(Node othr, boolean validate);
	/**
	 * Compare with another node
	 * @param othr
	 * @return
	 */
	public int compare(Node othr);
	/**
	 * If it is ID
	 * @return
	 */
	public boolean isID();
	/**
	 * if operator node
	 * @return
	 */
	public boolean isOperator();
	/**
	 * If parameter node
	 * @return
	 */
	public boolean isParameter();
	/**
	 * Return true if this node represent 
	 * a null value, otherwise false.
	 * @return
	 */
	public boolean isNull();
	/**
	 * Standard value
	 * @return
	 */
	public Object value();
	/**
	 * Get Higher value
	 * @return
	 */
	public Object higherValue();
	/**
	 * Return operator INT value
	 * @return
	 */
	public int intValue();
	/**
	 * Get Boolean value
	 * @return
	 */
	public boolean boolValue();
	/**
	 * Get String value
	 * @return
	 */
	public String stringValue();
	/**
	 * Set new priority of the node
	 * @param priority
	 */
	public void setPriority(int priority);	
}
