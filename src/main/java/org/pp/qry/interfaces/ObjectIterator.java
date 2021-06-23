package org.pp.qry.interfaces;

public interface ObjectIterator<T> {
	/**
	 * Check if next record exist
	 * @return
	 */
	public boolean hasNext();
	/**
	 * Return next object
	 * @return
	 */
	public T next(); 
	/**
	 * Update last return object
	 * @return
	 */
	public T update();
	/**
     * Remove last return object	
     * @return
     */
	public T remove();
	/**
	 * Close iterator
	 */
	public void close();
}
