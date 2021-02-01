package StringSearch;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by andreas.reichel on 28.01.21.
 */
public class SearchAlgorithm implements Runnable {
    private Thread t;

    private String searchString="";
    char[] wordsToSearchThroughCharArray;
    List<String> wordsToSearchThrough;
    List<String> wordsContainingSubstring;

    SearchAlgorithm(List<String> wordsToSearchThrough, List<String> wordsContainingSubstring) {
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
        /*List<String> results = new ArrayList<String>();
        for (String item : wordsToSearchThrough)
        {
            if(item.startsWith(searchString.toString()))
            {
                wordsContainingSubstring.add(item);
            }
        }*/
        naiveSearch(wordsToSearchThroughCharArray, wordsToSearchThroughCharArray.length, searchString.toCharArray(), searchString.length() );
    }

    private void naiveSearch(char[] text, int n, char[] pat, int m  )
    {
        int i, j, k, lim;
        lim = n - m + 1;
        StringBuilder wordFound = new StringBuilder();

        for(i=0; i < lim; i++)
        {

            k=i;
            for(j=0; j<m && text[k]==pat[j];j++) {
                wordFound.append(text[k]);
                k++;
            }
            if(j == m)
            {
                while( text[k]!=' ')
                {
                    wordFound.append(text[k]);
                    k++;
                }

                wordsContainingSubstring.add(wordFound.toString());

            }else
            {
                while(i+1 < lim && text[i+1]!= ' ')
                {
                    i++;
                }
                i++;
            }
            wordFound.setLength(0);
        }

    }
    public void start () {;
        if (t == null) {
            t = new Thread (this, "newThread");
            t.start ();
        }
    }
}
