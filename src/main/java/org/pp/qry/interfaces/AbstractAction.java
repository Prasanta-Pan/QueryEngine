package org.pp.qry.interfaces;

public abstract class AbstractAction {
	/**
	 * Execute action operator action with left and right node and return result
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public abstract Node execute(Node left, Node right);

	/**
	 * Validate if operation possible between these nodes
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public abstract Node validate(Node left, Node right);

	/**
	 * Indicate if this operator support seek operation or not
	 * 
	 * @return
	 */
	public boolean seekable() {
		/**
		 * Subclass should override default implementation when needed
		 */
		return false;
	}

	/**
	 * Return seekable value
	 * 
	 * @param value
	 * @return
	 */
	public Object getSeekableValue(Node value, boolean rev) {
		/**
		 * Subclass should override default implementation when needed
		 */
		return null;
	}
}
