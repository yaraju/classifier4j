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


package net.sf.classifier4J;

import junit.framework.TestCase;
import net.sf.classifier4J.IClassifier;
import net.sf.classifier4J.SimpleClassifier;


public class SimpleClassifierTest extends TestCase {

	private SimpleClassifier classifier = null;

	/**
	 * Constructor for SimpleClassifierTest.
	 * @param arg0
	 */
	public SimpleClassifierTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		classifier = new SimpleClassifier();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		classifier = null;
	}


	public void testGetSearchWord() {
		String word = "java";
		classifier.setSearchWord( word );
		assertEquals( word, classifier.getSearchWord() );
	}

	public void testSetSearchWord() {
		String word = "java";
		classifier.setSearchWord( word );
		assertEquals( word, classifier.getSearchWord() );		
	}

	public void testClassify() {
		String word = "java";
		classifier.setSearchWord( word );

		String sentance = "This is a sentance about java";				
		assertEquals( 1d, classifier.classify(sentance), 0d);	
		
		sentance = "This is not";				
		assertEquals( 0d, classifier.classify(sentance), 0d);			
	}

	public void testMatch() throws Exception {
		String word = "java";
		classifier.setSearchWord( word );

		String sentance = "This is a sentance about java";				
		assertTrue( classifier.isMatch(sentance) );
		
	}
	
	public void testIsMatchDouble() throws Exception {
		assertTrue(classifier.isMatch(IClassifier.DEFAULT_CUTOFF));		
		assertTrue(classifier.isMatch(IClassifier.DEFAULT_CUTOFF + 0.01d));
		assertFalse(classifier.isMatch(IClassifier.DEFAULT_CUTOFF - 0.01d));
	}

}
