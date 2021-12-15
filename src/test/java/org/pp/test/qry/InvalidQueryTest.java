package org.pp.test.qry;

import java.util.Map;

import org.junit.Test;
import org.pp.qry.QueryImp;
import org.pp.qry.interfaces.Query;

public class InvalidQueryTest extends AbstractTest {

	@Test(expected = UnsupportedOperationException.class)
	public void invalidAddBoolean() {
		String qry = "email + 5 || true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void invalidAddSet() {
		String qry = "email + 5 in (4,5,7)";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void invalidMulString() {
		String qry = "email + 'test' in (4,5,7)";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void invalidParanthesis() {
		String qry = "a * (b + c) > null";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void invalidCondBool() {
		String qry = "a * (b + c) > true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void invalidCondString() {
		String qry = "a * (b + c) > 'true'";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void invalidCondIn() {
		String qry = "a * (b + c) in ('true', 'false')";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void invalidInAdd() {
		String qry = "(a * (b + c) in (5, 7, 9)) + 5 > 9 + ID";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void invalidNestedExpr() {
		String qry = "a * (b + c) + (5 + 9 / P) + 5 > 9 + ID";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void invalidRandom() {
		String qry = "(a * (b + c) > 5 in (4,5,6) || b between (4, 5)) && b";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void invalidParam() {
		String qry = "a + (b + v) * c > id";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void invalidStringMul() {
		String qry = "a > 'test' || c > id";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void invalidStringOr() {
		String qry = "'test' || true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void invalidIn() {
		String qry = "'test' in (5, 6, 7) || true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test()
	public void validInString() {
		String qry = "? in ('pp', 'mp', 'mc') || true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = RuntimeException.class)
	public void invalidInString() {
		String qry = "? in ('pp', 'mp', 5) || true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = RuntimeException.class)
	public void invalidInBool() {
		String qry = "ID in (true, false) || true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = RuntimeException.class)
	public void invalidBetBool() {
		String qry = "ID bet (1, false) || true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = RuntimeException.class)
	public void invalidnBetBool() {
		String qry = "ID nbet (1, false) || true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = RuntimeException.class)
	public void invalidnotBetBool() {
		String qry = "ID not between (1, false) || true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
	@Test(expected = RuntimeException.class)
	public void invalidnotInBool() {
		String qry = "ID not in (1, 2, false) || true";
		@SuppressWarnings("unused")
		Query<Map<String, Object>> q = new QueryImp<>(qry, new MapQryCtx(getList()));
	}
	
}
