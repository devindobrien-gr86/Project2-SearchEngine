package searchengine;


/*
 * Note to brother
 * This class is just for storing one song from the TSV file.
 * Keep this one simple — just fields, constructor, getters, maybe toString.
 * Don’t put search logic or file reading in here.
 */

public class Song {
	
	    private String title;
	    private String tag;
	    private String artist;
	    private int year;
	    private int views;
	    private String lyrics;

	    public Song(String title, String tag, String artist, int year, int views, String lyrics) {
	        this.title = title;
	        this.tag = tag;
	        this.artist = artist;
	        this.year = year;
	        this.views = views;
	        this.lyrics = lyrics;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public String getTag() {
	        return tag;
	    }

	    public String getArtist() {
	        return artist;
	    }

	    public int getYear() {
	        return year;
	    }

	    public int getViews() {
	        return views;
	    }

	    public String getLyrics() {
	        return lyrics;
	    }

	    @Override
	    public String toString() {
	        return title + " by " + artist + " (" + year + ")";
	    }
	    
	    
	}



