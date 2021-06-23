package org.pp.qry.interfaces;

import java.util.List;
import java.util.Set;

public interface Query<T> {
	/**
	 * Set integer parameter
	 * @param val
	 * @return
	 */
	public Query<T> setParam(int val);
	/**
     * Set long parameter
     * @param val
     * @return
     */
	public Query<T> setParam(long val);
	/**
     * Set float parameter
     * @param val
     * @return
     */
	public Query<T> setParam(float val);
	/**
     * Set double parameter
     * @param val
     * @return
     */
	public Query<T> setParam(double val);
	/**
     * Set String parameter
     * @param val
     * @return
     */
	public Query<T> setParam(String val);
	/**
     * Set boolean parameter
     * @param val
     * @return
     */
	public Query<T> setParam(boolean val);
	
	/**
     * Set list of objects for between/not between operator
     * @param val
     * @return
     */
	public Query<T> setParam(List<?> list);
	
	/**
     * Set set of objects for in/not in operator
     * @param val
     * @return
     */
	public Query<T> setParam(Set<?> set);
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
