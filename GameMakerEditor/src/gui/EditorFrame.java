package gui;

import gui.controller.MenuActions;
import gui.properties.DefaultPropertiesPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import logic.Game;
import logic.Level;
import logic.objects.GameObject;
import managers.GameFileManager;
import view.IGameFrame;

public class EditorFrame extends JFrame implements IGameFrame {
    private static final String frameTitle = "GameMaker Editor";
    
    private Game game = new Game();
    private GameFileManager gameFileManager = new GameFileManager();
    
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
    
    private EditorGameView gamePreview;

    private JPanel gameStructure = new JPanel();
    private JPanel gameProperties = new JPanel();
    private JPanel gameToolbox = new JPanel();
    
    private StructureTree structureTree = new StructureTree(this);

    private CustomTabbedPane customTabbedPane = new CustomTabbedPane(this);
    
    public EditorFrame() {
        super(frameTitle);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setVisible(true);

        this.setLayout(new BorderLayout());

        this.add(horizontalPane, BorderLayout.CENTER);
        horizontalPane.add(verticalLeftPane);
        horizontalPane.add(verticalRightPane);

        // --- MENU ---
        createMenu();

        // --- STRUCTURE ---
        createStructureWindow();

        // --- GAME PREVIEW ---
        createGamePreviewPanel();
        
        // --- PROPERTIES ---
        createPropertiesWindow();

        // --- TOOLBOX ---
        createToolboxWindow();

        refreshStructureTree();
        
        validate();
    }

    private void createMenu() {
        menu.add(menuFile);
        menuFile.add(newGameItem);
        newGameItem.setAction(new MenuActions.NewGameAction(this, "New game..."));

        menuFile.add(openGameItem);
        openGameItem.setAction(new MenuActions.OpenGameAction(this,"Open game..."));

        menuFile.add(saveGameAsItem);
        saveGameAsItem.setAction(new MenuActions.SaveGameAction(this, "Save as..."));
        saveGameAsItem.setActionCommand("saveAs");

        menuFile.add(saveGameItem);
        saveGameItem.setAction(new MenuActions.SaveGameAction(this, "Save"));
        saveGameItem.setActionCommand("save");
        saveGameItem.setEnabled(false);

        menuFile.add(exitItem);
        exitItem.setAction(new MenuActions.ExitAction(this, "Exit"));

        this.add(menu, BorderLayout.PAGE_START);
    }

    private void createStructureWindow() {
        gameStructure.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        gameStructure.add(structureTree);
        structureTree.reload();
        
        verticalLeftPane.add(gameStructure);
        gameStructure.setAlignmentX(RIGHT_ALIGNMENT);
        gameStructure.setSize(500, 200);
    }

    private void createGamePreviewPanel(){
        Level level = Level.getSampleLevel();
        game.getGameStructure().addNewLevel( level );
        game.getGameStructure().setCurrentLevel( level );
        
        gamePreview = new EditorGameView(game);
        gamePreview.setPreferredSize(new Dimension(level.getWidth(), level.getHeight()));

        JScrollPane scrollPane = new JScrollPane(gamePreview);
        
        Dimension windowSize = this.getSize();
        Dimension size = new Dimension(
                (int) (windowSize.width * defaultPreviewWidth),
                (int) (windowSize.height * defaultPreviewHeight));
        scrollPane.setPreferredSize(size);
        
        verticalRightPane.add(scrollPane);
    }

    private void createPropertiesWindow() {
        gameProperties.setLayout(new GridLayout(1, 1));
        this.changePropertiesPanel(new DefaultPropertiesPanel());
        verticalLeftPane.add(gameProperties);
    }

    private void createToolboxWindow() {
        gameToolbox.add(new JLabel("toolbox"));
        gameToolbox.setLayout(new BorderLayout());
        gameToolbox.add(customTabbedPane, java.awt.BorderLayout.CENTER);
        verticalRightPane.add(gameToolbox);
    }
    
    public void changePropertiesPanel(JPanel propertiesPanel) {
        gameProperties.removeAll();
        gameProperties.add(propertiesPanel);
        gameProperties.repaint();
        gameProperties.revalidate();
    }
    
    public void refreshStructureTree() {
        structureTree.reload();
    }
    
    public void refreshGamePreview() {
        gamePreview.repaint();
    }

    public void saveAs() {
        saveGameItem.setEnabled(true);
    }
    
    public void refreshToolbox() {
        customTabbedPane.refreshItems();
    }
    
    public void setGame(Game game) {
        saveGameItem.setEnabled(true);
        
        this.game = game;
        
        setTitle(frameTitle + " - " + game.getGameStructure().getName());
        gamePreview.setGame(game);
    }
    
    @Override
    public Game getGame() {
        return this.game;
    }
    
    public GameFileManager getGameFileManager() {
        return this.gameFileManager;
    }
    
    public GameObject getObjectToAdd() {
        return gamePreview.getObjectToAdd();
    }

    public void setObjectToAdd(GameObject obj) {
        gamePreview.setObjectToAdd(obj);
    }
}
