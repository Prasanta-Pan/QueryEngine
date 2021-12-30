# QueryEngine
QueryEngine is a fast, high performance SQL like query engine written in java for any type of data source.
User can provide custom data adaptor to feed raw data to the engine. QueryEngine's highly plugable design ensure seemless integration to any application of choice. Most SQL operators operators are supported.

# Features

  * User can provide custom raw data adaptor.
  * Majority of SQL operators are supported.
  * Fast and high performance SQL query parser.
  * Plugable design, can be integrated to any applications easily.
  * Highly portable and can be embedded easly in any java based application.
 
# Getting the Source

```bash
git clone --recurse-submodules https://github.com/Prasanta-Pan/QueryEngine.git
```

# Building

First, please ensure [Maven](https://maven.apache.org/) already installed in local machine or buildbox.
After downloading the source (as mentioned above), switch to source folder and issue the following command

```bash
mvn clean package
```
# Using Query API's

The below code example demonstrate the query API's in Action.

```java
    .....
    import org.pp.qry.QueryImp;
    import org.pp.qry.interfaces.Query;
    import org.pp.qry.interfaces.QueryDataProvider;
    import org.pp.qry.interfaces.ObjectIterator;
    
    .....
    .........
    
    // My awesome query 
    String qry = "customerEmail = 'pan.prasanta@gmail.com' && orderDate between ? && (totalOrder > ? || units > ?)";
    // instantiate your awesome custom raw data provider
    QueryDataProvider dataAdaptor = new MyCustomRawDataProvider<Customer>();
    // create a query engine instance
    Query<Customer> query = new QueryImp(qry, dataAdaptor);
    /**
     * Time to set the required parameters one by one
     */
     // set order date range
     List<Long> dr = Arrays.asList(stringDateToMillis("2018-03-13"), stringDateToMillis("2020-09-17"));
     // now fetch the filtered customer list
     List<Customer> list = query.setParam(1, dr)
						     .setParam(2, 100.30) // set totalOrder parameter
						     .setParam(3, 5) // set number of units parameter
						     .list();
     // we can also iterate over the filtered customer list in order to bulk update or delete
     ObjectIterator<Customer> itr = query.setParam(1, dr)
							     .setParam(2, 100.30) // set totalOrder parameter
							     .setParam(3, 5) // set number of units parameter
							     .iterator();
     	// do bulk update or delete while iteration
     	while (itr.hasNext()) {
     	    // get the next customer
     	    Customer c = itr.next();
     	    // delete or remove customer
     	    if (c.getStatus == DEACTIVE)s
     	        itr.remove();
     	    else {
     	        // update customer and order info
     	        c.setRewards(100);
     	        c.setAddress("customer new address..blah..blah");
     	        // update customer
     	        itr.update();
     	    }
     	}
    
```

# Supported SQL Operators

Not all SQL operators are supported currently by the engine.
The following list depicts currently supported SQL Operators.

```java
    Parentheses 		--> 		(), (()), (exp)
    Arithmetic Operators	--> 		+, -, *, /, %
    Conditional Operators	--> 		>, <, >=, <=, =, !=, in, between (or bet), not in (or nin), not between (or bet)
    Relational Operators	-->		&&, ||, !
     
```

# Supported Data Types
 
 ```java
 
	String	-->	'my string' , 'c' // single character also a String
	Numbers	--> 	int, long, float and double
	Boolean	--> 	true, false
	Null		-->	null
	Parameter	-->	?
     
```
# About QueryDataProvider Interface

As mentioned in the beginning of the documentation, user supposed to provide their own custom implementation of QueryDataProvider interface to feed raw data to the engine in order get filtered
output from query engine. Lets have a look at the QueryDataInterface.

 ```java
 package org.pp.qry.interfaces;
/**
 * 
 * User supposed to implement this interface to
 * feed raw data to the engine
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
   * Seek data based on the provided sort key.
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
     
```
The below diagram depicts the interaction between query engine and custom QueryDataProvider component.

![Diagramme](https://github.com/Prasanta-Pan/QueryEngine/images/QueryEngine.png)



