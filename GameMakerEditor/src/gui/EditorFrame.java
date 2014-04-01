package gui;

import gamemakereditor.GameMakerEditor;
import gamemakerlibrary.Game;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorFrame extends JFrame {
    private Game game = new Game();
    private EditorFrame frame = this;
    private String filePath;
    
    private JMenuBar menu = new JMenuBar();
    
    private JMenu menuFile = new JMenu("File");
    private JMenuItem newGameItem = new JMenuItem("New game...");
    private JMenuItem saveGameAsItem = new JMenuItem("Save as...");
    private JMenuItem saveGameItem = new JMenuItem("Save");
    private JMenuItem exitItem = new JMenuItem("Exit");
    
    private CustomJSplitPane horizontalPane = 
            new CustomJSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private CustomJSplitPane verticalLeftPane = 
            new CustomJSplitPane(JSplitPane.VERTICAL_SPLIT);
    private CustomJSplitPane verticalRightPane = 
            new CustomJSplitPane(JSplitPane.VERTICAL_SPLIT);
    
    private JPanel gameContent = new JPanel();
    private JPanel gameStructure = new JPanel();
    private JPanel gameProperties = new JPanel();
    private JPanel gameToolbox = new JPanel();
    
    public EditorFrame() {
        super("GameMaker Editor");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
        
        this.setLayout(new BorderLayout());
        
        this.add(horizontalPane, BorderLayout.CENTER);
        horizontalPane.add(verticalLeftPane);
        horizontalPane.add(verticalRightPane);
        
        // --- MENU ---
        createMenu();       
        
        // --- STRUCTURE ---
        createStructureWindow();
        
        // --- GAME CONTENT ---
        createGameContentWindow();
        
        // --- PROPERTIES ---
        createPropertiesWindow();
        
        // --- TOOLBOX ---
        createToolboxWindow();
        
        validate();
    }
    
    private void createMenu(){
        menu.add(menuFile);
        menuFile.add(newGameItem);
        
        menuFile.add(saveGameAsItem);
        saveGameAsItem.setAction(new SaveAction("Save as..."));
        saveGameAsItem.setActionCommand("saveAs");
        
        menuFile.add(saveGameItem);
        saveGameItem.setAction(new SaveAction("Save"));
        saveGameItem.setActionCommand("save");
        saveGameItem.setEnabled(false);
        
        menuFile.add(exitItem);
        exitItem.setAction(new ExitAction("Exit"));
        
        this.add(menu, BorderLayout.PAGE_START);
    }
    
    private void createStructureWindow(){
        gameStructure.add(new JLabel("project tree\n"));
        gameStructure.add(new JTree());
        verticalLeftPane.add(gameStructure);
        gameStructure.setSize(500, 200);
    }
    
    private void createGameContentWindow(){
        gameContent.add(new JLabel("game preview"));
        verticalRightPane.add(gameContent);
    }
    
    private void createPropertiesWindow(){
        gameProperties.add(new JLabel("selected object properties"));
        verticalLeftPane.add(gameProperties);
    }
    
    private void createToolboxWindow(){
        gameToolbox.add(new JLabel("toolbox"));
        verticalRightPane.add(gameToolbox);
    }
    
    private class ExitAction extends AbstractAction {
        public ExitAction(String label) {
            super(label);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    private class SaveAction extends AbstractAction {
        public SaveAction(String label) {
            super(label);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "saveAs":
                    showSaveDialog(saveGameItem);
                    break;
                case "save":
                    saveToFile();
                    break;
            }
        }
    }
    
    private void showSaveDialog( Component parent ) {
        final JFileChooser finder = new JFileChooser();
            finder.setFileFilter(new FileNameExtensionFilter("GameMaker file", Game.FILE_EXTENSION));
            int returnVal = finder.showSaveDialog(null);
            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                File file = finder.getSelectedFile();
                filePath = file.toString();
                filePath = (filePath.endsWith(Game.FILE_EXTENSION_DOT)) ? filePath : filePath + Game.FILE_EXTENSION_DOT;  
                saveToFile();
            }
    }
    
    private void saveToFile() {
        JOptionPane.showMessageDialog(null, "Game saved to " + filePath , "Save file", JOptionPane.INFORMATION_MESSAGE);
        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fout);   
            oos.writeObject(game);
            oos.close();
            if(filePath != null) saveGameItem.setEnabled(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
