package gui.properties;

import gui.EditorFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import logic.Game;

public class GamePropertiesPanel extends AbstractPropertiesPanel {

    private class GamePropertiesActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EditorFrame frame = getFrame();
            Game game = frame.getGame();

            String newName = (String)getTable().getValueAt(0, 1);
            game.getGameStructure().setName(newName);

            frame.refreshStructureTree();

            JOptionPane.showMessageDialog(frame, "Game properties have been successfully saved!");
        }

    }

    @Override
    public ActionListener getActionListener() {
        return new GamePropertiesActionListener();
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
