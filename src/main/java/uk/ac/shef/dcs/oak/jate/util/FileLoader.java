package uk.ac.shef.dcs.oak.jate.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author <a href="mailto:z.zhang@dcs.shef.ac.uk">Ziqi Zhang</a>
 *         <p>
 *         Loads files in a directory recursively
 *         </p>
 */

public class FileLoader {

	/**
	 * Load files recursively from a directory
	 *
	 * @param dir
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<File> getFileRecursive(File dir) throws FileNotFoundException {
		List<File> result = new ArrayList<File>();

		File[] filesAndDirs = dir.listFiles();
		List filesDirs = Arrays.asList(filesAndDirs);
		Iterator filesIter = filesDirs.iterator();
		File file;
		while (filesIter.hasNext()) {
			file = (File) filesIter.next();
			if (file.isFile()) result.add(file); //always add, even if directory
			else {
				List<File> deeperList = getFileRecursive(file);
				result.addAll(deeperList);
			}

		}
		return result;
	}

	/**
	 * Load directories recursively from a directory
	 *
	 * @param dir
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<File> getFolderRecursive(File dir) throws FileNotFoundException {
		Set<File> result = new HashSet<File>();

		File[] filesAndDirs = dir.listFiles();
		List filesDirs = Arrays.asList(filesAndDirs);
		Iterator filesIter = filesDirs.iterator();
		File file;
		while (filesIter.hasNext()) {
			file = (File) filesIter.next();
			if (file.isDirectory()) {
				result.add(file); //always add, even if directory
				List<File> deeperList = getFolderRecursive(file);
				result.addAll(deeperList);
			}

		}
		result.add(dir);

		List<File> toreturn = new ArrayList<File>(result);
		Collections.sort(toreturn);
		return toreturn;
	}
}
