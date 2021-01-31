package StringSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreas.reichel on 29.01.21.
 */
public class SearchPerformer {
    private final static int NUMBER_THREADS_DEFAULT =2;
    private int numberThreads;
    List<String> wordsContainingSubstring=null;
    String wordToSearchFor=null;
    List<String> wordsToSearchThrough;
    Thread[] threadArray;
    Long timeNeededForSearchInMS=null;
    boolean searchAlreadyPerformed=false;

    public SearchPerformer(String wordToSearchFor, List<String> wordsToSearchThrough)
    {
        this(wordToSearchFor,wordsToSearchThrough, NUMBER_THREADS_DEFAULT);
    }

    public SearchPerformer(String wordToSearchFor, List<String> wordsToSearchThrough, int numberThreads)
    {
        this.wordToSearchFor=wordToSearchFor;
        this.wordsToSearchThrough=wordsToSearchThrough;
        this.numberThreads=numberThreads;
        initializeThreadsForPerformingMultithreadedSearch(wordToSearchFor, wordsToSearchThrough, numberThreads);
    }

    private void initializeThreadsForPerformingMultithreadedSearch(String wordToSearchFor, List<String> wordsToSearchThrough, int numberThreads)
    {
        wordsContainingSubstring = new ArrayList<String>();
        threadArray = new Thread[numberThreads];

        int firstIndex = 0;
        int sublistSize = wordsToSearchThrough.size() / numberThreads;
        int endIndexOfSublist = firstIndex + sublistSize;
        for(int i = 0; i < numberThreads; i++)
        {
            if(i == (numberThreads-1)) {
                endIndexOfSublist = wordsToSearchThrough.size();
            }else
            {
                endIndexOfSublist = firstIndex + sublistSize;
            }
            List<String> sublist = wordsToSearchThrough.subList(firstIndex, endIndexOfSublist);
            threadArray[i] = new Thread(new SearchAlgorithm(wordToSearchFor, sublist ,wordsContainingSubstring));
            firstIndex = endIndexOfSublist + 1;
        }
    }

    public void performSearch() {
        if (searchAlreadyPerformed == true) {
            reset();
        }
        long startTime = System.currentTimeMillis();
        for(Thread thread : threadArray)
        {
            thread.start();
        }

        try {
            for(Thread thread : threadArray)
            {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        searchAlreadyPerformed = true;
        timeNeededForSearchInMS = System.currentTimeMillis() - startTime;
    }

    public List<String> getWordsContainingSubstring() throws Exception {
        if(searchAlreadyPerformed==false)
        {
            throw new Exception("You need to perform search before getting the search results.");
        }
        return wordsContainingSubstring;
    }

    public long getTimeNeededForSearchInMS() throws Exception {
        if(searchAlreadyPerformed==false)
        {
            throw new Exception("You need to perform search before getting the time needed.");
        }
        return timeNeededForSearchInMS;
    }
    private void reset()
    {
        System.out.println("Reset search");
        timeNeededForSearchInMS = null;
        wordsContainingSubstring = new ArrayList<String>();
        initializeThreadsForPerformingMultithreadedSearch(wordToSearchFor, wordsToSearchThrough, numberThreads);
    }
}
