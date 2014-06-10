package gui.properties;

import gui.EditorFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import logic.Game;
import logic.Pos;
import logic.objects.GameObject;

public class ObjectPropertiesPanel extends AbstractPropertiesPanel {

    private class ObjectPropertiesActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EditorFrame frame = getFrame();
            Game game = frame.getGame();
            
            String newName = (String)getTable().getValueAt(0, 1);
            object.setObjectName(newName);
            
            int newPosx = Integer.parseInt((String)getTable().getValueAt(1, 1));
            object.setPos(new Pos(newPosx, object.getPos().getY()));
            
            frame.refreshStructureTree();
            frame.refreshGamePreview();
            
            JOptionPane.showMessageDialog(frame, "Object properties have been successfully updated!");
        }
    }
    
    private GameObject object;
    
    @Override
    public ActionListener getActionListener() {
        return new ObjectPropertiesActionListener();
    }
    
    public ObjectPropertiesPanel(EditorFrame frame, GameObject object) {
        super();
        
        this.object = object;
        this.setPanelName("Object properties");
        this.setFrame(frame);
        
        LinkedList<Property> properties = new LinkedList<>();
        
        String nameName = "Name";
        String nameValue = object.getObjectName();
        Property nameProperty = new Property(nameName, nameValue);
        properties.add(nameProperty);
        
        String posxName = "Position X";
        String posxValue = Integer.toString(object.getPos().getX());
        Property posxProperty = new Property(posxName, posxValue);
        properties.add(posxProperty);
        
        this.setProperties(properties);
        this.reload();
    }
}
