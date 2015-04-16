package uk.ac.shef.wit.commons.logger;

/**
 * Observer of the activity of an application.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public interface LoggerObserver {
   /**
    * Accepts a message from the application.
    *
    * @param message the message (can be <code>null</code>).
    * @param level   the level of the underlying process (see {@link Logger}).
    */
   void tell(String message, int level);

   /**
    * Signals the beginning of a new stage.
    *
    * @param level    the level of the stage, to allow tracking nested processes.
    * @param numSteps specifies the number of steps that will be perfomed in this new stage.
    * @param title    the title of the stage.
    */
   void start(int level, int numSteps, String title);

   /**
    * Signals a new step is being initiated in the current stage.
    *
    * @param level the level of the stage.
    */
   void progress(int level);

   /**
    * Signals a new step is being initiated in the current stage.
    *
    * @param level   the level of the stage.
    * @param message the message that explains what this step is about (can be null).
    */
   void progress(int level, String message);

   /**
    * Signals that the current stage has now ended.
    *
    * @param level the level of the stage.
    */
   void finish(int level);

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