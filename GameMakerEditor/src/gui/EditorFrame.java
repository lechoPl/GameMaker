package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.*;
import logic.Game;
import logic.Level;
import managers.GameFileManager;
import resources.GameResources;
import view.GameView;

public class EditorFrame extends JFrame {
    private Game game;
    // val 0-1
    private double defaultPreviewWidth = 0.8;
    private double defaultPreviewHeight = 0.7;
    
    private EditorFrame editorFrame = this;
    
    private JMenuBar menu = new JMenuBar();
    
    private JMenu menuFile = new JMenu("File");
    private JMenuItem newGameItem = new JMenuItem("New game...");
    private JMenuItem openGameItem = new JMenuItem("Open game...");
    private JMenuItem saveGameAsItem = new JMenuItem("Save as...");
    private JMenuItem saveGameItem = new JMenuItem("Save");
    private JMenuItem exitItem = new JMenuItem("Exit");
    
    private CustomJSplitPane horizontalPane = 
            new CustomJSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private CustomJSplitPane verticalLeftPane = 
            new CustomJSplitPane(JSplitPane.VERTICAL_SPLIT);
    private CustomJSplitPane verticalRightPane = 
            new CustomJSplitPane(JSplitPane.VERTICAL_SPLIT);
    
    private JPanel gamePreview;
    private JPanel gameStructure = new JPanel();
    private JPanel gameProperties = new JPanel();
    private JPanel gameToolbox = new JPanel();
    private CustomTabbedPane customTabbedPane = new CustomTabbedPane();
    
    public EditorFrame() {
        super("GameMaker Editor");
        
        GameResources.resetResources();
        game = GameResources.getGame();
        
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
        createGamePreviewPanel();
        
        // --- PROPERTIES ---
        createPropertiesWindow();
        
        // --- TOOLBOX ---
        createToolboxWindow();
        
        validate();
    }
    
    private void createMenu(){
        menu.add(menuFile);
        menuFile.add(newGameItem);
        newGameItem.setAction(new NewGameAction("New game..."));
        
        menuFile.add(openGameItem);
        openGameItem.setAction(new OpenGameAction("Open game..."));
        
        menuFile.add(saveGameAsItem);
        saveGameAsItem.setAction(new SaveGameAction("Save as..."));
        saveGameAsItem.setActionCommand("saveAs");
        
        menuFile.add(saveGameItem);
        saveGameItem.setAction(new SaveGameAction("Save"));
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
    
    private void createGamePreviewPanel(){
        Level level = Level.getSampleLevel();
        game.addNewLevel( level );
        game.setCurrentLevel( level );
        
        gamePreview = new EditorGameView(game);
        gamePreview.setPreferredSize(new Dimension(level.getWidth(), level.getHeight()));

        JScrollPane scrolPane = new JScrollPane(gamePreview);
        
        Dimension windowSize = this.getSize();
        Dimension size = new Dimension(
                (int) (windowSize.width * defaultPreviewWidth),
                (int) (windowSize.height * defaultPreviewHeight));
        scrolPane.setPreferredSize(size);
        
        verticalRightPane.add(scrolPane);
    }
    
    private void createPropertiesWindow(){
        gameProperties.add(new JLabel("selected object properties"));
        verticalLeftPane.add(gameProperties);
    }
    
    private void createToolboxWindow(){
        gameToolbox.add(new JLabel("toolbox"));
        CustomTabbedPane tabbedPane = new CustomTabbedPane();
        gameToolbox.setLayout(new BorderLayout());
        gameToolbox.add(customTabbedPane, java.awt.BorderLayout.CENTER);
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
    
    private class SaveGameAction extends AbstractAction {
        public SaveGameAction(String label) {
            super(label);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "saveAs":
                    GameFileManager.showSaveDialog(editorFrame);
                    if(GameFileManager.getFilePath() != null)
                        saveGameItem.setEnabled(true);
                    break;
                case "save":
                    GameFileManager.saveToFile();
                    break;
            }
        }
    }
    
    private class OpenGameAction extends AbstractAction {
        public OpenGameAction(String label) {
            super(label);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            game = GameFileManager.showOpenDialog(editorFrame);
            if(GameFileManager.getFilePath() != null) {
                saveGameItem.setEnabled(true);
                editorFrame.setTitle(game.getName());
                customTabbedPane.refreshItems();
            }
        }
    }
    
    private class NewGameAction extends AbstractAction {
        public NewGameAction(String label) {
            super(label);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            GameResources.resetResources();
            customTabbedPane.refreshItems();
        }
    }
}
