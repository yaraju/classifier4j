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
package net.sf.classifier4J.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.DefaultTokenizer;
import net.sf.classifier4J.ITokenizer;
import net.sf.classifier4J.ITrainableClassifier;
import net.sf.classifier4J.Utilities;
import net.sf.classifier4J.bayesian.BayesianClassifier;
import net.sf.classifier4J.bayesian.JDBMWordsDataSource;

/**
 * @author Nick Lothian
 * @author Peter Leschev
 */
public class Trainer {

    /**
     * Given an inputStream of data, a tokenizer this method trains the 
     * specified classifier.
     *
     * @returns Words Per Second 
     */
    public static double trainClassifier(ITokenizer tokenizer,
                                         ITrainableClassifier classifier, 
                                         boolean isMatch, 
                                         InputStream inputStream) throws IOException, ClassifierException {
            
//        System.out.println("Training Classifier4J using " + classifier + " and " +
//                           tokenizer);

        String contents = Utilities.getString(inputStream);
        int length = tokenizer.tokenize(contents).length;

        long startTime = System.currentTimeMillis();
        
        if (isMatch) {
//            System.out.println(length + 
//                               " matching words. This may take a while.");
            classifier.teachMatch(contents);
        } else {
//            System.out.println(length + 
//                               " non-matching words. This may take a while.");
            classifier.teachNonMatch(contents);
        }
        
        long endTime = System.currentTimeMillis();
        
        double time = (double)(endTime - startTime) / (double)1000;
        
        if (Double.compare(time, 0) == 0) {
            time = 1;
        }
        
        double wordsPerSecond = length / time;
        
//        System.out.println("Done. Took " + time + " seconds, which is " + 
//                           wordsPerSecond + " words per second.");
        
        return wordsPerSecond;
    }

    public static String connectionString = "jdbc:hsqldb:./database/";
    public static String username = "sa";
    public static String password = "";

    static JDBMWordsDataSource wds;

    private static ITrainableClassifier setupClassifier(String connString, String user, String pw) throws SQLException, IOException {
/*
DriverMangerJDBCConnectionManager cm = new DriverMangerJDBCConnectionManager(connString, user, pw);
JDBCWordsDataSource wds = new JDBCWordsDataSource(cm);
wds.createTable();
*/
        wds = new JDBMWordsDataSource("./database/");
        wds.open();
        return new BayesianClassifier(wds);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("This program reads in two files, one of which is considered to define a match.");
        System.out.println("These two files are analysed by Classifier4J and the resulting word probabilities are loaded into a JDBM database.");
        System.out.println("");
        System.out.println("To reset the word probabilities, delete the \"database\" directory which is created.");

        File dir = new File("./database");
        dir.mkdir();

        ITrainableClassifier classifier = setupClassifier(connectionString, username, password);
        ITokenizer tokenizer = new DefaultTokenizer();
        
        trainClassifier(tokenizer, 
                        classifier, 
                        true, 
                        new FileInputStream("./demodata/match.txt"));
        
        trainClassifier(tokenizer, 
                        classifier,
                        false, 
                        new FileInputStream("./demodata/nonmatch.txt"));
		
        wds.close();
    }
	/*
	static {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	*/
}
