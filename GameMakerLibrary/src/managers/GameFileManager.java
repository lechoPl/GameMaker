/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import logic.Game;
import resources.GameResources;
import view.GameFrame;

/**
 *
 * @author Pawel
 */
public class GameFileManager {

    private String gameFilePath;

    public GameFileManager() {
    }

    public void showSaveDialog(GameFrame parent) {
        final JFileChooser finder = new JFileChooser();
        finder.setFileFilter(new FileNameExtensionFilter("GameMaker file", Game.FILE_EXTENSION));
        int returnVal = finder.showSaveDialog(null);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            File file = finder.getSelectedFile();
            gameFilePath = file.toString();
            gameFilePath = (gameFilePath.endsWith(Game.FILE_EXTENSION_DOT)) ? gameFilePath : gameFilePath + Game.FILE_EXTENSION_DOT;
            saveToFile(parent.getGame());
        }
    }

    public void saveToFile(Game game) {
        try {
            game.saveGame(gameFilePath);
            JOptionPane.showMessageDialog(null, "Game saved to " + gameFilePath, "Save file", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Wystąpił problem podczas zapisywania pliku!", "Save file", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Game showOpenDialog(Component parent) {
        final JFileChooser finder = new JFileChooser();
        finder.setFileFilter(new FileNameExtensionFilter("GameMaker file", Game.FILE_EXTENSION));
        int returnVal = finder.showOpenDialog(null);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            File file = finder.getSelectedFile();
            gameFilePath = file.getPath();
            return openFile();
        }
        return null;
    }

    private Game openFile() {
        try {
            Game game = new Game(gameFilePath);
            JOptionPane.showMessageDialog(null, gameFilePath + " opened successfully.", "Open game", JOptionPane.INFORMATION_MESSAGE);
            return game;
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Wystąpił problem podczas otwierania pliku!", "Save file", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String getFilePath() {
        return gameFilePath;
    }
}
