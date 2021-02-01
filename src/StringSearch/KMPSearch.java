package StringSearch;

import java.util.List;

public class KMPSearch implements SearchAlgorithm {
    @Override
    public void search(char[] txt, char[] pat, List<String> foundWords) {
            int M = pat.length;
            int N = txt.length;
            StringBuilder foundWord = new StringBuilder();

            // create lps[] that will hold the longest
            // prefix suffix values for pattern
            int lps[] = new int[M];
            int j = 0; // index for pat[]

            // Preprocess the pattern (calculate lps[]
            // array)
            computeLPSArray(pat, M, lps);

            int i = 0; // index for txt[]
            while (i < N) {
                if (pat[j] == txt[i]) {
                    j++;
                    i++;
                }
                if (j == M) {
                    if(txt[i-(M+1)] == ' ') {
                        for (int x = (i - j); txt[x] != ' '; x++) {
                            foundWord.append(txt[x]);
                        }
                        foundWords.add(foundWord.toString());
                        foundWord.setLength(0);
                    }
                    j = lps[j - 1];
                }

                // mismatch after j matches
                else if (i < N && pat[j] != txt[i]) {
                    // Do not match lps[0..lps[j-1]] characters,
                    // they will match anyway
                    if (j != 0)
                        j = lps[j - 1];
                    else
                        i = i + 1;
                }
            }
        }

        void computeLPSArray(char[] pat, int M, int lps[])
        {
            // length of the previous longest prefix suffix
            int len = 0;
            int i = 1;
            lps[0] = 0; // lps[0] is always 0

            // the loop calculates lps[i] for i = 1 to M-1
            while (i < M) {
                if (pat[i] == pat[len]) {
                    len++;
                    lps[i] = len;
                    i++;
                }
                else // (pat[i] != pat[len])
                {
                    // This is tricky. Consider the example.
                    // AAACAAAA and i = 7. The idea is similar
                    // to search step.
                    if (len != 0) {
                        len = lps[len - 1];

                        // Also, note that we do not increment
                        // i here
                    }
                    else // if (len == 0)
                    {
                        lps[i] = len;
                        i++;
                    }
                }
            }
        }


    }
