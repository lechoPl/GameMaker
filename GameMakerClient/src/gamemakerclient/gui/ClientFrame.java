/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gamemakerclient.gui;

import gamemakerlibrary.Game;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Pawel
 */
public class ClientFrame extends JFrame {
    private ClientFrame clientFrame = this;
    private Game game;
    
    // Main menu bar
    private JMenuBar clientMenu = new JMenuBar();
    
    // File category menus
    private JMenu menuFile = new JMenu("File");
    private JMenuItem openGameItem = new JMenuItem("Open game...");
    private JMenuItem exitItem = new JMenuItem("Exit");
    
    // Help category menus
    private JMenu menuHelp = new JMenu("Help");
    private JMenuItem aboutItem = new JMenuItem("About"); 
    
    private JPanel gameContent = new JPanel();

    public ClientFrame() {
        super("GameMaker Client");
        
        createWindow();
        createFileMenu();
        createHelpMenu();
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
    
    private void showOpenDialog( Component parent ) {
        final JFileChooser finder = new JFileChooser();
            finder.setFileFilter(new FileNameExtensionFilter("GameMaker file", Game.FILE_EXTENSION));
            int returnVal = finder.showSaveDialog(null);
            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                File file = finder.getSelectedFile();
                String filePath = file.toString();
                filePath = (filePath.endsWith(Game.FILE_EXTENSION_DOT)) ? filePath : filePath + Game.FILE_EXTENSION_DOT;  
                openFile(filePath);
            }
    }
    
    private void openFile(String filePath) {
        JOptionPane.showMessageDialog(null, filePath + " opened successfully.", "Open game", JOptionPane.INFORMATION_MESSAGE);
        try {
            FileInputStream fin = new FileInputStream(filePath);
            try (ObjectInputStream ois = new ObjectInputStream(fin)) {
                game = (Game) ois.readObject();
            }
            System.out.println("Otworzono grę: " + game.getName());
            clientFrame.setTitle(game.getName());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
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
            showOpenDialog(clientFrame);
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
