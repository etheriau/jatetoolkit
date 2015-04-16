package uk.ac.shef.wit.commons;

/**
 * Holds a pair of objects and defines equals() and hashCode(), much like <i>Map.Entry</i>.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public class ObjectPair<K extends Comparable<K>,V> implements Comparable<ObjectPair<K,V>> {
   private K _key;
   private V _value;

   public ObjectPair(final K key, final V value) {
      _key = key;
      _value = value;
   }

   public K getKey() {
      return _key;
   }

   public ObjectPair<K,V> setKey(K key) {
      _key = key;
      return this;
   }

   public V getValue() {
      return _value;
   }

   public ObjectPair<K,V> setValue(V value) {
      _value = value;
      return this;
   }

   public int compareTo(final ObjectPair<K,V> pair) {
      return _key.compareTo(pair._key);
   }

   public boolean equals(final Object o) {
      if (this == o) return true;
      final ObjectPair<K,V> pair = (ObjectPair<K,V>)o;
      return _key.equals(pair._key) && _value.equals(pair._value);
   }

   public int hashCode() {
      return 29 * _key.hashCode() + _value.hashCode();
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