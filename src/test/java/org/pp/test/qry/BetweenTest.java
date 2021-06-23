package org.pp.test.qry;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.pp.qry.QueryImp;
import org.pp.qry.interfaces.Query;

public class BetweenTest extends AbstractTestMap {

	@Test() 
	public void testBetweenWithRelOr() {
		String qry = "units between ? || (price > ? && units <= ?)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		// output
		List<Map<String, Object>> fList = q	.setParam(Arrays.asList(10, 20))
							        		.setParam(10)
							        		.setParam(20)
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
	public void testBetweenWithRelAnd() {
		String qry = "units between ? && (price < ? || units < ?)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		// output
		List<Map<String, Object>> fList = q	.setParam(Arrays.asList(10, 20))
							        		.setParam(5)
							        		.setParam(20)
							        		.list();
        // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 5.73f, 10, "46 eastwood rd"));
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));
        		
        vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 5.73f, 10, "46 eastwood rd"));
        vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 10.30f, 15, "46 eastwood rd"));
        		
        vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 5.73f, 10, "46 eastwood rd"));
        vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 10.30f, 15, "46 eastwood rd"));
        
		// verify both list
		assertTrue(isEqual(fList, vList));
	}
		
	@Test() 
	public void testNotBetweenWithRelAnd() {
		String qry = "units not between ? && (price > ? || units <= ?)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		// output
		List<Map<String, Object>> fList = q	.setParam(Arrays.asList(5, 10))
							        		.setParam(20)
							        		.setParam(15)
							        		.list();
        // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));        		
        vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 10.30f, 15, "46 eastwood rd"));        
        vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 10.30f, 15, "46 eastwood rd"));
        
		// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	@Test() 
	public void testNBetWithRelAnd() {
		String qry = "units nbet (5,10) && (price > ? || units <= ?)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		// output
		List<Map<String, Object>> fList = q	.setParam(20)
							        		.setParam(15)
							        		.list();
        // prepare output data
        List<Map<String, Object>> vList = new ArrayList<>();
        vList.add(getMap("Prasanta Pan", "pan.prasanta@gmail.com", 84984827, 10.30f, 15, "46 eastwood rd"));        		
        vList.add(getMap("Maumita Pan", "pan.maumita@gmail.com", 83995981, 10.30f, 15, "46 eastwood rd"));        
        vList.add(getMap("Moana Pan", "pan.moana@gmail.com", 83995982, 10.30f, 15, "46 eastwood rd"));
        
		// verify both list
		assertTrue(isEqual(fList, vList));
	}
	
	@Test(expected = RuntimeException.class) 
	public void testNBetErr() {
		String qry = "units nbet (5,10, && (price > ? || units <= ?)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		q.close();
	}
	
	@Test(expected = RuntimeException.class) 
	public void testNBetErrMoreThan2() {
		String qry = "units nbet (5,10, 15) && (price > ? || units <= ?)";
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		q.close();
	}
	
}
