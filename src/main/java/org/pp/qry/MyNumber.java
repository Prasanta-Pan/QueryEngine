package org.pp.qry;
/**
 * 
 * @author prasantsmac
 *
 */
class MyNumber implements Comparable<MyNumber> {
	// number type int=0, long = 1, float = 2, double = 3
	int type = -1;
	// Java number object
	Number numObj = null;
	// define data type
	static final int INT = 0;
	static final int LONG = 1;
	static final int FLT = 2;
	static final int DBL = 3;

	// constructors
	MyNumber(int t, Object num) {
		this.numObj = (Number) num;
		this.type = t;
	}

	MyNumber(String numStr, int t) {
		type = t;
		switch (t) {
			case INT:
				numObj = Integer.parseInt(numStr);
				break;
			case LONG:
				numObj = Long.parseLong(numStr);
				break;
			case FLT:
				numObj = Float.parseFloat(numStr);
				break;
			case DBL:
				numObj = Double.parseDouble(numStr);
				break;
		}
	}    

	@Override
	public int hashCode() {
		return numObj.hashCode();
	}

	@Override
	public boolean equals(Object othr) {
		MyNumber o = (MyNumber) othr;
		return compareTo(o) == 0 ? true : false;
	}

	// addition
	public MyNumber add(MyNumber o) {
		int t = type >= o.type ? type : o.type;
		// INT or long
		if (t < FLT) {
			if (type == LONG || o.type == LONG) {
				long ll = numObj.longValue();
				long rl = o.numObj.longValue();
				return new MyNumber(LONG, ll + rl);
			}
			// continue with INT otherwise
			int li = numObj.intValue();
			int ri = o.numObj.intValue();
			return new MyNumber(INT, li + ri);
		} else if (t < DBL) { // Could INT, long or float
			// if long raise to double
			if (type == LONG || o.type == LONG) {
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return new MyNumber(DBL, ld + rd);
			}
			// ...either float
			float lf = numObj.floatValue();
			float rf = o.numObj.floatValue();
			return new MyNumber(FLT, lf + rf);
		} else { // raise all to double
			double ld = numObj.doubleValue();
			double rd = o.numObj.doubleValue();
			return new MyNumber(DBL, ld + rd);
		}
	}

	// subtraction
	public MyNumber sub(MyNumber o) {
		int t = type >= o.type ? type : o.type;
		// INT or long
		if (t < FLT) {
			if (type == LONG || o.type == LONG) {
				long ll = numObj.longValue();
				long rl = o.numObj.longValue();
				return new MyNumber(LONG, ll - rl);
			}
			// continue with INT otherwise
			int li = numObj.intValue();
			int ri = o.numObj.intValue();
			return new MyNumber(INT, li - ri);
		} else if (t < DBL) { // Could INT, long or float
			// if long raise to double
			if (type == LONG || o.type == LONG) {
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return new MyNumber(DBL, ld - rd);
			}
			// ...either float
			float lf = numObj.floatValue();
			float rf = o.numObj.floatValue();
			return new MyNumber(FLT, lf - rf);
		} else { // raise all to double
			double ld = numObj.doubleValue();
			double rd = o.numObj.doubleValue();
			return new MyNumber(DBL, ld - rd);
		}
	}

	// multiplication
	public MyNumber mul(MyNumber o) {
		int t = type >= o.type ? type : o.type;
		// INT or long
		if (t < FLT) {
			if (type == LONG || o.type == LONG) {
				long ll = numObj.longValue();
				long rl = o.numObj.longValue();
				return new MyNumber(LONG, ll * rl);
			}
			// continue with INT otherwise
			int li = numObj.intValue();
			int ri = o.numObj.intValue();
			return new MyNumber(INT, li * ri);
		} else if (t < DBL) { // Could INT, long or float
			// if long raise to double
			if (type == LONG || o.type == LONG) {
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return new MyNumber(DBL, ld * rd);
			}
			// ...either float
			float lf = numObj.floatValue();
			float rf = o.numObj.floatValue();
			return new MyNumber(FLT, lf * rf);
		} else { // raise all to double
			double ld = numObj.doubleValue();
			double rd = o.numObj.doubleValue();
			return new MyNumber(DBL, ld * rd);
		}
	}

	// divide
	public MyNumber div(MyNumber o) {
		int t = type >= o.type ? type : o.type;
		// INT or long
		if (t < FLT) {
			if (type == LONG || o.type == LONG) {
				long ll = numObj.longValue();
				long rl = o.numObj.longValue();
				return new MyNumber(LONG, ll / rl);
			}
			// continue with INT otherwise
			int li = numObj.intValue();
			int ri = o.numObj.intValue();
			return new MyNumber(INT, li / ri);
		} else if (t < DBL) { // Could INT, long or float
			// if long raise to double
			if (type == LONG || o.type == LONG) {
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return new MyNumber(DBL, ld / rd);
			}
			// ...either float
			float lf = numObj.floatValue();
			float rf = o.numObj.floatValue();
			return new MyNumber(FLT, lf / rf);
		} else { // raise all to double
			double ld = numObj.doubleValue();
			double rd = o.numObj.doubleValue();
			return new MyNumber(DBL, ld / rd);
		}
	}

	// remainder
	public MyNumber remainder(MyNumber o) {
		int t = type >= o.type ? type : o.type;
		// INT or long
		if (t < FLT) {
			if (type == LONG || o.type == LONG) {
				long ll = numObj.longValue();
				long rl = o.numObj.longValue();
				return new MyNumber(LONG, ll % rl);
			}
			// continue with INT otherwise
			int li = numObj.intValue();
			int ri = o.numObj.intValue();
			return new MyNumber(INT, li % ri);
		} else if (t < DBL) { // Could INT, long or float
			// if long raise to double
			if (type == LONG || o.type == LONG) {
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return new MyNumber(DBL, ld % rd);
			}
			// ...either float
			float lf = numObj.floatValue();
			float rf = o.numObj.floatValue();
			return new MyNumber(FLT, lf % rf);
		} else { // raise all to double
			double ld = numObj.doubleValue();
			double rd = o.numObj.doubleValue();
			return new MyNumber(DBL, ld % rd);
		}
	}

	@Override
	public int compareTo(MyNumber o) {
		int t = type >= o.type ? type : o.type;
		// INT and long
		if (t < FLT) {
			// if one of the type is LONG
			if (type == LONG || o.type == LONG) {
				long ll = numObj.longValue();
				long rl = o.numObj.longValue();
				return (ll < rl) ? -1 : ((ll == rl) ? 0 : 1);
			}
			// ...or INT if not
			int li = numObj.intValue();
			int ri = o.numObj.intValue();
			return (li < ri) ? -1 : ((li == ri) ? 0 : 1);
		}
		// FLoat
		else if (t < DBL) {
			// if long raise to double
			if (type == LONG || o.type == LONG) {
				double ld = numObj.doubleValue();
				double rd = o.numObj.doubleValue();
				return (ld < rd) ? -1 : ((ld == rd) ? 0 : 1);
			}
			// ...either float
			float lf = numObj.floatValue();
			float rf = o.numObj.floatValue();
			return (lf < rf) ? -1 : ((lf == rf) ? 0 : 1);
		}
		// double
		else {
			double ld = numObj.doubleValue();
			double rd = o.numObj.doubleValue();
			return (ld < rd) ? -1 : ((ld == rd) ? 0 : 1);
		}
	}
}
