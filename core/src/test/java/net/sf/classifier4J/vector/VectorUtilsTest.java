package net.sf.classifier4J.vector;

import junit.framework.TestCase;

public class VectorUtilsTest extends TestCase {

    public void testScalarProduct() {
        try {
            double result = VectorUtils.scalarProduct(new int[] { 1, 2, 3 }, null);
            fail("Null argument allowed");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        try {
            double result = VectorUtils.scalarProduct(null, new int[] { 1, 2, 3 });
            fail("Null argument allowed");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            double result = VectorUtils.scalarProduct(new int[] {1}, new int[] { 1, 2, 3 });
            fail("Arguments of different size allowed");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        assertEquals(3, VectorUtils.scalarProduct(new int[] {1,1,1}, new int[] { 1,1,1}));
        assertEquals(6, VectorUtils.scalarProduct(new int[] {1,1,1}, new int[] { 1,2,3}));
        assertEquals(14, VectorUtils.scalarProduct(new int[] {1,2,3}, new int[] { 1,2,3 }));
        assertEquals(0, VectorUtils.scalarProduct(new int[] {0,0,0}, new int[] { 1,2,3 }));

    }

    public void testVectorLength() {
        try {
            double result = VectorUtils.vectorLength(null);
            fail("Null argument allowed");
        } catch (IllegalArgumentException e) {
            // expected
        }        
    
        assertEquals(Math.sqrt(2), VectorUtils.vectorLength(new int[]{1,1}),0.001d);
        assertEquals(Math.sqrt(3), VectorUtils.vectorLength(new int[]{1,1,1}),0.001d);
        assertEquals(Math.sqrt(12), VectorUtils.vectorLength(new int[]{2,2,2}),0.001d);
    }

    public void testCosineOfVectors() {
        try {
            double result = VectorUtils.cosineOfVectors(new int[] { 1, 2, 3 }, null);
            fail("Null argument allowed");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        try {
            double result = VectorUtils.cosineOfVectors(null, new int[] { 1, 2, 3 });
            fail("Null argument allowed");
        } catch (IllegalArgumentException e) {
            // expected
        }
        
        try {
            double result = VectorUtils.cosineOfVectors(new int[] {1}, new int[] { 1, 2, 3 });
            fail("Arguments of different size allowed");
        } catch (IllegalArgumentException e) {
            // expected
        }        
        
        int[] one = new int[]{1,1,1};
        int[] two = new int[]{1,1,1};
        
        assertEquals(1d, VectorUtils.cosineOfVectors(one, two), 0.001);
    }    
    
}