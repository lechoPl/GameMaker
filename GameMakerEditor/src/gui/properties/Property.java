package gui.properties;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class Property {

    private JLabel label;
    private JComponent field;

    public Property(JLabel label, JComponent component) {
        this.label = label;
        this.field = component;
              
    }
    
    public JLabel getLabel() {
        return this.label;
    }
    
    public JComponent getField() {
        return this.field;
    }
}
