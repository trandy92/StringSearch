package StringSearch;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by andreas.reichel on 28.01.21.
 */
public class SearchRunnable implements Runnable {
    private Thread t;

    char[] wordsToSearchThroughCharArray;
    List<String> wordsToSearchThrough;
    List<String> wordsContainingSubstring;
    SearchAlgorithm searchAlgorithm;
    String searchString;

    SearchRunnable(List<String> wordsToSearchThrough, List<String> wordsContainingSubstring, SearchAlgorithm searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
        this.wordsContainingSubstring = wordsContainingSubstring;
        this.wordsToSearchThrough = wordsToSearchThrough;
        StringBuilder allWordsTogether = new StringBuilder();
        for (String wordToSearchThrough : wordsToSearchThrough)
        {
            allWordsTogether.append(wordToSearchThrough);
            allWordsTogether.append(" ");
        }
        this.wordsToSearchThroughCharArray = new char[allWordsTogether.length()];
        this.wordsToSearchThroughCharArray = allWordsTogether.toString().toCharArray();

    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;

    }

    @Override
    public void run() {
        searchAlgorithm.search(wordsToSearchThroughCharArray, searchString.toCharArray(), wordsContainingSubstring);
    }




    public void start () {;
        if (t == null) {
            t = new Thread (this, "newThread");
            t.start ();
        }
    }
}
