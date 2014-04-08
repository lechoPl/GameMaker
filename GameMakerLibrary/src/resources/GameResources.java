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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Pawel
 */
public class GameResources {
    public static final String IMAGE_EXTENSION = "png";
    
    private static final String TEMP_FILE = "game_maker_temp_file";
    private static final String IMAGES_PATH = "data/images/";
    private static final String BACKGROUNDS_PATH = "data/backgrounds/";
    
    private HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
    private HashMap<String, BufferedImage> backgrounds = new HashMap<String, BufferedImage>();
    
    private HashMap<String, Sound> sounds = new HashMap<String, Sound>();
    
    private enum RES_TYPE { img, bg, snd };
    
    public GameResources() {
    }
    
    private HashMap<String, BufferedImage> getImagesCollection(RES_TYPE img) {
        switch(img) {
            case bg:
                return backgrounds;
            default:
                return images;
        }
    }
    
    private String createPath(RES_TYPE img, String fileName) {
        switch(img) {
            case bg:
                return BACKGROUNDS_PATH + fileName + "." + IMAGE_EXTENSION;
            default:
                return IMAGES_PATH + fileName + "." + IMAGE_EXTENSION;
        }
    }
    
    private String getImageId(String fileName) {
        return fileName.subSequence(IMAGES_PATH.length(), fileName.length() - IMAGE_EXTENSION.length() - 1).toString();
    }
    
    private void addImg(HashMap<String, BufferedImage> imgs, String id, String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            if(image != null) imgs.put(id, image);
        } catch (IOException e) {
        }
    }
    
    public void addImage(String id, String filePath) {
        addImg(images, id, filePath);
    }
    
    public void addBackground(String id, String filePath) {
        addImg(backgrounds, id, filePath);
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
            ImageIO.write( img, IMAGE_EXTENSION, baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            out.write(imageInByte);
        }
    }
    
    private void saveImages(RES_TYPE img, ZipOutputStream out, String filePath) throws IOException {
        HashMap<String, BufferedImage> imgs = getImagesCollection(img);
        for(String key : imgs.keySet()) {
            putEntry(out, images.get(key), createPath(img, key));
        }
    }
    
    private void saveBackgrounds(String filePath) {
        
    }
    
    public void saveResources(String filePath) {
        ZipOutputStream out;
        try {
            out = new ZipOutputStream(new FileOutputStream(filePath));
            saveImages(RES_TYPE.img, out, filePath);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameResources.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameResources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getImages(RES_TYPE img, ZipInputStream in) throws IOException {
        BufferedOutputStream dest;
        int BUFFER = 2048;
        ZipEntry entry;
        HashMap<String, BufferedImage> imgs = getImagesCollection(img);
        while((entry = in.getNextEntry()) != null) {
            int count;
            byte data[] = new byte[BUFFER];
            
            FileOutputStream fos = new FileOutputStream(TEMP_FILE);
            dest = new BufferedOutputStream(fos, BUFFER);
            while ((count = in.read(data, 0, BUFFER)) != -1) {
               dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
            addImage(getImageId(entry.getName()), TEMP_FILE);   
        }
    }
    
    public void openResources(String filePath) {
        ZipInputStream in;
        images.clear();
        backgrounds.clear();
        sounds.clear();
        try {
            in = new ZipInputStream(new FileInputStream(filePath));
            getImages(RES_TYPE.img, in);
            new File(TEMP_FILE).delete();
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameResources.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameResources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
