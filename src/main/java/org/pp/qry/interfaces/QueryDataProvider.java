package org.pp.qry.interfaces;
/**
 * 
 * @author prasantsmac
 *
 */
public interface QueryDataProvider<T> {
		
   /**
    * Return true if specified field is sort key other wise false
    * @param fname
    * @return
    */
   public boolean isSortKey(String fname);
   
  /**
   * Seek data based on the provided sort key in range.
   * @param from
   * @param to
   * @return
   */
  public void seek(Object from, boolean rev);
   
  /**
   * Get field value 
   * @param fname
   * @return
   */
  public Object getFieldValue(String fname);
    
  /**
   * Return if next record exist otherwise false
   * @param <T>
   * @return
   */
  public boolean nextRecord();
  
  /**
   * Get current record
   * @param <T>
   * @return
   */  
  public T currentRecord();
  
  /**
   * Delete current record
   * @param <T>
   * @return
   */
  public T deleteCurrent();
  
  /**
   * Update current record
   * @param <T>
   * @param t
   * @return
   */
  public T updateCurrent();
  /**
  * Reset context
  */
  public void reset();
}
