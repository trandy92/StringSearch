package StringSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreas.reichel on 29.01.21.
 */
public class SearchPerformer {
    private final static int NUMBER_THREADS_DEFAULT = 2;
    private int numberThreads;
    List<String> wordsContainingSubstring = null;
    List<String> wordsToSearchThrough;
    SearchAlgorithm[] searchAlgorithms;
    Long timeNeededForSearchInMS = null;

    public SearchPerformer(List<String> wordsToSearchThrough) {
        this(wordsToSearchThrough, NUMBER_THREADS_DEFAULT);
    }

    public SearchPerformer(List<String> wordsToSearchThrough, int numberThreads) {
        this.wordsToSearchThrough = wordsToSearchThrough;
        this.numberThreads = numberThreads;
        initializeThreadsForPerformingMultithreadedSearch(wordsToSearchThrough, numberThreads);
    }


    private void initializeThreadsForPerformingMultithreadedSearch( List<String> wordsToSearchThrough, int numberThreads)
    {
        wordsContainingSubstring = new ArrayList<String>();
        searchAlgorithms = new SearchAlgorithm[numberThreads];

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

            searchAlgorithms[i] = new SearchAlgorithm(sublist ,wordsContainingSubstring);
            firstIndex = endIndexOfSublist + 1;
        }
    }

    public void performSearch(String wordToSearchFor) {
        timeNeededForSearchInMS = null;
        wordsContainingSubstring.clear();
        Thread[] threadArray = new Thread[searchAlgorithms.length];

        long startTime = System.currentTimeMillis();
        int indexThread =0 ;
        for(SearchAlgorithm searchAlgorithm : searchAlgorithms)
        {
            searchAlgorithm.setSearchString(wordToSearchFor);
            threadArray[indexThread] = new Thread(searchAlgorithm);
            threadArray[indexThread].start();
            indexThread++;
        }

        try {
            for(Thread thread : threadArray)
            {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timeNeededForSearchInMS = System.currentTimeMillis() - startTime;
    }

    public List<String> getWordsContainingSubstring() throws Exception {
        if(timeNeededForSearchInMS==null)
        {
            throw new Exception("You need to perform search before getting the search results.");
        }
        return wordsContainingSubstring;
    }

    public long getTimeNeededForSearchInMS() throws Exception {
        if(timeNeededForSearchInMS==null)
        {
            throw new Exception("You need to perform search before getting the time needed.");
        }
        return timeNeededForSearchInMS;
    }

}
