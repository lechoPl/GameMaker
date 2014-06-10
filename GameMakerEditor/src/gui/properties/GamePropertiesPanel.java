package gui.properties;

import gui.EditorFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import logic.Game;

public class GamePropertiesPanel extends AbstractPropertiesPanel {
    
    private class GamePropertiesTableModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            EditorFrame frame = getFrame();
            Game game = frame.getGame();

            String newName = (String)getTable().getValueAt(0, 1);
            game.getGameStructure().setName(newName);

            frame.refreshStructureTree();
        }
        
    }
    
    @Override
    public TableModelListener getTableModelListener() {
        return new GamePropertiesTableModelListener();
    }

    public GamePropertiesPanel(EditorFrame frame) {
        super();

        this.setPanelName("Game properties");
        this.setFrame(frame);

        Game game = this.getFrame().getGame();

        LinkedList<Property> properties = new LinkedList<Property>();

        String nameName = "Name";
        String nameValue = game.getGameStructure().getName();
        Property nameProperty = new Property(nameName, nameValue);
        properties.add(nameProperty);

        this.setProperties(properties);
        this.reload();
    }
}
