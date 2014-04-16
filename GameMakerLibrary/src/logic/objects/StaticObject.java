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
    
    public StaticObject(String imageId) {
        loadImage(imageId);
    }

    public StaticObject(Pos p, String imageId) {
        loadImage(imageId);
    }
    
    private void loadImage(String imageId) {
        image = GameResources.getImage(imageId);
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(image, this.getPos().getX(), this.getPos().getY(), null);
    }
    
    
}
