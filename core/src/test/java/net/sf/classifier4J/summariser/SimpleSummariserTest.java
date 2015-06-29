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

package net.sf.classifier4J.summariser;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SimpleSummariserTest extends TestCase {

    SimpleSummariser summariser = null;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        summariser = new SimpleSummariser();
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        summariser = null;
        super.tearDown();
    }

    public void testSummarise() {

        String input = "Classifier4J is a java package for working with text. Classifier4J includes a summariser.";
        String expectedResult = "Classifier4J is a java package for working with text.";

        String result = summariser.summarise(input, 1);
        assertEquals(expectedResult, result);

        input = "Classifier4J is a java package for working with text. Classifier4J includes a summariser. A Summariser allows the summary of text. A Summariser is really cool. I don't think there are any other java summarisers.";
        expectedResult = "Classifier4J is a java package for working with text. Classifier4J includes a summariser.";
        result = summariser.summarise(input, 2);
        assertEquals(expectedResult, result);

        /*
        // This fails due to appending "." instead of whatever the correct punctuation is 		 
        input = "Classifier4J is a java package for working with text! Classifier4J includes a summariser.";
        expectedResult = "Classifier4J is a java package for working with text";
        result = summariser.summarise(input, 1);
        System.out.println(expectedResult);
        System.out.println(result);		
        assertEquals(expectedResult, result);
        */

    }

    public void testGetMostFrequentWords() {
        Map input = new HashMap();
        String[] values = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten" };
        for (int i = 0; i < values.length; i++) {
            input.put(values[i], new Integer(i));
        }

        Set result = summariser.getMostFrequentWords(3, input);
        assertNotNull(result);
        assertEquals(3, result.size());

        Iterator it = result.iterator();
        int count = 1;
        while (it.hasNext()) {
            String resultValue = (String) it.next();
            assertEquals(values[values.length - count], resultValue);
            count++;
        }

        result = summariser.getMostFrequentWords(4, input);
        assertNotNull(result);
        assertEquals(4, result.size());

        it = result.iterator();
        count = 1;
        while (it.hasNext()) {
            String resultValue = (String) it.next();
            assertEquals(values[values.length - count], resultValue);
            count++;
        }

    }

}
