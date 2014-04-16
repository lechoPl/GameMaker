/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import managers.GameFileManager;
import resources.GameResources;

/**
 *
 * @author Pawel
 */
public class CustomTabbedPane extends JTabbedPane {
    GridLayout layout = new GridLayout();
        
    private JPanel objectsPanel = new JPanel(layout);
    private JPanel imagesPanel = new JPanel(layout);
    private JPanel backgroundsPanel = new JPanel(layout);
    private JPanel soundsPanel = new JPanel(layout);

    public CustomTabbedPane() {
        super();
        
        objectsPanel.setLayout(layout);
        addTab("Objects", objectsPanel);
        addTab("Images", imagesPanel);
        addTab("Backgrounds", backgroundsPanel);
        addTab("Sounds", soundsPanel);
        
        setImageDialogListener();
    }
    
    private void setImageDialogListener() {
        imagesPanel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                showAddImageDialog(null);
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        });
    }
    
    public void showAddImageDialog(Component parent) {
        String id, path;
        final JDialog dialog = new JDialog();
        dialog.setLayout(new GridLayout(3, 2));
        dialog.setTitle("Add new image");

        final JTextField imageIdTextField = new JTextField();
        final JTextField imagePathTextField = new JTextField();
        imagePathTextField.setEditable(false);

        final JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        imagePathTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                dialog.requestFocus();
                imagePathTextField.setText(showFindImageDialog());
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imageIdTextField.getText() != null && imagePathTextField.getText() != null) {
                    try {
                        GameResources.addImage(imageIdTextField.getText(), imagePathTextField.getText());
                        refreshItems();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Problem occured while adding file. Try again.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    dialog.dispose();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imageIdTextField.getText() != null && imagePathTextField.getText() != null) {
                    dialog.dispose();
                }
            }
        });

        JLabel idLabel = new JLabel("Image ID: ");
        idLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        dialog.add(idLabel);
        dialog.add(imageIdTextField);

        JLabel pathLabel = new JLabel("Image: ");
        pathLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        dialog.add(pathLabel);
        dialog.add(imagePathTextField);

        dialog.add(addButton);
        dialog.add(cancelButton);

        dialog.setLocationRelativeTo(null);
        dialog.setSize(400, 300);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private String showFindImageDialog() {
        final JFileChooser finder = new JFileChooser();
        finder.setFileFilter(new FileNameExtensionFilter("Image file", "png"));
        int returnVal = finder.showOpenDialog(null);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            File file = finder.getSelectedFile();
            return file.getPath();
        }
        return null;
    }
    
    public void refreshItems() {
        imagesPanel.removeAll();
        for(Entry<String, BufferedImage> set : GameResources.getImages().entrySet()) {
            imagesPanel.add(new ToolbarItem(set.getKey()));
        }
    }
}
