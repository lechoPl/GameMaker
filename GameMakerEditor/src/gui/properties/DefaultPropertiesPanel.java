package gui.properties;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import static javax.swing.SwingConstants.CENTER;

public class DefaultPropertiesPanel extends AbstractPropertiesPanel {

    public DefaultPropertiesPanel() {
        this.setLayout(new GridLayout(1, 1));

        JLabel textLabel = new JLabel("Properties window");
        textLabel.setHorizontalAlignment(CENTER);
        textLabel.setVerticalAlignment(CENTER);

        this.add(textLabel);
    }

    @Override
    public ActionListener getActionListener() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
