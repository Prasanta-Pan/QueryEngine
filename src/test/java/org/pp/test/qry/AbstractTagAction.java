package org.pp.test.qry;

abstract class AbstractTagAction {
	
	/**
	 * Split and validate
	 * @param line
	 * @return
	 */
	protected String[] split(String line) {
		// split based on commas
		String[] fields = line.split(",");
		String[] newFields = new String[fields.length];
		// validate all fields
		for (int i = 0; i < fields.length; i++) {
			// check if it is not empty
			String fld = fields[i].trim();
			if ("".equals(fld))
				throw new RuntimeException("Invalid line: '" + line + "'");		
			newFields[i] = fld;
		}
		// return fields
		return newFields;
	}
	
	/**
	 * 
	 * @param line
	 */
	protected String[] validateFieldNames(String line, String tag) {
		// split based on commas
		String[] fields = split(line);
		// validate all fields
		for (String fld : fields) {
			// validate each end every character
			for (int i = 0; i < fld.length(); i++) {
				char ch = fld.charAt(i);
				if (!isAlphaNumeric(ch))
					throw new RuntimeException("Invalid field '" + fld + "' for #" + tag);
			}			
		}
		// return fields
		return fields;
	}
	
	/**
	 * Convert String to object
	 * @param token
	 * @return
	 */
	protected Object toObject(String token) {
		// if its a String
		if (token.startsWith("\"") && token.endsWith("\"")) {
			token = token.substring(1, token.length() - 1);
			// validate each end every character
			for (int i = 0; i < token.length(); i++) {
				char ch = token.charAt(i);
				if (!isAlphaNumeric(ch))
					throw new RuntimeException("Invalid token " + token);
			}
			// return token if everything looks good
			return token;
			// if it's number
		} else if (isNumber(token.charAt(0))) {
			// check if floating point number
			if (token.contains(".")) {
				// if it is double number
				if (token.endsWith("d") || token.endsWith("D")) {
					token = token.substring(0, token.length() - 1);
					return Double.parseDouble(token);
				}
				// must be Float number
				else
					return Float.parseFloat(token);
			}
			// check if it is long number
			else if (token.endsWith("l") || token.endsWith("L")) {
				token = token.substring(0, token.length() - 1);
				return Long.parseLong(token);
			}
			// must be integer number than
			else
				return Integer.parseInt(token);
		}
		// if null value
		else if ("null".equals(token))
			return null;
		// if boolean true
		else if ("true".equals(token))
			return true;
		// if boolean false
		else if ("false".equals(token))
			return false;
		// otherwise throw error
		else
			throw new RuntimeException("Invalid token '" + token + "'");
	}
	
	/**
	 * Check if the code point is number
	 * 
	 * @param uPoint
	 * @return
	 */
	protected boolean isNumber(char uPoint) {
		return uPoint == '.' || (uPoint > 47 && uPoint < 58) || uPoint == '+' || uPoint == '-';
	}
	
	/**
	 * Check alpha numeric and acceptable special charecter
	 * 
	 * @param uPoint
	 * @return
	 */
	protected boolean isAlphaNumeric(char uPoint) {
		return (uPoint > 62 && uPoint < 91) || // Upper case...
				(uPoint > 96 && uPoint < 123) || // Lower case...
				(uPoint > 47 && uPoint < 58) || // Digits
				(uPoint == 35 || uPoint == 36) || // #,$
				(uPoint == 45 || uPoint == 46) || // -, .
				(uPoint == 95); // _ (under score)
	}
	/**
     * 
     * @param input
     * @param ctx
     */
	abstract void init(String input, TestContext ctx);
	
	/**
	 * 
	 * @param input
	 * @param ctx
	 */
	abstract void process(String input, TestContext ctx);
	
	/**
	 * 
	 * @param ctx
	 */
	abstract void finalise(TestContext ctx);
}
