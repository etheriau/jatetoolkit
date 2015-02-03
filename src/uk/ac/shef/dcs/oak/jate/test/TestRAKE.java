package uk.ac.shef.dcs.oak.jate.test;

import java.util.List;
import java.io.IOException;
import java.util.Date;
import uk.ac.shef.dcs.oak.jate.JATEException;
import uk.ac.shef.dcs.oak.jate.JATEProperties;
import uk.ac.shef.dcs.oak.jate.core.algorithm.RAKEAlgorithm;
import uk.ac.shef.dcs.oak.jate.core.algorithm.RAKEFeatureWrapper;
import uk.ac.shef.dcs.oak.jate.core.feature.indexer.GlobalIndexBuilderMem;
import uk.ac.shef.dcs.oak.jate.core.npextractor.PhraseExtractor;
import uk.ac.shef.dcs.oak.jate.model.CorpusImpl;
import uk.ac.shef.dcs.oak.jate.util.control.Lemmatizer;
import uk.ac.shef.dcs.oak.jate.util.control.StopList;


public class TestRAKE{

		public static void main(String[] args) throws IOException, JATEException {
		
		
			String path_to_corpus = JATEProperties.getInstance().getCorpusPath();
			String path_to_output = JATEProperties.getInstance().getResultPath();
	
			System.out.println("Started "+ TestRAKE.class+"at: " + new Date() + "... For detailed progress see log file jate.log.");

			//creates instances of required processors and resources

			//stop word list
			StopList stop = new StopList(true);

			//lemmatiser
			Lemmatizer lemmatizer = new Lemmatizer();

			//noun phrase extractor
			PhraseExtractor npextractor = new PhraseExtractor(stop, lemmatizer);
			
			GlobalIndexBuilderMem indexbuilder = new GlobalIndexBuilderMem();
			
			//build the global resource index			
		
			List<String> candidates = indexbuilder.build(new CorpusImpl(path_to_corpus), npextractor); 
			
			
			AlgorithmTester tester = new AlgorithmTester();
			tester.registerAlgorithm(new RAKEAlgorithm(), new RAKEFeatureWrapper(candidates));
			tester.execute(path_to_output);
			System.out.println("Ended at: " + new Date());
			

			
		}
	}