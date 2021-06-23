package org.pp.test.qry;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.pp.qry.QueryImp;
import org.pp.qry.interfaces.Query;

public class EqNeqTest extends AbstractTestMap {
     
	@Test() 
	public void testAllEq() {
		String qry = "email = 'pan.prasanta@gmail.com'";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.list();
        // prepare output data
		 // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
		
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	@Test(expected = RuntimeException.class) 
	public void testAllEqErr() {
		String qry = "email = 'pan.prasanta@gmail.com";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		q.list();        
	}
	
	@Test() 
	public void testEqWithAnd() {
		String qry = "email = 'pan.prasanta@gmail.com' && (price > ? || units < ?)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam(15)
										   .setParam(10)
										   .list();
        // prepare output data
		 // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
		// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	@Test() 
	public void testEqWithIn() {
		String qry = "email = ? && units in (10,15)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam("pan.prasanta@gmail.com")
										   .list();
        // prepare output data
		 // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	@Test() 
	public void testNotEqAll() {
		String qry = "email != ?";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam("pan.prasanta@gmail.com")
										   .list();
        // prepare output data
		 // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 5.73f, 10, "46 eastwood rd"));
        vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 10.30f, 15, "46 eastwood rd"));
        vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 15.54f, 20, "46 eastwood rd"));
		
        vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 5.73f, 10, "46 eastwood rd"));
        vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 10.30f, 15, "46 eastwood rd"));
        vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 15.54f, 20, "46 eastwood rd"));
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	@Test() 
	public void testNotEqAndIn() {
		String qry = "email != ? && units in (15,20)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam("pan.prasanta@gmail.com")
										   .list();
        // prepare output data
		 // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 10.30f, 15, "46 eastwood rd"));
        vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 15.54f, 20, "46 eastwood rd"));
		
        vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 10.30f, 15, "46 eastwood rd"));
        vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 15.54f, 20, "46 eastwood rd"));
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
}
