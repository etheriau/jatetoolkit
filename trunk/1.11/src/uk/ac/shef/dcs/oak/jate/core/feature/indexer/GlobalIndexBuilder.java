package uk.ac.shef.dcs.oak.jate.core.feature.indexer;

import uk.ac.shef.dcs.oak.jate.JATEException;
import uk.ac.shef.dcs.oak.jate.core.npextractor.CandidateTermExtractor;
import uk.ac.shef.dcs.oak.jate.model.Corpus;

/**
 * Build an instance of GlobalIndex
 */
public interface GlobalIndexBuilder {

    public GlobalIndex build(Corpus c, CandidateTermExtractor extractor) throws JATEException;
}
