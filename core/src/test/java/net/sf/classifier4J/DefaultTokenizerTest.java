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
import junit.textui.TestRunner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultTokenizerTest extends TestCase {
  
  private Log log = LogFactory.getLog(this.getClass());
  
  public DefaultTokenizerTest(String name) {
    super(name);
  }
  
  public void testConstructors() {
    ITokenizer tok = null;
    
    try {
      tok = new DefaultTokenizer(null);
      fail("Shouldn't be able to set a tokenizer of null");
    }
    catch(IllegalArgumentException e) {
      assertTrue(true);
    }
    
    tok = new DefaultTokenizer("");
    
    tok = new DefaultTokenizer(DefaultTokenizer.BREAK_ON_WHITESPACE);
    
    tok = new DefaultTokenizer(DefaultTokenizer.BREAK_ON_WORD_BREAKS);
    
    try {
      tok = new DefaultTokenizer(43);
      fail("Shouldn't be able to set a tokenizer of type 43");
    }
    catch(IllegalArgumentException e) {
      assertTrue(true);
    }
  }
  
  public void testTokenize() {
    
    ITokenizer tok = null;
    String words[] = null;
    
    tok = new DefaultTokenizer(DefaultTokenizer.BREAK_ON_WHITESPACE);
    words = tok.tokenize("My very,new string!");
    
    assertEquals(3, words.length);
    assertEquals("My",       words[0]);
    assertEquals("very,new", words[1]);
    assertEquals("string!",  words[2]);
    
    tok = new DefaultTokenizer(DefaultTokenizer.BREAK_ON_WORD_BREAKS);
    words = tok.tokenize("My very,new-string!and/more(NIO)peter's 1.4");
    
    assertEquals(11, words.length);
    assertEquals("My",       words[0]);
    assertEquals("very",     words[1]);
    assertEquals("new",      words[2]);
    assertEquals("string",   words[3]);
    assertEquals("and",      words[4]);
    assertEquals("more",     words[5]);
    assertEquals("NIO",      words[6]);
    
    //todo: Shouldn't this be "peter's", instead of "peter" & "s"?
    assertEquals("peter",    words[7]);
    assertEquals("s",        words[8]);  
    
    //todo: Shouldn't this be "1.4", instead of "1" & "4"?
    assertEquals("1",        words[9]); 
    assertEquals("4",        words[10]); 
  }
  
  public static void main(String[] args) throws Exception {
    TestRunner.run(DefaultTokenizerTest.class);
  }
}
