package StringSearch;

import java.util.List;

public interface SearchAlgorithm {
    public void search(char[] text, char[] pat, List<String> foundWords);
}
