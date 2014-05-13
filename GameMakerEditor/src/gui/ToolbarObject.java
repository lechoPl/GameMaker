/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import static gui.ToolbarItem.ITEM_SIZE;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import logic.objects.GameObject;
import logic.objects.StaticObject;

/**
 *
 * @author Pawel
 */
public class ToolbarObject extends JPanel {
    private StaticObject gameObject;
    private boolean selected = false;
    
    public ToolbarObject(StaticObject object, BufferedImage image) {
        this.gameObject = object;
        
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
    
    public StaticObject getObject() {
        return gameObject;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
        if(this.selected) {
            setBackground(Color.ORANGE);
        } else{
            setBackground(null);
        }
    }
}
