package org.pp.test.qry;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.pp.qry.QueryImp;
import org.pp.qry.interfaces.Query;
import org.pp.qry.interfaces.QueryDataProvider;

public class ArithmeticQryTest extends AbstractTest {

	@Test()
	public void mixDataType() {
		String qry = "email + 5l - +0.45d * 5.67 > 5";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void mixDataTypeInvalid() {
		String qry = "email + 5l - +0.45d * 5.67 || 5";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void mixDataTypeNegDouble() {
		String qry = "email + 5l - -0.45d * 5.67 > 5";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void mixDataTypeDouble() {
		String qry = "email + 5l - 567.45d * 5.67 > 5";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void mixDataTypeLong() {
		String qry = "email + 555555l - 567.45d * 5.67 > 5";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void mixDataTypeDiv() {
		String qry = "email + 555555l - 567.45d * 5.67 > 5 / ID % 45";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void mixDataTypeParan() {
		String qry = "(email + 555555l - 567.45d) * 5.67 > 5 / (ID % 45)";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void mixDataTypeParam() {
		String qry = "(i + ? - 567.45d) * 5.67 < 5 / (l % ?) + 8000";
		// get sample data list
		QueryDataProvider<Map<String,Object>> qdp = new MapQryCtx(getListArith());
		// get instance of Query Implementation
		Query<Map<String, Object>> q = new QueryImp<>(qry, qdp);
		// set parameters
		long t = System.nanoTime();
		q.setParam(1, 876.45);
		q.setParam(2, 691.87d);
		// execute query and get filtered list
		List<Map<String, Object>> list = q.list();
		System.out.println(System.nanoTime() - t);
		// print it
		print(list);		
	}
	
	
}
