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
    private static JFrame frame = new JFrame("StringSearchGUI");
    private JTextField wordToSearchForTextField;
    private JPanel panelMain;
    JLabel searchPerformanceLabel;
    List<String> bibleText;
    List<String> alphabetText;
    SearchPerformer bibleSearchPerformer;
    SearchPerformer alphabetSearchPerformer;

    private JList<String> searchResultsJlist;
    private DefaultListModel listModel = new DefaultListModel();

    SearchPerformer searchPerformer;
    //private final String[] alphabet= new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r","s", "t","u", "v","w","x","y","z"};
    String[] alphabet= new String[] {"a", "b", "c", "d"};


    public StringSearchGUI(){
        String[] algorithms = { "Naive", "Boyer", "Rabin Karp", "KMP" };
        JComboBox algorithmList = new JComboBox(algorithms);

        try {
            String bible = Files.readString(Path.of("bible.txt"), StandardCharsets.US_ASCII);
            String[] bibleWords=bible.split(" ");
            bibleText = Arrays.asList(bibleWords);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        alphabetText = AllPossibleWordsGenerator.getAllPossibleWords(8, alphabet);

        bibleSearchPerformer = new SearchPerformer(bibleText, 1, new BoyerMooreSearch());
        alphabetSearchPerformer = new SearchPerformer(alphabetText, 1, new BoyerMooreSearch());
        searchPerformer = alphabetSearchPerformer;

        algorithmList.setSelectedIndex(0);
        algorithmList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
            }
        });

        JRadioButton bibleButton=new JRadioButton("Bibel");
        JRadioButton alphabetButton=new JRadioButton("Alphabet");
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

        searchResultsJlist = new JList<String>(listModel);
        searchResultsJlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JScrollPane resultsScrollPane = new JScrollPane(searchResultsJlist);
        searchPerformanceLabel = new JLabel();
        panelMain.setLayout(new GridLayout());
        panelMain.add(resultsScrollPane);
        panelMain.add(searchPerformanceLabel);
        panelMain.add(algorithmList);
        panelMain.add(alphabetButton);
        panelMain.add(bibleButton);


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

        wordToSearchForTextField.setText("test");
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
                if(i>200)
                {
                    break;
                }
                listModel.addElement(searchResult);

            }
            searchPerformanceLabel.setText(searchPerformer.getTimeNeededForSearchInMS() + " ms");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        frame.setSize(300,250);
        frame.setContentPane(new StringSearchGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
