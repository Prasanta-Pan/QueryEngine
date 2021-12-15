package org.pp.test.qry;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.pp.qry.interfaces.QueryDataProvider;

class MapQryCtx implements QueryDataProvider<Map<String,Object>> {
	// list of map
	final List<Map<String,Object>> listMap;
	// iterator
	private Iterator<Map<String,Object>> itr = null;
	// currently selected object
	private Map<String,Object> m = null;
	
	MapQryCtx(List<Map<String,Object>> listMap) {
		this.listMap = listMap;
	}

	@Override
	public boolean isSortKey(String fname) {
		return false;
	}

	@Override
	public void seek(Object from, boolean rev) {
		itr = listMap.iterator();		
	}

	@Override
	public boolean nextRecord() {
		m = itr.hasNext() ? itr.next() : null;
		return m != null ? true : false;
	}

	@Override
	public Map<String,Object> currentRecord() {
		return m;
	}

	@Override
	public Map<String,Object> deleteCurrent() {
		return m;
	}

	@Override
	public Map<String,Object> updateCurrent() {
		return m;
	}

	@Override
	public Object getFieldValue(String fname) {
		return m.get(fname);
	}

	@Override
	public void reset() {
		itr = null;
		m = null;		
	}

}
