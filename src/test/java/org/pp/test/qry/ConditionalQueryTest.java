package org.pp.test.qry;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.pp.qry.QueryImp;
import org.pp.qry.interfaces.Query;

public class ConditionalQueryTest extends AbstractTest {
		
	@Test(expected = UnsupportedOperationException.class)
	public void invalidEq() {
		String qry = "email = 'pan.prasanta@gmail.com' || total + 5";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = RuntimeException.class)
	public void invalidEqParan() {
		String qry = "(email = 'pan.prasanta@gmail.com' || total) + 5";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void invalidEqIn() {
		String qry = "(email = 'pan.prasanta@gmail.com' || gendar != true) && ((5 + units) in (20,5,6))";
		// get the query instance
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		// list those queries
		List<Map<String, Object>> list = q.list();
		// print
		print(list);
	}
	
	@Test()
	public void invalidnEqIn() {
		String qry = "(email != 'pan.prasanta@gmail.com' || gendar != true) && ((5 + units) in (20,5,6))";
		// get the query instance
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		// list those queries
		List<Map<String, Object>> list = q.list();
		// print
		print(list);
	}
	
	@Test()
	public void invalidEqNum() {
		String qry = "(price != 5 || gendar != true) && ((units + 2) in (17,5,6))";
		// get the query instance
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		// list those queries
		List<Map<String, Object>> list = q.list();
		// print
		print(list);
	}
	
	@Test()
	public void invalidEqGdn() {
		String qry = "(price != 5 || gendar != true) && !(units > 5 || total < 17)";
		// get the query instance
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
		// list those queries
		List<Map<String, Object>> list = q.list();
		// print
		print(list);
	}
}
