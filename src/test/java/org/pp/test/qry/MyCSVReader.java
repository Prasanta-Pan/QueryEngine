package org.pp.test.qry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCSVReader {
	
	/**
	 * basic test of resource loading
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String path = getAbsPath("org/pp/test/qry/input.txt");
		List<Map<String, Object>> lm = toListMap(path);
		System.out.println(lm.size());
	}
	
   /**
    * 
    * @param fPath
    * @return
    */
   public static final List<Map<String, Object>> toListMap(String fPath) throws Exception {
	   	List<Map<String, Object>> lm = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fPath), 4096)) {
			// to build token
			StringBuilder sb = new StringBuilder();
			// List of columns
			List<String> colmns = new ArrayList<>();
			// next line
			String line = null;			
			/**
			 * Read line by line
			 */
			for (int r = 1; (line = br.readLine()) != null; r++) {
				String[] tokens = line.split(",");
				// check if column count miss match
				if (colmns.size() > 0 && colmns.size() != tokens.length)
					throw new RuntimeException("Extra column @" + r);
				// allocate new hash map
				Map<String, Object> m = new HashMap<>();
				for (int i = 0; i < tokens.length; i++) {
					try {
						Object val = stringToObj(sb, tokens[i]);
						if (i == colmns.size()) {
							colmns.add((String) val);
							continue;
						}
						else 
							m.put(colmns.get(i), val);							
						
					} catch (IllegalStateException e) {
						throw new RuntimeException("Invalid token '" + tokens[i] + "' @" + r + "," + i);
					} finally {
						sb.setLength(0);
					}					
				}
				// add map to the list
				if (m.size() > 0)
				   lm.add(m);
			}
		}

	   return lm;
   }
   /**
    * Convert token to object
    * @param sbldr
    * @param token
    * @return
    */
   private static final Object stringToObj(StringBuilder sbldr, String token) {
		int type = -1, state = -1;
		// continue recognising operand
		for (int uPoint = -1, pos = 0; pos < token.length(); pos++) {
			uPoint = token.codePointAt(pos);
			// state machine
			switch (state) {
			// initial state
			case -1:
				if (Character.isWhitespace(uPoint))
					continue;
				// if start of string
				if (uPoint == '\"') {
					state = type = 0;					
					continue;
				}
				// If start of number
				if (isNumber(uPoint))
					state = type = 1; // default integer type
				// keyword
				else
					state = type = 5;
				pos--;
				continue;
			// abandoned state
			case -2:
				if (Character.isWhitespace(uPoint))
					continue;
				else
					throw new IllegalStateException();
			// String state
			case 0:
				if (uPoint == '\"') {
					state = -2;
					continue;
				}				
				break;
			// Number state
			case 1:
				if (uPoint == '.')
					type = 2; // float type
				// verify other type
				else if (!isNumber(uPoint)) {
					if (uPoint == 'd' || uPoint == 'D')
						type = 4; // Double type
					else if (uPoint == 'l' || uPoint == 'L')
						type = 3; // Long type
					else 
					    pos--;
					// move to abandoned state
					state = -2;
					continue;
				}
				break;
			// keyword
			case 5:
				// keep adding code point
				if (Character.isWhitespace(uPoint)) {
					state = -2;
					continue;
				}				
				break;
			default:
				throw new IllegalStateException();
			}
			// keep adding code points
			sbldr.appendCodePoint(uPoint);
		}

		// Create proper object based upon type
		switch (type) {
		// Keyword
		case 5:
			String id = sbldr.toString().trim();
			if ("true".equals(id))
				return true;
			else if ("false".equals(id))
				return false;
			else if ("null".equals(id))
				return null;
			else
				throw new IllegalStateException();
			// String type
		case 0:
			return sbldr.toString();
		// integer
		case 1:
			return Integer.parseInt(sbldr.toString());
		// Float
		case 2:
			return Float.parseFloat(sbldr.toString());
		// Long
		case 3:
			return Long.parseLong(sbldr.toString());
		// Double
		case 4:
			return Double.parseDouble(sbldr.toString());
		default:
			throw new IllegalStateException();
		}
   }
   /**
	 * Check if the code point is number
	 * 
	 * @param uPoint
	 * @return
	 */
	private static boolean isNumber(int uPoint) {
		return uPoint == '.' || 
				(uPoint > 47 && uPoint < 58) || 
				uPoint == '+' || uPoint == '-';
	}
	/**
	 * Get absolute path of 
	 * @param fname
	 * @return
	 */
	static final String getAbsPath(String fname) {
		return MyCSVReader.class.getClassLoader().getResource(fname).getPath();
	}
}
