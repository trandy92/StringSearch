
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {


        String[] alphabet= new String[] {"a", "b", "c", "d"};
        Vector<String> input = AllPossibleWordsGenerator.getAllPossibleWords(4, alphabet);
        for (String item : input)
        {
            System.out.println(item);
        }

        Vector<String> results = findSubstring("he", input);
        for (String item : results)
        {
            System.out.println(item + "\n");
        }
    }

    private static Vector<String> findSubstring(String substring, Vector<String> input) {
        Vector<String> results = new Vector<String>();
        for (String item : input)
        {
            if(item.startsWith(substring))
            {
                results.add(item);
            }
        }
        return results;
    }


}
