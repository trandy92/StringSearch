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
        searchRabinKarp(searchString.toCharArray(), searchString.length(), wordsToSearchThroughCharArray, wordsToSearchThroughCharArray.length, 101);
        //naiveSearch(wordsToSearchThroughCharArray, wordsToSearchThroughCharArray.length, searchString.toCharArray(), searchString.length() );
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
                while(i < lim && text[i]!= ' ')
                {
                    i++;
                }

            }
            wordFound.setLength(0);
        }

    }
    public final static int d = 26;
    static void searchRabinKarp(char[] pat, int M, char[] txt, int N, int q)
    {
        int i, j;
        int p = 0; // hash value for pattern
        int t = 0; // hash value for txt
        int h = 1;

        // The value of h would be "pow(d, M-1)%q"
        for (i = 0; i < M - 1; i++)
            h = (h * d) % q;

        // Calculate the hash value of pattern and first
        // window of text
        for (i = 0; i < M; i++) {
            p = (d * p + pat[i]) % q;
            t = (d * t + txt[i]) % q;
        }

        // Slide the pattern over text one by one
        for (i = 0; i <= N - M; i++) {

            // Check the hash values of current window of text
            // and pattern. If the hash values match then only
            // check for characters one by one
            if (p == t) {
                /* Check for characters one by one */
                for (j = 0; j < M; j++) {
                    if (txt[i+j] != pat[j])
                        break;
                }

                // if p == t and pat[0...M-1] = txt[i, i+1, ...i+M-1]
                if (j == M)
                {
                    //System.out.println("Pattern found at index " + i);
                }

            }

            // Calculate hash value for next window of text: Remove
            // leading digit, add trailing digit
            if (i < N - M) {
                t = (d * (t - txt[i] * h) + txt[i+M]) % q;

                // We might get negative value of t, converting it
                // to positive
                if (t < 0)
                    t = (t + q);
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
