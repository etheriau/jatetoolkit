package uk.ac.shef.dcs.oak.jate.util.control;

import uk.ac.shef.dcs.oak.jate.JATEProperties;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a stop word list. These are the words which usually should not occur in a valid term
 *
 * @author <a href="mailto:z.zhang@dcs.shef.ac.uk">Ziqi Zhang</a>
 */


public class StopList implements IStopList {

	private final Set<String> words = new HashSet<String>();

	private final boolean _caseSensitive;

	/**
	 * Creates an instance of stop word list
	 * @param caseSensitive whether the list should ignore cases
	 * @throws IOException
	 */
	public StopList (final boolean caseSensitive) throws IOException {
		super();
		_caseSensitive =caseSensitive;
		loadStopList( JATEProperties.getInstance().getNLPInputStream( "stoplist.txt" ),_caseSensitive);
	}

	/**
	 * @param word
	 * @return true if the word is a stop word, false if otherwise
	 */
	public boolean isStopWord(String word){
		if(!_caseSensitive) return words.contains(word.toLowerCase());
		return words.contains(word);
	}

	private void loadStopList(final InputStream stream, final boolean lowercase) throws IOException {
      final BufferedReader reader = new BufferedReader(new InputStreamReader( stream ));
	  try {
		  String line;
		  while ((line = reader.readLine()) != null) {
			  line = line.trim();
			  if (line.equals("") || line.startsWith("//")) continue;
			  if (lowercase) words.add(line.toLowerCase());
			  else words.add(line);
		  }
	  } finally {
		  reader.close();
	  }
   }

}
