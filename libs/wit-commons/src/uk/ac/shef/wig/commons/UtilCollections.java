package uk.ac.shef.wit.commons;

import java.util.*;

/**
 * Several utility methods related to collections.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public class UtilCollections {

   private UtilCollections() {
   }


   /**
    * Converts a <code>Set</code> into a sorted <code>List</code>.
    *
    * @param set        the set to convert.
    * @param comparator the comparator used to sort the list.
    * @return the sorted list.
    */
   public static <T> List<T> setToSortedList(final Set<T> set, final Comparator<T> comparator) {
      final List<T> list = new LinkedList<T>(set);
      Collections.sort(list, comparator);
      return list;
   }

   /**
    * Compares two character arrays lexicographically.
    */
   public static final Comparator<char[]> CHAR_ARRAY_COMPARATOR = new Comparator<char[]>() {
      public int compare(final char[] o1, final char[] o2) {
         final int length = o1.length < o2.length ? o1.length : o2.length;
         for (int i = 0; i < length; ++i) {
            if (o1[i] > o2[i])
               return 1;
            else if (o1[i] < o2[i])
               return -1;
         }
         return length < o1.length ? 1 : length < o2.length ? -1 : 0;
      }
   };

   /**
    * Compares two char arrays by length first and then from right to left lexicographically.
    */
   public static final Comparator<char[]> CHAR_ARRAY_COMPARATOR_FAST = new Comparator<char[]>() {
      public int compare(final char[] o1, final char[] o2) {
         if (o1.length < o2.length) return -1;
         if (o2.length < o1.length) return 1;
         for (int i = o1.length - 1; i >= 0; --i) {
            if (o1[i] > o2[i])
               return 1;
            else if (o1[i] < o2[i])
               return -1;
         }
         return 0;
      }
   };

   /**
    * Compares contents of two StringBuilder lexicographically.
    */
   public static final Comparator<StringBuilder> STRING_BUILDER_COMPARATOR = new Comparator<StringBuilder>() {
      public int compare(final StringBuilder o1, final StringBuilder o2) {
         final int length = o1.length() < o2.length() ? o1.length() : o2.length();
         for (int i = 0; i < length; ++i) {
            if (o1.charAt(i) > o2.charAt(i))
               return 1;
            else if (o1.charAt(i) < o2.charAt(i))
               return -1;
         }
         return length < o1.length() ? 1 : length < o2.length() ? -1 : 0;
      }
   };

   public static <E, B extends Collection<E>> B add(final B base, final E... elements) {
      for (E element : elements) base.add(element);
      return base;
   }

   public static <E, B extends Collection<E>> B add2(final B base, final E... elements) {
      for (E element : elements) base.add(element);
      return base;
   }

   public static <E, B extends Collection<E>> B remove(final B base, final E... elements) {
      for (E element : elements) base.remove(element);
      return base;
   }

   // TODO: confirm that if it is passed an IntSet it uses the more efficient type-specific addAll()
   public static <E, C extends Collection<E>> C addAll(final C base, final C... collections) {
      for (C collection : collections) if (collection != null) base.addAll(collection);
      return base;
   }

   public static <C extends Collection<?>> C removeAll(final C base, final C... collections) {
      for (C collection : collections) if (collection != null) base.removeAll(collection);
      return base;
   }

   public static <E, C extends Collection<E>> C replace(final C base, final C remove, final C add) {
      base.removeAll(remove);
      base.addAll(add);
      return base;
   }

   public static <E, C extends Collection<E>> E transfer(final E element, final C from, final C to) {
      from.remove(element);
      to.add(element);
      return element;
   }

   public static <E> boolean containsSome(final Collection<E> c1, final Collection<E> c2) {
      for (E e : c2) if (c1.contains(e)) return true;
      return false;
   }

   public static Object[] mergeArrays(final Object[] a1, final Object[] a2, final Object[] merge) {
      System.arraycopy(a1, 0, merge, 0, a1.length);
      System.arraycopy(a2, 0, merge, a1.length, a2.length);
      return merge;
   }

   public static boolean checkArrayContains(final Object[] array, final Object object) {
      for (Object obj : array) if (object.equals(obj)) return true;
      return false;
   }

   public static int[] toIntArray(final Collection<Integer> collection) {
      final int[] array = new int[collection.size()];
      int i = 0;
      for (Integer integer : collection) array[i++] = integer;
      return array;
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