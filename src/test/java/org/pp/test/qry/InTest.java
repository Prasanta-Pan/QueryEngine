package org.pp.test.qry;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.pp.qry.QueryImp;
import org.pp.qry.interfaces.Query;

public class InTest extends AbstractTestMap {

	@Test() 
	public void testAllInParam() {
		String qry = "units in ?";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		// output
		Set<Integer> set = new HashSet<>(Arrays.asList(10,15,20));
		List<Map<String, Object>> fList = q	.setParam(set)
							        		.list();
        // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
		
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
	public void testAllNoIn() {
		String qry = "units not in (10,15,20)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.list();
        // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	@Test() 
	public void testNinWithOr() {
		String qry = "units nin (10,15,20) || (units >= ? || price > ?)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam(20)
										   .setParam(15)
										   .list();
        // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
        vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 15.54f, 20, "46 eastwood rd"));
        vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 15.54f, 20, "46 eastwood rd"));
        
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	@Test() 
	public void testInWithAnd() {
		String qry = "units in (10,15,20) && (price > ? || units >= ?)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam(11)
										   .setParam(20)
										   .list();
        // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
		vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 15.54f, 20, "46 eastwood rd"));
		vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 15.54f, 20, "46 eastwood rd"));
        
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
	@Test(expected = RuntimeException.class) 
	public void testInError() {
		String qry = "units in (10,15, && (price > ? || units >= ?)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam(11)
										   .setParam(20)
										   .list();
        // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
		vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 15.54f, 20, "46 eastwood rd"));
		vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 15.54f, 20, "46 eastwood rd"));
        
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
}
