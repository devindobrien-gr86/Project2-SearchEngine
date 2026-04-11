
package searchengine;

import java.util.*;



public abstract class AbstractSearchEngine implements Searchable {

    // stores all songs from the TSV file
    protected List<Song> songs;

    // stores term frequency (TF)
   
    protected Map<String, Map<Song, Integer>> tf;

    // Stores IDF
    protected Map<String, Double> idf;

    // Constructor
    public AbstractSearchEngine() {
        songs = new ArrayList<>();
        tf = new HashMap<>();
        idf = new HashMap<>();
    }

    public abstract void buildTF();

    public abstract void buildIDF();
    
    
    
}


