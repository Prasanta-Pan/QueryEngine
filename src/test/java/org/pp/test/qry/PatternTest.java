package org.pp.test.qry;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;

public class PatternTest {
	
	@Ignore()
	public void regx() {
		// string
		String[] emails = new String[] {
				"pan.prasanta@gmail.com",
				"pan.maumita@gmail.com",
				"pan.moana@gmail.com" ,
				"pan.*",
				".pan*"
		};
		// patterns
		String[] patterns = new String[] {
				"\\.pan\\*",
				"pan\\.*",
				"pan\\.\\*",
				".*maumita",
				".*maumita.*",
				"maumita.*",
				".*prasanta.*",
				"pan.*com",
				".com",
				".........@gmail.com"
		};
		// print
		for (String ptr: patterns) {
			// compile
			Pattern p = Pattern.compile(ptr);
			// start matching
			for (String email: emails) {
				Matcher m = p.matcher(email);
				System.out.println(ptr + ", " + email + ", " + m.matches());
			}
		}		
	}
	
	public String escapeTest(String s) {
		// escape sequence indicator
		boolean escape = false;
		// to build new String
		StringBuilder sbldr = new StringBuilder();
		// scan through all character
		for (int i = 0; i < s.length(); i++) {
			// get the character
			char ch = s.charAt(i);
			//
			switch (ch) {
			    // replace % -> .*
				case '%' 
						:
							if (!escape) { sbldr.append(".*"); continue; }
							break;
				// replace - -> .
				case '_' 
						:
							if (!escape) { sbldr.append('.'); continue;	}
							break;
				case '\\' 
		 				:
		 					if (!escape) { escape = true; continue; }
				case '*' 
						:													
				case '.'
						:   // restored escape
							if (escape)	
								sbldr.append("\\");
							// escape '*' or '.'
							sbldr.append("\\");
							break;	
				default
						:
							// restored escape
							if (escape)	
								sbldr.append("\\");
							break;
							
			}
            // make escape false
			escape = false;
			// append character
			sbldr.append(ch);
		}
		// return result
		return sbldr.toString();
	}
	
	/**
	 * Split string based on regular expression
	 * @param s
	 * @return
	 */
	public List<String> split(String s) {
		// first trim string
		s = s.trim();
		// list of strings to return
		List<String> rlist = new LinkedList<>();
		// escape sequence indicator
		boolean escape = false;
		// to build new String
		StringBuilder sbldr = new StringBuilder();
		// scan through all character
		for (int i = 0; i < s.length(); i++) {
			// get the character
			char ch = s.charAt(i);
			//
			switch (ch) {
			    // replace % -> .*
				case '%' 
						:
							if (!escape) {
								// add substring
								rlist.add(sbldr.toString());
								// reset string builder
								sbldr.setLength(0);
								continue;
							}
							break;
				
				case '\\' 
		 				:
		 					if (!escape) { escape = true; continue; }
		 					sbldr.append('\\');
		 					break;
		 		default
						:	
		 					if (escape) { sbldr.append('\\'); }
		 					break;
			}
            // make escape false
			escape = false;
			// append character
			sbldr.append(ch);
		}
		// add last substring
		rlist.add(sbldr.toString());
		// return result
		return rlist;
	}
	
	/**
	 * Match patterns against source String
	 * @param pl
	 * @param s
	 * @return
	 */
	public boolean match(List<String> pl, String s) {
		// loop throw patterns
		for (String p: pl) {
			// skip if empty
			if ("".equals(p))
				continue;
			
		}
		
		return false;
	}
	
	public int match(String p, String s, int index) {
		// Pattern and String character
		char pch, sch;
		for (int i = 0, j = 0; i < p.length() && j < p.length();) {
			// get the character
			char ch = s.charAt(i);
			//
			switch (ch) {
			   
				case '\\'	:
							break;
				case '_'	:
					   break;
			    default 	:
			    		// get pattern character
			    	    pch = p.charAt(j);
			    	    sch = s.charAt(i);
			    	    if (pch != sch)
			    	    	i++;
			    	    else {
			    	    	i++;
			    	    	j++;
			    	    }
			    		break;
			}
		}
		
		return 0;
	}
	
	@Ignore()
	public void escape() {
		// string
		String[] patterns = new String[] { 
				"pan%pra%nta__", 
				"pan\\%pra%_gmail\\.", 
				"pan\\*\\.%_ta",
				"pan\\__%ta"				
		};
		// print
		for (String p: patterns)
			System.out.println(p + " --> " + escapeTest(p));
	}
	
	@Test()
	public void splitTest() {
		// string
		String[] patterns = new String[] { 
				"pan%pra%nta__", 
				"pan\\%pra%_gmail\\.", 
				"pan\\*\\.%_ta",
				"pan\\__%ta" ,
				"pan%",
				"%pan%"
		};
		// print
		for (String p: patterns)
			System.out.println(p + " --> " + Arrays.toString(split(p).toArray()));
	}
	
	
	
}
