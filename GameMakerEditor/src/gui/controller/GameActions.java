package gui.controller;

import gui.EditorFrame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import logic.Game;
import logic.Level;

public class GameActions {

    public static int selectedLevel = -1;
    
    public static class NewLevelAction extends AbstractAction {

        private EditorFrame frame;

        public NewLevelAction(EditorFrame frame) {
            super();
            this.frame = frame;
        }

        public NewLevelAction(EditorFrame frame, String label) {
            super(label);
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Game game = frame.getGame();
            Level newLevel = new Level("New level");
            game.getGameStructure().getLevels().add(newLevel);
            frame.refreshStructureTree();
            JOptionPane.showMessageDialog(frame, "New level has been successfully added!");
        }
    }

    public static class DeleteLevelAction extends AbstractAction {

        private EditorFrame frame;

        public DeleteLevelAction(EditorFrame frame) {
            super();
            this.frame = frame;
        }

        public DeleteLevelAction(EditorFrame frame, String label) {
            super(label);
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedLevel >= 0) {
                Game game = frame.getGame();
                
                Level levelToDelete = game.getGameStructure().getLevels().get(selectedLevel);
                Level currentLevel = game.getGameStructure().getCurrentLevel();
                
                if(levelToDelete == currentLevel)
                    game.getGameStructure().setCurrentLevel(null);
                
                game.getGameStructure().getLevels().remove(selectedLevel);
                frame.refreshStructureTree();
                frame.refreshGamePreview();
                JOptionPane.showMessageDialog(frame, "Selected level has been successfully deleted!");
            }
        }
    }
}
