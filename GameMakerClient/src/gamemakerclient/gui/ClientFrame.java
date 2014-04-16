package gamemakerclient.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import logic.Game;
import managers.GameFileManager;
import view.GameView;

/**
 *
 * @author Pawel
 */
public class ClientFrame extends JFrame {

    private ClientFrame clientFrame = this;
    private Game game;
    private GameFileManager gameFileManager = new GameFileManager();

    // Main menu bar
    private JMenuBar clientMenu = new JMenuBar();

    // File category menus
    private JMenu menuFile = new JMenu("File");
    private JMenuItem openGameItem = new JMenuItem("Open game...");
    private JMenuItem exitItem = new JMenuItem("Exit");

    // Help category menus
    private JMenu menuHelp = new JMenu("Help");
    private JMenuItem aboutItem = new JMenuItem("About");

    private GameView gameContent;

    public ClientFrame() {
        super("GameMaker Client");

        createWindow();
        createFileMenu();
        createHelpMenu();
        createGameContent();

        validate();
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
        openGameItem.setAction(new OpenGameAction("Open game..."));

        menuFile.add(exitItem);
        exitItem.setAction(new ExitAction("Exit"));

        clientMenu.add(menuFile);

        validate();
    }

    private void createHelpMenu() {
        menuHelp.add(aboutItem);
        aboutItem.setAction(new AboutAction("About"));

        clientMenu.add(menuHelp);

        validate();
    }

    private void createGameContent() {
        gameContent = new GameView();
        this.add(gameContent, BorderLayout.CENTER);

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

    private class OpenGameAction extends AbstractAction {

        public OpenGameAction(String label) {
            super(label);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            game = gameFileManager.showOpenDialog(clientFrame);
            if (game != null) {
                clientFrame.setTitle(game.getName());
                gameContent.setGame(game);
            }
        }
    }

    class AboutAction extends AbstractAction {

        public AboutAction(String label) {
            super(label);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(clientFrame,
                    "<html><center>Java open source GameMaker 1.0<br><br>Available at: <a href=\"https://github.com/lechoPl/GameMaker\">GitHub</a><br><br>Authors:<br>Paweł Krzywodajć<br>Kamil Kwaśny<br>Michał Lech</center></html>");
        }
    }
}
