package uk.ac.shef.dcs.oak.jate.util.control;

/**
 * Represents a stop word list. These are the words which usually should not occur in a valid term.
 *
 * This interface allows it to be easily customizable.
 */
public interface IStopList {
    /**
     * @return true if the word is a stop word, false if otherwise
     */
    boolean isStopWord( String word );
}
