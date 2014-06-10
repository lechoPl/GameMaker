package gui.structure;

import gui.EditorFrame;
import gui.controller.GameActions;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ObjectPopupMenu extends JPopupMenu {
    private EditorFrame frame;
    
    public ObjectPopupMenu(EditorFrame frame) {
        this.frame = frame;
        
        JMenuItem menuDeleteObject = new JMenuItem("Delete object");
        menuDeleteObject.addActionListener(new GameActions.DeleteObjectAction(frame));
        
        this.add(menuDeleteObject);
    }
}
