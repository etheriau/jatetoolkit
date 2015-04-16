package uk.ac.shef.wit.commons;

import java.util.*;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;

/**
 * Several utility methods related to formatting output.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public class UtilOutput {

   private UtilOutput() {
   }

   /**
    * Separator string for lists.
    */
   public static final String LIST_SEPARATOR = ",";

   /**
    * Separator string for lines.
    */
   public static final String LINE_SEPARATOR = System.getProperty("line.separator");

   /**
    * Prints a collection.
    *
    * @param collection the collection to print.
    * @param out        the stream to write to.
    */
   public static void printCollection(final Collection<?> collection, final PrintStream out) {
      for (Object obj : collection) out.println(obj);
   }

   /**
    * Prints a map.
    *
    * @param map the map to print.
    * @param out the stream to write to.
    */
   public static void printMap(final Map<?, ?> map, final PrintStream out) {
      for (Object o : map.entrySet()) {
         final Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
         out.print(entry.getKey());
         out.print(" --> ");
         out.println(entry.getValue());
      }
   }

   /**
    * Prints an array.
    *
    * @param array the array to print.
    * @param out   the stream to write to.
    */
   public static void printArray(final Object[] array, final PrintStream out) {
      for (int i = 0; i < array.length; ++i) {
         out.print(i);
         out.print(" --> ");
         out.println(array[i]);
      }
   }

   /**
    * Generates a string that enumerates just the first elements of a collection (a preview).
    *
    * @param collection  the collection.
    * @param previewSize the number of elements to enumerate.
    * @return a string with the first <i>previewSize</i> elements of the collection.
    */
   public static String getCollectionPreview(final Collection<?> collection, int previewSize) {
      final Iterator<?> it = collection.iterator();
      final StringBuilder buffer = new StringBuilder();
      if (it.hasNext()) {
         buffer.append('[').append(it.next().toString());
         for (; previewSize > 0 && it.hasNext(); --previewSize) buffer.append(LIST_SEPARATOR).append(' ').append(it.next().toString());
         if (it.hasNext()) buffer.append(" ... ");
         buffer.append(']');
      }
      return buffer.toString();
   }

   /**
    * <p>Generates a string that enumerates just the first elements of a map (a preview).</p>
    *
    * @param map         the map.
    * @param previewSize the number of elements to enumerate.
    * @return a string with the first <i>previewSize</i> elements of the collection.
    */
   public static String getMapPreview(final Map map, int previewSize) {
      final Iterator it = map.entrySet().iterator();
      final StringBuilder buffer = new StringBuilder();
      if (it.hasNext()) {
         Map.Entry entry = (Map.Entry) it.next();
         buffer.append('[').append(entry.getKey()).append("->").append(entry.getValue());
         for (; previewSize > 0 && it.hasNext(); --previewSize) {
            entry = (Map.Entry) it.next();
            buffer.append(LIST_SEPARATOR).append(' ').append(entry.getKey()).append("->").append(entry.getValue());
         }
         if (it.hasNext()) buffer.append(" ... ");
         buffer.append(']');
      }
      return buffer.toString();
   }

   /**
    * Generates a string that enumerates just the first elements of an array (a preview).
    *
    * @param array  the array.
    * @param previewSize the number of elements to enumerate.
    * @return a string with the first <i>previewSize</i> elements of the collection.
    */
   public static String getArrayPreview(final Object[] array, final int previewSize) {
      return getCollectionPreview(Arrays.asList(array), previewSize);
   }

   /**
    * Returns a string describing of a collection of URI objects.
    *
    * @param uris the collection of URI objects.
    * @return the string describing uris
    */
   public static String getURIsPrettyPrint(final Collection<URI> uris) {
      final StringBuilder builder = new StringBuilder(uris.size() << 4).append("[ ");
      for (URI uri : uris) builder.append(uri.getFragment()).append(", ");
      builder.replace(builder.length() - 2, builder.length(), " ]");
      return builder.toString();
   }

   /**
    * Returns a string describing of a collection of URL objects.
    *
    * @param urls the collection of URL objects.
    * @return the string describing uris
    */
   public static String getURLsPrettyPrint(final Collection<URL> urls) {
      final StringBuilder builder = new StringBuilder(urls.size() << 4).append("[ ");
      for (URL url : urls) builder.append(url.getFile()).append(", ");
      builder.replace(builder.length() - 2, builder.length(), " ]");
      return builder.toString();
   }

   /**
    * Adds a number of spaces before and after the string passed as argument.
    *
    * @param start   the number of spaces to getComponentIndex at the start.
    * @param end     the required width of the string.
    * @param message the string to alter.
    * @return the formatted string.
    */
   public static String getFormattedString(final int start, final int end, final String message) {
      final StringBuilder buffer = new StringBuilder();
      for (int i = 0; i < start; ++i) buffer.append(' ');
      buffer.append(message);
      for (int i = 0; i < end - start - message.length(); ++i) buffer.append(' ');
      return buffer.toString();
   }

   /**
    * Applies StringTokenizer to the string and returns all the tokens found.
    *
    * @param string the string to tokenize.
    * @return all the tokens found in string.
    */
   public static String[] getAllTokens(final String string) {
      return getAllTokens(string, LIST_SEPARATOR);
   }

   /**
    * Applies StringTokenizer to the string and returns all the tokens found.
    *
    * @param string    the string to tokenize.
    * @param separator the separator for the tokens.
    * @return all the tokens found in string.
    */
   public static String[] getAllTokens(final String string, final String separator) {
      final StringTokenizer tokenizer = new StringTokenizer(string, separator);
      final String[] tokens = new String[tokenizer.countTokens()];
      for (int i = 0; i < tokens.length; ++i) tokens[i] = tokenizer.nextToken().trim();
      return tokens;
   }

   /**
    * Returns a time string with the format "hours:minutes:seconds.milliseconds".
    *
    * @param time time in milliseconds as given by <i>System.currentTimeMillis()</i>.
    * @return the formatted time string.
    */
   public static String getPrettyTime(long time) {
      final StringBuilder buffer = new StringBuilder();
      long place = time / 3600000L;
      buffer.append(place).append(':');
      place = (time %= 3600000L) / 60000L;
      if (time < 10) buffer.append('0');
      buffer.append(place).append(':');
      place = (time %= 60000L) / 1000L;
      if (time < 10) buffer.append('0');
      buffer.append(place).append('.').append(time % 1000L);
      return buffer.toString();
   }

}

/*
    (c) Copyright 2004-2006 Natural Language Processing Group, The University of Sheffield
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:

    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.

    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.

    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/