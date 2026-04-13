package searchengine;

import java.util.*;

/*
 * This is the shared setup for the search engine.
 * Put stuff here that every search engine would need,
 * like the song list, TF map, and IDF map.
 * Don't put full search logic here unless it's something we want shared.
 * @author Devin O'Brien
 */
public abstract class AbstractSearchEngine implements Searchable {

    // stores all songs loaded from the TSV file
    protected List<Song> songs;

    // stores term frequency 
    protected Map<String, Map<Song, Integer>> tf;

    // stores inverse document frequency 
    protected Map<String, Double> idf;

    /**
     * Constructor — initializes all three maps
     */
    public AbstractSearchEngine() {
        songs = new ArrayList<>();
        tf = new HashMap<>();
        idf = new HashMap<>();
    }

    /**
     * Must be implemented in SearchEngine
     * Builds the TF map from all loaded songs
     */
    public abstract void buildTF();

    /**
     * Must be implemented in SearchEngine
     * Builds the IDF map using the TF map
     */
    public abstract void buildIDF();

}