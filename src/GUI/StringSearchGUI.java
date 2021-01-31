package GUI;

import StringSearch.AllPossibleWordsGenerator;
import StringSearch.SearchPerformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StringSearchGUI {
    private static JFrame frame = new JFrame("StringSearchGUI");
    private JTextField wordToSearchForTextField;
    private JPanel panelMain;
    private JButton searchButton;

    private ArrayList<String> allPossibleWords;
    private JList<String> searchResultsJlist;
    private DefaultListModel listModel = new DefaultListModel();
    private final String[] alphabet= new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r","s", "t","u", "v","w","x","y","z"};
    //String[] alphabet= new String[] {"a", "b", "c", "d"};


    public StringSearchGUI(){
        this.allPossibleWords = AllPossibleWordsGenerator.getAllPossibleWords(4, alphabet);
        searchResultsJlist = new JList<String>(listModel);
        searchResultsJlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JScrollPane resultsScrollPane = new JScrollPane(searchResultsJlist);
        panelMain.setLayout(new GridLayout());
        panelMain.add(resultsScrollPane);
        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
                SearchPerformer searchPerformer = new SearchPerformer(wordToSearchForTextField.getText(), allPossibleWords, 4);
                searchPerformer.performSearch();
                try {
                    List<String> searchResults = searchPerformer.getWordsContainingSubstring();
                    for(String searchResult : searchResults)
                    {
                        listModel.addElement(searchResult);
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args) {

        frame.setSize(300,250);
        frame.setContentPane(new StringSearchGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
