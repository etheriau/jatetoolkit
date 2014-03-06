package uk.ac.shef.wit.commons;

import java.util.Comparator;

/**
 * Holds an object and a numerical value associated with it.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public class ObjectWithScore <T> implements Comparable<ObjectWithScore<T>> {
   private T _object;
   private double _score;

   public ObjectWithScore(final T object) {
      this(object, 0.0);
   }

   public ObjectWithScore(final T object, final double value) {
      _object = object;
      _score = value;
   }

   public int compareTo(final ObjectWithScore<T> o) {
      return COMPARATOR_SCORE_NATURAL_ORDER.compare(this, o);
   }

   // accessors

   public T getObject() {
      return _object;
   }

   public double getScore() {
      return _score;
   }

   // comparators

   private final static Comparator<ObjectWithScore> COMPARATOR_SCORE_NATURAL_ORDER = getComparatorScoreNaturalOrder();

   public static <T> Comparator<ObjectWithScore> getComparatorScoreNaturalOrder() {
      return new Comparator<ObjectWithScore>() {
         public int compare(final ObjectWithScore o1, final ObjectWithScore o2) {
            return o1._score < o2._score ? -1 : o1._score > o2._score ? 1 : 0;
         }
      };
   }

   public static <T> Comparator<ObjectWithScore> getComparatorScoreReverseOrder() {
      return new Comparator<ObjectWithScore>() {
         public int compare(final ObjectWithScore o1, final ObjectWithScore o2) {
            return o1._score < o2._score ? 1 : o1._score > o2._score ? -1 : 0;
         }
      };
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