package StringSearch;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by andreas.reichel on 28.01.21.
 */
public class SearchAlgorithm implements Runnable {
    private Thread t;


    private String searchString;
    List<String> wordsToSearchThrough;
    List<String> wordsContainingSubstring;

    SearchAlgorithm(String searchString, List<String> wordsToSearchThrough, List<String> wordsContainingSubstring) {
        this.searchString = searchString;
        this.wordsToSearchThrough = wordsToSearchThrough;
        this.wordsContainingSubstring = wordsContainingSubstring;
    }

    @Override
    public void run() {
        System.out.println("Running new thread");
        List<String> results = new ArrayList<String>();
        for (String item : wordsToSearchThrough)
        {
            if(item.startsWith(searchString))
            {
                wordsContainingSubstring.add(item);
            }
        }
        System.out.println("Thread exiting.");
    }

    public void start () {
        System.out.println("Starting new Thread" );
        if (t == null) {
            t = new Thread (this, "newThread");
            t.start ();
        }
    }
}
