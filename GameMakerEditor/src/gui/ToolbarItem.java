/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import resources.GameResources;

/**
 *
 * @author Pawel
 */
public class ToolbarItem extends JPanel {
    public ToolbarItem(String id) {
        ImageIcon icon = new ImageIcon();
        icon.setImage(GameResources.getImage(id).getScaledInstance(100, 100, Image.SCALE_FAST));
        
        JLabel label = new JLabel(id, icon, JLabel.CENTER);
        add(label);
    }
}
