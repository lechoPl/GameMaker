package gui.structure;

import gui.EditorFrame;
import gui.controller.GameActions;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class GamePopupMenu extends JPopupMenu {

    private EditorFrame frame;

    public GamePopupMenu(EditorFrame frame) {
        this.frame = frame;

        JMenuItem menuAddLevel = new JMenuItem("Add level");
        menuAddLevel.addActionListener(new GameActions.NewLevelAction(frame));

        this.add(menuAddLevel);
    }
}
