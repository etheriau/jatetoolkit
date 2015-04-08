package uk.ac.shef.dcs.oak.jate.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import uk.ac.shef.dcs.oak.jate.JATEProperties;
import uk.ac.shef.dcs.oak.jate.core.npextractor.CandidateTermExtractor;
import uk.ac.shef.dcs.oak.jate.util.control.IStopList;
import uk.ac.shef.dcs.oak.jate.util.control.Lemmatizer;
import uk.ac.shef.dcs.oak.jate.util.control.StopList;

/** Newly added. Contains the common utility functions for NCValue Algorithm and Chi Square Algorithm.
 *
 **/

public class Utility{
	
	/** Returns the input string after lemmatization. */	
	public static String getLemma(String context, IStopList stoplist, Lemmatizer lemmatizer) throws IOException{
		String stopremoved = CandidateTermExtractor.applyTrimStopwords(context.trim(), stoplist, lemmatizer);
		String lemma=null;
		if(stopremoved!=null)
			lemma = lemmatizer.normalize(stopremoved.toLowerCase().trim());
		return lemma;
	}
	
	/** Returns the input string after lemmatization. */	
	//Ankit: Removing this method as it is not really required.
/*	public static String getLemma(String context) throws IOException{
		StopList stoplist = new StopList(true);
		Lemmatizer lemmatizer = new Lemmatizer();
		String stopremoved = CandidateTermExtractor.applyTrimStopwords(context.trim(), stoplist, lemmatizer);
		String lemma=null;
		if(stopremoved!=null)
			lemma = lemmatizer.normalize(stopremoved.toLowerCase().trim());
		return lemma;
	}
	
	*/
	
	public static String getLemmaChiSquare(String context, StopList stoplist, Lemmatizer lemmatizer) throws IOException{
		String stopremoved = CandidateTermExtractor.applyTrimStopwords(context.trim(), stoplist, lemmatizer);
		String lemma=null;
		if(stopremoved!=null)
			lemma = lemmatizer.normalize(stopremoved.toLowerCase().trim());
		//Ankit: unnecessary output information
		//else{
		//	System.out.println("null lemma" + context.trim());
		//}
		return lemma;
	}
	
	/** Returns the sentence after modifying it by replacing the characters which are not in the character set [a-zA-Z0-9 -] by a space. */
	public static String getModifiedSent(String sentence) {
		StringBuilder modified_sent = new StringBuilder();		
		String[] split_sent = sentence.split(" ");
		for(String s: split_sent){					
			s= CandidateTermExtractor.applyCharacterReplacement(s, JATEProperties.TERM_CLEAN_PATTERN);
			//Ankit: to ignore double spaces in the sentence
			if(s.length() > 0)
				modified_sent.append(s).append(" ");
		}		
		return modified_sent.toString().trim();
	}
	
	/** Returns the subset of variants passed as an argument which are present in the input sentence. */
	public static Set<String> getTermVariants_sent(String sent, Set<String> variants){
		Set<String> TermVariants_Sent = new HashSet<String>();		
		for(String variant: variants) {
			//Ankit: testing
//			if(sent.contains("learn") && variant.contains("learn"))
//				System.out.println("Sent: "+sent+"\nVariant: "+variant);
			
			if(sent.contains(variant)) {
				//Ankit: testing
//				if(sent.contains("learn") && variant.equals("learn")){
//					System.out.println("Sent: "+sent+"\nVariant: "+variant);
//					System.out.println("char before check: "+(sent.charAt(sent.indexOf(variant)-1)==' '));
//					System.out.println("char after check: "+(sent.charAt(sent.indexOf(variant)+variant.length())));
//					System.out.println("check sentence length: "+(sent.indexOf(variant)+variant.length()<sent.length()));
//				}
			
				//Ankit: Fix, it was skipping 'learn' if 'learning' occurred before it in a sentence, and similar cases
//				if((sent.indexOf(variant)==0 || sent.charAt(sent.indexOf(variant)-1)==' ') 
//						&& (sent.indexOf(variant)+variant.length()<sent.length() && sent.charAt(sent.indexOf(variant)+variant.length())== ' ')
//							||sent.indexOf(variant)+variant.length() == sent.length()) 
				
				if(sent.startsWith(variant+' ') || sent.endsWith(' '+variant) || sent.equals(variant) ||sent.contains(' '+variant+' '))
				{
					TermVariants_Sent.add(variant);						
					//Ankit: testing
//					if(variant.equals("learn"))
//						System.out.println("Sent Variant Added: "+variant);
				}
			}
		}
		return TermVariants_Sent;
	}
	
	/**
	 * Returns the set of words after lemmatizing every word present in the input set of context words. */
	//Ankit: added stoplist and lemmatizer as input parameters to avoid multiple allocations
	public static Set<String> getLemmatizedWordSet(Set<String> word_set, IStopList stoplist, Lemmatizer lemmatizer) throws IOException{
		Set<String> LemmatizedWordSet = new HashSet<String>();		
		for(String context: word_set){
			String lemmatizedWord = getLemma(context, stoplist, lemmatizer);
			if(lemmatizedWord == null){
				continue;
			}
			else
				LemmatizedWordSet.add(lemmatizedWord);
		}
		return LemmatizedWordSet;
	}
}
