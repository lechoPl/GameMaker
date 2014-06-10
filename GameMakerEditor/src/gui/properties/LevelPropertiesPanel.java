package gui.properties;

import gui.EditorFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import logic.Game;
import logic.Level;

public class LevelPropertiesPanel extends AbstractPropertiesPanel {

    private class LevelPropertiesTableModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            EditorFrame frame = getFrame();

            String newName = (String) getTable().getValueAt(0, 1);
            level.setName(newName);

            int newWidth = Integer.parseInt((String) getTable().getValueAt(1, 1));
            level.setWidth(newWidth);

            int newHeight = Integer.parseInt((String) getTable().getValueAt(2, 1));
            level.setHeight(newHeight);
            
            frame.refreshStructureTree();
            frame.refreshGamePreview();
        }
        
    }

    private Level level;
    
    @Override
    public TableModelListener getTableModelListener() {
        return new LevelPropertiesTableModelListener();
    }

    public LevelPropertiesPanel(EditorFrame frame, Level level) {
        super();

        this.level = level;
        this.setPanelName("Level properties");
        this.setFrame(frame);

        LinkedList<Property> properties = new LinkedList<Property>();

        String nameName = "Name";
        String nameValue = level.getName();
        Property nameProperty = new Property(nameName, nameValue);
        properties.add(nameProperty);

        String widthName = "Width";
        String widthValue = Integer.toString(level.getWidth());
        Property widthProperty = new Property(widthName, widthValue);
        properties.add(widthProperty);

        String heightName = "Height";
        String heightValue = Integer.toString(level.getHeight());
        Property heightProperty = new Property(heightName, heightValue);
        properties.add(heightProperty);

        this.setProperties(properties);
        this.reload();
    }
}
