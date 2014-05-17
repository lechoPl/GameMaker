/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import logic.objects.GameObject;
import logic.objects.StaticObject;
import managers.GameFileManager;
import resources.GameResources;

/**
 *
 * @author Pawel
 */
public class CustomTabbedPane extends JTabbedPane {
    private EditorFrame frame;
    private CustomTabbedPane thisPane = this;
    
    private GridLayout gridLayout = new GridLayout(1, 1);
        
    private JPanel imagesPanel = new JPanel(gridLayout);
    private JScrollPane imagesScroll = new JScrollPane(imagesPanel);
        
    private JPanel objectsPanel = new JPanel(gridLayout);
    private JScrollPane objectsScroll = new JScrollPane(objectsPanel);
    
    private JPanel backgroundsPanel = new JPanel(gridLayout);
    private JPanel soundsPanel = new JPanel(gridLayout);
    
    private JPopupMenu pMenu = new JPopupMenu("Menu");
    private JDialog addItemDialog = new JDialog();
    
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();
    
    private int gridWidth = 0;
    
    private ObjectMouseListener objectMouseListener = new ObjectMouseListener();

    public CustomTabbedPane(EditorFrame frame) {
        super();
        
        this.frame = frame;
        
        MouseListener popupListener = new PopupListener();
        
        imagesPanel.addMouseListener(popupListener);
        addTab("Images", imagesScroll);
        
        backgroundsPanel.addMouseListener(popupListener);
        addTab("Backgrounds", backgroundsPanel);
        
        objectsPanel.addMouseListener(popupListener);
        addTab("Objects", objectsPanel);
        
        soundsPanel.addMouseListener(popupListener);
        addTab("Sounds", soundsPanel);
        
        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                Component c = thisPane.getSelectedComponent();
                if(c == imagesScroll) {
                    refreshImages();
                } else if(c == backgroundsPanel) {
                    refreshBackgrounds();
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) { }

            @Override
            public void componentShown(ComponentEvent e) { }

            @Override
            public void componentHidden(ComponentEvent e) { }
        });
        
        setMenu();
        refreshItems();
    }
    
    private void setMenu() {
        JMenu sectionsMenu = new JMenu("Add");
        OpenDialogAction dialogAction = new OpenDialogAction();
        
        JMenuItem menuAddImage = new JMenuItem("Image");
        menuAddImage.addActionListener(dialogAction);
        menuAddImage.setActionCommand("image");
        sectionsMenu.add(menuAddImage);
        
        JMenuItem menuAddBackground = new JMenuItem("Background");
        menuAddBackground.addActionListener(dialogAction);
        menuAddBackground.setActionCommand("background");
        sectionsMenu.add(menuAddBackground);
        
        JMenu objectsSectionMenu = new JMenu("Object");
        
        JMenuItem menuAddSimpleObject = new JMenuItem("Simple object");
        menuAddSimpleObject.addActionListener(dialogAction);
        menuAddSimpleObject.setActionCommand("simple_object");
        objectsSectionMenu.add(menuAddSimpleObject);
        
        JMenuItem menuAddAnimatedObject = new JMenuItem("Animated object");
        menuAddAnimatedObject.addActionListener(dialogAction);
        menuAddAnimatedObject.setActionCommand("animated_object");
        objectsSectionMenu.add(menuAddAnimatedObject);
        
        sectionsMenu.add(objectsSectionMenu);
        
        JMenuItem menuAddSound = new JMenuItem("Sound");
        menuAddSound.addActionListener(dialogAction);
        menuAddSound.setActionCommand("sound");
        sectionsMenu.add(menuAddSound);
        
        pMenu.add(sectionsMenu);
    }
    
    public void showAddImageDialog() {
        addItemDialog.dispose();
        addItemDialog = new JDialog();
        addItemDialog.setLayout(null);
        addItemDialog.setSize(300, 200);
        addItemDialog.setTitle("Add new image");

        final JTextField imageIdTextField = new JTextField();
        imageIdTextField.setBounds(150, 30, 120, 25);
        addItemDialog.add(imageIdTextField);
        
        final JTextField imagePathTextField = new JTextField();
        imagePathTextField.setBounds(150, 60, 120, 25);
        imagePathTextField.setEditable(false);
        addItemDialog.add(imagePathTextField);

        final JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        imagePathTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                addItemDialog.requestFocus();
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
                        frame.getGame().getGameResources().addImage(imageIdTextField.getText(), imagePathTextField.getText());
                        refreshImages();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Problem occured while adding file. Try again.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    addItemDialog.dispose();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemDialog.dispose();
            }
        });

        JLabel idLabel = new JLabel("Image ID: ");
        idLabel.setBounds(20, 20, 80, 25);
        addItemDialog.add(idLabel);

        JLabel pathLabel = new JLabel("Image: ");
        pathLabel.setBounds(20, 60, 80, 25);
        addItemDialog.add(pathLabel);
        addItemDialog.add(imagePathTextField);

        addButton.setBounds(20, 100, 120, 25);
        addItemDialog.add(addButton);
        cancelButton.setBounds(150, 100, 120, 25);
        addItemDialog.add(cancelButton);
        
        addItemDialog.validate();

        addItemDialog.setResizable(false);
        addItemDialog.setVisible(true);
        addItemDialog.setLocationRelativeTo(null);
    }
    
    public void showAddStaticObjectDialog() {
        if(frame.getGame().getGameResources().getImages().keySet().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                                "You must add at least one image before you create object.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
        } else {
            addItemDialog.dispose();
            addItemDialog = new JDialog();
            addItemDialog.setLayout(null);
            addItemDialog.setSize(300, 250);
            addItemDialog.setTitle("Add new object");

            JLabel idLabel = new JLabel("Object name: ");
            idLabel.setBounds(20, 20, 80, 25);
            addItemDialog.add(idLabel);

            JLabel pathLabel = new JLabel("Image: ");
            pathLabel.setBounds(20, 60, 80, 25);
            addItemDialog.add(pathLabel);
            
            final JTextField objectNameTextField = new JTextField();
            objectNameTextField.setBounds(150, 20, 120, 25);
            addItemDialog.add(objectNameTextField);

            final String[] keyArr = frame.getGame().getGameResources().getImages().keySet().toArray(new String[0]);
            final JComboBox objectPathComboBox = new JComboBox(keyArr);
            objectPathComboBox.setBounds(150, 60, 120, 25);
            objectPathComboBox.setEditable(false);
            addItemDialog.add(objectPathComboBox);

            final JButton addButton = new JButton("Add");
            JButton cancelButton = new JButton("Cancel");

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (objectNameTextField.getText() != null && keyArr.length > 0) {
                        try {
                                frame.getGame().getGameResources().addStaticObject(objectNameTextField.getText(), objectPathComboBox.getSelectedItem().toString());   
                            refreshObjects();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null,
                                    "Problem occured while adding object. Try again.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        addItemDialog.dispose();
                    }
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addItemDialog.dispose();
                }
            });
            
            addButton.setBounds(20, 140, 120, 25);
            addItemDialog.add(addButton);
            cancelButton.setBounds(150, 140, 120, 25);
            addItemDialog.add(cancelButton);

            addItemDialog.validate();

            addItemDialog.setResizable(false);
            addItemDialog.setVisible(true);
            addItemDialog.setLocationRelativeTo(null);
        }
    }
    
    public void showAddAnimatedObjectDialog() {
        if(frame.getGame().getGameResources().getImages().keySet().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                                "You must add at least one image before you create object.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
        } else {
            addItemDialog.dispose();
            addItemDialog = new JDialog();
            addItemDialog.setLayout(null);
            addItemDialog.setSize(300, 250);
            addItemDialog.setTitle("Add new object");

            JLabel idLabel = new JLabel("Object name: ");
            idLabel.setBounds(20, 20, 80, 25);
            addItemDialog.add(idLabel);

            JLabel pathLabel = new JLabel("Image: ");
            pathLabel.setBounds(20, 60, 80, 25);
            addItemDialog.add(pathLabel);
            
            JLabel framesLabel = new JLabel("Frames: ");
            framesLabel.setBounds(20, 100, 80, 25);
            addItemDialog.add(framesLabel);
            
            JLabel frequencyLabel = new JLabel("Frame duration (ms): ");
            frequencyLabel.setBounds(20, 140, 80, 25);
            addItemDialog.add(frequencyLabel);
            
            final JTextField objectNameTextField = new JTextField();
            objectNameTextField.setBounds(150, 20, 120, 25);
            addItemDialog.add(objectNameTextField);

            final String[] keyArr = frame.getGame().getGameResources().getImages().keySet().toArray(new String[0]);
            final JComboBox objectPathComboBox = new JComboBox(keyArr);
            objectPathComboBox.setBounds(150, 60, 120, 25);
            objectPathComboBox.setEditable(false);
            addItemDialog.add(objectPathComboBox);
            
            final JTextField framesTextField = new JTextField();
            framesTextField.setBounds(150, 100, 120, 25);
            addItemDialog.add(framesTextField);
            
            final JTextField frequencyTextField = new JTextField();
            frequencyTextField.setBounds(150, 140, 120, 25);
            addItemDialog.add(frequencyTextField);

            final JButton addButton = new JButton("Add");
            JButton cancelButton = new JButton("Cancel");

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (objectNameTextField.getText() != null && keyArr.length > 0) {
                        try {
                            int frames = Integer.parseInt(framesTextField.getText());
                            int frequency = Integer.parseInt(frequencyTextField.getText());
                            frame.getGame().getGameResources().addAnimatedObject(objectNameTextField.getText(), objectPathComboBox.getSelectedItem().toString(), frequency, frames);   
                            refreshObjects();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null,
                                    "Problem occured while adding object. Try again.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        addItemDialog.dispose();
                    }
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addItemDialog.dispose();
                }
            });
            
            addButton.setBounds(20, 180, 120, 25);
            addItemDialog.add(addButton);
            cancelButton.setBounds(150, 180, 120, 25);
            addItemDialog.add(cancelButton);

            addItemDialog.validate();

            addItemDialog.setResizable(false);
            addItemDialog.setVisible(true);
            addItemDialog.setLocationRelativeTo(null);
        }
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
        refreshImages();
        refreshBackgrounds();
        refreshObjects();
        refreshSounds();
    }
    
    private void refreshImages() {
        imagesPanel.removeAll();
        int width = Math.max(getWidth() / ToolbarItem.ITEM_SIZE, 1);
        imagesPanel.setLayout(new GridLayout(0, width));
        for(Entry<String, BufferedImage> item : frame.getGame().getGameResources().getImages().entrySet()) {
            imagesPanel.add(new ToolbarItem(item.getKey(), frame.getGame().getGameResources().getImage(item.getKey())));
        }
        imagesPanel.revalidate();
        imagesScroll.repaint();
    }
    
    private void refreshBackgrounds() {
        backgroundsPanel.removeAll();
        int width = Math.max(getWidth() / ToolbarItem.ITEM_SIZE, 1);
        backgroundsPanel.setLayout(new GridLayout(0, width));
        for(Entry<String, BufferedImage> item : frame.getGame().getGameResources().getBackgrounds().entrySet()) {
            backgroundsPanel.add(new ToolbarItem(item.getKey(), frame.getGame().getGameResources().getImage(item.getKey())));
        }
        backgroundsPanel.revalidate();
    }
    
    private void refreshObjects() {
        objectsPanel.removeAll();
        int width = Math.max(getWidth() / ToolbarItem.ITEM_SIZE, 1);
        objectsPanel.setLayout(new GridLayout(0, width));
        for(Entry<String, GameObject> item : frame.getGame().getGameResources().getObjects().entrySet()) {
            ToolbarObject to = new ToolbarObject(item.getValue(), item.getKey(), frame.getGame().getGameResources().getImage(item.getValue().getImageId()));
            to.addMouseListener(objectMouseListener);
            objectsPanel.add(to);
        }
        
        objectsPanel.revalidate();
        objectsScroll.repaint();
    }
    
    private void refreshSounds() {
        
    }
    
    class PopupListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                pMenu.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
    }
    
    private class OpenDialogAction extends AbstractAction {
        public OpenDialogAction() {
            super();
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()) {
                case("image"):
                    showAddImageDialog();
                    break;
                case("background"):
                    break;
                case("simple_object"):
                    showAddStaticObjectDialog();
                    break;
                case("animated_object"):
                    showAddAnimatedObjectDialog();
                    break;
                case("sound"):
                    break;
                default:
                    break;
            }
        }
    }
    
    private GameObject selectObject(ToolbarObject toolbarObject) {
        GameObject obj = null;
        for(Component component : objectsPanel.getComponents()) {
            ToolbarObject to = (ToolbarObject) component;
            to.setSelected(to.equals(toolbarObject));
            if(to.equals(toolbarObject)) obj = to.getObject();
        }
        return obj;
    }
    
    private class ObjectMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            frame.setObjectToAdd(((ToolbarObject) e.getSource()).getObject());
            selectObject((ToolbarObject) e.getSource());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
        
    }
}
