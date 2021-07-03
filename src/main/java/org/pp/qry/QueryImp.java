package org.pp.qry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.pp.qry.interfaces.ObjectIterator;
import org.pp.qry.interfaces.Query;
import org.pp.qry.interfaces.QueryContext;
/**
 * implementation of Query API
 * @author prasantsmac
 *
 * @param <T>
 */
public class QueryImp<T> implements Query<T> {
	/** declare operator cOonstant */
	private static final int OP_MUL = 0;
	private static final int OP_DIV = 1;
	private static final int OP_MOD = 2;
	private static final int OP_ADD = 3;
	private static final int OP_SUB = 4;
	private static final int OP_LT = 5;
	private static final int OP_LTE = 6;
	private static final int OP_GTE = 7;
	private static final int OP_GT = 8;
	private static final int OP_EQ = 9;
	private static final int OP_NEQ = 10;
	private static final int OP_AND = 11;
	private static final int OP_OR = 12;
	private static final int OP_BET = 13;
	private static final int OP_NBET = 14;
	private static final int OP_IN = 15;
	private static final int OP_NIN = 16;
	private static final int OP_PRN = 17;

	/** Declares operator's priority */
	private static final int PRI_PARAN = 99; // '(' expression ')'
	private static final int PRI_MATH_HIGH = 95; // '*', '/' , '%'
	private static final int PRI_MATH_LOW = 80; // '+' , '-'
	private static final int PRI_COND_OP = 75; // '>', '>=', '<', '<=', '=' , '!=', 'in', 'between'
	private static final int PRI_AND = 70; // '&&'
	private static final int PRI_OR = 65; // '||'

	/** Declare value type */
	private static final int TYPE_PRM = 7; // Type Parameter
	private static final int TYPE_OP = 6; // Type operator
	private static final int TYPE_ID = 5; // Value type is ID
	private static final int TYPE_STR = 4; // Value type is String value
	private static final int TYPE_NUM = 3; // Value type is number
	private static final int TYPE_BOOL = 2; // Value type is boolean
	private static final int TYPE_NULL = 1; // Value type is null
	private static final int TYPE_PRN = 0;

	/** Constant for debugging purpose */
	private static final String STR_ADD = "+";
	private static final String STR_SUB = "-";
	private static final String STR_MUL = "*";
	private static final String STR_DIV = "/";
	private static final String STR_MOD = "%";
	private static final String STR_GT = ">";
	private static final String STR_GTE = ">=";
	private static final String STR_LT = "<";
	private static final String STR_LTE = "<=";
	private static final String STR_EQ = "=";
	private static final String STR_NEQ = "!=";
	private static final String STR_AND = "&&";
	private static final String STR_OR = "||";
	private static final String STR_IN = "in";
	private static final String STR_NOT = "not";
	private static final String STR_NIN = "nin";
	private static final String STR_BET = "between";
	private static final String STR_NBET = "nbet";
	private static final String STR_PRN = "(";
	private static final String STR_PRN_END = ")";

	/** Root Node of expression tree */
	private Node root = null;
	/** Query Context to handle data loading */
	private QueryContext<T> ctx;
	/** Code points of SQL queries */
	private int[] codePoints = null;
	/** Hold current code pointer as well as number of operations */
	private int pos = 0;
	/** Load sequence number */
	private int lseq = 0;
	/** User parameters */
	private List<Node> params = new ArrayList<>();
	/** Parameter Index */
	private int prmIndx = 0;
	/** To build token */
	private StringBuilder sb = new StringBuilder();
	/** To help build tree (expression) */
	private Deque<Node> stk = new LinkedList<>();
	/** if data was seek already */
	private boolean seek = false;
	/** Order of traversing */
	private boolean rev = false;
	/** if seek by sort key */
	private boolean seekBySk = false;

	private QueryImp() {		
	}

	/** New instance of Query Engine */
	public QueryImp(String sql, QueryContext<T> ctx) {
		this();
		// 
		if (sql == null)
			throw new NullPointerException("Null query");
		// 
		if (ctx == null)
			throw new NullPointerException("Query context can not be null");
		// extract code points
		this.codePoints = sql.codePoints().toArray();
		this.ctx = ctx;
		compile();
	}

	@Override
	public void close() {
		ctx.reset();
		seek = seekBySk = false;
		stk = null;
		codePoints = null;
		params = null;
		root = null;
		sb = null;
	}

	@Override
	public Query<T> setParam(int val) {
		// convert to number
		MyNumber num = new MyNumber(MyNumber.INT, val);
		// convert to number node
		Node node = new Node(num, TYPE_NUM);
		// add to parameter list
		params.add(node);
	    return this;
	}

	@Override
	public Query<T> setParam(long val) {
		// convert to number
		MyNumber num = new MyNumber(MyNumber.LONG, val);
		// convert to number node
		Node node = new Node(num, TYPE_NUM);
		// add to parameter list
		params.add(node);
		return this;
	}

	@Override
	public Query<T> setParam(float val) {
		// convert to number
		MyNumber num = new MyNumber(MyNumber.FLT, val);
		// convert to number node
		Node node = new Node(num, TYPE_NUM);
		// add to parameter list
		params.add(node);
		return this;
	}

	@Override
	public Query<T> setParam(double val) {
		// convert to number
		MyNumber num = new MyNumber(MyNumber.DBL, val);
		// convert to number node
		Node node = new Node(num, TYPE_NUM);
		// add to parameter list
		params.add(node);
		return this;
	}

	@Override
	public Query<T> setParam(String val) {
		// convert to number node
		Node node = new Node(val, TYPE_STR);
		// add to parameter list
		params.add(node);
		// return reference
		return this;
	}

	@Override
	public Query<T> setParam(boolean val) {
		// convert to number node
		Node node = new Node(val, TYPE_BOOL);
		// add to parameter list
		params.add(node);
		return this;
	}
	
	@Override
	public Query<T> setParam(List<?> list) {
		// must be for between/not between operator
		if (list.size() != 2)
			tRex("Parameter list size must be 2");
		// construct a new list of arguments
		List<Object> newList = new ArrayList<>();
		Node tmp = null, node = null;
		// verify all objects
		for (Object obj : list) {
			node = Node.objToNode(obj);
			// custom object type and null is not allowed
			if (node.type == TYPE_NULL)
				tRex("Parameter list can not contains null");
			if (tmp != null && tmp.type != node.type)
				tRex("All objects in paramter list must be of same type");
			tmp = node;
			newList.add(tmp.val);
		}
		// verify if range is valid
		if (compare(tmp.type, newList.get(0), newList.get(1)) >= 0)
			tRex("Invalid range");
		// create a new node
		node = new Node(newList, tmp.type);
		// add to parameter list
		params.add(node);
		// return reference
		return this;
	}

	@Override
	public Query<T> setParam(Set<?> set) {
		// first check for valid set
		if (set == null || set.size() == 0)
			tRex("Empty or nul set");
		// new hash set
		Set<Object> newSet = new HashSet<>();
		Node tmp = null, node = null;
		// verify all objects
		for (Object obj : set) {
			node = Node.objToNode(obj);
			// custom object type and null is not allowed
			if (node.type == TYPE_NULL)
				tRex("Parameter set can not contains null");
			if (tmp != null && tmp.type != node.type)
				tRex("All objects in paramter set must be of same type");
			tmp = node;
			newSet.add(tmp.val);
		}
		// create a new node
		node = new Node(newSet, tmp.type);
		// add to parameter list
		params.add(node);
		// return reference
		return this;
	}
	
	@Override
	public Query<T> setReverseOrder(boolean rev) {
		this.rev = rev;
		return this;
	}
	
	@Override
	public T get() {
		// instantiate iterator
		ObjectIteratorImp itr = null;
		try {
			itr = new ObjectIteratorImp();
			return itr.hasNext() ? itr.next() : null;
		} finally {
			itr.close();
		}
	}

	@Override
	public List<T> list() {
		List<T> list = new ArrayList<>();
		// instantiate iterator
		ObjectIteratorImp itr = null;
		try {
			itr = new ObjectIteratorImp();
			while (itr.hasNext())
				list.add(itr.next());
		} finally {
			itr.close();
		}
		// return list
		return list;
	}

	@Override
	public ObjectIterator<T> iterator() {
		return new ObjectIteratorImp();
	}

	/**
	 * Reset query engine
	 */
	void reset() {
		ctx.reset();
		params = new ArrayList<>();
		pos = lseq = 0;
		seek = seekBySk = false;
	}

	/**
	 * Compile the query
	 */
	private void compile() {
		expr();
		// Stack size
		if (stk.size() != 1)
			tRex("Something seriously went wrong");
		// assign it to root node
		root = stk.pop();
		if (root.priority > PRI_COND_OP)
			tRex("Invalid expression, expression must be evaluated to binray value");
	}

	/**
	 * Start parsing expression
	 */
	private void expr() {
		char token = checkNextChar();
		// if parentheses start
		if (token != '(')
			operand();// get operand
		else {
			pos++;
			stk.push(new Node(OP_PRN, TYPE_PRN, PRI_PARAN));
			// call expression recursively
			expr();
			expectToken(STR_PRN_END);
			// process parentheses
			processParan();
		}
		// if operator
		operator();
	}

	/**
	 * Process parentheses
	 */
	private void processParan() {
		// remove right operand
		Node r = stk.pop();
		if (r.type == OP_PRN)
			return;
		// remove '('
		stk.pop();
		// Make it high priority
		r.priority = PRI_PARAN;
		if (!stk.isEmpty())
			buildTree(r);
	}

	/**
	 * Scan next operator
	 */
	private void operator() {
		// get basic operator
		int cPos = pos;
		// get token
		String op = nextToken();
		if (op.length() > 0) {
			// Possible operator of one length
			if (op.length() == 1) {
				switch (op) {
				case STR_GT:
					stk.push(new Node(OP_GT, TYPE_OP, PRI_COND_OP));
					break;
				case STR_LT:
					stk.push(new Node(OP_LT, TYPE_OP, PRI_COND_OP));
					break;
				case STR_EQ:
					stk.push(new Node(OP_EQ, TYPE_OP, PRI_COND_OP));
					break;
				case STR_ADD:
					stk.push(new Node(OP_ADD, TYPE_OP, PRI_MATH_LOW));
					break;
				case STR_SUB:
					stk.push(new Node(OP_SUB, TYPE_OP, PRI_MATH_LOW));
					break;
				case STR_MUL:
					stk.push(new Node(OP_MUL, TYPE_OP, PRI_MATH_HIGH));
					break;
				case STR_DIV:
					stk.push(new Node(OP_DIV, TYPE_OP, PRI_MATH_HIGH));
					break;
				case STR_MOD:
					stk.push(new Node(OP_MOD, TYPE_OP, PRI_MATH_HIGH));
					break;
				case STR_PRN_END:
					pos = cPos;
					return;
				default:
					tRexOp(cPos);
				}
			}
			// possible operator of length 2
			else if (op.length() == 2) {
				switch (op) {
				case STR_GTE:
					stk.push(new Node(OP_GTE, TYPE_OP, PRI_COND_OP));
					break;
				case STR_LTE:
					stk.push(new Node(OP_LTE, TYPE_OP, PRI_COND_OP));
					break;
				case STR_NEQ:
					stk.push(new Node(OP_NEQ, TYPE_OP, PRI_COND_OP));
					break;
				case STR_OR:
					stk.push(new Node(OP_OR, TYPE_OP, PRI_OR));
					break;
				case STR_AND:
					stk.push(new Node(OP_AND, TYPE_OP, PRI_AND));
					break;
				case STR_IN:
					processSplOp(OP_IN);
					return;
				default:
					tRexOp(cPos);
				}
			}
			// ... not between or in
			else {
				switch (op) {
				case STR_NOT:
					// Check if between or in provided or not
					cPos = pos;
					op = nextToken();
					switch (op) {
					case STR_BET:
						processSplOp(OP_NBET);
						return;
					case STR_IN:
						processSplOp(OP_NIN);
						return;
					default:
						tRex("Expecting 'between' or 'in' operator @" + cPos);
					}
					break;
				case STR_BET:
					processSplOp(OP_BET);
					return;
				case STR_NBET:
					processSplOp(OP_NBET);
					return;
				case STR_NIN:
					processSplOp(OP_NIN);
					return;
				default:
					tRexOp(cPos);
				}
			}
			// now call expression
			expr();
		}
	}

	/**
	 * Scan next string token
	 * 
	 * @return
	 */
	private String nextToken() {
		sb.setLength(0);
		for (int uPoint = -1, state = -1; pos < codePoints.length; pos++) {
			uPoint = codePoints[pos];
			switch (state) {
			   case -1 :
				   if (Character.isWhitespace(uPoint))
					   continue;
				   state = -2;				   
			   case -2 :
				   if (!Character.isWhitespace(uPoint)) {
					   sb.appendCodePoint(uPoint);
					   continue;
				   }				  
			}
			// break loop
			break;
		}
		// return token
		return sb.toString();
	}

	/**
	 * Expect next token. if not throw exception
	 * 
	 * @param exTkn
	 * @return
	 */
	private boolean expectToken(String exTkn) {
		int cPos = pos;
		if (!exTkn.equals(nextToken()))
			tRex("Excpecting '" + exTkn + "' @" + cPos);
		return true;
	}

	/**
	 * Get the next character token
	 * 
	 * @return
	 */
	private char checkNextChar() {
		for (int uPoint = -1; pos < codePoints.length; pos++) {
			uPoint = codePoints[pos];
			if (!Character.isWhitespace(uPoint))
				return (char) uPoint;
		}
		return ' ';
	}

	/**
	 * Process operator between/not between/in/not in operators
	 * 
	 * @param op
	 */
	private void processSplOp(int op) {
		stk.push(new Node(op, TYPE_OP, PRI_COND_OP));
		// Check parentheses start
		Node r = null;
		int cPos = pos;
		char ch = checkNextChar();
		// if wild card no need to proceed further
		if (ch == '?') {
			operand();
			r = stk.pop();
		} else {
			if (ch != '(')
				tRex("Expecting '(' @" + cPos);
			pos++;
			// list of right arguments
			Collection<Object> col = op < OP_IN ? new ArrayList<>() : new HashSet<>();
			// Start getting operand
			Node l = null;
			for (;;) {
				// get next operand
				operand();
				r = stk.pop();
				// id is not allowed
				if (r.type == TYPE_ID || r.type == TYPE_NULL || r.type == TYPE_PRM)
					tRex("Identifier/null/? not allowed for in/between operator @" + cPos);
				if (l != null && l.type != r.type)
					tRex("Type mismatch for in/between operator @" + cPos);
				// add to set
				l = r;
				col.add(l.val);
				try {
					// get ',' or ')'
					ch = checkNextChar();
					if (ch == ',')
						continue;
					if (ch == ')')
						break;
					else
						tRex("Expecting ',' or ')' @" + cPos);
				} finally {
					pos++;
				}
				
			}
			// check if multiple operands were provided
			if (op < OP_IN && col.size() > 2)
				tRex("More than two operands for (not) between operator @" + cPos);
			// verify list in range for between/not between operator
			if (op < OP_IN) {
				List<Object> list = (List<Object>) col;
				if (compare(l.type, list.get(0), list.get(1)) >= 0)
					tRex("Invalid range for between/not between operator @" + cPos);
			}
			// Set of Node
			r = new Node(col, l.type);
		}
		// build tree now
		buildTree(r);
		// check for relational operator's presence
		checkRelOp();
	}

	/**
	 * Relational operator check for Operator Between/In operators
	 */
	private void checkRelOp() {
		int cPos = pos;
		String token = nextToken();
		if (token.length() > 0) {
			switch (token) {
			case "&&":
				stk.push(new Node(OP_AND, TYPE_OP, PRI_AND));
				break;
			case "||":
				stk.push(new Node(OP_OR, TYPE_OP, PRI_OR));
				break;
			default:
				tRex("Expecting '&&' or '||' @" + cPos);
			}
			expr();
		}
	}

	/**
	 * Scan next operand
	 */
	private void operand() {
		// clean string builder first
		sb.setLength(0);
		// record current position
		int c = pos;
		// scan next operand
		int state = nextOperand();
		// operand Node
		Node oprnd = null;
		// state machine
		switch (state) {
		// number state
		case 0:
		case 1:
		case 2:
		case 3:
			// convert string to number now
			MyNumber mn = null;
			try {
				mn = new MyNumber(sb.toString(), state);
			} catch (NumberFormatException ne) {
				tRexOprnd(c);
			}
			oprnd = new Node(mn, TYPE_NUM);
			break;
		// String
		case 5:
			oprnd = new Node(sb.toString(), TYPE_STR);
			break;
		// ID or keyword
		case 6:
			String id = sb.toString();
			// verify not empty id
			if ("".equals(id))
				tRexOprnd(c);
			// check type
			switch (id) {
			case "true":
				oprnd = new Node(true, TYPE_BOOL);
				break;
			case "false":
				oprnd = new Node(false, TYPE_BOOL);
				break;
			case "null":
				oprnd = new Node(null, TYPE_NULL);
				break;
			case "?":
				oprnd = new Node(prmIndx++, TYPE_PRM);
				break;
			default:
				oprnd = new Node(id, TYPE_ID);
			}
			break;
		// incomplete state
		default:
			tRexOprnd(c);
		}		
		// stack is empty or its between/in (not) operator
		if (stk.isEmpty() || ((int) stk.peek().val) > OP_OR) {
			stk.push(oprnd);
			return;
		}
		// Now build tree if possible
		buildTree(oprnd);	
	}
	/**
	 * Scan next operand and return type
	 * @return
	 */
	private int nextOperand() {
		// state and type to be return
		int state = -1;
		// start processing character by character
		for (int uPoint = -1; pos < codePoints.length; pos++) {
			uPoint = codePoints[pos];
			// state machine
			switch (state) {
			// initial state
			case -1:
				if (Character.isWhitespace(uPoint))
					continue;
				// if start of string
				else if (uPoint == '\'') {
					state = 4;	
					continue;
				}
				// If start of number
				else if (isNumber(uPoint)) 
					state = 0; // default integer type
				// keyword
				else
					state = 6;				
			// String state
			case 4:
				if (uPoint == '\'') {
					pos++;
					return 5;
				}
				break;
			// keyword
			case 6:
				// stop if not 
				if (!isAlphaNumeric(uPoint)) 	
					return 6;
				break;
		    // number type
			default:
				if (uPoint == '.')
					state = 2; // float type
				// verify other type
				else if (!isNumber(uPoint)) {
					if (uPoint == 'd' || uPoint == 'D')
						state = 3; // Double type
					else if (uPoint == 'l' || uPoint == 'L')
						state = 1; // Long type
					else 
						pos--;
					// point to next
					pos++;
					return state;
				}
			}
			// keep adding code points
			sb.appendCodePoint(uPoint);
		}		
		return state;		
	}

	/**
	 * Check if the code point is number
	 * 
	 * @param uPoint
	 * @return
	 */
	private boolean isNumber(int uPoint) {
		return uPoint == '.' || (uPoint > 47 && uPoint < 58) || uPoint == '+' || uPoint == '-';
	}

	/**
	 * Check alpha numeric and acceptable special charecter
	 * 
	 * @param uPoint
	 * @return
	 */
	private boolean isAlphaNumeric(int uPoint) {
		return (uPoint > 62 && uPoint < 91) || // Upper case...
				(uPoint > 96 && uPoint < 123) || // Lower case...
				(uPoint > 47 && uPoint < 58) || // Digits
				(uPoint == 35 || uPoint == 36) || // #,$
				(uPoint == 45 || uPoint == 46) || // -, .
				(uPoint == 95); // _ (under score)
	}

	/**
	 * Build expression tree
	 * 
	 * @param r
	 */
	private void buildTree(Node r) {
		// Pop operator
		Node op = stk.pop();
		// Pop left operand or subtree
		Node l = stk.pop();

		Node tmp = l;
		while (tmp.right != null && op.priority > tmp.priority)
			tmp = tmp.right;

		Node nl = tmp.copy();
		tmp.reset(op.val, op.type, op.priority, nl, r);
		// Push resultant tree
		stk.push(l);
	}

	/**
	 * Evaluate expression
	 * 
	 * @param node
	 * @return
	 */
	private Node evaluate(Node node) {
		/**
		 * Return if terminal node
		 */
		if (node.type != TYPE_OP)
			return node;
		// visit left subtree first
		Node left = evaluate(node.left);
		// get operator
		int op = (int) node.val;
		/**
		 * if operator is '||' and left condition was satisfied than no need to visit
		 * right subtree
		 */
		if (op == OP_OR && (boolean) left.val)
			return left;
		/**
		 * If operator is '&&' and left condition was not satisfied than no need to
		 * visit right subtree
		 */
		if (op == OP_AND && !(boolean) left.val)
			return left;
		/**
		 * Visit right subtree
		 */
		Node right = evaluate(node.right);
		/**
		 * Result to be return
		 */
		Node result = null;
		/**
		 * Operation tracker. help to track where to stop
		 */
		pos++;

		/**
		 * Load records first if not loaded yet
		 */
		if (!seek && (left.type == TYPE_ID || right.type == TYPE_ID)) {
			/**
			 * Check if records can be loaded using SortKey
			 */
			if (root.priority != PRI_OR && // if root condition is not '||'
			// ensure both operand are not ID
					!(left.type == TYPE_ID && right.type == TYPE_ID)) {
				/**
				 * Check if SortKey was specified
				 */
				Node tmp = null;
				if (left.type == TYPE_ID && ctx.isSortKey((String) left.val))
					tmp = paramToNode(right, op);
				else if (right.type == TYPE_ID && ctx.isSortKey((String) right.val))
					tmp = paramToNode(left, op);

				/**
				 * if sort key was provided
				 */
				if (tmp != null) {
					// Assuming data will be seek based on sort key
					seek = seekBySk = true;
					// for '<'
					if (op == OP_LT)
						ctx.seek(rev ? tmp.val : null, rev);
					// for '<='
					else if (op == OP_LTE)
						ctx.seek(rev ? higherValue(tmp) : null, rev);
					// for '>'
					else if (op == OP_GT)
						ctx.seek(rev ? null : higherValue(tmp), rev);
					// for '>='
					else if (op == OP_GTE)
						ctx.seek(rev ? null : tmp.val, rev);
					// for '='
					else if (op == OP_EQ)
						ctx.seek(rev ? higherValue(tmp) : tmp.val, rev);
					// for between operator
					else if (op == OP_BET) {
						@SuppressWarnings("unchecked")
						List<Object> list = (List<Object>) tmp.val;
						Object sVal = rev ? higherValue(new Node(list.get(1), tmp.type)) : list.get(0);
						// convert to proper value
						if (sVal instanceof MyNumber) {
							sVal = ((MyNumber) sVal).numObj;
						}
						// seek now
						ctx.seek(sVal, rev);
					} else
						seek = seekBySk = false;
				}
			}
			/**
			 * If not seek yet. Start loading from the beginning
			 */
			if (!seek)
				ctx.seek(null, rev);
			// check if next record exist or not
			if (!ctx.nextRecord())
				throw new NoSuchElementException("No record found");
			/**
			 * At this point data is seek. mark the operation number
			 */
			lseq = pos;
			seek = true;
		}

		/**
		 * Convert ID to value node
		 */
		Node l = idToNode(left);
		Node r = idToNode(right);

		/**
		 * Convert parameter node to value node
		 */
		l = paramToNode(l, op);
		r = paramToNode(r, op);

		// If arithmetic operator '+', '-' , '/' , '*' , '%'
		if (op < OP_LT) {
			// validate type
			if (l.type != r.type || l.type != TYPE_NUM)
				tRex("Type must be number for both operand '" + left.val + " " + op + " " + right.val + "'");
			// Now get numbers
			MyNumber mnl = (MyNumber) l.val;
			MyNumber mnr = (MyNumber) r.val;
			//
			switch (op) {
			case OP_ADD:
				result = new Node(mnl.add(mnr), TYPE_NUM);
				break;
			case OP_SUB:
				result = new Node(mnl.sub(mnr), TYPE_NUM);
				break;
			case OP_MUL:
				result = new Node(mnl.mul(mnr), TYPE_NUM);
				break;
			case OP_DIV:
				result = new Node(mnl.div(mnr), TYPE_NUM);
				break;
			case OP_MOD:
				result = new Node(mnl.remainder(mnr), TYPE_NUM);
				break;
			default:
				tRex("Unknown operation type");
			}
		}
		/**
		 * Must be conditional operator
		 */
		else {
			// Return value must be boolean condition
			result = new Node(false, TYPE_BOOL);
			// For condition operator >, >=, <, <=
			if (op < OP_EQ) {
				// validate type
				if (l.type != r.type || l.val == null)
					tRex("Type mismatch or null value '" + left.val + " " + op + " " + right.val + "'");
				// do action
				switch (op) {
				case OP_GT:
					result.val = (compare(l.type, l.val, r.val) > 0) ? true : false;
					break;
				case OP_GTE:
					result.val = (compare(l.type, l.val, r.val) >= 0) ? true : false;
					break;
				case OP_LT:
					result.val = (compare(l.type, l.val, r.val) < 0) ? true : false;
					break;
				case OP_LTE:
					result.val = (compare(l.type, l.val, r.val) <= 0) ? true : false;
					break;
				default:
					tRex("Unknown operation type");
				}
			}
			// For Conditional operator = , !=
			else if (op < OP_AND) {
				/**
				 * If both are non null
				 */
				if (l.val != null && r.val != null) {
					// Check type first
					if (l.type != r.type)
						tRex("Type mismatch '" + left.val + " " + op + " " + right.val + "'");
					/**
					 * Compare both operand
					 */
					int eq = compare(l.type, l.val, r.val);
					/**
					 * For not equal to operator
					 */
					if (eq != 0 && op == OP_NEQ)
						result.val = true;
					/**
					 *  for equal operator
					 */
					else if (eq == 0 && op == OP_EQ)
						result.val = true;
				}
				/**
				 * If at least one operand is null and not equal operator
				 */
				else if (l.val != null || r.val != null)
					result.val = op == OP_NEQ ?  true : false;
				/**
				 * if both are null and operator is equal
				 */
				else if (op == OP_EQ)
					result.val = true;
			}
			// '||' or '&&' operator
			else if (op < OP_BET) {
				/**
				 * Check type, type must be boolean
				 */
				if (l.type != r.type || l.type != TYPE_BOOL)
					tRex("Type mismatch/must be of boolean type '" + left.val + " " + op + " " + right.val + "'");
				/**
				 *  For '||' operator
				 */
				if (op == OP_OR && ((boolean) l.val || (boolean) r.val))
					result.val = true;
				/**
				 *  For '&&' operator
				 */
				else if (op == OP_AND && ((boolean) l.val && (boolean) r.val))
					result.val = true;
			}
			// 'between' or 'not between'
			else if (op < OP_IN) {
				/**
				 * Type must match
				 */
				if (l.type != r.type)
					tRex("Type mismatch '" + left.val + " " + op + " " + right.val + "'");
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) r.val;
				/**
				 * If between operator check the range
				 */
				if (op == OP_BET
						&& (compare(l.type, l.val, list.get(0)) >= 0 && compare(l.type, l.val, list.get(1)) <= 0))
					result.val = true;
				/**
				 * if not between operator
				 */
				else if (op == OP_NBET
						&& (compare(l.type, l.val, list.get(0)) < 0 || compare(l.type, l.val, list.get(1)) > 0))
					result.val = true;
			}
			// 'in' or 'not in' operator
			else {
				/**
				 * Check type 
				 */
				if (l.type != r.type)
					tRex("Type mismatch '" + left.val + " " + op + " " + right.val + "'");
				/**
				 * Get set of objects
				 */
				@SuppressWarnings("unchecked")
				Set<Object> nSet = (Set<Object>) r.val;
				/**
				 * Verify set contains the left operand
				 */
				if (op == OP_IN && nSet.contains(l.val))
					result.val = true;
				/**
				 * for not in 
				 */
				else if (op == OP_NIN && !nSet.contains(l.val))
					result.val = true;
			}

			/**
			 * If condition was not satisfied and the data was loaded using SortKey If so we
			 * are done, no further data loading is required
			 */
			if (!(boolean) result.val && pos == lseq && seekBySk)
				ctx.reset();
		}
		return result;
	}

	// Convert ID to Value node
	private Node idToNode(Node n) {
		if (n.type == TYPE_ID) {
			// convert ID to value node now
			Object obj = ctx.getFieldValue((String) n.val);
			n = Node.objToNode(obj);
		}
		return n;
	}

	// Convert parameter Node to value node
	private Node paramToNode(Node n, int op) {
		if (n.type == TYPE_PRM) {
			int index = (int) n.val;
			if (index >= params.size())
				throw new RuntimeException("Missing parameter at index : " + (index + 1));
			n = params.get(index);
		}
		return n;
	}

	/**
	 * Get higher value
	 * 
	 * @param n
	 * @return
	 */
	private Object higherValue(Node n) {
		Object v = null;
		switch (n.type) {
		// if number type
		case TYPE_NUM:
			MyNumber num = (MyNumber) n.val;
			switch (num.type) {
			case MyNumber.INT:
				v = num.numObj.intValue() + 1;
				break;
			case MyNumber.LONG:
				v = num.numObj.longValue() + 1;
				break;
			case MyNumber.FLT:
				v = Math.nextUp(num.numObj.floatValue());
				break;
			case MyNumber.DBL:
				v = Math.nextUp(num.numObj.doubleValue());
				break;
			}
			break;
		case TYPE_STR:
			// convert string char array
			char[] chars = ((String) n.val).toCharArray();
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
			v = sbLocal.toString();
			break;
		// boolean type
		case TYPE_BOOL:
			boolean val = (boolean) n.val;
			v = val ? val : true;
			break;
		case TYPE_NULL:
			throw new RuntimeException("Null is not allowed for > operator");
		default:
			throw new RuntimeException("Unsupported type");
		}
		return v;
	}

	/**
	 * Compare two node value
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	private int compare(int type, Object n1, Object n2) {
		switch (type) {
		// if number type
		case TYPE_NUM:
			MyNumber l = (MyNumber) n1;
			MyNumber r = (MyNumber) n2;
			return l.compareTo(r);
		// if String type
		case TYPE_STR:
			String ls = (String) n1;
			String rs = (String) n2;
			return ls.compareTo(rs);
		// if boolean
		case TYPE_BOOL:
			Boolean lb = (Boolean) n1;
			Boolean rb = (Boolean) n2;
			return lb.compareTo(rb);
		// must be custom object
		default:
			throw new RuntimeException("Unsupported type");
		}
	}
	
	/**
	 * Object Iterator implementation
	 * 
	 * @author prasantsmac
	 *
	 * @param <T>
	 */
	private final class ObjectIteratorImp implements ObjectIterator<T> {
		// iterator close indicator
		private boolean close = false;

		@Override
		public boolean hasNext() {
			// if iterator closed already
			status();
			try {
				/**
				 * If data was not seek than increment to next record. loop until condition was
				 * not satisfied till end
				 */
				while (!seek || ctx.nextRecord()) {
					pos = 0; // new evaluation indicator
					Node tmp = evaluate(root);
					// if no seek, break the loop and return false
					if (!seek)
						throw new NoSuchElementException();
					// if match found
					if ((boolean) tmp.val)
						return true;
				}
			} catch (Throwable t) {
				close();
				throw t;
			}
			// close iterator
			close();
			return false;
		}

		@Override
		public T update() {
			return status() ? ctx.updateCurrent() : null;
		}

		@Override
		public T remove() {
			return status() ? ctx.deleteCurrent() : null;
		}

		@Override
		public T next() {
			return status() ? ctx.currentRecord() : null;
		}

		@Override
		public void close() {
			if (!close) {
				reset();
				close = true;
			}			
		}

		/**
		 * Check iterator status
		 * 
		 * @return
		 */
		private boolean status() {
			if (close)
				throw new RuntimeException("Iterator closed already");
			return true;
		}

	}

	/**
	 * Expression tree node
	 * 
	 * @author prasantsmac
	 *
	 */
	private static final class Node {
		/** terminal or non terminal value */
		private Object val;
		/** operator priority */
		private int priority = -1;
		/** node type value */
		private int type = -1;
		/** Left/right sub tree pointer */
		private Node left, right;

		// constructors
		private Node(Object val, int type) {
			this.val = val;
			this.type = type;
		}

		private Node(Object val, int type, int priority) {
			this(val, type);
			this.priority = priority;
		}

		// redefine node
		private void reset(Object val, int type, int priority, Node l, Node r) {
			this.left = l;
			this.right = r;
			this.val = val;
			this.type = type;
			this.priority = priority;
		}

		// clone Node
		private Node copy() {
			Node node = new Node(this.val, this.type, this.priority);
			node.left = this.left;
			node.right = this.right;
			return node;
		}

		// Object to node mapping
		private static Node objToNode(Object obj) {
			if (obj == null)
				return new Node(obj, TYPE_NULL);
			if (obj instanceof String)
				return new Node(obj, TYPE_STR);
			if (obj instanceof Integer)
				return new Node(new MyNumber(MyNumber.INT, obj), TYPE_NUM);
			if (obj instanceof Long)
				return new Node(new MyNumber(MyNumber.LONG, obj), TYPE_NUM);
			if (obj instanceof Float)
				return new Node(new MyNumber(MyNumber.FLT, obj), TYPE_NUM);
			if (obj instanceof Double)
				return new Node(new MyNumber(MyNumber.DBL, obj), TYPE_NUM);
			if (obj instanceof Boolean)
				return new Node(obj, TYPE_BOOL);
			else
				throw new RuntimeException("Unknow data type");
		}

		@Override
		public String toString() {
			if (val == null)
				return "null";
			else if (type != TYPE_OP)
				return val.toString();
			else {
				int v = (int) val;
				switch (v) {
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
					tRex("Unknown operator");
				}
			}
			return null;
		}
	}

	private static void tRex(String msg) {
		throw new RuntimeException(msg);
	}

	private static void tRexOprnd(int curPos) {
		throw new RuntimeException("Expecting number/identifier/string" + "/'null'/'true'/'false'/'?' @" + curPos);
	}

	private static void tRexOp(int curPos) {
		throw new RuntimeException("Expecting operators (e.g. >, <, >=, <= ...etc) @" + curPos);
	}	
}
