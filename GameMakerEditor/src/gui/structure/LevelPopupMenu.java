package gui.structure;

import gui.EditorFrame;
import gui.controller.GameActions;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class LevelPopupMenu extends JPopupMenu {

    private EditorFrame frame;

    public LevelPopupMenu(EditorFrame frame) {
        this.frame = frame;
        
        JMenuItem menuDeleteLevel = new JMenuItem("Delete level");
        menuDeleteLevel.addActionListener(new GameActions.DeleteLevelAction(frame));
        
        this.add(menuDeleteLevel);
    }
}
