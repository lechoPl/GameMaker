/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import com.sun.java.swing.plaf.motif.MotifBorders;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders;
import logic.objects.GameObject;
import logic.objects.StaticObject;
import resources.GameResources;

/**
 *
 * @author Pawel
 */
public class ToolbarItem extends JPanel {
    public static final int ITEM_SIZE = 120;
    
    public ToolbarItem(String id, BufferedImage image) {
        //this.setLayout(new GridLayout(1, 2));
        ImageIcon icon = new ImageIcon();
        icon.setImage(image.getScaledInstance(100, 100, Image.SCALE_FAST));
        
        JLabel label = new JLabel(icon);
        label.setText(id);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        add(label);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), getBorder()));
        setSize(ITEM_SIZE, ITEM_SIZE);
    }
    
    public ToolbarItem(StaticObject object, BufferedImage image) {
        //this.setLayout(new GridLayout(1, 2));
        ImageIcon icon = new ImageIcon();
        icon.setImage(image.getScaledInstance(100, 100, Image.SCALE_FAST));
        
        JLabel label = new JLabel(icon);
        label.setText(object.getName());
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        add(label);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), getBorder()));
        setSize(ITEM_SIZE, ITEM_SIZE);
    }
}
