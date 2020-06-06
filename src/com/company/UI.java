package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class UI extends JFrame {
    private final File file = new File();
    private final JMenuBar menuBar = new JMenuBar();
    private JTextArea textArea;
    private JTextField searchField = new JTextField();
    private PositionFinder positionFinder = new PositionFinder();
    private JCheckBox useRegExCheckbox = new JCheckBox("Use regex");
    private boolean isRegexp = false;
    private int caretPosition = 0;

    public UI() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        initMenuBar();
        initFileMenu();
        initSearchMenu();
        initWorkSpace();
        setVisible(true);
    }

    private void initMenuBar() {
        setJMenuBar(menuBar);
    }

    private void initFileMenu() {
        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuFile.setName("MenuFile");
        menuBar.add(menuFile);

        JMenuItem menuOpen = new JMenuItem("Load");
        menuOpen.setMnemonic(KeyEvent.VK_L);
        menuOpen.setName("MenuLoad");
        menuFile.add(menuOpen);

        JMenuItem menuSave = new JMenuItem("Save");
        menuSave.setMnemonic(KeyEvent.VK_S);
        menuSave.setName("MenuSave");
        menuFile.add(menuSave);

        menuFile.addSeparator();

        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setMnemonic(KeyEvent.VK_E);
        menuExit.setName("MenuExit");
        menuFile.add(menuExit);

        menuOpen.addActionListener(event -> loadFile());
        menuSave.addActionListener(event -> saveFile());
        menuExit.addActionListener(event -> System.exit(0));
    }

    private void initSearchMenu() {
        JMenu menuSearch = new JMenu("Search");
        menuSearch.setName("MenuSearch");
        menuBar.add(menuSearch);

        JMenuItem menuStartSearch = new JMenuItem("Start search");
        menuStartSearch.setName("MenuStartSearch");
        menuSearch.add(menuStartSearch);

        JMenuItem menuPreviousMatch = new JMenuItem("Previous match");
        menuPreviousMatch.setName("MenuPreviousMatch");
        menuSearch.add(menuPreviousMatch);

        JMenuItem menuNextMatch = new JMenuItem("Next match");
        menuNextMatch.setName("MenuNextMatch");
        menuSearch.add(menuNextMatch);

        JMenuItem menuUseRegExp = new JMenuItem("Use regular expression");
        menuUseRegExp.setName("MenuUseRegExp");
        menuSearch.add(menuUseRegExp);

        menuStartSearch.addActionListener(event -> newFind());
        menuPreviousMatch.addActionListener(event -> previousFind());
        menuNextMatch.addActionListener(event -> nextFind());
        menuUseRegExp.addActionListener(event -> useRegExCheckbox.doClick());
    }

    private void initWorkSpace() {
        JPanel panel = new JPanel();
        panel.setName("Panel");
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.GREEN);
        add(panel, BorderLayout.NORTH);

        searchField.setName("SearchField");
//        filenameField.setMinimumSize(new Dimension(100, 25));
        searchField.setPreferredSize(new Dimension(400, 25));
//        filenameField.setMaximumSize(new Dimension(300, 25));

        JButton saveButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("resources/file_save_24.png"));
            saveButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println("Cannot read save icon" + ex);
        }
        saveButton.setName("SaveButton");

        JButton openButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("resources/file_open_24.png"));
            openButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println("Cannot read save icon" + ex);
        }
        openButton.setName("OpenButton");

        JButton startSearchButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("resources/search_24.png"));
            startSearchButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println("Cannot read save icon" + ex);
        }
        startSearchButton.setName("StartSearchButton");

        JButton previousMatchButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("resources/arrow_left_24.png"));
            previousMatchButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println("Cannot read save icon" + ex);
        }
        previousMatchButton.setName("PreviousMatchButton");

        JButton nextMatchButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("resources/arrow_right_24.png"));
            nextMatchButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println("Cannot read save icon" + ex);
        }
        nextMatchButton.setName("NextMatchButton");

        useRegExCheckbox.setName("UseRegexCheckbox");

        panel.add(openButton);
        panel.add(saveButton);
        panel.add(searchField);
        panel.add(startSearchButton);
        panel.add(previousMatchButton);
        panel.add(nextMatchButton);
        panel.add(useRegExCheckbox);

        textArea = new JTextArea();
        textArea.setName("TextArea");

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        add(scrollPane, BorderLayout.CENTER);

        openButton.addActionListener(event -> loadFile());
        saveButton.addActionListener(event -> saveFile());
        startSearchButton.addActionListener(event -> newFind());
        useRegExCheckbox.addActionListener(event -> setCheckBox());
        previousMatchButton.addActionListener(event -> previousFind());
        nextMatchButton.addActionListener(event -> nextFind());
    }

    private void loadFile() {
        textArea.setText(file.loadFile());
    }

    private void saveFile() {
        file.saveFile(textArea.getText());
    }

    private void newFind() {
        caretPosition = 0;
        find();
    }

    private void previousFind() {
        caretPosition--;
        find();
    }

    private void nextFind() {
        caretPosition++;
        find();
    }

    private void find() {
        Indexies indexies;
        int startIndex = 0;
        int endIndex = 0;
        String text = textArea.getText();
        String pattern = searchField.getText();

        positionFinder.setText(text);
        positionFinder.setIsRegexp(isRegexp);
        positionFinder.setPatternString(pattern);
        positionFinder.find();

        indexies = positionFinder.getIndex(caretPosition);
        startIndex = indexies.getStartIndex();
        endIndex = indexies.getEndIndex();
        textArea.select(startIndex, endIndex);
        textArea.grabFocus();

        //textArea.setCaretPosition(index + foundText.length());
        //        textArea.select(startIndex, index + foundText.length());
//        textArea.grabFocus();
    }

    private void setCheckBox() {
        isRegexp = !isRegexp;
    }
}
