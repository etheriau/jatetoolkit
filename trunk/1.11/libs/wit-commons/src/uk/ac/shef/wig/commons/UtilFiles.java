package uk.ac.shef.wit.commons;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Several utility methods related to file manipulation.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public class UtilFiles {

   public static final String CLASSPATH_SEPARATOR = System.getProperty("os.name").contains("inux") ? ":" : ";";
   

   private static final int BUFFER_SIZE = 2 << 13;
   public static final char[] _readBuffer = new char[BUFFER_SIZE];

   /**
    * <p>Lists files in directories and their subdirectories (recursively).</p>
    *
    * @param paths the directories to list the files from.
    * @return a sorted set of <i>File</i> objects.
    */
   public static Set<File> listFilesRecursive(final String[] paths) {
      final Set<File> files = new TreeSet<File>();
      for (String path : paths) files.addAll(listFilesRecursive(path));
      return files;
   }

   /**
    * <p>Lists files in directory and its subdirectories (recursively).</p>
    *
    * @param path the directory to list the files from.
    * @return a sorted set of <i>File</i> objects.
    */
   public static Set<File> listFilesRecursive(final String path) {
      final Set<File> files = new TreeSet<File>();
      listFilesRecursive(files, new File(path));
      return files;
   }

   /**
    * <p>Lists filenames in directories and their subdirectories (recursively).</p>
    *
    * @param paths the directories to list the filenames from.
    * @return a sorted set of <i>String</i> objects (the filenames).
    */
   public static Set<String> listFilenamesRecursive(final String[] paths) {
      final Set<String> filenames = new TreeSet<String>();
      for (String path : paths) filenames.addAll(listFilenamesRecursive(path));
      return filenames;
   }

   /**
    * <p>Lists filenames in directory and its subdirectories (recursively).</p>
    *
    * @param path the directory to list the filenames from.
    * @return a sorted set of <i>String</i> objects (the filenames).
    */
   public static Set<String> listFilenamesRecursive(final String path) {
      final List<File> files = new LinkedList<File>();
      final Set<String> filenames = new TreeSet<String>();
      listFilesRecursive(files, new File(path));
      try {
         for (File file : files) filenames.add(file.getCanonicalPath());
      } catch (IOException e) {
         e.printStackTrace();
      }
      return filenames;
   }

   /**
    * <p>Lists urls of files in directories and their subdirectories (recursively).</p>
    *
    * @param paths the directories to list the urls from.
    * @return a sorted set of <i>URL</i> objects.
    */
   public static Set<URL> listURLsRecursive(final String[] paths) {
      final Set<URL> urls = new HashSet<URL>();
      for (String path : paths) urls.addAll(listURLsRecursive(path));
      return urls;
   }

   /**
    * <p>Lists urls of files in directory and its subdirectories (recursively).</p>
    *
    * @param path the directory to list the urls from.
    * @return a sorted set of <i>URL</i> objects.
    */
   public static Set<URL> listURLsRecursive(final String path) {
      final List<File> files = new LinkedList<File>();
      final Set<URL> urls = new HashSet<URL>();
      listFilesRecursive(files, new File(path));
      try {
         for (File file : files) urls.add(file.toURL());
      } catch (IOException e) {
         e.printStackTrace();
      }
      return urls;
   }

   /**
    * <p>Converts filenames to their respective URL forms.</p>
    */
   public static URL[] filenamesToURLs(final String[] filenames) throws MalformedURLException {
      final Collection<URL> urls = new LinkedList<URL>();
      for (String filename : filenames) urls.add(new File(filename).toURL());
      return urls.toArray(new URL[urls.size()]);
   }

   /**
    * <p>Deletes files recursively.</p>
    *
    * @param path the file or directory to delete.
    */
   public static void deleteFilesRecursive(final File path) {
      if (path.isDirectory()) {
         final File[] files = path.listFiles();
         for (File file : files) deleteFilesRecursive(file);
      }
      path.delete();
   }

   /**
    * <p>Adds a separator character to the end of the filename if it does not have one already.</p>
    *
    * @param filename the filename.
    * @return the filename with a separator at the end.
    */
   public static String addSeparator(String filename) {
      if (filename != null && !filename.endsWith(File.separator)) filename += File.separator;
      return filename;
   }

   /**
    * <p>Replaces the base path of a set of filenames.</p>
    *
    * @param filenames   the set of filenames.
    * @param newBasePath the new base path.
    * @return the set of new filenames.
    */
   public static Set<String> replaceBasePath(final Set<String> filenames, final String newBasePath) {
      final Set<String> replaced = new HashSet<String>();
      for (String filename : filenames) replaced.add(newBasePath + new File(filename).getName());
      return replaced;
   }

   /**
    * <p>Replaces the filename extension with another one.</p>
    *
    * @param filename     the filename.
    * @param newExtension the new extension.
    * @return the filename with a new extension.
    */
   public static String replaceExtension(final String filename, final String newExtension) {
      final File file = new File(filename);
      final String name = file.getName();
      final String parent = file.getParent();
      final int pos = name.lastIndexOf(".");
      return (pos == -1 ? filename : parent + File.separatorChar + name.substring(0, pos)) + newExtension;
   }

   /**
    * <p>Reads content from an URL into a string buffer.</p>
    *
    * @param url the url to get the content from.
    * @return string buffer with the contents of the file.
    */
   public static StringBuilder getContent(final URL url) throws IOException {
      final StringBuilder buffer = new StringBuilder(BUFFER_SIZE);
      InputStreamReader reader = null;
      try {
         reader = new InputStreamReader(url.openStream());
         for (int numRead = 0; numRead >= 0;) {
            int offset = 0;
            for (; offset < _readBuffer.length && (numRead = reader.read(_readBuffer, offset, _readBuffer.length - offset)) >= 0; offset += numRead) ;
            buffer.append(_readBuffer, 0, offset);
         }
      } finally {
         if (reader != null) reader.close();
      }
      buffer.trimToSize();
      return buffer;
   }

   /**
    * <p>Writes content from a String into a file.</p>
    *
    * @param content the string with the data to write
    * @param pathToOutput the path to the output file
    */
   public static void writeToFile(final String content, final String pathToOutput) throws IOException {
      OutputStream stream = null;
      try {
         stream = new BufferedOutputStream(new FileOutputStream(new File(pathToOutput)));
         stream.write(content.getBytes());
      }
      finally {
         if (stream != null) stream.close();
      }
   }

   /**
    * <p>Convenience method for serializing an object into a file.</p>
    */
   public static void serialize(final String path, final Object object) throws IOException {
      final ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(path));
      stream.writeObject(object);
      stream.close();
   }

   /**
    * <p>Convenience method for deserializing an object from a file.</p>
    *
    * @return the object obtained from the file.
    */
   public static Object deserialize(final String path) throws ClassNotFoundException, IOException {
      Object result;
      FileInputStream inputStream = new FileInputStream(path);
      ObjectInputStream objectInputStream = null;
      try {
         objectInputStream = new ObjectInputStream(inputStream);
         result = objectInputStream.readObject();
      } catch (IOException e) {        // be sure to release input stream lock (at some point couldn't delete file)
         inputStream.close();
         if (objectInputStream != null) objectInputStream.close();
         throw e;
      } catch (ClassNotFoundException e) {
         objectInputStream.close();
         throw e;
      }
      objectInputStream.close();
      return result;
   }



   private static void listFilesRecursive(final Collection<File> fileCollection, final File path) {
      if (path.isDirectory()) {
         final File[] files = path.listFiles();
         for (File file : files) listFilesRecursive(fileCollection, file);
      } else
         fileCollection.add(path);
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