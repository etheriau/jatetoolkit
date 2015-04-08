package uk.ac.shef.dcs.oak.jate.core.algorithm;

import java.util.Map;
import java.util.Set;

import uk.ac.shef.dcs.oak.jate.model.Corpus;
import uk.ac.shef.dcs.oak.jate.test.AlgorithmTester;


public class NCValueFeatureWrapper extends AbstractFeatureWrapper {

	private final Corpus corpus;
	private final Map<String, Double> _contextWords;
	private final AlgorithmTester _tester;
	
	
	public NCValueFeatureWrapper(Corpus corpus, Map<String, Double> contextWords, AlgorithmTester tester){
		this.corpus = corpus;
		_contextWords = contextWords;
		_tester = tester;
	}

	
	public Map<String, Double> getContextWordsMap(){
		return _contextWords;
	}

	public AlgorithmTester getTesterObject(){
		return _tester;
	}


	@Override
	public Set<String> getTerms() {
		// TODO Auto-generated method stub
		return null;
	}

	public Corpus getCorpus() {
		return corpus;
	}
}
