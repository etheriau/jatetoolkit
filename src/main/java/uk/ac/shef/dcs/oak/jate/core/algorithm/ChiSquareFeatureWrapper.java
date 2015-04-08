package uk.ac.shef.dcs.oak.jate.core.algorithm;

import java.util.Set;

import uk.ac.shef.dcs.oak.jate.model.Corpus;
import uk.ac.shef.dcs.oak.jate.model.Term;
import uk.ac.shef.dcs.oak.jate.test.AlgorithmTester;

/**
 * ChiSquareFeatureWrapper wraps an instance of AlgorithmTester, which enables to get the terms along with their confidence values identified from the execution of that AlgorithmTester Instance.
 */

public class ChiSquareFeatureWrapper extends AbstractFeatureWrapper {

	private final Corpus corpus;
	private final AlgorithmTester _tester;

	
	public ChiSquareFeatureWrapper(Corpus corpus, AlgorithmTester tester){
		_tester = tester;
		this.corpus = corpus;
	}

	public AlgorithmTester getTesterObject(){
		return _tester;
	}
	
	public Term[] getCandidateTerms() {
		// TODO Auto-generated method stub		
		return _tester.getTerms();
	}
	
	
	public Set<String> getVariants(String term){
		return _tester.getIndex().retrieveVariantsOfTermCanonical(term);
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
