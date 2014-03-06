package uk.ac.shef.wit.commons;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads a taxonomy from file.
 * The file format is one concept per line, where tabs indicate the Levels in the taxonomy tree.
 *
 * @author Jose' Iria, NLP Group, University of Sheffield
 *         (<a  href="mailto:J.Iria@dcs.shef.ac.uk" >email</a>)
 */
public class SimpleTaxonomyReader {

   protected String _prefix;

   public SimpleTaxonomyReader() {
      this("");
   }

   public SimpleTaxonomyReader(final String prefix) {
      _prefix = prefix;
   }

   public TreeNode read(final File file) throws IOException {
      DefaultMutableTreeNode root = null, current;
      int currentLevel = 0;

      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = reader.readLine();
      if (line != null) {
         root = current = new DefaultMutableTreeNode(_prefix + line.trim().toLowerCase());
         while ((line = reader.readLine()) != null) {
            final DefaultMutableTreeNode node = new DefaultMutableTreeNode(_prefix + line.trim().toLowerCase());
            final int level = countTabs(line);
            if (currentLevel >= level)
               for (int i = 0; i < (currentLevel - level) + 1; ++i) current = (DefaultMutableTreeNode) current.getParent();
            current.add(node);
            current = node;
            currentLevel = level;
         }
      }
      return root;
   }

   private int countTabs(String line) {
      int count = 0;
      while (line.charAt(count) == ' ') ++count;
      return count >> 2;
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