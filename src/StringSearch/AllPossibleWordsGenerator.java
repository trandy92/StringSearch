package StringSearch;

import java.util.*;

/**
 * Created by andreas.reichel on 28.01.21.
 */
public class AllPossibleWordsGenerator {
    static private List<String> allPossibleWords = new ArrayList<String>();

    static public List<String> getAllPossibleWords(int wordLength, String[] alphabet)
    {
        List<String> alphabetList= Arrays.asList(alphabet);

        StringBuilder newWord= new StringBuilder();
        permute(alphabetList, wordLength, newWord);
        Collections.shuffle(allPossibleWords);
        return allPossibleWords;
    }

    static private void permute(List<String> alphabet, int wordLength, StringBuilder oldWord) {
        if (oldWord.length() >= wordLength) {
            allPossibleWords.add(oldWord.toString());
            return;
        }

        for (int i = 0; i < alphabet.size(); i++) {
            StringBuilder newWord = new StringBuilder(oldWord);
            newWord.append(alphabet.get(i));
            permute(alphabet, wordLength, newWord);
        }
    }

}
