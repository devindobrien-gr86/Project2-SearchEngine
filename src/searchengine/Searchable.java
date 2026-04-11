package searchengine;

import java.util.List;

/*
 
 * This is just the interface.
 * Its job is to say what a search engine should be able to do.
 * Implemented for the grade as stated "interface used correctly"  
 */

public interface Searchable  {
	
	// This method must be implemented by any class that uses this interface
	 List<Song> search(String query);

}
