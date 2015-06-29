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
import net.sf.classifier4J.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * @author Nick Lothian
 * @author Peter Leschev
 */
public class BayesianClassifierTest extends TestCase {

	private Log log = LogFactory.getLog(this.getClass());

	public BayesianClassifierTest(String name) {
		super(name);
	}

	public void testClassify() throws Exception {

		SimpleWordsDataSource wds = new SimpleWordsDataSource();
		BayesianClassifier classifier = new BayesianClassifier(wds);

		String sentence[] = { "This", "is", "a", "sentence", "about", "java" };

		assertEquals(IClassifier.NEUTRAL_PROBABILITY, classifier.classify(ICategorisedClassifier.DEFAULT_CATEGORY, sentence), 0d);

		wds.setWordProbability(new WordProbability("This", 0.5d));
		wds.setWordProbability(new WordProbability("is", 0.5d));
		wds.setWordProbability(new WordProbability("a", 0.5d));
		wds.setWordProbability(new WordProbability("sentence", 0.2d));
		wds.setWordProbability(new WordProbability("about", 0.5d));
		wds.setWordProbability(new WordProbability("java", 0.99d));

		assertEquals(0.96d, classifier.classify(ICategorisedClassifier.DEFAULT_CATEGORY, sentence), 0.009d);
	}

	public void testTeaching() throws Exception {
		BayesianClassifier classifier = new BayesianClassifier();

		String sentence1[] = {"The", "menu", "tag", "library", "manages", "the", 
							  "complex", "process", "of", "creating", "menus", "in",
							  "JavaScript", "The", "menu", "tag", "itself", "is", 
							  "an", "abstract", "class", "that", "extends", "the", 
							  "TagSupport", "class", "and", "overrides", "the", 
							  "doStartTag", "and", "doEndTag", "methods.", "The", 
							  "getMenu", "method,", "which", "is", "a", "template", 
							  "method", "and", "should", "be", "overridden", "in", 
							  "the", "subclasses,", "provides", "JavaScript", "to", 
							  "add", "menu", "items", "in", "the", "menu", 
							  "structure", "created", "in", "the", "doStartTag", 
							  "method", "Subclasses", "of", "the", "menu", "tag", 
							  "override", "the", "getMenu", "method,", "which", 
							  "uses", "menu", "builders", "to", "render", "menu", 
							  "data", "from", "the", "data", "source"};
							  						
		String sentence2[] = {"I", "witness", "a", "more", "subtle", 
							  "demonstration", "of", "real", "time", "physics", 
							  "simulation", "at", "the", "tiny", "Palo", "Alto", 
							  "office", "of", "Havok", "a", "competing", "physics", 
							  "engine", "shop", "On", "the", "screen", "a", 
							  "computer", "generated", "sailboat", "floats", "in", 
							  "a", "stone", "lined", "pool", "of", "water", "The", 
							  "company's", "genial", "Irish", "born", "cofounder", 
							  "Hugh", "Reynolds", "shows", "me", "how", "to", 
							  "push", "the", "boat", "with", "a", "mouse", "When", 
							  "I", "nudge", "it", "air", "fills", "the", "sail", 
							  "causing", "the", "ship", "to", "tilt", "leeward", 
							  "Ripples", "in", "the", "water", "deflect", "off", 
							  "the", "stones", "intersecting", "with", "one", 
							  "another", "I", "urge", "the", "boat", "onward", 
							  "and", "it", "glides", "effortlessly", "into", "the", 
							  "wall", "Reynolds", "tosses", "in", "a", "handful", 
							  "of", "virtual", "coins", "they", "spin", "through", 
							  "the", "air,", "splash", "into", "the", "water,", 
							  "and", "sink"};
							  
		String sentence3[] = {"The", "New", "Input", "Output", "NIO", "libraries", 
							 "introduced", "in", "Java", "2", "Platform", 
							 "Standard", "Edition", "J2SE", "1.4", "address", 
							 "this", "problem", "NIO", "uses", "a", "buffer", 
							 "oriented", "model", "That", "is", "NIO", "deals", 
							 "with", "data", "primarily", "in", "large", "blocks", 
							 "This", "eliminates", "the", "overhead", "caused", 
							 "by", "the", "stream", "model", "and", "even", "makes",
							 "use", "of", "OS", "level", "facilities", "where", 
							 "possible", "to", "maximize", "throughput"};
							 
		String sentence4[] = {"As", "governments", "scramble", "to", "contain", 
							 "SARS", "the", "World", "Health", "Organisation", 
							 "said", "it", "was", "extending", "the", "scope", "of",
							 "its", "April", "2", "travel", "alert", "to", 
							 "include", "Beijing", "and", "the", "northern", 
							 "Chinese", "province", "of", "Shanxi", "together", 
							 "with", "Toronto", "the", "epicentre", "of", "the", 
							 "SARS", "outbreak", "in", "Canada"};
							 
		String sentence5[] = {"That", "was", "our", "worst", "problem", "I", 
							 "tried", "to", "see", "it", "the", "XP", "way", "Well",
							 "what", "we", "can", "do", "is", "implement", 
							 "something", "I", "can't", "give", "any", "guarantees",
							 "as", "to", "how", "much", "of", "it", "will", "be", 
							 "implemented", "in", "a", "month", "I", "won't", 
							 "even", "hazard", "a", "guess", "as", "to", "how", 
							 "long", "it", "would", "take", "to", "implement", "as",
							 "a", "whole", "I", "can't", "draw", "UML", "diagrams", 
							 "for", "it", "or", "write", "technical", "specs", 
							 "that", "would", "take", "time", "from", "coding", 
							 "it", "which", "we", "can't", "afford", "Oh", "and", 
							 "I", "have", "two", "kids", "I", "can't", "do", "much",
							 "OverTime", "But", "I", "should", "be", "able", "to", 
							 "do", "something", "simple", "that", "will", "have", 
							 "very", "few", "bugs", "and", "show", "a", "working", 
							 "program", "early", "and", "often"}; 		
    

		classifier.teachMatch(ICategorisedClassifier.DEFAULT_CATEGORY, sentence1);
		classifier.teachNonMatch(ICategorisedClassifier.DEFAULT_CATEGORY, sentence2);
		classifier.teachMatch(ICategorisedClassifier.DEFAULT_CATEGORY, sentence3);
		classifier.teachNonMatch(ICategorisedClassifier.DEFAULT_CATEGORY, sentence4);
		classifier.teachMatch(ICategorisedClassifier.DEFAULT_CATEGORY, sentence5);

		assertTrue(classifier.isMatch(ICategorisedClassifier.DEFAULT_CATEGORY, sentence1));
		assertTrue(!classifier.isMatch(ICategorisedClassifier.DEFAULT_CATEGORY, sentence2));
		assertTrue(classifier.isMatch(ICategorisedClassifier.DEFAULT_CATEGORY, sentence3));
		assertTrue(!classifier.isMatch(ICategorisedClassifier.DEFAULT_CATEGORY, sentence4));
		assertTrue(classifier.isMatch(ICategorisedClassifier.DEFAULT_CATEGORY, sentence5));
	}

	public void testGetWordsDataSource() throws Exception {
		SimpleWordsDataSource wds = new SimpleWordsDataSource();
		BayesianClassifier classifier = new BayesianClassifier(wds);
		
		assertEquals(wds, classifier.getWordsDataSource());
	}

	public void testGetTokenizer() throws Exception {
		SimpleWordsDataSource wds = new SimpleWordsDataSource();
		ITokenizer tokenizer = new DefaultTokenizer(DefaultTokenizer.BREAK_ON_WORD_BREAKS);		
		BayesianClassifier classifier = new BayesianClassifier(wds, tokenizer);
		
		assertEquals(tokenizer, classifier.getTokenizer());
	}

	public void testGetStopWordProvider() throws Exception {
		SimpleWordsDataSource wds = new SimpleWordsDataSource();
		ITokenizer tokenizer = new DefaultTokenizer(DefaultTokenizer.BREAK_ON_WORD_BREAKS);
		IStopWordProvider stopWordProvider =  new DefaultStopWordsProvider();		
		BayesianClassifier classifier = new BayesianClassifier(wds, tokenizer, stopWordProvider);
		
		assertEquals(stopWordProvider, classifier.getStopWordProvider());		
	}

	public void testCaseSensitive() throws Exception {
		BayesianClassifier classifier = new BayesianClassifier();
		assertFalse(classifier.isCaseSensitive()); // case insensitive by default;
		classifier.setCaseSensitive(true);
		assertTrue(classifier.isCaseSensitive());
	}

	public void testTransformWord() throws Exception {
		BayesianClassifier classifier = new BayesianClassifier();
		assertFalse(classifier.isCaseSensitive());
		
		String word = null;
		try {		
			classifier.transformWord(word);
			fail("No exception thrown when null passed");
		} catch (IllegalArgumentException e) {
			// do nothing - this should be thrown
		}
		
		word = "myWord";
		assertEquals(word.toLowerCase(), classifier.transformWord(word));
		
		classifier.setCaseSensitive(true);
		assertNotSame(word.toLowerCase(), classifier.transformWord(word));
		assertEquals(word, classifier.transformWord(word));		
	}

	public void testCalculateOverallProbability() throws Exception {
		double prob = 0.3d;
		WordProbability wp1 = new WordProbability("myWord1", prob);
		WordProbability wp2 = new WordProbability("myWord2", prob);
		WordProbability wp3 = new WordProbability("myWord3", prob);
		
		WordProbability[] wps = {wp1, wp2, wp3};
		double errorMargin = 0.0001d;
		
		double xy = (prob * prob * prob);
		double z = (1-prob)*(1-prob)*(1-prob);
		
		double result = xy/(xy + z);
		
		BayesianClassifier classifier = new BayesianClassifier();
		 		
		assertEquals(result, classifier.calculateOverallProbability(wps), errorMargin);
	}


	public static void main(String[] args) throws Exception {
		TestRunner.run(BayesianClassifierTest.class);
	}
}
