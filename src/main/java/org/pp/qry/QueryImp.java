package org.pp.qry;

import static org.pp.qry.AbstractNode.toNode;
import static org.pp.qry.NumberNode.DBL;
import static org.pp.qry.NumberNode.FLT;
import static org.pp.qry.NumberNode.INT;
import static org.pp.qry.NumberNode.LNG;
import static org.pp.qry.interfaces.Node.OP_ADD;
import static org.pp.qry.interfaces.Node.OP_AND;
import static org.pp.qry.interfaces.Node.OP_BET;
import static org.pp.qry.interfaces.Node.OP_DIV;
import static org.pp.qry.interfaces.Node.OP_EQ;
import static org.pp.qry.interfaces.Node.OP_GT;
import static org.pp.qry.interfaces.Node.OP_GTE;
import static org.pp.qry.interfaces.Node.OP_IN;
import static org.pp.qry.interfaces.Node.OP_LT;
import static org.pp.qry.interfaces.Node.OP_LTE;
import static org.pp.qry.interfaces.Node.OP_MOD;
import static org.pp.qry.interfaces.Node.OP_MUL;
import static org.pp.qry.interfaces.Node.OP_NBET;
import static org.pp.qry.interfaces.Node.OP_NEQ;
import static org.pp.qry.interfaces.Node.OP_NIN;
import static org.pp.qry.interfaces.Node.OP_OR;
import static org.pp.qry.interfaces.Node.OP_PRN;
import static org.pp.qry.interfaces.Node.OP_SUB;
import static org.pp.qry.interfaces.Node.PRI_AND;
import static org.pp.qry.interfaces.Node.PRI_COND_OP;
import static org.pp.qry.interfaces.Node.PRI_MATH_HIGH;
import static org.pp.qry.interfaces.Node.PRI_MATH_LOW;
import static org.pp.qry.interfaces.Node.PRI_OR;
import static org.pp.qry.interfaces.Node.PRI_PARAN;
import static org.pp.qry.interfaces.Node.STR_ADD;
import static org.pp.qry.interfaces.Node.STR_AND;
import static org.pp.qry.interfaces.Node.STR_BET;
import static org.pp.qry.interfaces.Node.STR_DIV;
import static org.pp.qry.interfaces.Node.STR_EQ;
import static org.pp.qry.interfaces.Node.STR_GT;
import static org.pp.qry.interfaces.Node.STR_GTE;
import static org.pp.qry.interfaces.Node.STR_IN;
import static org.pp.qry.interfaces.Node.STR_LT;
import static org.pp.qry.interfaces.Node.STR_LTE;
import static org.pp.qry.interfaces.Node.STR_MOD;
import static org.pp.qry.interfaces.Node.STR_MUL;
import static org.pp.qry.interfaces.Node.STR_NBET;
import static org.pp.qry.interfaces.Node.STR_NEQ;
import static org.pp.qry.interfaces.Node.STR_NIN;
import static org.pp.qry.interfaces.Node.STR_NOT;
import static org.pp.qry.interfaces.Node.STR_OR;
import static org.pp.qry.interfaces.Node.STR_PRN_END;
import static org.pp.qry.interfaces.Node.STR_SUB;
import static org.pp.qry.interfaces.Node.TYPE_NUM;
import static org.pp.qry.interfaces.Node.TYPE_OP;
import static org.pp.qry.interfaces.Node.TYPE_PRN;
import static org.pp.qry.interfaces.Node.TYPE_STR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.pp.qry.interfaces.AbstractAction;
import org.pp.qry.interfaces.Node;
import org.pp.qry.interfaces.ObjectIterator;
import org.pp.qry.interfaces.Query;
import org.pp.qry.interfaces.QueryDataProvider;
/**
 * implementation of Query API
 * @author prasantsmac
 *
 * @param <T>
 */
public class QueryImp<T> implements Query<T> {
	/** Root Node of expression tree */
	private Node root = null;
	/** Query Context to handle data loading */
	private QueryDataProvider<T> ctx;
	/** Code points of SQL queries */
	private char[] chars = null;
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
	// all action to Execute
	static final AbstractAction[] actions = new AbstractAction[17];
	
	// configure all actions
    static {
    	// multiply action
    	actions[OP_MUL] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.mul(right, false);
			}

			@Override
			public Node validate(Node left, Node right) {
				// TODO Auto-generated method stub
				return left.mul(right, true);
			}
    	};
    	// addition action
    	actions[OP_ADD] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.plus(right, false);
			}

			@Override
			public Node validate(Node left, Node right) {
				return left.plus(right, true);
			}
	
    	};
    	// divide action
    	actions[OP_DIV] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.div(right, false);
			}

			@Override
			public Node validate(Node left, Node right) {
				// TODO Auto-generated method stub
				return left.div(right, true);
			}
    	};
    	// remainder action
    	actions[OP_MOD] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.rem(right, false);
			}	
			
			@Override
			public Node validate(Node left, Node right) {
				// TODO Auto-generated method stub
				return left.rem(right, true);
			}
    	};
    	// subtraction action
    	actions[OP_SUB] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.minus(right, false);
			}

			@Override
			public Node validate(Node left, Node right) {
				// TODO Auto-generated method stub
				return left.minus(right, true);
			}	
    	};
    	// greater action
    	actions[OP_GT] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.gt(right, false);
			}

			@Override
			public Node validate(Node left, Node right) {
				// TODO Auto-generated method stub
				return left.gt(right, true);
			}  
			
			@Override
			public boolean seekable() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public Object getSeekableValue(Node value, boolean rev) {
				// TODO Auto-generated method stub
				return rev ? null : value.higherValue();
			}			 		
    	};
    	// greater than equal action
    	actions[OP_GTE] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.ge(right, false);
			}
			
			@Override
			public Node validate(Node left, Node right) {
				// TODO Auto-generated method stub
				return left.gt(right, true);
			}   

			@Override
			public boolean seekable() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public Object getSeekableValue(Node value, boolean rev) {
				// TODO Auto-generated method stub
				return rev ? null : value.value();
			}    		
    	};
    	// less than action
    	actions[OP_LT] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.lt(right, false);
			}
			
			@Override
			public Node validate(Node left, Node right) {
				return left.lt(right, true);
			}

			@Override
			public boolean seekable() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public Object getSeekableValue(Node value, boolean rev) {
				// TODO Auto-generated method stub
				return rev ? value.value(): null;
			}    		
    	};
    	// less than equal to action
    	actions[OP_LTE] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.le(right, false);
			}
			
			@Override
			public Node validate(Node left, Node right) {
				return left.le(right, true);
			}

			@Override
			public boolean seekable() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public Object getSeekableValue(Node value, boolean rev) {
				// TODO Auto-generated method stub
				return rev ? value.higherValue() : null;
			}    		
    	};
    	// equal action
    	actions[OP_EQ] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.eq(right, false);
			}
			
			@Override
			public Node validate(Node left, Node right) {
				return left.eq(right, true);
			}
			
			@Override
			public boolean seekable() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public Object getSeekableValue(Node value, boolean rev) {
				// TODO Auto-generated method stub
				return rev ? value.higherValue() : value.value();
			}    		
    	};
    	// not equal action
    	actions[OP_NEQ] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.neq(right, false);
			}
			
			@Override
			public Node validate(Node left, Node right) {
				return left.neq(right, true);
			}
   		
    	};
    	// between action
    	actions[OP_BET] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.bet(right, false);
			}

			@Override
			public Node validate(Node left, Node right) {
				return left.bet(right, true);
			}
			
			@Override
			public boolean seekable() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			@SuppressWarnings("unchecked")
			public Object getSeekableValue(Node value, boolean rev) {
				// ensure node is of List Node type
				if (value instanceof ListNode) {
					// cast to list node
					ListNode o = (ListNode) value;
				    // get list of nodes
					List<Node> list = (List<Node>) o.value();
					return rev ? list.get(1).higherValue() : list.get(0).value();
				}
				throw new RuntimeException("Was expecting a ListNode type");
			}    		
    	};
    	// not between action
    	actions[OP_NBET] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.nbet(right, false);
			}

			@Override
			public Node validate(Node left, Node right) {
				return left.nbet(right, true);
			}    		
    	};
    	// in action
    	actions[OP_IN] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.in(right, false);
			}
			
			@Override
			public Node validate(Node left, Node right) {
				return left.in(right, true);
			}		   		
    	};
    	// not between action
    	actions[OP_NIN] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.nin(right, false);
			}
			
			@Override
			public Node validate(Node left, Node right) {
				return left.nin(right, true);
			}		
    	};
    	// and action
    	actions[OP_AND] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.and(right, false);
			}
			
			@Override
			public Node validate(Node left, Node right) {
				return left.and(right, true);
			}
 		
    	};
    	// or action
    	actions[OP_OR] = new AbstractAction() {
			@Override
			public Node execute(Node left, Node right) {
				return left.or(right, false);
			}
			
			@Override
			public Node validate(Node left, Node right) {
				return left.or(right, true);
			} 		
    	};
    }
    
	private QueryImp() {		
	}

	/** New instance of Query Engine */
	public QueryImp(String sql, QueryDataProvider<T> ctx) {
		this();
		// 
		if (sql == null || ctx == null)
			throw new NullPointerException("Null query or DataProvider not allowed");
		// extract code points
		chars = sql.toCharArray();
		this.ctx = ctx;
		compile();
	}

	@Override
	public void close() {
		ctx.reset();
		seek = seekBySk = false;
		stk = null;
		chars = null;
		params = null;
		root = null;
		sb = null;
	}
	
	private Query<T> add(int pos, Node node) {
		// check position 
		pos = pos -1;
		if (pos < 0 || pos > params.size())
			throw new RuntimeException("Invalid parameter position: " + pos);
		// add node
		params.add(pos, node);
		return this;
			
	}

	@Override
	public Query<T> setParam(int pos, int val) {
		// convert to number node
		Node node = new IntNode(val);
		// add to parameter list
		return add(pos, node);
	}

	@Override
	public Query<T> setParam(int pos, long val) {
		// convert to number node
		Node node = new LongNode(val);
		// add to parameter list
		return add(pos, node);
	}

	@Override
	public Query<T> setParam(int pos, float val) {
		// convert to number node
		Node node = new FloatNode(val);
		// add to parameter list
		return add(pos, node);
	}

	@Override
	public Query<T> setParam(int pos, double val) {
		// convert to double node
		Node node = new DoubleNode(val);
		// add to parameter list
		return add(pos, node);
	}

	@Override
	public Query<T> setParam(int pos, String val) {
		// convert to String node
		Node node = new StringNode(val);
		// add to parameter list
		return add(pos, node);
	}

	@Override
	public Query<T> setParam(int pos, boolean val) {
		// convert to boolean node
		Node node = new BooleanNode(val);
		// add to parameter list
		return add(pos, node);
	}
	
	@Override
	public Query<T> setParam(int pos, List<?> list) {
		// must be for between/not between operator
		if (list.size() != 2)
			tRex("Parameter list size must be 2");
		// construct a new list of arguments
		List<Node> newList = new ArrayList<>();
		Node tmp = null, node = null;
		// verify all objects
		for (Object obj : list) {
			// null value is not allowed
			if (obj == null || obj instanceof Boolean)
				throw new RuntimeException("Parameter list can not contains null or boolean type");
			// convert to value node
			node = toNode(obj);
			// all node must be of same type
			if (tmp != null && tmp.getType() != node.getType())
				throw new RuntimeException("All objects in paramter list must be of same type");
			// reference to check same type
			tmp = node;
			// Add to the list
			newList.add(node);
		}
		// verify if range is valid
		if (newList.get(0).compare(newList.get(1)) >= 0)
			throw new RuntimeException("Invalid range");
		// add to parameter list
		return add(pos, node);
	}

	@Override
	public Query<T> setParam(int pos, Set<?> set) {
		// first check for valid set
		if (set == null || set.size() == 0)
			tRex("Empty or nul set");
		// new hash set
		Set<Node> newSet = new HashSet<>();
		Node tmp = null, node = null;
		// verify all objects
		for (Object obj : set) {
			// null value is not allowed
			if (obj == null || obj instanceof Boolean)
				throw new RuntimeException("Parameter list can not contains null or boolean type");
			// convert to value node
			node = toNode(obj);
			// all node must be of same type
			if (tmp != null && tmp.getType() != node.getType())
				throw new RuntimeException("All objects in paramter list must be of same type");
			// reference to check same type
			tmp = node;
			newSet.add(node);
		}
		// add to parameter list
		return add(pos, node);
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
			tRex("Invalid Expression");
		// assign it to root node
		root = stk.pop();
		if (root.getPriority()> PRI_COND_OP)
			tRex("Invalid expression, expression must be evaluated to binary value");
		// validate expression tree now
		validate(root);
	}

	/**
	 * Start parsing expression
	 */
	private void expr() {
		char token = checkNextChar();
		// if start of parentheses
		if (token == '(') {
			pos++;
			stk.push(new OperatorNode(OP_PRN, TYPE_PRN, PRI_PARAN));
			// call expression recursively
			expr();
			if (checkNextChar() != ')')
				throw new RuntimeException("Expecting ')' @" + pos);
			// process parentheses
			processParan();
			pos++;
		}
		// otherwise must expect a operand
		else 
			operand();
		// check any operator available or not
		operator();
	}

	/**
	 * Process parentheses
	 */
	private void processParan() {
		// remove right operand
		Node r = stk.pop();
		if (r.getType() == TYPE_PRN)
			return;
		// remove '('
		stk.pop();
		// Make it high priority
		if (r.isOperator())
			r.setPriority(PRI_PARAN);
		// build tree if stack not empty
		if (!stk.isEmpty() && stk.peek().getType() == TYPE_OP) {
			buildTree(r);
		}
     	// otherwise push to stack
		else 
			stk.push(r);
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
			// if parentheses end 
			if (op.startsWith(")")) {
				pos = cPos;
				return;
			}
			// Possible operator of one length
			if (op.length() == 1) {
				switch (op) {
				case STR_GT:
					stk.push(new OperatorNode(OP_GT, TYPE_OP, PRI_COND_OP));
					break;
				case STR_LT:
					stk.push(new OperatorNode(OP_LT, TYPE_OP, PRI_COND_OP));
					break;
				case STR_EQ:
					stk.push(new OperatorNode(OP_EQ, TYPE_OP, PRI_COND_OP));
					break;
				case STR_ADD:
					stk.push(new OperatorNode(OP_ADD, TYPE_OP, PRI_MATH_LOW));
					break;
				case STR_SUB:
					stk.push(new OperatorNode(OP_SUB, TYPE_OP, PRI_MATH_LOW));
					break;
				case STR_MUL:
					stk.push(new OperatorNode(OP_MUL, TYPE_OP, PRI_MATH_HIGH));
					break;
				case STR_DIV:
					stk.push(new OperatorNode(OP_DIV, TYPE_OP, PRI_MATH_HIGH));
					break;
				case STR_MOD:
					stk.push(new OperatorNode(OP_MOD, TYPE_OP, PRI_MATH_HIGH));
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
					stk.push(new OperatorNode(OP_GTE, TYPE_OP, PRI_COND_OP));
					break;
				case STR_LTE:
					stk.push(new OperatorNode(OP_LTE, TYPE_OP, PRI_COND_OP));
					break;
				case STR_NEQ:
					stk.push(new OperatorNode(OP_NEQ, TYPE_OP, PRI_COND_OP));
					break;
				case STR_OR:
					stk.push(new OperatorNode(OP_OR, TYPE_OP, PRI_OR));
					break;
				case STR_AND:
					stk.push(new OperatorNode(OP_AND, TYPE_OP, PRI_AND));
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
		for (int uPoint = -1, state = -1; pos < chars.length; pos++) {
			uPoint = chars[pos];
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
	 * Get the next character token
	 * 
	 * @return
	 */
	private char checkNextChar() {
		for (int uPoint = -1; pos < chars.length; pos++) {
			uPoint = chars[pos];
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
		// push condition to stack
		stk.push(new OperatorNode(op, TYPE_OP, PRI_COND_OP));
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
				throw new RuntimeException("Expecting '(' @" + cPos);
			pos++;
			// list of right arguments
			Collection<Node> col = op < OP_IN ? new ArrayList<>() : new HashSet<>();
			// Start getting operand
			Node l = null;
			for (;;) {
				// get next operand
				operand();
				// pop last recorded operand
				r = stk.pop();
				// check prohibited operators
				int opr = r.getType();
				if (!(opr == TYPE_NUM || opr == TYPE_STR))
					throw new RuntimeException("Only String/Number type allowed for in/between operator @" + cPos);
				// check value type matches
				if (l != null && l.getType() != r.getType())
					throw new RuntimeException("Type mismatch for in/between operator @" + cPos);
				// add to set
				l = r;
				col.add(r);
				try {
					// get ',' or ')'
					ch = checkNextChar();
					if (ch == ',')
						continue;
					if (ch == ')')
						break;
					else
						throw new RuntimeException("Expecting ',' or ')' @" + cPos);
				} finally {
					pos++;
				}
				
			}
			// some extra validation for between/not between operator
			if (op < OP_IN) {
				// more than 2 operands are not acceptable
				if (col.size() > 2)
					throw new RuntimeException("More than two operands for (not) between operator @" + cPos);
				// now check range validity
				List<Node> list = (List<Node>) col;
				if (list.get(0).compare(list.get(1)) >= 0)
					throw new RuntimeException("Invalid range for between/not between operator @" + cPos);
				// if all cool
				r = new ListNode(list);
			}
			// if in/not operator create set node
			else {
				Set<Node> set = (Set<Node>) col;
				r = new SetNode(set, l instanceof NumberNode ? true : false);
			}			
		}
		// build tree now
		buildTree(r);
		// check for relational operator's presence
		operator();
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
			case INT: // INT
				oprnd = new IntNode(sb.toString());
				break;
			case LNG: // long
				oprnd = new LongNode(sb.toString());
				break;
			case FLT: // Float 
				oprnd = new FloatNode(sb.toString());
				break;
			case DBL: // Double
				oprnd = new DoubleNode(sb.toString());
				break;
			// String
			case 5:
				oprnd = new StringNode(sb.toString());
				break;
			// ID or keyword
			case 6:
				String id = sb.toString();
				// verify not empty id
				if ("".equals(id))
					tRexOprnd(c);
				// if parentheses end
				if (id.startsWith(")")) {
					pos = c;
					return;
				}
				// check type
				switch (id) {
					case "true":
						oprnd = new BooleanNode(true);
						break;
					case "false":
						oprnd = new BooleanNode(false);
						break;
					case "null":
						oprnd = new NullNode();
						break;
					case "?":
						oprnd = new ParameterNode(prmIndx++);
						break;
					default:
						oprnd = new IDNode(id);
				}
				break;
			// incomplete state
			default:
				tRexOprnd(c);
		}		
		// stack is empty or its between/in (not) operator
		if (stk.isEmpty() || stk.peek().intValue() > OP_OR) {
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
		for (int uPoint = -1; pos < chars.length; pos++) {
			uPoint = chars[pos];
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
					state = INT; // default integer type
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
					state = FLT; // float type
				// verify other type
				else if (!isNumber(uPoint)) {
					if (uPoint == 'd' || uPoint == 'D')
						state = DBL; // Double type
					else if (uPoint == 'l' || uPoint == 'L')
						state = LNG; // Long type
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
		return uPoint == '.' || 
				(uPoint > 47 && uPoint < 58) || 
				uPoint == '+' || uPoint == '-';
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
        // 
		Node tmp = l, parent = null;
		// find the right most node
		while (tmp.right() != null && op.getPriority() > tmp.getPriority()) {
			// reference parent
			parent = tmp;
			// move to right
			tmp = tmp.right();
		}
        // set left node of operator
		op.setLeft(tmp);
		// set right
		op.setRight(r);
		// adjust tree root
		if (parent == null)
			l = op;
		else
			parent.setRight(op);
		// Push root to stack
		stk.push(l);
	}
	
	/**
	 * Validate entire expression tree
	 * @param node
	 * @return
	 */
	private Node validate(Node node) {
		/**
		 * Return if non terminal node
		 */
		if (!node.isOperator())
			return node;
		/**
		 * visit left subtree first
		 */
		Node left = validate(node.left());
		/**
		 * Visit right subtree
		 */
		Node right = validate(node.right());
		
		// get operation code
		int op = node.intValue();
		/**
		 * Validate now
		 */
		return actions[op].validate(left, right);		
	}
	
	/**
	 * Evaluate expression
	 * 
	 * @param node
	 * @return
	 */
	private Node evaluate(Node node) {
		/**
		 * return if non terminal operator
		 */
		if (!node.isOperator())
			return node;
		/**
		 * Visit left subtree
		 */
		Node left = evaluate(node.left());
		/**
		 * Get operator code
		 */
		int op = node.intValue();
		/**
		 * if operator is '||' and left condition was satisfied than no need to visit
		 * right subtree
		 */
		if (op == OP_OR && left.boolValue())
			return left;
		/**
		 * If operator is '&&' and left condition was not satisfied than no need to
		 * visit right subtree
		 */
		if (op == OP_AND && !left.boolValue())
			return left;
		/**
		 * Visit right subtree
		 */
		Node right = evaluate(node.right());
		/**
		 * Operation tracker. help to track where to stop
		 */
		pos++;

		/**
		 * Load records first if not loaded yet
		 */
		if (!seek && (left.isID() || right.isID())) {
			/**
			 * Check if records can be loaded using SortKey
			 */
			if (root.getPriority() != PRI_OR && // if root condition is not '||'
			// ensure both operand are not ID
					!(left.isID() && right.isID())) {
				/**
				 * Check if SortKey was specified
				 */
				Node tmp = null;
				// if left sort key
				if (left.isID() && ctx.isSortKey(left.stringValue()))
					tmp = paramToNode(right);
				// right sort key
				else if (right.isID() && ctx.isSortKey(right.stringValue()))
					tmp = paramToNode(left);

				/**
				 * if sort key was provided
				 */
				if (tmp != null) {
					// retrieve action object using operator
					AbstractAction action = actions[op];
					// check if operator support seek or not
					if (action.seekable()) {
						// get value to seek
						Object val = action.getSeekableValue(tmp, rev);
						// seek now
						ctx.seek(val, rev);
						// marked as data seek is done
						seek = seekBySk = true;
					}
						
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
		l = paramToNode(l);
		r = paramToNode(r);
		
        /**
         * Execute operator action now
         */
		Node result = actions[op].execute(l, r);

		/**
		 * If the operation sequence is sort key condition Stop moving further
		 */
		if (seekBySk && pos == lseq && !result.boolValue()) {
			// reset context
			ctx.reset();
			// no more elements
			throw new NoSuchElementException();
		}
		// return result
		return result;
	}

	// Convert ID to Value node
	private Node idToNode(Node n) {
		if (n.isID()) {
			// convert ID to value node now
			Object obj = ctx.getFieldValue(n.stringValue());
			// convert to node
			n = toNode(obj);
		}
		return n;
	}

	// Convert parameter Node to value node
	private Node paramToNode(Node n) {
		if (n.isParameter()) {
			// get index
			int index = n.intValue();
			// check if index out of bound
			if (index >= params.size())
				throw new RuntimeException("Missing parameter at index : " + (index + 1));
			// get Proper Node
			n = params.get(index);
		}
		return n;
	}

	/**
	 * Object Iterator implementation
	 * 
	 * @author prasantsmac
	 *
	 * @param <T>
	 */
	private final class ObjectIteratorImp implements ObjectIterator<T> {
		/**
		 * Iterator close indicator
		 */
		private boolean close = false;

		@Override
		public boolean hasNext() {
			try {
				/**
				 * Check iterator status. 
				 */
				status();
				/**
				 * Advance to next record if data already seek
				 */
				while (!seek || ctx.nextRecord()){
					// new evaluation indicator
					pos = 0; 
					// start evaluating expression
					if (evaluate(root).boolValue())
						return true;					
				}
				/**
				 * No more record
				 */
				throw new NoSuchElementException();
			}  
			catch (NoSuchElementException e) { close(); return false; }
			catch (Throwable t) { close(); throw t;	}			
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
			if (!close)
				return true;
			// either throw exception 
			throw new RuntimeException("Iterator closed already");			
		}

	}

	static void tRex(String msg) {
		throw new RuntimeException(msg);
	}

	static void tRexOprnd(int curPos) {
		throw new RuntimeException("Expecting number/identifier/string" + "/'null'/'true'/'false'/'?' @" + curPos);
	}

	static void tRexOp(int curPos) {
		throw new RuntimeException("Expecting operators (e.g. >, <, >=, <= ...etc) @" + curPos);
	}	
}
