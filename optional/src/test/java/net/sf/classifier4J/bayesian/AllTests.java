package net.sf.classifier4J.bayesian;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for net.sf.classifier4J.bayesian");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(JDBMWordsDataSourceTest.class));
		//$JUnit-END$
		return suite;
	}
}
