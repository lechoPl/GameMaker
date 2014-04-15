/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import logic.Pos;
import resources.GameResources;

/**
 *
 * @author Pawel
 */
public class StaticObject extends GameObject {
    private BufferedImage image;
    private String objectName;
    
    public StaticObject(String objectName, String imageId) {
        this.objectName = objectName;
        loadImage(imageId);
    }

    public StaticObject(String objectName, Pos p, String imageId) {
        this.objectName = objectName;
        loadImage(imageId);
    }
    
    private void loadImage(String imageId) {
        image = GameResources.getImage(imageId);
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(image, this.getPos().getX(), this.getPos().getY(), null);
    }
    
    public String getName() {
        return objectName;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public void setPosition(Pos pos) {
        this.position = pos;
    }
}
