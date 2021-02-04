package StringSearch;

import java.util.List;

public class BoyerMooreSearch implements SearchAlgorithm {
    private final static int NO_OF_CHARS=256;

    static void badCharHeuristic( char[] str, int size,
                           int badchar[])
    {
        int i;

        // Initialize all occurrences as -1
        for (i = 0; i < NO_OF_CHARS; i++) {
            badchar[i] = -1;
        }

        // Fill the actual value of last occurrence
        // of a character
        for (i = 0; i < size; i++) {
            badchar[str[i]] = i;
        }
    }

    public void search( char[] txt, char[] pat, List<String> results)
    {
        int m = pat.length;
        int n = txt.length;
        StringBuilder foundWord = new StringBuilder();
        int badchar[] = new int[NO_OF_CHARS];
    /* Fill the bad character array by calling
    the preprocessing function badCharHeuristic()
    for given pattern */
        badCharHeuristic(pat, m, badchar);

        int s = 0; // s is shift of the pattern with
        // respect to text
        while(s <= (n - m))
        {
            int j = m - 1;

        /* Keep reducing index j of pattern while
        characters of pattern and text are
        matching at this shift s */
        while(j >= 0 && pat[j] == txt[s + j])
        {
            j--;
        }

        /* If the pattern is present at current
        shift, then index j will become -1 after
        the above loop */
        if (j < 0)
        {
            if(s!= 0 && txt[s-1] == ' ')
            {
                for(int x = s; x < txt.length && txt[x]!=' '; x++)
                {
                    foundWord.append(txt[x]);
                }
                foundWord.toString();
                results.add(foundWord.toString());
            }

            /* Shift the pattern so that the next
            character in text aligns with the last
            occurrence of it in pattern.
            The condition s+m < n is necessary for
            the case when pattern occurs at the end
            of text */
                s += (s + m < n)? m-badchar[txt[s + m]] : 1;
            }

            else {
            /* Shift the pattern so that the bad character
            in text aligns with the last occurrence of
            it in pattern. The max function is used to
            make sure that we get a positive shift.
            We may get a negative shift if the last
            occurrence of bad character in pattern
            is on the right side of the current
            character. */
                s += Math.max(1, j - badchar[txt[s + j]]);
                foundWord.setLength(0);
            }
        }
    }

}
