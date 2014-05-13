package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ToolbarItem extends JPanel {

    public static final int ITEM_SIZE = 120;
    private String itemId;

    public ToolbarItem(String id, BufferedImage image) {
        this.itemId = id;

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

    public String getItemId() {
        return itemId;
    }
}
