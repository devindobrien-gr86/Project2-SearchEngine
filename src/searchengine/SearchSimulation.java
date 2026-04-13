package searchengine;
import java.util.*;

/*
 * This class is mostly for testing and the report part of the project.
 * Use this for timing how long TF/IDF takes, how long searches take,
 * and anything related to evaluating the engine.
 * This isn't really the main user-facing part.
 *
 * This class should NOT IMPLEMENT UI FEATURES DIRECTLY, ONLY CALL THEM
 */
public class SearchSimulation {

    /**
     * The simulation runs (and will run upon completion) as follows:
     * Excluding the user interface outside of the object, because all queries are pre-designed
     * 1- nanosecond timer begins
     * 2- TF and IDF are built for the tsv file, document can now be used for queries
     * 3 - Timer ends for the TF and IDF building
     * 4- For each query, the top 5 results are printed, ranked with precision
     */
    public static void main(String[] args) {

        // load songs
        TSVReader reader = new TSVReader();
        List<Song> songs = reader.read("song_lyrics.tsv");

        if (songs.isEmpty()) {
            System.out.println("No songs loaded. Check the file path.");
            return;
        }

        System.out.println("Songs loaded: " + songs.size());

        // create engine first — needed for TF/IDF build
        SearchEngine engine = new SearchEngine(songs);

        // time the TF/IDF build
        long tfidStart = System.nanoTime();

        engine.buildTF();
        engine.buildIDF();

        long tfidEnd = System.nanoTime();

        // create UI object after TF/IDF is built
        UserInterface ui = new UserInterface(engine);

        // prints tf idf time in both ns and ms for readability
        System.out.println("TF/IDF build time: " + (tfidEnd - tfidStart) + " ns");
        System.out.println("TF/IDF build time: " + (tfidEnd - tfidStart) / 1_000_000.0 + " ms\n");

        // queries for the searches
        // mix of: single word, multi word, rare word, very common word, gibberish
        List<String> q = Arrays.asList(
            "love",
            "baby",
            "love you",
            "broken heart",
            "triangle",
            "lose yourself",
            "california",
            "massachusetts",
            "the",
            "itsfridayincaliforniahuhhudfihdsdjfnj"
        );

        // loops through queries, also times how long this takes
        for (String query : q) {

            System.out.println("----------------------------------------------");

            long start = System.nanoTime();
            List<Song> results = engine.search(query);
            long end = System.nanoTime();

            System.out.println("\nQuery: \"" + query + "\"");
            System.out.println("Search time: " + (end - start) + " ns");

            ui.displayResults(results);

            // precision@5 — how many of the top 5 actually contain the query
            double precision = ui.precisionAt5(results, query);
            int relevant = (int)(precision * 5);
            System.out.println("Precision@5: " + precision + " (" + relevant + "/5 relevant)");
        }

        // hand off to interactive UI after simulation finishes
        System.out.println("\n==============================================");
        System.out.println("         SEARCH ENGINE - COS 161            ");
        System.out.println("==============================================\n");
        ui.start();
    }
}