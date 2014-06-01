package logic;

import controller.GameController;
import controller.PlayerController;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import static logic.GameStructure.GAME_PATH;
import logic.objects.DynamicObject;
import logic.objects.GameObject;
import logic.objects.StaticObject;
import resources.GameResources;

public class Game {

    // constants
    public static final String FILE_EXTENSION = "gmf";
    public static final String FILE_EXTENSION_DOT = "." + FILE_EXTENSION;
    public static final String TEMP_FILE_NAME = "game_maker_temp_file";

    private GameStructure gameStructure;
    private GameResources gameResources;
    private GameController gameController; // to implementet - controll game menu, etc.

    public Game() {
        init();
    }

    public Game(String filePath) throws IOException, ClassNotFoundException {
        init();
        loadGame(filePath);
    }

    private void init() {
        this.gameStructure = new GameStructure();
        this.gameResources = new GameResources();
    }

    public void saveGame(String filePath) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filePath));
        gameResources.saveResources(out);
        saveGameStructure(out);
        //saveObjects(out);
        out.close();
    }

    private void saveGameStructure(ZipOutputStream out) throws IOException {
        out.putNextEntry(new ZipEntry(GAME_PATH));
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(gameStructure);
            oos.writeObject(gameResources.getObjects());
            oos.writeObject(gameResources.getCreatures());
            out.closeEntry();
        }
    }

    private void loadObjects(ZipInputStream in) throws IOException, ClassNotFoundException {
        BufferedOutputStream dest;
        int BUFFER = 2048;
        ZipEntry entry;
        while ((entry = in.getNextEntry()) != null) {
            int count;
            byte data[] = new byte[BUFFER];

            FileOutputStream fos = new FileOutputStream(Game.TEMP_FILE_NAME);
            dest = new BufferedOutputStream(fos, BUFFER);
            while ((count = in.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();

            System.out.println("Name = " + entry.getName().toString());
            if (entry.getName().endsWith(GAME_PATH)) {
                FileInputStream fin = new FileInputStream(new File(Game.TEMP_FILE_NAME));
                try (ObjectInputStream ois = new ObjectInputStream(fin)) {
                    gameStructure = (GameStructure) ois.readObject();
                    gameResources.setObjects((HashMap<String, StaticObject>) ois.readObject());
                    gameResources.setCreatures((HashMap<String, DynamicObject>) ois.readObject());
                }
            } else if (entry.getName().startsWith(GameResources.IMAGES_PATH)) {
                gameResources.loadImage(entry.getName(), Game.TEMP_FILE_NAME);
            }
        }
    }

    public void loadGame(String filePath) throws IOException, ClassNotFoundException {
        ZipInputStream in = new ZipInputStream(new FileInputStream(filePath));
        loadObjects(in);
        new File(TEMP_FILE_NAME).delete();
        in.close();
    }

    public GameResources getGameResources() {
        return this.gameResources;
    }

    public GameStructure getGameStructure() {
        return this.gameStructure;
    }

    public PlayerController getPlayerController() {
        return gameStructure.getPlayerController();
    }

    public void setPlayerController(PlayerController controller) {
        gameStructure.setPlayerController(controller);
    }

    public void pullControllers() {
        if (gameStructure.getPlayerController() != null) {
            gameStructure.getPlayerController().pull();
        }
    }
}
