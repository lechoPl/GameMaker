package gamemakerclient.gui.controler;

import gamemakerclient.gui.ClientFrame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class MenuActions {

    public static class ExitAction extends AbstractAction {

        private ClientFrame frame;

        public ExitAction(String label, ClientFrame frame) {
            super(label);

            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            frame.exitGameClient();
        }
    }

    public static class OpenGameAction extends AbstractAction {

        private ClientFrame frame;

        public OpenGameAction(String label, ClientFrame frame) {
            super(label);

            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            frame.openGame();
        }
    }

    public static class AboutAction extends AbstractAction {

        private ClientFrame frame;

        public AboutAction(String label, ClientFrame frame) {
            super(label);

            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame,
                    "<html><center>Java open source GameMaker 1.0<br><br>Available at: <a href=\"https://github.com/lechoPl/GameMaker\">GitHub</a><br><br>Authors:<br>Paweł Krzywodajć<br>Kamil Kwaśny<br>Michał Lech</center></html>");
        }
    }

    public static class ShowFPSAction extends AbstractAction {

        private ClientFrame frame;

        public ShowFPSAction(String label, ClientFrame frame) {
            super(label);

            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.showFPS();
        }
    }
}
