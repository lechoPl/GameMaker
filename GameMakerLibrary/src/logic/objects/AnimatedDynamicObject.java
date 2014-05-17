/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import logic.Pos;
import resources.GameResources;

/**
 *
 * @author Pawel
 */
public class AnimatedDynamicObject extends DynamicObject {
    private String objectName;
    private int currentFrame = 0;
    private int framesCount;
    private long lastFrameChange;   // When last frame change occured
    private int frequency;          // Time between each frame
    
    public AnimatedDynamicObject(String objectName, String imageId, int width, int height, int frequency, int frameCount) {
        super();
        
        this.objectName = objectName;
        this.imageId = imageId;
        this.width = width;
        this.height = height;
        this.frequency = frequency;
        this.framesCount = frameCount;
    }
    
    private void nextFrame() {
        if(System.currentTimeMillis() - lastFrameChange > frequency) {
            currentFrame = (currentFrame + 1) % framesCount;
            lastFrameChange = System.currentTimeMillis();
        }
    }
    
    private BufferedImage getFrame(GameResources gameResources) {
        BufferedImage img = gameResources.getImage(imageId);
        return img.getSubimage(currentFrame * (img.getWidth()/framesCount), 0, img.getWidth()/framesCount, img.getHeight());
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        nextFrame();
        g.drawImage(getFrame(gameResources), this.getPos().getX(), this.getPos().getY(), width, height, null);
    }   
}
