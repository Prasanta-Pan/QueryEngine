package org.pp.qry.interfaces;

import java.util.List;
import java.util.Set;

public interface Query<T> {
	/**
	 * Set integer parameter
	 * @param val
	 * @return
	 */
	public Query<T> setParam(int pos, int val);
	
	/**
     * Set long parameter
     * @param val
     * @return
     */
	public Query<T> setParam(int pos, long val);
	
	/**
     * Set float parameter
     * @param val
     * @return
     */
	public Query<T> setParam(int pos, float val);
	/**
     * Set double parameter
     * @param val
     * @return
     */
	public Query<T> setParam(int pos, double val);
	/**
     * Set String parameter
     * @param val
     * @return
     */
	public Query<T> setParam(int pos, String val);
	/**
     * Set boolean parameter
     * @param val
     * @return
     */
	public Query<T> setParam(int pos, boolean val);
	
	/**
     * Set list of objects for between/not between operator
     * @param val
     * @return
     */
	public Query<T> setParam(int pos, List<?> list);
	
	/**
     * Set set of objects for in/not in operator
     * @param val
     * @return
     */
	public Query<T> setParam(int pos, Set<?> set);
	/**
	 * Default is asc order. set true to reverse order
	 * @param rev
	 * @return
	 */
	public Query<T> setReverseOrder(boolean rev);
		
	/**
	 * Get a single object
	 * @return
	 */
	public T get();
	/**
	 * Get list of Objects
	 * @return
	 */
	public List<T> list();
	/**
	 * Return a new object iterator
	 * @return
	 */
	public ObjectIterator<T> iterator();
	/**
	 * Close query processor
	 */	
	public void close();
	
}
