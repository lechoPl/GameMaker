/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import static javax.swing.text.StyleConstants.Alignment;
import logic.Game;
import resources.GameResources;

/**
 *
 * @author Pawel
 */
public class GameFileManager {

    private static String gameFilePath;

    public GameFileManager() {

    }

    public static void showSaveDialog(Component parent) {
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

    public static void saveToFile() {
        JOptionPane.showMessageDialog(null, "Game saved to " + gameFilePath, "Save file", JOptionPane.INFORMATION_MESSAGE);
        try {
            GameResources.saveResources(gameFilePath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Wystąpił problem podczas zapisywania pliku!", "Save file", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Game showOpenDialog(Component parent) {
        final JFileChooser finder = new JFileChooser();
        finder.setFileFilter(new FileNameExtensionFilter("GameMaker file", Game.FILE_EXTENSION));
        int returnVal = finder.showOpenDialog(null);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            File file = finder.getSelectedFile();
            gameFilePath = file.getPath();
            openFile();
        }
        return GameResources.getGame();
    }

    private static void openFile() {
        JOptionPane.showMessageDialog(null, gameFilePath + " opened successfully.", "Open game", JOptionPane.INFORMATION_MESSAGE);
        try {
            GameResources.openResources(gameFilePath);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getFilePath() {
        return gameFilePath;
    }
}
