package org.pp.test.qry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class AbstractTestMap {
	/**
	 * Prepared some test data
	 */
	private static List<Map<String, Object>> list = new ArrayList<>();
	/**
	 * Load data during boot
	 */
	static {
		list.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
		list.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));
		list.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
		
		list.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 5.73f, 10, "46 eastwood rd"));
		list.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 10.30f, 15, "46 eastwood rd"));
		list.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 15.54f, 20, "46 eastwood rd"));
		
		list.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 5.73f, 10, "46 eastwood rd"));
		list.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 10.30f, 15, "46 eastwood rd"));
		list.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 15.54f, 20, "46 eastwood rd"));
	}
	/**
	 * Return input data
	 * @return
	 */
	protected List<Map<String, Object>> getList() {
		return list;
	}
	
	/**
	 * Get map out of objects
	 * @param vals
	 * @return
	 */
	protected static Map<String,Object> getMap(Object...vals) {
		Map<String,Object> m = new HashMap<>();
		m.put("name", vals[0]);
		m.put("email", vals[1]);
		m.put("mobile", vals[2]);
		m.put("price", vals[3]);
		m.put("units", vals[4]);
		m.put("address", vals[5]);		
		return m;
	}
	/**
	 * Print list of map
	 * @param fList
	 */
	protected static void print(List<Map<String, Object>> fList) {
		StringBuilder sbldr = new StringBuilder();
		for (Map<String, Object> m: fList) {
			for (Object o : m.values())
				sbldr.append(o.toString() + ",");
			
			sbldr.append("\n");
		}
		System.out.println(sbldr);
	}
	/**
	 * Compare two list of map
	 * @param list1
	 * @param list2
	 * @return
	 */
	protected static boolean isEqual(List<Map<String, Object>> list1, List<Map<String, Object>> list2) {
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
	private static boolean equals(Object obj1, Object obj2) {
		// if both are non null
		if (obj1 != null && obj2 != null)
			return obj1.equals(obj2);
		// else
		return (obj1 != null || obj2 != null) ? false : true;
	}
 
}
