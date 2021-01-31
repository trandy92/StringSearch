package StringSearch;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by andreas.reichel on 28.01.21.
 */
public class SearchAlgorithm implements Runnable {
    private Thread t;

    private String searchString="";
    List<String> wordsToSearchThrough;
    List<String> wordsContainingSubstring;

    SearchAlgorithm(List<String> wordsToSearchThrough, List<String> wordsContainingSubstring) {
        this.wordsToSearchThrough = wordsToSearchThrough;
        //for (String wordToSearchThrough : wordsToSearchThrough)

        //char[] hello=
        this.wordsContainingSubstring = wordsContainingSubstring;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;

    }

    @Override
    public void run() {
        List<String> results = new ArrayList<String>();
        for (String item : wordsToSearchThrough)
        {
            if(item.startsWith(searchString.toString()))
            {
                wordsContainingSubstring.add(item);
            }
        }
    }

    public void start () {;
        if (t == null) {
            t = new Thread (this, "newThread");
            t.start ();
        }
    }
}
