/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managers;

import logic.Game;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Pawel
 */
public class GameFileManager {
    private Game game;
    private String gameFilePath;
    
    public GameFileManager() {
    }
    
    public GameFileManager(Game game) {
        this.game = game;
    }
    public void showSaveDialog( Component parent ) {
        final JFileChooser finder = new JFileChooser();
        finder.setFileFilter(new FileNameExtensionFilter("GameMaker file", Game.FILE_EXTENSION));
        int returnVal = finder.showSaveDialog(null);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            File file = finder.getSelectedFile();
            gameFilePath = file.toString();
            gameFilePath = (gameFilePath.endsWith(Game.FILE_EXTENSION_DOT)) ? gameFilePath : gameFilePath + Game.FILE_EXTENSION_DOT;  
            saveToFile();
        }
    }
    
    public void saveToFile() {
        if(game != null) {
            JOptionPane.showMessageDialog(null, "Game saved to " + gameFilePath , "Save file", JOptionPane.INFORMATION_MESSAGE);
            try {
                FileOutputStream fout = new FileOutputStream(gameFilePath);
                try (ObjectOutputStream oos = new ObjectOutputStream(fout)) {
                    oos.writeObject(game);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Wystąpił problem podczas zapisywania pliku!", "Save file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Game showOpenDialog( Component parent ) {
        final JFileChooser finder = new JFileChooser();
        finder.setFileFilter(new FileNameExtensionFilter("GameMaker file", Game.FILE_EXTENSION));
        int returnVal = finder.showOpenDialog(null);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            File file = finder.getSelectedFile();
            gameFilePath = file.toString();
            openFile();
        }
        return game;
    }
    
    private void openFile() {
        JOptionPane.showMessageDialog(null, gameFilePath + " opened successfully.", "Open game", JOptionPane.INFORMATION_MESSAGE);
        try {
            FileInputStream fin = new FileInputStream(gameFilePath);
            try (ObjectInputStream ois = new ObjectInputStream(fin)) {
                game = (Game) ois.readObject();
            }
            System.out.println("Otworzono grę: " + game.getName());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String getFilePath() {
        return gameFilePath;
    }
}
