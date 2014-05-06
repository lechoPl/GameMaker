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
    private String objectName;
    private String imageId;
    
    public StaticObject(String objectName, String imageId, int width, int height) {
        super();
        this.objectName = objectName;
        this.imageId = imageId;
        this.width = width;
        this.height = height;
    }

    public StaticObject(String objectName, Pos p, String imageId, int width, int height) {
        super(p);
        this.objectName = objectName;
        this.imageId = imageId;
        this.width = width;
        this.height = height;
    }
    
    public String getImageId() {
        return imageId;
    }
    
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    
    @Override
    public void render(Graphics g, GameResources gameResources) {
        g.drawImage(gameResources.getImage(imageId), this.getPos().getX(), this.getPos().getY(), width, height, null);
    }
    
    public String getName() {
        return objectName;
    }

    public void setPosition(Pos pos) {
        this.position = pos;
    }
}
