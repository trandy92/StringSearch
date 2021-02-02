package GUI;

import StringSearch.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringSearchGUI {
    private final int NUMBER_THREADS=8;
    private static JFrame frame = new JFrame("StringSearchGUI");
    private final String[] ALGORITHMS = { "Naive", "Boyer", "Rabin Karp", "KMP" };
    //private final String[] ALPHABET= new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r","s", "t","u", "v","w","x","y","z"};
    private String[] ALPHABET = new String[] {"a", "b", "c", "d"};
    private final int NUMBER_RESULTS_DISPLAY = 200;
    private final int ALPHABET_LENGTH=4;

    private JTextField wordToSearchForTextField;
    private JPanel panelMain;
    private JLabel searchPerformanceLabel;
    private List<String> bibleText;
    private List<String> alphabetText;
    private SearchPerformer bibleSearchPerformer;
    private SearchPerformer alphabetSearchPerformer;
    private JComboBox algorithmList;
    private JRadioButton bibleButton;
    private JRadioButton alphabetButton;

    private JList<String> searchResultsJlist;
    private DefaultListModel listModel = new DefaultListModel();
    private SearchPerformer searchPerformer;



    public StringSearchGUI(){
        panelMain.setLayout(new GridLayout(0,2));

        initAlgorithmSelection();
        initSearchText();


        searchResultsJlist = new JList<String>(listModel);
        searchResultsJlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JScrollPane resultsScrollPane = new JScrollPane(searchResultsJlist);
        searchPerformanceLabel = new JLabel();

        panelMain.add(resultsScrollPane);
        panelMain.add(searchPerformanceLabel);
        panelMain.add(algorithmList);
        panelMain.add(alphabetButton);
        panelMain.add(bibleButton);

        initWordToSearchForTextField();
    }

    private void initSearchText() {
        bibleButton=new JRadioButton("Bibel");
        alphabetButton=new JRadioButton("Alphabet");
        bibleButton.setBounds(75,50,100,30);
        alphabetButton.setBounds(75,100,100,30);
        ButtonGroup bg=new ButtonGroup();
        bg.add(bibleButton);
        bg.add(alphabetButton);


        alphabetButton.addActionListener(e -> {
            searchPerformer= alphabetSearchPerformer;
            performSearch();

        });
        bibleButton.addActionListener(e -> {
            searchPerformer= bibleSearchPerformer;
            performSearch();
        });
        alphabetButton.setSelected(true);

        try {
            String bible = Files.readString(Path.of("bible.txt"), StandardCharsets.US_ASCII);
            String[] bibleWords=bible.split(" ");
            bibleText = Arrays.asList(bibleWords);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        alphabetText = AllPossibleWordsGenerator.getAllPossibleWords(ALPHABET_LENGTH, ALPHABET);

        bibleSearchPerformer = new SearchPerformer(bibleText, NUMBER_THREADS, new NaiveStringSearch());
        alphabetSearchPerformer = new SearchPerformer(alphabetText, NUMBER_THREADS, new NaiveStringSearch());
        searchPerformer = alphabetSearchPerformer;
    }

    private void performSearch()
    {
        listModel.clear();
        if(wordToSearchForTextField.getText().isEmpty())
        {
            return;
        }
        searchPerformer.performSearch(wordToSearchForTextField.getText());

        try {
            List<String> searchResults = searchPerformer.getWordsContainingSubstring();

            int i=0;
            for(String searchResult : searchResults)
            {
                i++;
                if(i>NUMBER_RESULTS_DISPLAY)
                {
                    break;
                }
                listModel.addElement(searchResult);

            }
            searchPerformanceLabel.setText("Search took " + searchPerformer.getTimeNeededForSearchInMS() + " ms");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    private void initWordToSearchForTextField()
    {
        wordToSearchForTextField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }
        });

        wordToSearchForTextField.setText("AAAA");
    }
    private void initAlgorithmSelection()
    {
        algorithmList = new JComboBox(ALGORITHMS);
        algorithmList.setSelectedIndex(0);
        algorithmList.addActionListener(e -> {

            if(algorithmList.getSelectedItem().equals("Naive"))
            {
                searchPerformer.setAlgorithm(new NaiveStringSearch());
            }
            if(algorithmList.getSelectedItem().equals("Boyer"))
            {
                searchPerformer.setAlgorithm(new BoyerMooreSearch());
            }
            if(algorithmList.getSelectedItem().equals("Rabin Karp"))
            {
                searchPerformer.setAlgorithm(new RabinKarpSearch());
            }
            if(algorithmList.getSelectedItem().equals("KMP"))
            {
                searchPerformer.setAlgorithm(new KMPSearch());
            }
            performSearch();
        });
    }


    public static void main(String[] args) {
        frame.setSize(300,250);
        frame.setContentPane(new StringSearchGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
