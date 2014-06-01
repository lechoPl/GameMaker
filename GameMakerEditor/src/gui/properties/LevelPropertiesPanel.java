package gui.properties;

import gui.EditorFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import logic.Game;
import logic.Level;

public class LevelPropertiesPanel extends AbstractPropertiesPanel {
    
    private class LevelPropertiesActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EditorFrame frame = getFrame();
            Game game = frame.getGame();
            
            String newName = nameField.getText();
            level.setName(newName);
            
            int newWidth = Integer.parseInt(widthField.getText());
            level.setWidth(newWidth);
            
            int newHeight = Integer.parseInt(heightField.getText());
            level.setHeight(newHeight);
            
            frame.refreshStructureTree();
            frame.refreshGamePreview();
            
            JOptionPane.showMessageDialog(frame, "Level properties have been successfully saved!");
        }
        
    }
    
    private Level level;
    
    private JLabel nameLabel;
    private JTextField nameField;
    
    private JLabel widthLabel;
    private JTextField widthField;
    
    private JLabel heightLabel;
    private JTextField heightField;
    
    @Override
    public ActionListener getActionListener() {
        return new LevelPropertiesActionListener();
    }

    public LevelPropertiesPanel(EditorFrame frame, Level level) {
        super();

        this.level = level;
        this.setPanelName("Level properties");
        this.setFrame(frame);

        Game game = this.getFrame().getGame();

        LinkedList<Property> properties = new LinkedList<Property>();

        nameLabel = new JLabel("Name");
        nameField = new JTextField();
        nameField.setText(level.getName());
        Property nameProperty = new Property(nameLabel, nameField);
        properties.add(nameProperty);
        
        widthLabel = new JLabel("Width");
        widthField = new JTextField();
        widthField.setText(Integer.toString(level.getWidth()));
        Property widthProperty = new Property(widthLabel, widthField);
        properties.add(widthProperty);
        
        heightLabel = new JLabel("Height");
        heightField = new JTextField();
        heightField.setText(Integer.toString(level.getHeight()));
        Property heightProperty = new Property(heightLabel, heightField);
        properties.add(heightProperty);
        
        this.setProperties(properties);
        this.reload();
    }
}
