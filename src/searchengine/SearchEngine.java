package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * note
 * This is the main backend class.
 * This is where the actual search engine logic should go:
 * loading songs into the system, building TF/IDF, and ranking results.
 * If it has to do with how search works, it probably goes here.
 *
 * Replaced interface implementation with the Abstract class, which will implement the interface - dax
 */
public class SearchEngine extends AbstractSearchEngine {

    @Override
    public void buildTF() {
        for (Song song : songs) {
            // doesn't completely prevent the crash but this is still better on memory allowing for 290k

            // Combine lyrics into one block of text and lowercase everything
            String text = song.getLyrics().toLowerCase();

            // needed strip of punctuation guaranteed ' or other item
            String[] words = text.split("[^a-zA-Z0-9]+");

            for (String word : words) {
                if (word.isEmpty()) continue;

                // If we haven't seen this word before, create a new sub map
                tf.putIfAbsent(word, new HashMap<>());

                // Increment the count for this word in this song
                tf.get(word).put(song, tf.get(word).getOrDefault(song, 0) + 1);
            }
        }
    }

    @Override
    public void buildIDF() {
        int totalSongs = songs.size();

        // Loop through every term we collected in buildTF
        for (String term : tf.keySet()) {

            // Count how many songs contain this term
            int songsWithTerm = tf.get(term).size();

            // IDF = total songs / songs containing the term
            double idfScore = (double) totalSongs / songsWithTerm;

            idf.put(term, idfScore);
        }
    }

    // setter for song to be called in the search simulation
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    // Engine constructor
    public SearchEngine(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public List<Song> search(String query) {
        String[] terms = query.toLowerCase().split("[^a-zA-Z0-9]+");

        Map<Song, Double> scores = new HashMap<>();

        for (String term : terms) {
            if (!tf.containsKey(term)) continue;

            double idfScore = idf.getOrDefault(term, 0.0);

            for (Map.Entry<Song, Integer> entry : tf.get(term).entrySet()) {
                Song song = entry.getKey();
                int termFreq = entry.getValue();
                scores.put(song, scores.getOrDefault(song, 0.0) + (termFreq * idfScore));
            }
        }

        List<Song> results = new ArrayList<>(scores.keySet());
        results.sort((a, b) -> Double.compare(scores.get(b), scores.get(a)));

        return results;
    }
}