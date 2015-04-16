package uk.ac.shef.dcs.oak.jate.util.control;

import dragon.nlp.tool.lemmatiser.EngLemmatiser;
import uk.ac.shef.dcs.oak.jate.JATEProperties;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Lemmatizer is a specific type of Normaliser and returns a string to its dictionary root.
 *
 * @author <a href="mailto:z.zhang@dcs.shef.ac.uk">Ziqi Zhang</a>
 */
public class Lemmatizer extends Normalizer {

	private EngLemmatiser lemmatizer;
	private final Map<String, Integer> tagLookUp = new HashMap<String, Integer>();

	public Lemmatizer() {
		init();
	}

	/**
	 * @param value original word
	 * @param pos the part of speech of the last word
	 * @return the lemma of original word
	 */
	public String getLemma(String value, String pos) {
		int POS = tagLookUp.get(pos);
		if (POS == 0)
			return lemmatizer.lemmatize(value);
		else
			return lemmatizer.lemmatize(value, POS);
	}

	/**
	 * Lemmatise a phrase or word. If a phrase, only lemmatise the most RHS word.
	 * @param value
	 * @return
	 */
	public String normalize(String value) {
		if(value.indexOf(" ")==-1||value.endsWith(" s")||value.endsWith("'s")) //if string is a single word, or it is in "XYZ's" form where the ' char has been removed
			return lemmatizer.lemmatize(value,1).trim();

		String part1 = value.substring(0,value.lastIndexOf(" "));
		String part2 = lemmatizer.lemmatize(value.substring(value.lastIndexOf(" ")+1),1);
		return part1+" "+part2.trim();

	}

	/**
	 * Lemmatise every word in the input string
	 * @param in
	 * @return the lemmatised string
	 */
	public String normalizeContent(String in){
		StringBuilder sb = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(in);
		while(tokenizer.hasMoreTokens()){
			String tok=tokenizer.nextToken();
			sb.append(normalize(tok)).append(" ");
		}
		return sb.toString().trim();
	}


	private void init() {
		// Initializes the temporary directory if required.
		final JATEProperties instance = JATEProperties.getInstance();
		String path = instance.getNLPPath();
		if ( path.startsWith( "jar:" ) ) {
			String dir = instance.getWorkPath() + File.separator + "lemmatizer";
			path = dir;
			File directory = new File(dir);
			if ( !directory.exists() ) {
				if ( !directory.mkdirs() ) {
					throw new UnsupportedOperationException("Could not initialize " + directory);
				}
				for ( String file : new String[]{ "adj.exc", "adj.index", "adv.exc", "adv.index", "noun.exc", "stopwordexc.list", "umlserror.list", "verb.exc", "verb.index" } ) {
					try {
						InputStream input = null;
						OutputStream output = null;
						try {
							input = instance.getNLPInputStream( "lemmatizer" + File.separator + file );
							if ( input == null ) {
								throw new IOException( "Unable to read: " + file );
							}
							output = new FileOutputStream( dir + File.separator + file );
							byte [] bytes = new byte[ 4096 ];
							while ( true ) {
								int len = input.read( bytes );
								if ( len == -1 ) {
									break;
								}
								output.write( bytes, 0, len );
							}
						} finally {
							try {
								if ( input != null ) {
									input.close();
								}
							} finally {
								if ( output != null ) {
									output.close();
								}
							}
						}
					} catch ( IOException ioe ) {
						throw new UnsupportedOperationException( "Unable to copy data", ioe );
					}
				}
			}
		}

		lemmatizer = new EngLemmatiser( path, false, true );
		tagLookUp.put("NN", 1);
		tagLookUp.put("NNS", 1);
		tagLookUp.put("NNP", 1);
		tagLookUp.put("NNPS", 1);
		tagLookUp.put("VB", 2);
		tagLookUp.put("VBG", 2);
		tagLookUp.put("VBD", 2);
		tagLookUp.put("VBN", 2);
		tagLookUp.put("VBP", 2);
		tagLookUp.put("VBZ", 2);
		tagLookUp.put("JJ", 3);
		tagLookUp.put("JJR", 3);
		tagLookUp.put("JJS", 3);
		tagLookUp.put("RB", 4);
		tagLookUp.put("RBR", 4);
		tagLookUp.put("RBS", 4);
	}

}
