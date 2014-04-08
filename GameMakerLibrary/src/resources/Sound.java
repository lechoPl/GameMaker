/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Pawel
 */
public class Sound {
    private String id;
    //private XXX sound;
    
    public Sound(String id, String filePath) {
        loadSound(filePath);
    }
    
    public String getId() {
        return id;
    }
    
    /*public XXX getSound() {
        return sound;
    }*/
    
    private void loadSound(String filePath) {
        // TODO
    }
}
