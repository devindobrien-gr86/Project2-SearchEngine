README.txt
Search Engine Project
Team: Devin O'Brien, Dax Morin

HOW TO RUN:

1. Place song_lyrics.tsv in the Project2 root folder
2. Run SearchSimulation.java as a Java Application in Eclipse
3. The simulation will run automatically, then launch the interactive search where you can search any word


HOW THE SYSTEM WORKS:

The search engine uses the TF-IDF (Term Frequency - Inverse Document Frequency)
retrieval model to rank songs by relevance to a given query.

1. TSVReader loads up to 290,000 songs from the TSV file into memory.
   The 290k limit is due to Java heap memory constraints on large datasets.

2. SearchEngine builds two maps:
   - TF map: tracks how many times each word appears in each song
   - IDF map: tracks how rare each word is across the entire collection

3. When a query is entered, each song is scored by summing TF * IDF
   for every word in the query. Songs are then ranked by highest score.

4. The top 5 results are displayed for each query.


FEATURES:

- Search across 290,000 songs by lyrics and title
- Top 5 results ranked by TF-IDF relevance score
- Interactive search loop — issue as many queries as you want
- Type EXIT to quit
- Session summary printed on exit showing all queries made
- Precision@5 score calculated for each query in simulation mode


DESIGN CHOICES:

OOP Design:
- Searchable interface declares the search() contract
- AbstractSearchEngine implements Searchable and holds shared maps
- SearchEngine extends AbstractSearchEngine with full TF/IDF logic
- This gives a clean interface into an abstract class into concrete classes

Search Enhancements:
1. Title Boost — title words are counted 3 extra times in the TF map.
   This ensures songs whose titles match the query rank higher.
   Example: searching "lose yourself" ranks "Lose Yourself to Dance" at #1.

2. Log IDF — the IDF formula uses Math.log() as in the original TF-IDF model.
   This prevents very common words like "the" and "love" from dominating
   multi-word query scores.

File Loading:
- Scanner used for TSV reading — simple and reliable for this use case
- Safety check skips any malformed rows with fewer than 6 columns
- parseIntSafe handles any non-numeric year/views values without crashing

