package uk.ac.shef.dcs.oak.jate.core.feature;

import uk.ac.shef.dcs.oak.jate.JATEException;
import uk.ac.shef.dcs.oak.jate.core.feature.indexer.GlobalIndex;
import uk.ac.shef.dcs.oak.jate.util.control.Normalizer;
import uk.ac.shef.dcs.oak.jate.util.counter.TermFreqCounter;
import uk.ac.shef.dcs.oak.jate.util.counter.WordCounter;

/**
 * Feature builder that builds a feature from a GlobalIndex
 *
 * @author <a href="mailto:z.zhang@dcs.shef.ac.uk">Ziqi Zhang</a>
 */



public abstract class AbstractFeatureBuilder {

	protected TermFreqCounter _termFreqCounter;// = new NPFreqCounter();
	protected Normalizer _normaliser; //= new Lemmatizer();
	protected WordCounter _wordCounter;// = new WordCounter();

	/**
	 * Creates an instance
	 *
	 * @param counter1   an instance of term frequency counter
	 * @param counter2   an instance of word counter
	 * @param normaliser an instance of Normalizer, which returns a candidate term to its canonical form
	 */
	public AbstractFeatureBuilder(TermFreqCounter counter1, WordCounter counter2, Normalizer normaliser) {
		_termFreqCounter = counter1;
		_wordCounter = counter2;
		_normaliser = normaliser;
	}

	public abstract AbstractFeature build(GlobalIndex index) throws JATEException;


}
