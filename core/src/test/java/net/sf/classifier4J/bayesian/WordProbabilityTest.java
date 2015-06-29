/*
 * ====================================================================
 * 
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 Nick Lothian. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        developers of Classifier4J (http://classifier4j.sf.net/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The name "Classifier4J" must not be used to endorse or promote 
 *    products derived from this software without prior written 
 *    permission. For written permission, please contact   
 *    http://sourceforge.net/users/nicklothian/.
 *
 * 5. Products derived from this software may not be called 
 *    "Classifier4J", nor may "Classifier4J" appear in their names 
 *    without prior written permission. For written permission, please 
 *    contact http://sourceforge.net/users/nicklothian/.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package net.sf.classifier4J.bayesian;

import junit.framework.TestCase;
import junit.textui.TestRunner;
import net.sf.classifier4J.IClassifier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Nick Lothian
 * @author Peter Leschev
 */
public class WordProbabilityTest extends TestCase {

	private Log log = LogFactory.getLog(this.getClass());

	public WordProbabilityTest(String name) {
		super(name);
	}

	public void testAccessors() {
		WordProbability wp = null;

		wp = new WordProbability("", 0.96d);
		assertEquals("", wp.getWord());
		try {
			assertEquals(0, wp.getMatchingCount());
			fail("Shouldn't be able to obtain matching count when we haven't set them");
		} catch (UnsupportedOperationException e) {
			assertTrue(true);
		}
		try {
			assertEquals(0, wp.getNonMatchingCount());
			fail("Shouldn't be able to obtain matching count when we haven't set them");
		} catch (UnsupportedOperationException e) {
			assertTrue(true);
		}
		assertEquals(0.96d, wp.getProbability(), 0);

		wp = new WordProbability("aWord", 10, 30);
		assertEquals("aWord", wp.getWord());
		assertEquals(10, wp.getMatchingCount());
		assertEquals(30, wp.getNonMatchingCount());
		assertEquals(0.25d, wp.getProbability(), 0d);
                
                try {
                    wp.setMatchingCount(-10);
                    fail("Shouldn't be able to set -ve matchingCount");
                }
                catch(IllegalArgumentException e) {
                    assertTrue(true);
                }
                
                try {
                    wp.setNonMatchingCount(-10);
                    fail("Shouldn't be able to set -ve nonMatchingCount");
                }
                catch(IllegalArgumentException e) {
                    assertTrue(true);
                }
	}

	public void testCalculateProbability() {

		WordProbability wp = null;

		wp = new WordProbability("", 10, 10);
		assertEquals(IClassifier.NEUTRAL_PROBABILITY, wp.getProbability(), 0);

		wp = new WordProbability("", 20, 10);
		assertEquals(0.66, wp.getProbability(), 0.01);

		wp = new WordProbability("", 30, 10);
		assertEquals(0.75, wp.getProbability(), 0);

		wp = new WordProbability("", 10, 20);
		assertEquals(0.33, wp.getProbability(), 0.01);

		wp = new WordProbability("", 10, 30);
		assertEquals(0.25, wp.getProbability(), 0);

		wp = new WordProbability("", 10, 0);
		assertEquals(IClassifier.UPPER_BOUND, wp.getProbability(), 0);

		wp = new WordProbability("", 100, 1);
		assertEquals(IClassifier.UPPER_BOUND, wp.getProbability(), 0);

		wp = new WordProbability("", 1000, 1);
		assertEquals(IClassifier.UPPER_BOUND, wp.getProbability(), 0);

		wp = new WordProbability("", 0, 10);
		assertEquals(IClassifier.LOWER_BOUND, wp.getProbability(), 0);

		wp = new WordProbability("", 1, 100);
		assertEquals(IClassifier.LOWER_BOUND, wp.getProbability(), 0);

		wp = new WordProbability("", 1, 1000);
		assertEquals(IClassifier.LOWER_BOUND, wp.getProbability(), 0);
	}

	public void testComparator() {

		String method = "testComparator() ";

		WordProbability wp = null;
		WordProbability wp2 = null;

		wp = new WordProbability("a", 0, 0);
		wp2 = new WordProbability("b", 0, 0);

		try {
			wp.compareTo(new Object());
			fail("Shouldn't be able to compareTo objects other than WordProbability");
		} catch (ClassCastException e) {
			assertTrue(true);
		}

		if (log.isDebugEnabled()) {
			log.debug(method + "wp.getProbability() " + wp.getProbability());
			log.debug(method + "wp2.getProbability() " + wp2.getProbability());
		}

		assertTrue(wp.compareTo(wp2) < 0);
		assertTrue(wp2.compareTo(wp) > 0);
	}

        public void testMatchingAndNonMatchingCountRollover() {
            
            WordProbability wp = new WordProbability("aWord", Long.MAX_VALUE, Long.MAX_VALUE);
            try {
                wp.registerMatch();
                fail("Should detect rollover");
            }
            catch(UnsupportedOperationException e) {
                assertTrue(true);
            }
            try {
                wp.registerNonMatch();
                fail("Should detect rollover");
            }
            catch(UnsupportedOperationException e) {
                assertTrue(true);
            }
        }
        
	public static void main(String[] args) throws Exception {
		TestRunner.run(WordProbabilityTest.class);
	}
}
