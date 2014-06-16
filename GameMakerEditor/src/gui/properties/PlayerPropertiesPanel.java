package gui.properties;

import gui.EditorFrame;
import logic.objects.GameObject;

public class PlayerPropertiesPanel extends ObjectPropertiesPanel {
    public PlayerPropertiesPanel(EditorFrame frame, GameObject player) {
        super(frame, player, true);
    }
}
