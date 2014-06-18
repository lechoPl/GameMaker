package gamemakerclient;

import gamemakerclient.gui.ClientFrame;

public class GameMakerClient {

    public static void main(String[] args) {
        ClientFrame mainFrame = new ClientFrame();
        if(args.length > 0) {
            mainFrame.openGame(args[0]);
        }
    }
    
}
