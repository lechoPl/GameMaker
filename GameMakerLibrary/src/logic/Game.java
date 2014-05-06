package logic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import static logic.GameStructure.GAME_PATH;
import resources.GameResources;
import static resources.GameResources.IMAGE_EXTENSION;
import view.IViewable;

public class Game {

    // constants
    public static final String FILE_EXTENSION = "gmf";
    public static final String FILE_EXTENSION_DOT = "." + FILE_EXTENSION;
    public static final String TEMP_FILE_NAME = "game_maker_temp_file";

    private GameStructure gameStructure;
    private GameResources gameResources;

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
        out.close();
    }

    private void saveGameStructure(ZipOutputStream out) throws IOException {
        out.putNextEntry(new ZipEntry(GAME_PATH));
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(gameStructure);
        }
    }

    private GameStructure loadGameStructure(ZipInputStream in) throws IOException, ClassNotFoundException {
        BufferedOutputStream dest;
        int BUFFER = 2048;
        ZipEntry entry = in.getNextEntry();
        int count;
        byte data[] = new byte[BUFFER];

        FileOutputStream fos = new FileOutputStream(Game.TEMP_FILE_NAME);
        dest = new BufferedOutputStream(fos, BUFFER);
        while ((count = in.read(data, 0, BUFFER)) != -1) {
            dest.write(data, 0, count);
        }
        dest.flush();
        dest.close();

        FileInputStream fin = new FileInputStream(new File(Game.TEMP_FILE_NAME));
        try (ObjectInputStream ois = new ObjectInputStream(fin)) {
            return (GameStructure) ois.readObject();
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
                System.out.println(entry.getName());
                FileInputStream fin = new FileInputStream(new File(Game.TEMP_FILE_NAME));
                try (ObjectInputStream ois = new ObjectInputStream(fin)) {
                    gameStructure = (GameStructure) ois.readObject();
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
}
