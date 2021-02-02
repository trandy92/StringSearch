package StringSearch;

import java.util.List;

public class NaiveStringSearch implements SearchAlgorithm {
    public void search(char[] text, char[] pat, List<String> results)
    {
        int n = text.length;
        int m = pat.length;
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
                results.add(wordFound.toString());
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

}
