
package net.sf.classifier4J.vector;

import junit.framework.TestCase;
import net.sf.classifier4J.ClassifierException;


public class VectorClassifierTest extends TestCase {

    String sentence1 = "hello there is this a long sentence yes it is blah. blah hello";
    
    /*
     * Class under test for double classify(String, String)
     */
    public void testClassifyStringString() {
        TermVectorStorage storage = new HashMapTermVectorStorage();
        VectorClassifier vc = new VectorClassifier(storage);
        try {
            String category = "test";
            vc.teachMatch(category, sentence1);
            assertEquals(0.852d, vc.classify(category, "hello blah"), 0.001);
            assertEquals(0.301d, vc.classify(category, "sentence"), 0.001);
            assertEquals(0.0d, vc.classify(category, "bye"), 0.001);
            
            assertEquals(0.0d, vc.classify("does not exist", "bye"), 0.001);
        } catch (ClassifierException e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        }            
    }

    /*
     * Class under test for boolean isMatch(String, String)
     */
    public void testIsMatchStringString() {
        TermVectorStorage storage = new HashMapTermVectorStorage();
        VectorClassifier vc = new VectorClassifier(storage);
        try {
            String category = "test";
            vc.teachMatch(category, sentence1);
            assertTrue(vc.isMatch(category, "hello blah"));
            assertFalse(vc.isMatch(category, "sentence"));
            assertFalse(vc.isMatch(category, "bye"));
        } catch (ClassifierException e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        }            
    }

    /*
     * Class under test for void teachMatch(String, String)
     */
    public void testTeachMatchStringString() {
        TermVectorStorage storage = new HashMapTermVectorStorage();
        VectorClassifier vc = new VectorClassifier(storage);
        try {
            String category = "test";
            vc.teachMatch(category, sentence1);
            TermVector tv = storage.getTermVector(category);
            assertNotNull("TermVector should not be null", tv);
            assertEquals(5, tv.getTerms().length);
            assertEquals(tv.getValues().length, tv.getTerms().length);
            assertEquals("blah", tv.getTerms()[0]);
            assertEquals(2, tv.getValues()[0]);
            assertEquals("hello", tv.getTerms()[1]);
            assertEquals(2, tv.getValues()[1]);
            assertEquals("long", tv.getTerms()[2]);
            assertEquals(1, tv.getValues()[2]);
            assertEquals("sentence", tv.getTerms()[3]);
            assertEquals(1, tv.getValues()[3]);
            assertEquals("yes", tv.getTerms()[4]);
            assertEquals(1, tv.getValues()[4]);
                        
            assertEquals("{[blah, 2] [hello, 2] [long, 1] [sentence, 1] [yes, 1] }", tv.toString());
        } catch (ClassifierException e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        }
    }
    

}
