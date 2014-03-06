package uk.ac.shef.dcs.oak.jate;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 */


public class JATEProperties {
    private Properties _properties = new Properties();
    private static JATEProperties _ref = null;

    //public static final String NP_FILTER_PATTERN = "[^a-zA-Z0-9\\-]";
    //replaced by the following var:
    public static final String TERM_CLEAN_PATTERN = "[^a-zA-Z0-9\\-]";

    public static final String NLP_PATH = "jate.system.nlp";
    public static final String TERM_MAX_WORDS = "jate.system.term.maxwords";
    public static final String TERM_IGNORE_DIGITS = "jate.system.term.ignore_digits";
    public static final String MULTITHREAD_COUNTER_NUMBERS="jate.system.term.frequency.counter.multithread";

    private JATEProperties() {
        read();
    }

    public static JATEProperties getInstance() {
        if (_ref == null) {
            _ref = new JATEProperties();
        }
        return _ref;
    }

    private void read() {
        InputStream in = null;
        try {
            /*InputStream x= getClass().getResourceAsStream("/indexing.properties");*/
            in = getClass().getResourceAsStream("/jate.properties");
            _properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) try {
                in.close();
                in = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getProperty(String key) {
        return _properties.getProperty(key);
    }

    public String getNLPPath() {
        return getProperty(NLP_PATH);
    }

    public int getMaxMultipleWords() {
        try {
            return Integer.valueOf(getProperty(TERM_MAX_WORDS));
        } catch (NumberFormatException e) {
            return 5;
        }
    }

    public int getMultithreadCounterNumbers() {
        try {
            return Integer.valueOf(getProperty(MULTITHREAD_COUNTER_NUMBERS));
        } catch (NumberFormatException e) {
            return 5;
        }
    }

    public boolean isIgnoringDigits() {
        try {
            return Boolean.valueOf(getProperty(TERM_IGNORE_DIGITS));
        } catch (Exception e) {
            return true;
        }
    }

}
