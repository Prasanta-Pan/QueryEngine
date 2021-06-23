package org.pp.test.qry;

import java.util.Map;

import org.pp.qry.interfaces.QueryContext;

public class MapQryCtxSorted implements QueryContext<Map<String,Object>> {
    
	
	@Override
	public boolean isSortKey(String fname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void seek(Object from, boolean rev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean nextRecord() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> currentRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteCurrent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateCurrent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getFieldValue(String fname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
