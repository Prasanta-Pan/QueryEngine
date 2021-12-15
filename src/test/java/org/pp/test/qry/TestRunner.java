package org.pp.test.qry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TestRunner {
	/**
	 * To read test file
	 */
	private BufferedReader reader;
	/**
	 * Registered action map
	 */
	static final Map<String, AbstractTagAction> actions = new HashMap<>();
	/**
	 * Initialise Tag Handler
	 */
	static {
		// Test Name tag handler
		actions.put("TestName", new TextTagHandler("TestName"));
		// query tag handler
		actions.put("Query", new TextTagHandler("Query"));
		// Reverse tag handler
		actions.put("Reverse", new ReverseOrderHandler());
		// input tag handler
		actions.put("input", new ListMapHandler("input"));
		// output tag handler
		actions.put("output", new ListMapHandler("output"));
		// error tag handler
		actions.put("error", new TextTagHandler("error"));
		// Sort Keys tag handler
		actions.put("SortKeys", new SortKeyHandler());		
	}
	
    /**
     * No outside access
     */
	private TestRunner() { }
	
	
	public static void main(String[] args) throws Exception {
		// run the tests
		newTestRunner("AllTests.txt").run();
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	static final TestRunner newTestRunner(String testFileName) throws Exception {
		// Get the file URL
		URL url = TestRunner.class.getClassLoader().getResource(testFileName);
		if (url == null)
			throw new RuntimeException("Not found " + testFileName);
		// Open buffered reader
		TestRunner runner = new TestRunner();
		runner.reader = new BufferedReader(new FileReader(new File(url.toURI())), 1024 * 1024);
		return runner;
	}
	/**
	 * Run all tests one by one
	 */
	void run() throws Exception {
		// current tag handler
		AbstractTagAction curTagHdlr = null;
		// current test context
		TestContext tCtx = null;
		// read test file line by line
		for (String line; (line = reader.readLine()) != null;) {
			// trim it
			line = line.trim();
			// skip empty line and comment
			if ("".equals(line) || line.startsWith("//"))
				continue;
			// if action tag found
			else if (line.startsWith("#")) {
				// remove # and get the tag name
				line = line.substring(1).trim();
				// check if their was any tag handler.
				if (curTagHdlr != null)
					curTagHdlr.finalise(tCtx);
				// new Action state
				curTagHdlr = actions.get(line);
				// if tag handler already registered
				if (curTagHdlr != null) {
					// check if start of new Test Cycle
					if ("TestName".equals(line)) {
						// execute test
						if (tCtx != null)
						  tCtx.execute();						
						// initiate a new Test Context
						tCtx = new TestContext();							
					}
					// initialise tag handler
					curTagHdlr.init(line, tCtx);					
				}
				// throw Exception
				else
					throw new RuntimeException("Unknown Tag #" + line);					
			}
			// if a tag handler currently in action
			else if (curTagHdlr != null)
				curTagHdlr.process(line, tCtx);
			// else throw exception
			else
				throw new RuntimeException("Expecting a #tag");
		}
		// execute the last test
		if (tCtx != null)
			tCtx.execute();
	}
	
	/**
	 * To handle Query and error tag
	 * @author prasantsmac
	 *
	 */
	static final class TextTagHandler extends AbstractTagAction {
		/**
		 * The tag to handle
		 */
		private String tag;
		/**
		 * To support input building
		 */
		private StringBuilder sb = new StringBuilder();
		
		/**
		 * Common Test tag handler
		 * @param tag
		 */
		private TextTagHandler(String tag) {
			this.tag = tag;
		}

		@Override
		void init(String input, TestContext ctx) {
			// TODO Auto-generated method stub			
		}

		@Override
		void process(String input, TestContext ctx) {
			// TODO Auto-generated method stub
			sb.append(input);			
		}

		@Override
		void finalise(TestContext ctx) {
			// TODO Auto-generated method stub
			String input = sb.toString();
			// check if proper input was provided
			if ("".equals(input))
				throw new RuntimeException("No input for #" + tag);
			// set field accordingly	
			switch (tag) {
				case "TestName":
					ctx.setTestName(input);
					break;
				case "Query":
					ctx.setQry(input);
					break;
				case "error":
					ctx.setErrMsg(input);
					break;				    
			}
			// reset string builder
			sb.setLength(0);
		}
	}
	
	/**
	 * To handle input and output 
	 * @author prasantsmac
	 *
	 */
	static final class ListMapHandler extends AbstractTagAction {
		/**
		 * The tag to handle
		 */
		private String tag;
		/**
		 * Fields to map
		 */
		private String[] fields;
		
		/**
		 * 
		 * @param tag
		 */
		private ListMapHandler(String tag) {
			this.tag = tag;
		}

		@Override
		void init(String input, TestContext ctx) {
			// TODO Auto-generated method stub
			
		}

		@Override
		void process(String input, TestContext ctx) {
			// get field names
			if (fields == null) 
				fields = validateFieldNames(input, tag);
			else {
				// split line
				String[] data = split(input);
				// verify field and data count
				if (data.length != fields.length)
					throw new RuntimeException("Fileds and data count doesnt match");
				// instantiate a map
				Map<String,Object> m = new HashMap<>();
				// iterate throw all tokens
				for (int i = 0; i < data.length; i++) {
					// convert to object
					Object val = toObject(data[i]);
					// set to the map
					m.put(fields[i], val);					
				}
				// add to list
				if ("input".equals(tag))
					ctx.getInput().add(m);
				else
					ctx.getExpectedOutput().add(m);
			}
		}

		@Override
		void finalise(TestContext ctx) {
			// TODO Auto-generated method stub
			fields = null;
		}
		
	}
	
	/**
	 * 
	 * @author prasantsmac
	 *
	 */
	static final class SortKeyHandler extends AbstractTagAction {
		/**
		 * Indicate Sort keys validation is over or not
		 */
        private boolean done = false;
        
		@Override
		void init(String input, TestContext ctx) {
			// TODO Auto-generated method stub
			
		}

		@Override
		void process(String input, TestContext ctx) {
			// TODO Auto-generated method stub
			if (!done) {
				// get sort key fields
				String[] fields = validateFieldNames(input, "SortKeys");
				// ensure input list is not empty
				List<Map<String, Object>> lm = ctx.getInput();
				if (lm.size() == 0)
					throw new RuntimeException("No Input has been provided to validate sort keys");
				// verify that all sort keys are present in map
				Map<String, Object> m = lm.get(0);
				for (String fld : fields) {
					if (!m.containsKey(fld))
						throw new RuntimeException("SortKey field '" + fld + "' is not present in input");					
				}
				// store sort keys
				ctx.setSortKeys(fields);
				// mark as done
				done = true;
			}
			else 
				throw new RuntimeException("Sort Keys are already processed");
		}

		@Override
		void finalise(TestContext ctx) {
			// TODO Auto-generated method stub
			done = false;
		}		
		
	}
	
	/**
	 * 
	 * @author prasantsmac
	 *
	 */
	static final class ReverseOrderHandler extends AbstractTagAction {
		/**
		 * Indicate reverse order processing is done
		 */
        private boolean done = false;
        
		@Override
		void init(String input, TestContext ctx) {
			// TODO Auto-generated method stub
			
		}

		@Override
		void process(String input, TestContext ctx) {
			// TODO Auto-generated method stub
			if (!done) {
				ctx.setReverse(Boolean.parseBoolean(input));
				done = true;
			}
			else 
				throw new RuntimeException("Reverse order is already processed");
		}

		@Override
		void finalise(TestContext ctx) {
			// TODO Auto-generated method stub
			done = false;
		}
		
	}
	
	/**
	 * To handle parameters
	 * @author prasantsmac
	 *
	 */
	static final class ParameterHandler extends AbstractTagAction {

		@Override
		void init(String input, TestContext ctx) {
			// TODO Auto-generated method stub
			
		}

		@Override
		void process(String input, TestContext ctx) {
			// TODO Auto-generated method stub
						
		}

		@Override
		void finalise(TestContext ctx) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
