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

import java.io.File;

public class JDBMWordsDataSourceTest extends AbstractWordsDataSourceSupport {
	
	/**
	 * @param arg0
	 */
	public JDBMWordsDataSourceTest(String arg0) {
		super(arg0);		
	}

	//JDBMWordsDataSource wordsDataSource = null;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		wordsDataSource = new JDBMWordsDataSource();
		((JDBMWordsDataSource)wordsDataSource).open();
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		((JDBMWordsDataSource)wordsDataSource).close();
		wordsDataSource = null;
		
		File dbFile = new File(JDBMWordsDataSource.databaseName + ".db");		
		if (dbFile.exists()) {			
			dbFile.delete();
			//System.out.println( "deleting " + dbFile.getAbsolutePath() );
		}
		dbFile = null;

		File indexFile = new File(JDBMWordsDataSource.databaseName + ".lg");
		if (indexFile.exists()) {			
			indexFile.delete();
			//System.out.println( "deleting " + indexFile.getAbsolutePath() );
		}	
		indexFile.delete();		
		
		super.tearDown();
	}
	
	/*
	public void testMultipleWrites2() {
		long startTime = System.currentTimeMillis();	
		
		String word = "myWord";
		int count = 500000;
		for (int i=0; i < count; i++) {
			wordsDataSource.addNonMatch(word + count);
		}				
		long endTime = System.currentTimeMillis();
		
		System.out.println(count + " writes took " + (endTime-startTime)/1000 + " seconds");
	}
	*/
	
	public void testMultipleCategories() throws Exception {
		String word = "myWord";
		String category = "category1";
		((ICategorisedWordsDataSource)wordsDataSource).addNonMatch(category, word);
		((ICategorisedWordsDataSource)wordsDataSource).addMatch(category, word);
		((ICategorisedWordsDataSource)wordsDataSource).addMatch(category, word);		
		assertNull(wordsDataSource.getWordProbability(word)); // should be null in the default category
		
		WordProbability wp = ((ICategorisedWordsDataSource)wordsDataSource).getWordProbability(category, word);
		assertNotNull(wp); // should not be null for the correct category
		assertEquals(1, wp.getNonMatchingCount());
		assertEquals(2, wp.getMatchingCount());
	}

}