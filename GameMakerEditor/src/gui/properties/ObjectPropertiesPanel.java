package gui.properties;

import gui.EditorFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import logic.Game;
import logic.Pos;
import logic.objects.DynamicObject;
import logic.objects.EndPoint;
import logic.objects.GameObject;

public class ObjectPropertiesPanel extends AbstractPropertiesPanel {

    private class ObjectPropertiesTableModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            EditorFrame frame = getFrame();
            Game game = frame.getGame();

            String newName = (String) getTable().getValueAt(0, 1);
            object.setObjectName(newName);

            int newPosx = Integer.parseInt((String) getTable().getValueAt(1, 1));
            object.setPos(new Pos(newPosx, object.getPos().getY()));

            int newPosy = Integer.parseInt((String) getTable().getValueAt(2, 1));
            object.setPos(new Pos(object.getPos().getX(), newPosy));

            int newWidth = Integer.parseInt((String) getTable().getValueAt(3, 1));
            object.setWidth(newWidth);

            int newHeight = Integer.parseInt((String) getTable().getValueAt(4, 1));
            object.setHeight(newHeight);

            int newZindex = Integer.parseInt((String) getTable().getValueAt(5, 1));
            object.setZindex(newZindex);

            if (object instanceof DynamicObject) {
                DynamicObject dynamic = (DynamicObject) object;

                double newSpeedx = Double.parseDouble((String) getTable().getValueAt(6, 1));
                dynamic.setMoveSpeed(newSpeedx);
                
                double newSpeedy = Double.parseDouble((String) getTable().getValueAt(7, 1));
                dynamic.setJumpSpeed(newSpeedy);
                
                double newGrav = Double.parseDouble((String) getTable().getValueAt(8, 1));
                dynamic.setGravitation(newGrav);
            }
            
            if(object instanceof EndPoint) {
                EndPoint endpoint = (EndPoint) object;
                
                int newLevelid = Integer.parseInt((String)getTable().getValueAt(6, 1));
                endpoint.setNextLevelId(newLevelid);
            }

            frame.refreshStructureTree();
            frame.refreshGamePreview();
        }
    }

    private GameObject object;

    @Override
    public TableModelListener getTableModelListener() {
        return new ObjectPropertiesTableModelListener();
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

        String posyName = "Position Y";
        String posyValue = Integer.toString(object.getPos().getY());
        Property posyProperty = new Property(posyName, posyValue);
        properties.add(posyProperty);

        String widthName = "Width";
        String widthValue = Integer.toString(object.getWidth());
        Property widthProperty = new Property(widthName, widthValue);
        properties.add(widthProperty);

        String heightName = "Height";
        String heightValue = Integer.toString(object.getHeight());
        Property heightProperty = new Property(heightName, heightValue);
        properties.add(heightProperty);

        String zindexName = "Z-index";
        String zindexValue = Integer.toString(object.getZindex());
        Property zindexProperty = new Property(zindexName, zindexValue);
        properties.add(zindexProperty);

        if (object instanceof DynamicObject) {
            DynamicObject dynamic = (DynamicObject) object;

            String speedxName = "Speed X";
            String speedxValue = Double.toString(dynamic.getMoveSpeed());
            Property speedxProperty = new Property(speedxName, speedxValue);
            properties.add(speedxProperty);
            
            String speedyName = "Speed Y";
            String speedyValue = Double.toString(dynamic.getJumpSpeed());
            Property speedyProperty = new Property(speedyName, speedyValue);
            properties.add(speedyProperty);
            
            String gravName = "Gravitation";
            String gravValue = Double.toString(dynamic.getGravitation());
            Property gravProperty = new Property(gravName, gravValue);
            properties.add(gravProperty);
        }
        
        if(object instanceof EndPoint) {
            EndPoint endpoint = (EndPoint) object;
            
            String levelidName = "Next level ID";
            String levelidValue = "";
            if(endpoint.getNextLevelId() != null)
                levelidValue = Integer.toString(endpoint.getNextLevelId());
            Property levelidProperty = new Property(levelidName, levelidValue);
            properties.add(levelidProperty);
        }

        this.setProperties(properties);
        this.reload();
    }
}
