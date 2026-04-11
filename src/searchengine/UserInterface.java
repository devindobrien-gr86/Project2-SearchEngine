package searchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Team note:
 * This class is for the part the user actually interacts with.
 * Stuff like welcome messages, asking for queries, showing results,
 * and exiting should all go here.
 * Don’t put the heavy backend search logic in this file.
 */

/*
 * 
 * Note to brother
 * Nano implemented in main method, still working with it, its a huge WIP.
 */
public class UserInterface {
    //@author Dax Morin

    private Scanner scn;
    private String userName;
    private List<String> queryHistory;
    private SearchEngine searchEngine;

    /** Scans for searches in the engine, adds previous searches into an arraylist
     * @param engine
     */
    public UserInterface(SearchEngine engine) {
        scn = new Scanner(System.in);
        queryHistory = new ArrayList<>();
        this.searchEngine = engine;
    }

    /**
     * Program startup, greeting message, function loop, then the exit message
     * all of these are run as a heiarchy, one before the other
     */
    public void start() {
        System.out.print("Enter your name: ");
        userName = scn.nextLine();
        greetUser();
        runSearchLoop();
        exitMessage();
    }

    /**
     * Prints options and greeting message
     */
    private void greetUser() {
    	System.out.println("\nWelcome, " + userName + "!");
    	System.out.println("Type your search query below.");
    	System.out.println("Type EXIT to quit.\n");
    	System.out.println("---------------------------------------------------------");
    }

    /**
     * Basic idea for search loop
     */
    private void runSearchLoop() {
    	
    	while (true) {
            System.out.print("\nSearch> ");
            String query = scn.nextLine();
           
            if (query.equalsIgnoreCase("EXIT")) {
                break;
            }
            //if user just doesn't type anything and hits enter
            if (query.isEmpty()) {
            	System.out.println("Please enter a valid query.\n");
                continue;
            }

            queryHistory.add(query);

            List<Song> results = searchEngine.search(query);
            displayResults(results);
        }
    }

    /**
     * @param results
     * Prints results based on the query, prints top 5
     */
    public void displayResults(List<Song> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }
        

        System.out.println("\nTop 5 Results:");
        int count = 1;
        for (Song song : results) {
            System.out.println(count + ". " + song);
            count++;
            if (count > 5) {
                break;
            }
        }
    }

    /**
     * Final message after user exits program
     */
    private void exitMessage() {
    	System.out.println("\n--- Search Session Summary ---");

    	if (queryHistory.isEmpty()) {
    	    System.out.println("No queries entered.");
    	} else {
    	    for (String q : queryHistory) {
    	        System.out.println("- " + q);
    	    }
    	}
    	 System.out.println("\nGoodbye, " + userName + "!");
    }
    
    public double precisionAt5(List<Song> results, String query) {

        int relevant = 0;
        int limit = Math.min(5, results.size());

        for (int i = 0; i < limit; i++) {
            Song song = results.get(i);

            String text = (song.getTitle() + " " + song.getLyrics()).toLowerCase();

            if (text.contains(query.toLowerCase())) {
                relevant++;
            }
        }

        return limit == 0 ? 0 : (double) relevant / 5;
    }
}