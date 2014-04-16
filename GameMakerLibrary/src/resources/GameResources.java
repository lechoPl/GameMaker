/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import logic.Game;

/**
 *
 * @author Pawel
 */
public class GameResources {

    public static final String IMAGE_EXTENSION = "png";

    private static final String TEMP_FILE_NAME = "game_maker_temp_file";
    private static final String IMAGES_PATH = "data/images/";
    private static final String BACKGROUNDS_PATH = "data/backgrounds/";
    private static final String GAME_PATH = "game.g";

    private static Game game;

    private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
    private static HashMap<String, BufferedImage> backgrounds = new HashMap<String, BufferedImage>();

    private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();

    private enum RES_TYPE {

        img, bg, snd
    };

    protected GameResources() {
    }

    private static HashMap<String, BufferedImage> getImagesCollection(RES_TYPE img) {
        switch (img) {
            case bg:
                return backgrounds;
            default:
                return images;
        }
    }

    private static String createPath(RES_TYPE img, String fileName) {
        switch (img) {
            case bg:
                return BACKGROUNDS_PATH + fileName + "." + IMAGE_EXTENSION;
            default:
                return IMAGES_PATH + fileName + "." + IMAGE_EXTENSION;
        }
    }

    private static String getImageId(String fileName) {
        return fileName.subSequence(IMAGES_PATH.length(), fileName.length() - IMAGE_EXTENSION.length() - 1).toString();
    }

    private static void addImg(HashMap<String, BufferedImage> imgs, String id, String filePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        if (image != null) {
            imgs.put(id, image);
        }
    }

    public static void addImage(String id, String filePath) throws IOException {
        addImg(images, id, filePath);
    }

    public static void addBackground(String id, String filePath) throws IOException {
        addImg(backgrounds, id, filePath);
    }

    public static BufferedImage getImage(String id) {
        return images.get(id);
    }

    public static BufferedImage getBackground(String id) {
        return backgrounds.get(id);
    }

    private static void putEntry(ZipOutputStream out, BufferedImage img, String fileName) throws IOException {
        out.putNextEntry(new ZipEntry(fileName));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(img, IMAGE_EXTENSION, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            out.write(imageInByte);
        }
    }

    private static void saveImages(RES_TYPE img, ZipOutputStream out, String filePath) throws IOException {
        HashMap<String, BufferedImage> imgs = getImagesCollection(img);
        for (String key : imgs.keySet()) {
            putEntry(out, images.get(key), createPath(img, key));
        }
    }

    private static void saveBackgrounds(String filePath) {

    }

    private static void saveGame(ZipOutputStream out) throws IOException {
        out.putNextEntry(new ZipEntry(GAME_PATH));
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(game);
        }
    }

    public static void saveResources(String filePath) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filePath));
        saveImages(RES_TYPE.img, out, filePath);
        saveGame(out);
        out.close();
    }

    private static void loadGame(ZipInputStream in) throws IOException, ClassNotFoundException {
        BufferedOutputStream dest;
        int BUFFER = 2048;
        ZipEntry entry = in.getNextEntry();
        int count;
        byte data[] = new byte[BUFFER];

        FileOutputStream fos = new FileOutputStream(TEMP_FILE_NAME);
        dest = new BufferedOutputStream(fos, BUFFER);
        while ((count = in.read(data, 0, BUFFER)) != -1) {
            dest.write(data, 0, count);
        }
        dest.flush();
        dest.close();

        FileInputStream fin = new FileInputStream(new File(TEMP_FILE_NAME));
        try (ObjectInputStream ois = new ObjectInputStream(fin)) {
            game = (Game) ois.readObject();
        }
    }

    private static void loadImages(RES_TYPE img, ZipInputStream in) throws IOException {
        BufferedOutputStream dest;
        int BUFFER = 2048;
        ZipEntry entry;
        HashMap<String, BufferedImage> imgs = getImagesCollection(img);
        while ((entry = in.getNextEntry()) != null) {
            int count;
            byte data[] = new byte[BUFFER];

            FileOutputStream fos = new FileOutputStream(TEMP_FILE_NAME);
            dest = new BufferedOutputStream(fos, BUFFER);
            while ((count = in.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
            addImage(getImageId(entry.getName()), TEMP_FILE_NAME);
        }
    }

    public static void openResources(String filePath) throws IOException, ClassNotFoundException {
        images.clear();
        backgrounds.clear();
        sounds.clear();
        ZipInputStream in = new ZipInputStream(new FileInputStream(filePath));
        loadImages(RES_TYPE.img, in);
        loadGame(in);
        new File(TEMP_FILE_NAME).delete();
        in.close();
    }

    public static Game getGame() {
        return game;
    }
    
    public static HashMap<String, BufferedImage> getImages() {
        return images;
    }

    public static void resetResources() {
        images.clear();
        backgrounds.clear();
        sounds.clear();
        game = new Game();
    }
}
