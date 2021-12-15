package org.pp.test.qry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.pp.qry.QueryImp;
import org.pp.qry.interfaces.Query;
import org.pp.qry.interfaces.QueryDataProvider;

class TestContext {
	/**
	 * Test name to execute
	 */
	private String testName;
	/**
	 * Query to execute
	 */
	private String qry;
	/**
	 * Input to feed
	 */
	private List<Map<String, Object>> input = new ArrayList<>();
	/**
	 * Expected output
	 */
	private List<Map<String, Object>> expectedOutput = new ArrayList<>();
	/**
	 * List of user provided parameters 
	 */
	private List<Map<Integer,Object>> paramList = new ArrayList<>();
	/**
	 * If reverse order is required
	 */
	private boolean reverse = false;
	/**
	 * If sort keys were provided
	 */
	private String[] sortKeys;
	/**
	 * If error message was expected
	 */
	private String errMsg;
	
	
	String getTestName() {
		return testName;
	}

	void setTestName(String testName) {
		this.testName = testName;
	}

	String getQry() {
		return qry;
	}

	void setQry(String qry) {
		this.qry = qry;
	}

	List<Map<String, Object>> getInput() {
		return input;
	}

	void setInput(List<Map<String, Object>> input) {
		this.input = input;
	}

	List<Map<String, Object>> getExpectedOutput() {
		return expectedOutput;
	}

	void setExpectedOutput(List<Map<String, Object>> expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	boolean isReverse() {
		return reverse;
	}

	void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	String[] getSortKeys() {
		return sortKeys;
	}

	void setSortKeys(String[] sortKeys) {
		this.sortKeys = sortKeys;
	}

	String getErrMsg() {
		return errMsg;
	}

	void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
		
	List<Map<Integer, Object>> getParamList() {
		return paramList;
	}

	/**
	 * Execute test
	 */
	void execute() {
		try {
			// if query null
			if (qry == null)
				throw new RuntimeException("Query can not be null");
			// data provider
			QueryDataProvider<Map<String, Object>> dp = null;
			// if sort keys were provided
			if (sortKeys != null)
				dp = new SortMapDataProvider(input, sortKeys);
			else
				dp = new MapQryCtx(input);
			// create a new Query Engine instance
			Query<Map<String, Object>> query = new QueryImp<>(qry, dp);
			// check if reverse
			query = reverse ? query.setReverseOrder(true) : query;
			// execute query and return list
			List<Map<String, Object>> output = query.list();
			// asserts that both lists are equal
			boolean res = assertEquals(output, expectedOutput);
			// print status
			System.out.println("[TestName#: " + testName + ",Query: " + qry + ",Status: " + (res ? "PASS" : "FAILED"));
		} catch (Exception e) {
			if (errMsg == null || !e.getMessage().contains(errMsg))
				System.out.println("[TestName#: " + testName + ",Query: " + qry + ",Status: " + "FAILED" + ",Msg: " + e.getMessage());
		}
	}
	
	/**
	 * Compare two list of map
	 * @param list1
	 * @param list2
	 * @return
	 */
	private boolean assertEquals(List<Map<String, Object>> list1, List<Map<String, Object>> list2) {
		// if one of them is null
		if (list1 == null || list2 == null)
			return false;
		// if size doesn't match
		if (list1.size() != list2.size())
			return false;
		// loop throw each of the list
		Iterator<Map<String, Object>> itr1 = list1.iterator();
		Iterator<Map<String, Object>> itr2 = list2.iterator();	
		//
		while (itr1.hasNext() && itr2.hasNext()) {
			Map<String, Object> m1 = itr1.next();
			Map<String, Object> m2 = itr2.next();
			// check both map size
			if (m1.size() != m2.size())
				return false;
			// iterator of entry set
			Iterator<Map.Entry<String, Object>> eset1 = m1.entrySet().iterator();
			Iterator<Map.Entry<String, Object>> eset2 = m2.entrySet().iterator();
			// loop together to check equality
			while (eset1.hasNext() && eset2.hasNext()) {
				Map.Entry<String, Object> e1 = eset1.next();
				Map.Entry<String, Object> e2 = eset2.next();
				// compare both key
				if (!equals(e1.getKey(), e2.getKey()))
					return false;
				// compare values
				if (!equals(e1.getValue(), e2.getValue()))
					return false;				
			}		
		}
		return true;		
	}
	/**
	 * Compare two objects
	 * @param str1
	 * @param str2
	 * @return
	 */
    private boolean equals(Object obj1, Object obj2) {
		// if both are non null
		if (obj1 != null && obj2 != null)
			return obj1.equals(obj2);
		// else
		return (obj1 != null || obj2 != null) ? false : true;
	}	

}
