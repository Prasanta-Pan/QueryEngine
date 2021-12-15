package org.pp.test.qry;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.pp.qry.interfaces.QueryDataProvider;

class SortMapDataProvider implements QueryDataProvider<Map<String,Object>> {
	/**
	 * Sort Keys
	 */
	private String[] sortKeys = null;
	
	/**
	 * Sorted Set
	 */
	private NavigableSet<Map<String,Object>> set;
	/**
	 * Backing iterator
	 */
	private Iterator<Map<String,Object>> itr = null;
	/**
	 * Backing map
	 */
	private Map<String,Object> m = null;
	
	/**
	 * Create Sort Data provider map
	 * @param listMap
	 * @param sortKeys
	 */
	SortMapDataProvider(List<Map<String,Object>> listMap, String[] sortKeys) {
		// initialise sorted map
		set = new TreeSet<>(new Comparator<Map<String,Object>>() {			
			@Override
			@SuppressWarnings("unchecked")
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				// TODO Auto-generated method stub
				int res = 0;
				for (String sortKey : sortKeys) {
					Comparable<Object> v1 = (Comparable<Object>) o1.get(sortKey);
					Comparable<Object> v2 = (Comparable<Object>) o2.get(sortKey);
					// compare 2 objects
					if (v1 == null && v2 == null)
						return 0;
					if ((res = v1.compareTo(v2)) != 0)
						return res;										
				}
				return res;
			}			
		});
		// add list to set
		set.addAll(listMap);
	}
	
	@Override
	public boolean isSortKey(String fname) {
		// TODO Auto-generated method stub
		return sortKeys[0].equals(fname);
	}

	@Override
	public void seek(Object from, boolean rev) {
		// TODO Auto-generated method stub
		if (!rev && from != null) {
			Map<String,Object> m = new HashMap<>();
			m.put(sortKeys[0], from);
			set = set.tailSet(m, true);
		}
		else if (rev && from != null) {
			set = set.descendingSet();
			Map<String,Object> m = new HashMap<>();
			m.put(sortKeys[0], from);
			set = set.tailSet(m, true);
		}
		else if (rev)
			set = set.descendingSet();
		// get iterator
		itr = set.iterator();
	}

	@Override
	public Object getFieldValue(String fname) {
		// TODO Auto-generated method stub
		return m.get(fname);
	}

	@Override
	public boolean nextRecord() {
		m = itr.hasNext() ? itr.next() : null;
		return m != null ? true : false;
	}

	@Override
	public Map<String, Object> currentRecord() {
		// TODO Auto-generated method stub
		return m;
	}

	@Override
	public Map<String, Object> deleteCurrent() {
		// TODO Auto-generated method stub
		return m;
	}

	@Override
	public Map<String, Object> updateCurrent() {
		// TODO Auto-generated method stub
		return m;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		itr = null;
		m = null;	
		set = null;
	}

}
