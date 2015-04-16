package uk.ac.shef.wit.commons.logger;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A Logger class. It is able organizes output into stages.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public class Logger {
   public static final int LEVEL_ERROR = 3, LEVEL_WARN = 2, LEVEL_INFO = 1, LEVEL_DEBUG = 0;

   private static Logger _instance;

   public static void tell(String message) {
      getInstance().fireTell(message, LEVEL_INFO);
   }

   public static void tell(String message, int level) {
      getInstance().fireTell(message, level);
   }

   public static void start(int numSteps, String title) {
      getInstance().fireStart(numSteps, title);
   }

   public static void progress() {
      getInstance().fireProgress();
   }

   public static void progress(String message) {
      getInstance().fireProgress(message);
   }

   public static void finish() {
      getInstance().fireFinish();
   }


   private Collection<LoggerObserver> _observers = new LinkedList<LoggerObserver>();
   private int _stageLevel = 0;

   private Logger() { }

   public static Logger getInstance() {
      if (_instance == null) _instance = new Logger();
      return _instance;
   }

   public synchronized void addObserver(final LoggerObserver observer) {
      _observers.add(observer);
   }

   public synchronized void removeObserver(final LoggerObserver observer) {
      _observers.remove(observer);
   }

   public synchronized void fireTell(final String message) {
      fireTell(message, LEVEL_INFO);
   }

   public synchronized void fireTell(final String message, final int level) {
      for (final LoggerObserver observer : _observers) observer.tell(message, level);
   }

   public synchronized void fireStart(final int numSteps, final String title) {
      fireProgress(title);
      ++_stageLevel;
      for (final LoggerObserver observer : _observers) observer.start(_stageLevel, numSteps, title);
   }

   public synchronized void fireProgress() {
      for (final LoggerObserver observer : _observers) observer.progress(_stageLevel);
   }

   public synchronized void fireProgress(String message) {
      for (final LoggerObserver observer : _observers) observer.progress(_stageLevel, message);
   }

   public synchronized void fireFinish() {
      for (final LoggerObserver observer : _observers) observer.finish(_stageLevel);
      --_stageLevel;
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