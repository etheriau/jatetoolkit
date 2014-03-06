package uk.ac.shef.wit.commons;

import java.util.HashMap;
import java.util.Map;

/**
 * Convenience wrapper for maps whose values are <code>Integer</code> objects.
 */
public class IntegerMap<K> extends HashMap<K, Integer> {

   private Integer _defaultReturnValue;

   public IntegerMap(final int defaultReturnValue, final int initialCapacity,final float loadFactor) {
      super(initialCapacity, loadFactor);
      _defaultReturnValue = defaultReturnValue;

   }

   public IntegerMap(final int defaultReturnValue, final int initialCapacity) {
      super(initialCapacity);
      _defaultReturnValue = defaultReturnValue;
   }

   public IntegerMap(final int defaultReturnValue) {
      _defaultReturnValue = defaultReturnValue;
   }

   public IntegerMap() {
      this(-1);
   }

   public IntegerMap(Map<? extends K, ? extends Integer> m) {
      super(m);
   }

   public Integer get(final Object key) {
      final Integer i = super.get(key);
      return i == null? _defaultReturnValue : i;
   }

}

/*
    (c) Copyright 2005-2006 Natural Language Processing Group, The University of Sheffield
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