package StringSearch;

import GUI.StringSearchGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchEvaluation {
    private static final String[] ALPHABET= new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r","s", "t","u", "v","w","x","y","z"};

    public static void main(String[] args) throws Exception {
        int wordLength=4;
        List<String> searchText = AllPossibleWordsGenerator.getAllPossibleWords(wordLength,ALPHABET);
        List<String> fourLetterWords = searchText;
        List<String> threeLetterWords = AllPossibleWordsGenerator.getAllPossibleWords(3,ALPHABET);
        List<String> twoLetterWords = AllPossibleWordsGenerator.getAllPossibleWords(2,ALPHABET);

        Collections.shuffle(fourLetterWords);
        Collections.shuffle(threeLetterWords);
        Collections.shuffle(twoLetterWords);

        List<String> evaluationWords = new ArrayList<String>();
        evaluationWords.addAll(fourLetterWords.subList(0,676));
        evaluationWords.addAll(threeLetterWords.subList(0,676));
        evaluationWords.addAll(twoLetterWords);

        SearchPerformer searchPerformer = new SearchPerformer(searchText, 8, new RabinKarpSearch());
        long[] times = new long[evaluationWords.size()];
        Collections.shuffle(evaluationWords);
        System.out.println(evaluationWords.size());
        for(int j=0; j<10; j++)
        {
            double timesSummedUp = 0;
            for(int i = 0; i < evaluationWords.size(); i++)
            {
                searchPerformer.performSearch(evaluationWords.get(i));
                timesSummedUp += searchPerformer.getTimeNeededForSearchInMS();;
                i++;
            }
            System.out.println(timesSummedUp/(evaluationWords.size()));
        }
    }
}
