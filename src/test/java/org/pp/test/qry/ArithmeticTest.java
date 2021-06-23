package org.pp.test.qry;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.pp.qry.QueryImp;
import org.pp.qry.interfaces.Query;

public class ArithmeticTest extends AbstractTestMap {
	
	@Test() 
	public void testPlusMinus() {
		String qry = "units + ? - ? < 20 && email = 'pan.prasanta@gmail.com'";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam(5)
										   .setParam(5)
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
	public void testMulDiv() {
		String qry = "units * ? / ? < 30 && email = 'pan.prasanta@gmail.com'";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam(5)
										   .setParam(5)
										   .list();
        // prepare output data
		 // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 15.54f, 20, "46 eastwood rd"));
        	
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	@Test() 
	public void testParan() {
		String qry = "units + (5 * ?) <= 35d && email = 'pan.prasanta@gmail.com'";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam(5)
										   .list();
        // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
               	
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	@Test(expected = RuntimeException.class) 
	public void testParanErr() {
		String qry = "units + (5 * ? <= 35d && email = 'pan.prasanta@gmail.com'";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		List<Map<String, Object>> fList = q.setParam(5)
										   .list();
        // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
               	
       	// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	
}
