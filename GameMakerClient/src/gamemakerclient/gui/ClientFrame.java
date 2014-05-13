package gamemakerclient.gui;

import gamemakerclient.gui.controler.MenuActions;
import java.awt.BorderLayout;
import javax.swing.*;
import logic.Game;
import managers.GameFileManager;
import view.IGameFrame;

public class ClientFrame extends JFrame implements IGameFrame {

    // Main menu bar
    private JMenuBar clientMenu = new JMenuBar();
    
    private GameFileManager gameFileManager = new GameFileManager();

    // File category menus
    private JMenu menuFile = new JMenu("File");
    private JMenuItem openGameItem = new JMenuItem("Open game...");
    private JMenuItem exitItem = new JMenuItem("Exit");

    // View category menus
    private JMenu menuView = new JMenu("View");
    private JRadioButtonMenuItem showFpsButtonItem
            = new JRadioButtonMenuItem("Show FPS");

    // Help category menus
    private JMenu menuHelp = new JMenu("Help");
    private JMenuItem aboutItem = new JMenuItem("About");
    
    private GameClientView gameContent;
    private Thread gameMainLoop;
    
    public ClientFrame() {
        super("GameMaker Client");
        
        createWindow();
        
        createFileMenu();
        createViewMenu();
        createHelpMenu();
        
        createGameContent();
        
        validate();
        
        gameMainLoop = new Thread(gameContent);
        gameMainLoop.start();
    }
    
    private void createWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
        
        this.setLayout(new BorderLayout());
        
        this.add(clientMenu, BorderLayout.PAGE_START);
    }
    
    private void createFileMenu() {
        menuFile.add(openGameItem);
        openGameItem.setAction(new MenuActions.OpenGameAction("Open game...", this));
        
        menuFile.add(exitItem);
        exitItem.setAction(new MenuActions.ExitAction("Exit", this));
        
        clientMenu.add(menuFile);
    }
    
    private void createHelpMenu() {
        menuHelp.add(aboutItem);
        aboutItem.setAction(new MenuActions.AboutAction("About", this));
        
        clientMenu.add(menuHelp);
    }
    
    private void createViewMenu() {
        menuView.add(showFpsButtonItem);
        showFpsButtonItem
                .setAction(new MenuActions.ShowFPSAction("ShowFPS", this));
        
        clientMenu.add(menuView);
    }
    
    private void createGameContent() {
        gameContent = new GameClientView();
        this.add(gameContent, BorderLayout.CENTER);
    }
    
    public synchronized void openGame() {
        Game game = gameFileManager.showOpenDialog(this);
        
        if (game != null) {
            this.setTitle(game.getGameStructure().getName());
            
            gameContent.setGame(game);
        }
    }
    
    public void showFPS() {
        gameContent.showFPS(showFpsButtonItem.isSelected());
    }
    
    public synchronized void exitGameClient() {
        gameMainLoop.interrupt();
        
        System.exit(0);
    }
    
    @Override
    public Game getGame() {
        return gameContent.getGame();
    }
}
