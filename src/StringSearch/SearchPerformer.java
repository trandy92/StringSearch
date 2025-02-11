package StringSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreas.reichel on 29.01.21.
 */
public class SearchPerformer {
    private final static int NUMBER_THREADS_DEFAULT = 2;
    private int numberThreads;
    private List<String> wordsContainingSubstring = null;
    private List<String> wordsToSearchThrough;
    private SearchRunnable[] searchRunnables;
    private Long timeNeededForSearchInMS = null;
    private SearchAlgorithm searchAlgorithm;

    public SearchPerformer(List<String> wordsToSearchThrough, SearchAlgorithm searchAlgorithm) {
        this(wordsToSearchThrough, NUMBER_THREADS_DEFAULT, searchAlgorithm);
    }

    public SearchPerformer(List<String> wordsToSearchThrough, int numberThreads, SearchAlgorithm searchAlgorithm) {
        this.wordsToSearchThrough = wordsToSearchThrough;
        this.numberThreads = numberThreads;
        this.searchAlgorithm = searchAlgorithm;
        initializeThreadsForPerformingMultithreadedSearch();
    }

    public void setAlgorithm(SearchAlgorithm searchAlgorithm)
    {
        this.searchAlgorithm = searchAlgorithm;
        initializeThreadsForPerformingMultithreadedSearch();
    }

    private void initializeThreadsForPerformingMultithreadedSearch()
    {
        wordsContainingSubstring = new ArrayList<String>();
        searchRunnables = new SearchRunnable[numberThreads];

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

            searchRunnables[i] = new SearchRunnable(sublist ,wordsContainingSubstring,searchAlgorithm);
            firstIndex = endIndexOfSublist + 1;
        }
    }

    public void performSearch(String wordToSearchFor) {
        timeNeededForSearchInMS = null;
        wordsContainingSubstring.clear();
        Thread[] threadArray = new Thread[searchRunnables.length];

        long startTime = System.currentTimeMillis();
        int indexThread =0 ;
        for(SearchRunnable searchAlgorithm : searchRunnables)
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
