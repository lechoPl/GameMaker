package resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import logic.objects.GameObject;
import logic.objects.StaticObject;

public class GameResources {

    public static final String IMAGE_EXTENSION = "png";

    public static final String IMAGES_PATH = "data/images/";
    public static final String BACKGROUNDS_PATH = "data/backgrounds/";

    private HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
    private HashMap<String, BufferedImage> backgrounds = new HashMap<String, BufferedImage>();

    private HashMap<String, Sound> sounds = new HashMap<String, Sound>();

    private HashMap<String, GameObject> objects = new HashMap<String, GameObject>();

    private enum RES_TYPE {

        img, bg, snd
    };

    public GameResources() {
    }

    private HashMap<String, BufferedImage> getImagesCollection(RES_TYPE img) {
        switch (img) {
            case bg:
                return backgrounds;
            default:
                return images;
        }
    }

    private String createPath(RES_TYPE img, String fileName) {
        switch (img) {
            case bg:
                return BACKGROUNDS_PATH + fileName + "." + IMAGE_EXTENSION;
            default:
                return IMAGES_PATH + fileName + "." + IMAGE_EXTENSION;
        }
    }

    private void addImg(HashMap<String, BufferedImage> imgs, String id, String filePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        if (image != null) {
            imgs.put(id, image);
        }
    }

    private String getImageId(String fileName) {
        System.out.println(fileName + ", " + IMAGES_PATH.length() + ", " + (fileName.length() - IMAGE_EXTENSION.length()));
        return fileName.subSequence(IMAGES_PATH.length(), fileName.length() - IMAGE_EXTENSION.length() - 1).toString();
    }

    public void loadImage(String entryPath, String filePath) throws IOException {
        addImage(getImageId(entryPath), filePath);
    }

    public void addImage(String fileName, String filePath) throws IOException {
        addImg(images, fileName, filePath);
    }

    public void addBackground(String fileName, String filePath) throws IOException {
        addImg(backgrounds, fileName, filePath);
    }

    public BufferedImage getImage(String id) {
        return images.get(id);
    }

    public BufferedImage getBackground(String id) {
        return backgrounds.get(id);
    }

    private void putEntry(ZipOutputStream out, BufferedImage img, String fileName) throws IOException {
        out.putNextEntry(new ZipEntry(fileName));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(img, IMAGE_EXTENSION, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            out.write(imageInByte);
        }
    }

    private void saveImages(RES_TYPE img, ZipOutputStream out) throws IOException {
        HashMap<String, BufferedImage> imgs = getImagesCollection(img);
        for (String key : imgs.keySet()) {
            putEntry(out, images.get(key), createPath(img, key));
        }
    }

    private void saveBackgrounds(String filePath) {

    }

    public void saveResources(ZipOutputStream out) throws IOException {
        saveImages(RES_TYPE.img, out);
    }

    public HashMap<String, BufferedImage> getImages() {
        return images;
    }

    public HashMap<String, BufferedImage> getBackgrounds() {
        return backgrounds;
    }

    public HashMap<String, GameObject> getObjects() {
        return objects;
    }

    public void addObject(String name, String imageId) {
        objects.put(name, new StaticObject(name, imageId, getImage(imageId).getWidth(), getImage(imageId).getHeight()));
    }
}
