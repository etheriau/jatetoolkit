/**
Test file for NC-Value Algorithm.
 */

package uk.ac.shef.dcs.oak.jate.test;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import uk.ac.shef.dcs.oak.jate.JATEException;
import uk.ac.shef.dcs.oak.jate.JATEProperties;
import uk.ac.shef.dcs.oak.jate.core.algorithm.NCValueAlgorithm;
import uk.ac.shef.dcs.oak.jate.core.algorithm.NCValueFeatureWrapper;
import uk.ac.shef.dcs.oak.jate.core.context.ContextExtraction;
import uk.ac.shef.dcs.oak.jate.model.Corpus;
import uk.ac.shef.dcs.oak.jate.model.CorpusImpl;
import uk.ac.shef.dcs.oak.jate.util.control.Lemmatizer;
import uk.ac.shef.dcs.oak.jate.util.control.StopList;

public class TestNCValue {

	public static void main(String[] args) throws IOException, JATEException {
		
			System.out.println("Started "+ TestNCValue.class+" at: " + new Date() + "... For detailed progress see log file jate.log.");
			String[] argument = new String[]{JATEProperties.getInstance().getCorpusPath(), JATEProperties.getInstance().getResultPath()};
			
			/* Call the C-Value Algorithm first to get the resultant candidates with CValue score. */
			AlgorithmTester tester = TestCValue.CValueTester(argument);
		    if ( tester == null ) {
				System.err.println( "Unable to find algorithm: " + argument );
				return;
			}
			
			//Ankit: added the required parameters stoplist and lemmatizer
			StopList stoplist = new StopList(true);
			Lemmatizer lemmatizer = new Lemmatizer();
			
			/* Get the list of context words by making use of the 'top candidates' of CValue resultant term candidates. */
			Corpus corpus = new CorpusImpl( JATEProperties.getInstance().getCorpusPath() );
			ContextExtraction contextExtract = new ContextExtraction(tester,stoplist,lemmatizer);
			Map<String, Double> contextWords = contextExtract.Extract( corpus );
			
			/* Register the NC-Value Algorithm and get it executed. */
			AlgorithmTester NCTester = new AlgorithmTester();
			//Ankit: added the required parameters stoplist and lemmatizer
			NCTester.registerAlgorithm(new NCValueAlgorithm(contextExtract,stoplist,lemmatizer), new NCValueFeatureWrapper(corpus, contextWords, tester));
			NCTester.execute(tester.getIndex(), JATEProperties.getInstance().getResultPath());
			System.out.println("Ended at: " + new Date());		
		}	 
	}


