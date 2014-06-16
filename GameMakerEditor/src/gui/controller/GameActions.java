package gui.controller;

import gui.EditorFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import logic.Game;
import logic.Level;
import logic.objects.GameObject;

public class GameActions {

    public static int selectedLevel = -1;
    
    public static int selectedObject = -1;
    public static int selectedObjectLevel = -1;
    
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
            game.getGameStructure().addNewLevel(newLevel);
            frame.refreshStructureTree();
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

    public static class DeleteObjectAction extends AbstractAction {

        private EditorFrame frame;
        
        public DeleteObjectAction(EditorFrame frame) {
            super();
            this.frame = frame;
        }
        
        public DeleteObjectAction(EditorFrame frame, String label) {
            super(label);
            this.frame = frame;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedObject >= 0 && selectedObjectLevel >= 0) {
                Game game = frame.getGame();
                
                Level level = game.getGameStructure().getLevels().get(selectedObjectLevel);
                
                GameObject objectToDelete = level.getObject(selectedObject);
                level.deleteObject(objectToDelete);
                
                frame.refreshStructureTree();
                frame.refreshGamePreview();
                JOptionPane.showMessageDialog(frame, "Selected object has been successfully deleted!");
            }
        }
    }
}
