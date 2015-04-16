package uk.ac.shef.wit.commons.logger;

import java.io.PrintStream;

/**
 * An observer that simply logs the application's activity to a PrintStream.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public class LogToPrintStream implements LoggerObserver {

   protected static final int DISPLACEMENT_UNIT = 3;

   protected PrintStream _out;

   /**
    * Sole constructor.
    *
    * @param out the stream to output to.
    */
   public LogToPrintStream(final PrintStream out) {
      _out = out;
   }

   @Override
   protected void finalize() {
      try {
         super.finalize();
      } catch (Throwable ignore) {
      }
      _out.close();
   }

   /**
    * Accepts a message from the application.
    *
    * @param message the message (can be <code>null</code>).
    * @param level   the level of the underlying process (see {@link Logger}).
    */
   public void tell(final String message, final int level) {
      _out.println(message);
   }

   public void start(final int level, final int numSteps, final String title) {
   }

   public void progress(final int level) {
   }

   public void progress(final int level, final String message) {
      for (int i = 0; i < level * DISPLACEMENT_UNIT; ++i) _out.print(" ");
      tell(message, 0);
   }

   public void finish(final int level) {
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