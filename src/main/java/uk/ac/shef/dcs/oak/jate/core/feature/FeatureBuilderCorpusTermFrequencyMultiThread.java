package uk.ac.shef.dcs.oak.jate.core.feature;

import org.apache.log4j.Logger;
import uk.ac.shef.dcs.oak.jate.JATEException;
import uk.ac.shef.dcs.oak.jate.JATEProperties;
import uk.ac.shef.dcs.oak.jate.core.feature.indexer.GlobalIndex;
import uk.ac.shef.dcs.oak.jate.core.npextractor.CandidateTermExtractor;
import uk.ac.shef.dcs.oak.jate.model.Document;
import uk.ac.shef.dcs.oak.jate.util.control.Normalizer;
import uk.ac.shef.dcs.oak.jate.util.counter.TermFreqCounter;
import uk.ac.shef.dcs.oak.jate.util.counter.WordCounter;

import java.util.*;

/**
 * Serves the same goal as FeatureCorpusTermFrequency, but uses multi-thread in counting term frequencies in corpus.
 * Uses more memory and CPU but faster on large corpus.
 *
 * @author <a href="mailto:z.zhang@dcs.shef.ac.uk">Ziqi Zhang</a>
 */
public class FeatureBuilderCorpusTermFrequencyMultiThread extends AbstractFeatureBuilder {
    private static Logger _logger = Logger.getLogger(FeatureBuilderCorpusTermFrequencyMultiThread.class);

    /**
     * Creates an instance
     *
     * @param counter    word counter, counting number of words in documents
     * @param normaliser a normaliser for returning terms to their canonical forms
     *                   over the corpus and add up to the total frequencies of the lemma.
     */
    public FeatureBuilderCorpusTermFrequencyMultiThread(WordCounter counter, Normalizer normaliser) {
        super(null, counter, normaliser);
    }

    /**
     * Build an instance of FeatureCorpusTermFrequency
     *
     * @param index the global resource index
     * @return
     * @throws uk.ac.shef.dcs.oak.jate.JATEException
     *
     */
    public FeatureCorpusTermFrequency build(GlobalIndex index) throws JATEException {
        FeatureCorpusTermFrequency _feature = new FeatureCorpusTermFrequency(index);
        if (index.getTermsCanonical().size() == 0 || index.getDocuments().size() == 0) throw new
                JATEException("No resource indexed!");

        _logger.info("About to build FeatureCorpusTermFrequency...");
        int totalCorpusTermFreq = 0;
        startCounting(index.getDocuments(), _feature, index, JATEProperties.getInstance().getMultithreadCounterNumbers());
        for (Document d : index.getDocuments()) {
            totalCorpusTermFreq += _wordCounter.countWords(d);
        }
        _feature.setTotalCorpusTermFreq(totalCorpusTermFreq);

        return _feature;
    }


    private void startCounting(Set<Document> docs, FeatureCorpusTermFrequency feature, GlobalIndex index, int totalThreads) {
        int size = docs.size() / totalThreads + docs.size() % totalThreads;
        Iterator<Document> it = docs.iterator();
        int count = 0;
        Set<Document> seg = new HashSet<Document>();
        List<TermFreqCounterThread> allCounters = new ArrayList<TermFreqCounterThread>();

        while (it.hasNext()) {
            if (count >= size) {
                count = 0;
                allCounters.add(
                        new TermFreqCounterThread(new TermFreqCounter(), new HashSet<Document>(seg), index, feature));
                seg.clear();
            }

            if (count < size) {
                seg.add(it.next());
                count++;
            }
        }
        if (seg.size() > 0)
            allCounters.add(new TermFreqCounterThread(new TermFreqCounter(), new HashSet<Document>(seg), index, feature));

        //start all
        for (TermFreqCounterThread t : allCounters)
            t.start();

        //until finished
        boolean finished = false;
        while (!finished) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            finished = true;
            for (TermFreqCounterThread t : allCounters) {
                if (!t.isFinished())
                    finished = false;
            }
        }
    }


    private class TermFreqCounterThread extends Thread {

        private TermFreqCounter counter;
        private boolean finished;
        private Set<Document> docs;
        private GlobalIndex index;
        private FeatureCorpusTermFrequency feature;


        public TermFreqCounterThread(TermFreqCounter counter, Set<Document> docs, GlobalIndex index, FeatureCorpusTermFrequency feature) {
            this.counter = counter;
            this.docs = docs;
            this.index = index;
            this.feature = feature;
        }

        @Override
        public void run() {
            count();
            setFinished(true);
        }

        private void count() {
            for (Document d : docs) {
                _logger.info("For Document " + d);
                String context = CandidateTermExtractor.applyCharacterReplacement(
                        d.getContent(), JATEProperties.TERM_CLEAN_PATTERN
                );

                Set<String> candidates = index.retrieveTermsCanonicalInDoc(d);
                for (String t : candidates) {
                    int freq = counter.count(context, index.retrieveVariantsOfTermCanonical(t));
                    feature.addToTermFreq(t, freq);
                }
            }
        }

        public boolean isFinished() {
            return finished;
        }

        private void setFinished(boolean finished) {
            this.finished = finished;
        }
    }
}
