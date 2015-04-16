package uk.ac.shef.wit.commons.logger;

import uk.ac.shef.wit.commons.UtilOutput;

import java.io.PrintStream;

/**
 * An observer that logs application's activity to a PrintStream, outputting some profile information as well.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public class LogToPrintStreamWithProfileInformation extends LogToPrintStream {

   protected long[] _time = new long[1 << 8];

   public LogToPrintStreamWithProfileInformation(final PrintStream out) {
      super(out);
   }

   @Override
   public void start(final int level, final int numSteps, final String title) {
      super.start(level, numSteps, title);
      tell("(" + numSteps + " steps)", level);
      _time[level] = System.currentTimeMillis();
   }

   @Override
   public void finish(final int level) {
      super.finish(level);
      for (int i = 0; i < level * DISPLACEMENT_UNIT; ++i) _out.print(" ");
      _out.println(new StringBuilder().append("> ").
            append("total time: ").append(UtilOutput.getPrettyTime(System.currentTimeMillis() - _time[level])).append(", ").
            append("used memory: ").append(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() >> 20).append("Mb").toString());
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