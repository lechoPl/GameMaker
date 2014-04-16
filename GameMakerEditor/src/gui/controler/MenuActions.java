package gui.controler;

import gui.EditorFrame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import logic.Game;
import managers.GameFileManager;
import resources.GameResources;

public class MenuActions {
    public static class NewGameAction extends AbstractAction {
        private EditorFrame frame;
        public NewGameAction(EditorFrame frame) {
            super();
            this.frame = frame;
        }
        
        public NewGameAction(EditorFrame frame, String label) {
            super(label);
            this.frame = frame;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            GameResources.resetResources();
            frame.refreshToolbox();
        }
    }

    public static class ExitAction extends AbstractAction {
        private EditorFrame frame;
        
        public ExitAction(EditorFrame frame)
        {
            this.frame = frame;
        }
        
        public ExitAction(EditorFrame frame, String label) {
            super(label);
            
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static class SaveGameAction extends AbstractAction {
        private EditorFrame frame;
        
        public SaveGameAction(EditorFrame frame)
        {
            super();
            this.frame = frame;
        }
        
        public SaveGameAction(EditorFrame frame, String label) {
            super(label);
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "saveAs":
                    GameFileManager.showSaveDialog(frame);
                    if (GameFileManager.getFilePath() != null) {
                        frame.saveAs();
                    }
                    break;
                case "save":
                    GameFileManager.saveToFile();
                    break;
            }
        }
    }

    public static class OpenGameAction extends AbstractAction {

        private EditorFrame frame;
        
        public OpenGameAction(EditorFrame frame)
        {
            super();
            this.frame = frame;
        }
        
        public OpenGameAction(EditorFrame frame, String label) {
            super(label);
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Game game = GameFileManager.showOpenDialog(frame);
            if (GameFileManager.getFilePath() != null) {

                frame.loadGame(game);
            }
        }
    }
}
