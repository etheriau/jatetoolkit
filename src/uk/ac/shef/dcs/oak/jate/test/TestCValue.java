package uk.ac.shef.dcs.oak.jate.test;

import org.apache.log4j.Logger;
import uk.ac.shef.dcs.oak.jate.JATEException;
import uk.ac.shef.dcs.oak.jate.JATEProperties;
import uk.ac.shef.dcs.oak.jate.core.algorithm.AbstractFeatureWrapper;
import uk.ac.shef.dcs.oak.jate.core.algorithm.Algorithm;
import uk.ac.shef.dcs.oak.jate.core.algorithm.CValueAlgorithm;
import uk.ac.shef.dcs.oak.jate.core.algorithm.CValueFeatureWrapper;
import uk.ac.shef.dcs.oak.jate.core.feature.FeatureBuilderCorpusTermFrequency;
import uk.ac.shef.dcs.oak.jate.core.feature.FeatureBuilderTermNest;
import uk.ac.shef.dcs.oak.jate.core.feature.FeatureCorpusTermFrequency;
import uk.ac.shef.dcs.oak.jate.core.feature.FeatureTermNest;
import uk.ac.shef.dcs.oak.jate.core.feature.TermVariantsUpdater;
import uk.ac.shef.dcs.oak.jate.core.feature.indexer.GlobalIndexBuilderMem;
import uk.ac.shef.dcs.oak.jate.core.npextractor.NounPhraseExtractorOpenNLP;
import uk.ac.shef.dcs.oak.jate.io.ResultWriter2File;
import uk.ac.shef.dcs.oak.jate.model.Term;
import uk.ac.shef.dcs.oak.jate.util.control.IStopList;
import uk.ac.shef.dcs.oak.jate.util.control.StopList;
import uk.ac.shef.dcs.oak.jate.util.counter.TermFreqCounter;
import uk.ac.shef.dcs.oak.jate.util.counter.WordCounter;
import uk.ac.shef.dcs.oak.jate.core.feature.indexer.GlobalIndexMem;
import uk.ac.shef.dcs.oak.jate.core.npextractor.CandidateTermExtractor;
import uk.ac.shef.dcs.oak.jate.model.CorpusImpl;
import uk.ac.shef.dcs.oak.jate.util.control.Lemmatizer;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TestCValue {

	private Map<Algorithm, AbstractFeatureWrapper> _algregistry = new HashMap<Algorithm, AbstractFeatureWrapper>();
	private static Logger _logger = Logger.getLogger(AlgorithmTester.class);

	public void registerAlgorithm(Algorithm a, AbstractFeatureWrapper f) {
		_algregistry.put(a, f);
	}

	public void execute(GlobalIndexMem index) throws JATEException, IOException {
		_logger.info("Initializing outputter, loading NP mappings...");
		ResultWriter2File writer = new ResultWriter2File(index);
		if (_algregistry.size() == 0) throw new JATEException("No algorithm registered!");
		_logger.info("Running NP recognition...");

		/*.extractNP(c);*/
		for (Map.Entry<Algorithm, AbstractFeatureWrapper> en : _algregistry.entrySet()) {
			_logger.info("Running feature store builder and ATR..." + en.getKey().toString());
			Term[] result = en.getKey().execute(en.getValue());
			writer.output(result, en.getKey().toString() + ".txt");
		}
	}

	public static void main(String[] args) throws IOException, JATEException{
		String[] argument = new String[]{JATEProperties.getInstance().getCorpusPath(), JATEProperties.getInstance().getResultPath()};
		TestCValue.CValueTester(argument);
	}
	
	public static AlgorithmTester CValueTester(String[] args) throws IOException, JATEException {
		
		
		String path_to_corpus= args[0];
		String path_to_output=args[1];
		if (args.length < 2) {
			System.out.println("Usage: java TestCValue [path_to_corpus] [output_folder]");
		}
		else
		{
			System.out.println("Started "+ TestCValue.class+"at: " + new Date() + "... For detailed progress see log file jate.log.");

			//creates instances of required processors and resources

			//stop word list
			IStopList stop = new StopList(true);

			//lemmatizer
			Lemmatizer lemmatizer = new Lemmatizer();

			//noun phrase extractor
			CandidateTermExtractor npextractor = new NounPhraseExtractorOpenNLP(stop, lemmatizer);

			//counters
			TermFreqCounter npcounter = new TermFreqCounter();
			WordCounter wordcounter = new WordCounter();

			//create global resource index builder, which indexes global resources, such as documents and terms and their
			//relations
			GlobalIndexBuilderMem builder = new GlobalIndexBuilderMem();
			//build the global resource index
			GlobalIndexMem termDocIndex = builder.build(new CorpusImpl(path_to_corpus), npextractor);

			/*newly added for improving frequency count calculation: begins*/
			
			
			TermVariantsUpdater update = new TermVariantsUpdater(termDocIndex, stop, lemmatizer);
			 
			
			GlobalIndexMem termIndex = update.updateVariants();
			
			FeatureCorpusTermFrequency termCorpusFreq =
							new FeatureBuilderCorpusTermFrequency(npcounter, wordcounter, lemmatizer).build(termIndex);
			
			
			/*newly added for improving frequency count calculation: ends*/
			
			
			//build a feature store required by the tfidf algorithm, using the processors instantiated above
			/*FeatureCorpusTermFrequency termCorpusFreq =
				new FeatureBuilderCorpusTermFrequency(npcounter, wordcounter, lemmatizer).build(termDocIndex);
			FeatureTermNest termNest =
					new FeatureBuilderTermNest().build(termDocIndex);
					*/
			FeatureTermNest termNest =
					new FeatureBuilderTermNest().build(termIndex);

			AlgorithmTester tester = new AlgorithmTester();
			tester.registerAlgorithm(new CValueAlgorithm(), new CValueFeatureWrapper(termCorpusFreq,termNest));
			//tester.execute(termDocIndex, path_to_output);
			tester.execute(termIndex, path_to_output);
			System.out.println("Ended at: " + new Date());
			return tester;
		}
		return null;
	}
}

