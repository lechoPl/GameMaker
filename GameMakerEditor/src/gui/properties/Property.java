package gui.properties;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class Property {

    private String name;
    private Object value;

    public Property(String name, Object value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Object getValue() {
        return this.value;
    }
}
