package uk.ac.shef.wit.commons.iterator;

import java.util.*;

/**
 * Convenience class to iterate through all the values in a map of the form Map&lt;?, Map&lt;?, ?&gt;&gt;.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public final class IteratorOfMapsMap<T> implements Iterator<T> {

   private Iterator<? extends Map<?, T>> _mapIterator;
   private Iterator<T> _collectionIterator;

   public IteratorOfMapsMap(final Map<?, ? extends Map<?, T>> map) {
      _mapIterator = map.values().iterator();
      _collectionIterator = ((List<T>) Collections.emptyList()).iterator();
      hasNext();
   }

   public boolean hasNext() {
      if (_collectionIterator.hasNext()) return true;
      while (_mapIterator.hasNext()) {
         _collectionIterator = _mapIterator.next().values().iterator();
         if (_collectionIterator.hasNext()) return true;
      }
      return false;
   }

   public T next() {
      return _collectionIterator.next();
   }

   public void remove() {
      _collectionIterator.remove();
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