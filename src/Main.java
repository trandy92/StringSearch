
import java.util.List;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String[] alphabet= new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r","s", "t","u", "v","w","x","y","z"};
        //String[] alphabet= new String[] {"a", "b", "c", "d"};

        ArrayList<String> allPossibleWords = AllPossibleWordsGenerator.getAllPossibleWords(5, alphabet);

        SearchPerformer searchPerformer = new SearchPerformer("hanna", allPossibleWords, 4);
        searchPerformer.performSearch();

        try {
            System.out.println("time in ms: " + searchPerformer.getTimeNeededForSearchInMS());
            List<String> searchResults = searchPerformer.getWordsContainingSubstring();
            System.out.print("Found " + searchResults.size() + " matches");
            for (String item : searchResults)
            {
                System.out.println(item + "\n");
            }
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
        }


    }

    private static ArrayList<String> findSubstring(String substring, ArrayList<String> input) {
        ArrayList<String> results = new ArrayList<String>();
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
