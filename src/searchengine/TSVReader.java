package searchengine;

import java.io.*;
import java.util.*;

// parse 290k lines to prevent the crash
/*
 * Note to brother
 * This class should only handle reading the TSV file and turning each row into a Song.
 * Basically all file loading stuff goes here.
 * Don't put TF/IDF or user interaction in this class we will implement later.
 */
public class TSVReader {

	/*
	 * I know you said not to touch tsvreader but a canadian friend of mine gave me an idea
	 * bufferedreader is super efficient for our project, it skips a lot of the extra stuff a normal reader would use
	 * I have a suggested design below, do what you want for it but I'm 99% sure that bufferedreader will be very fast
	 * No offense if you delete it, it doesn't implement TF and IDF exactly how I want it to (if at all)
	 */

	/**
	 * @param string
	 * @return
	 * parses tsv file,
	 */

	// Takes a file path, returns a list of Song objects
	public List<Song> read(String filePath) {
		List<Song> songs = new ArrayList<>();

		try {
			// Decided on Scanner object because it gives the same result
			// both SCNR and BR allow around 290000 items before a crash
			// it may be better over all but this is what we know and are comfortable with
			Scanner scn = new Scanner(new File(filePath));

			// skip header
			if (scn.hasNextLine()) scn.nextLine();

			// we need to cut the scanner or else this error is thrown because a lack of memory
//            Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//        	at searchengine.SearchEngine.buildTF(SearchEngine.java:37)
//        	at searchengine.SearchSimulation.main(SearchSimulation.java:66)

			// int count will store this
			int count = 0;

			while (scn.hasNextLine()) {

				if (count >= 1500) break; // stop at 290k, at 300k we get error message

				String line = scn.nextLine();
				String[] parts = line.split("\t");

				// Safety check
				if (parts.length < 6) continue;

				// Separate each column
				String title  = parts[0];
				String tag    = parts[1];
				String artist = parts[2];
				int year      = parseIntSafe(parts[3]);
				int views     = parseIntSafe(parts[4]);
				String lyrics = parts[5];

				Song song = new Song(title, tag, artist, year, views, lyrics);
				songs.add(song);
				count++;
			}

			scn.close();

		} catch (IOException e) {
			System.out.println("Error reading TSV file: " + e.getMessage());
		}

		return songs;
	}

	// crash prevention, not sure how it actually works but this is the standard logic
	private int parseIntSafe(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}