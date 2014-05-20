package gui.properties;

import gui.EditorFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import logic.Game;

public class GamePropertiesPanel extends AbstractPropertiesPanel {

    private class GamePropertiesActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EditorFrame frame = getFrame();
            Game game = frame.getGame();

            String newName = nameField.getText();
            game.getGameStructure().setName(newName);

            frame.refreshStructureTree();

            JOptionPane.showMessageDialog(frame, "Game properties have been successfully saved!");
        }

    }

    private JLabel nameLabel;
    private JTextField nameField;

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

        nameLabel = new JLabel("Name");
        nameField = new JTextField();
        nameField.setText(game.getGameStructure().getName());
        Property nameProperty = new Property(nameLabel, nameField);
        properties.add(nameProperty);

        this.setProperties(properties);
        this.reload();
    }
}
