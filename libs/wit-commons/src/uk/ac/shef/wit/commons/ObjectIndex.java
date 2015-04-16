package uk.ac.shef.wit.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Indexes objects by associating unique integer values to each object.
 * Notice that you should wrap it in a Collections.synchronizeMap() if using it in concurrent environments.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
// TODO: what's faster - HashMap or Object2IntHashMap?
public class ObjectIndex extends HashMap<Object, Integer> {

   protected int _index;

   public ObjectIndex() {
   }

   public ObjectIndex(final int initialCapacity) {
      super(initialCapacity);
   }

   public ObjectIndex(final int initialCapacity, final float loadFactor) {
      super(initialCapacity, loadFactor);
   }

   public ObjectIndex(final Object[] index) {
      super(index.length << 1);
      for (_index = 0; _index < index.length; ++_index)
         if (index[_index] != null) put(index[_index], _index);
   }

   public int add(final Object object) {
      Integer index = get(object);
      if (index == null) put(object, index = _index++);
      return index;
   }

   public void addAll(final Collection<Object> objects) {
      for (Object object : objects) add(object);
   }

   public void addAll(final Object[] objects) {
      for (Object object : objects) add(object);
   }

   public Object[] toArray(Object[] inverted) {
      for (Map.Entry<Object,Integer> entry : entrySet()) inverted[entry.getValue()] = entry.getKey();
      return inverted;
   }

   public ObjectIndex read(final BufferedReader reader) throws IOException {
      clear();
      String line;
      while ((line = reader.readLine()) != null) {
         final StringTokenizer tokenizer = new StringTokenizer(line);
         final int idx = Integer.parseInt(tokenizer.nextToken());
         final Object value = tokenizer.nextToken();
         put(value, idx);
      }
      reader.close();
      return this;
   }

   public ObjectIndex write(final PrintWriter out) {
      for (Map.Entry<Object,Integer> entry : entrySet()) {
         out.print(entry.getValue());
         out.print(' ');
         out.print(entry.getKey());
         out.println();
      }
      out.close();
      return this;
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