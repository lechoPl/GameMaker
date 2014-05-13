package gui.properties;

import gui.EditorFrame;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public abstract class AbstractPropertiesPanel extends JPanel {

    private class PropertiesPanelListener implements ComponentListener {

        @Override
        public void componentHidden(ComponentEvent e) {
        }

        @Override
        public void componentResized(ComponentEvent e) {
            reload();
        }

        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }
    }

    private static final int MARGIN = 20;
    private static final int FIELD_HEIGHT = 30;

    protected EditorFrame frame;
    
    protected String name;
    protected LinkedList<Property> properties;

    public AbstractPropertiesPanel() {
        super();

        this.setLayout(null);
        this.addComponentListener(new PropertiesPanelListener());
    }

    public void setProperties(LinkedList<Property> properties) {
        this.properties = properties;
    }

    public void setPanelName(String name) {
        this.name = name;
    }

    public String getPanelName() {
        return this.name;
    }
    
    public void setFrame(EditorFrame frame) {
        this.frame = frame;
    }
    
    public EditorFrame getFrame() {
        return this.frame;
    }
    
    public abstract ActionListener getActionListener();

    public void reload() {
        if(this instanceof DefaultPropertiesPanel)
            return;
        
        this.removeAll();
        
        int w = (this.getWidth() - MARGIN * 3) / 5;
        
        JLabel nameLabel = new JLabel(this.getPanelName());
        nameLabel.setBounds(MARGIN, MARGIN - 10, this.getWidth() - 2 * MARGIN, FIELD_HEIGHT);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(nameLabel);
        
        int i = 1;

        for (Property p : properties) {
            int y = i * FIELD_HEIGHT + MARGIN;

            JLabel label = p.getLabel();
            label.setBounds(MARGIN, y, 2 * w, FIELD_HEIGHT);

            JComponent field = p.getField();
            field.setBounds(MARGIN * 2 + 2 * w, y, 3 * w, FIELD_HEIGHT);

            this.add(label);
            this.add(field);

            i++;
        }
        
        int y = i * FIELD_HEIGHT + MARGIN;
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(this.getWidth() / 4, y + 10, this.getWidth() / 2, FIELD_HEIGHT);
        saveButton.addActionListener(this.getActionListener());
        this.add(saveButton);

        this.repaint();
        this.revalidate();
    }
}
