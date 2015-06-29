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


public abstract class AbstractWordsDataSourceSupport extends TestCase {
	
	IWordsDataSource wordsDataSource = null;

	/**
	 * Constructor for AbstractWordsDataSourceTst.
	 * @param arg0
	 */
	public AbstractWordsDataSourceSupport(String arg0) {
		super(arg0);
	}


	public void testEmptySource() throws Exception {
		WordProbability wp = wordsDataSource.getWordProbability("myWord");		
		assertNull(wp);
	}

	public void testAddMatch() throws Exception {
		wordsDataSource.addMatch("myWord");
		WordProbability wp = wordsDataSource.getWordProbability("myWord");
                assertNotNull(wp);
		assertEquals(1, wp.getMatchingCount());
		assertEquals(0, wp.getNonMatchingCount());
		
		wordsDataSource.addMatch("myWord");
		
		wp = wordsDataSource.getWordProbability("myWord");
                assertNotNull(wp);
		assertEquals(2, wp.getMatchingCount());
		assertEquals(0, wp.getNonMatchingCount());				
	}

	public void testAddNonMatch() throws Exception {			
		wordsDataSource.addNonMatch("myWord");
		WordProbability wp = wordsDataSource.getWordProbability("myWord");
                assertNotNull(wp);
		assertEquals(0, wp.getMatchingCount());
		assertEquals(1, wp.getNonMatchingCount());
		
		wordsDataSource.addNonMatch("myWord");
		
		wp = wordsDataSource.getWordProbability("myWord");
                assertNotNull(wp);
		assertEquals(0, wp.getMatchingCount());
		assertEquals(2, wp.getNonMatchingCount());		
	}


	public void testAddMultipleMatches() throws Exception {
		String word = "myWord";
		int count = 10;
		for (int i=0; i < count; i++) {
			wordsDataSource.addMatch(word);
		}
		WordProbability wp = wordsDataSource.getWordProbability(word);	
		assertNotNull(wp);	
		assertEquals(count, wp.getMatchingCount());				
	}


	public void testAddMultipleNonMatches() throws Exception {
		String word = "myWord";
		int count = 10;
		for (int i=0; i < count; i++) {
			wordsDataSource.addNonMatch(word);
		}
		WordProbability wp = wordsDataSource.getWordProbability(word);
		assertNotNull(wp);		
		assertEquals(count, wp.getNonMatchingCount());				
	}


	public void testMultipleWrites() throws Exception {
		long startTime = System.currentTimeMillis();	
		
		String word = "myWord";
		int count = 500;
		for (int i=0; i < count; i++) {
			wordsDataSource.addNonMatch(word + count);
		}				
		long endTime = System.currentTimeMillis();
	}

}
