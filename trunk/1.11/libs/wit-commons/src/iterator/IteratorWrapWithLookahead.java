package uk.ac.shef.wit.commons.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Encapsulates common functionality concerning implementing a lookahead for an iterator.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public abstract class IteratorWrapWithLookahead <T> extends IteratorNoRemove<T> {

   private Iterator<T> _iterator;
   private T _lookahead;

   /**
    * Constructs this iterator by wrapping an existing one.
    * @param iterator the iterator to use.
    */
   protected IteratorWrapWithLookahead(final Iterator<T> iterator) {
      _iterator = iterator;
      _lookahead = null;
   }

   /**
    * It determines whether this iterator will return one more element. It iterates the underlying iterator testing
    * the objects returned. It stops when the shouldInclude returns true, holding the lookahead object.
    * @return whether this iterator will return one more element.
    */
   public boolean hasNext() {
      if (_lookahead == null)
         do {
            _lookahead = _iterator.hasNext() ? _iterator.next() : null;
         } while (_lookahead != null && !shouldInclude(_lookahead));
      return _lookahead != null;
   }

   /**
    * Returns the lookahead object.
    * @return the lookahead object
    */
   public T next() {
      if (_lookahead == null) throw new NoSuchElementException();
      final T object = _lookahead;
      _lookahead = null;
      return object;
   }

   /**
    * Tests whether the given object coming from the underlying iterator should be included in this iterator.
    * @param lookahead object coming from the underlying iterator.
    * @return true if object should be included in this iterator, false otherwise.
    */
   protected abstract boolean shouldInclude(T lookahead);

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