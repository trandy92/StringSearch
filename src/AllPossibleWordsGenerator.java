import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Created by andreas.reichel on 28.01.21.
 */
public class AllPossibleWordsGenerator {
    static private ArrayList<String> allPossibleWords = new ArrayList<String>();

    static public ArrayList<String> getAllPossibleWords(int wordLength, String[] alphabet)
    {
        List<String> alphabetList= Arrays.asList(alphabet);

        StringBuilder newWord= new StringBuilder();
        permute(alphabetList, wordLength, newWord);

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
