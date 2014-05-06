package logic;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import resources.GameResources;
import view.IViewable;

public class Background implements Serializable, IViewable {
    private ArrayList<BackgroundImage> backgroundImages = new ArrayList<BackgroundImage> ();
    
    public Background() {
        
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        
    }
    
    public void addImage(String imageId, int posX, int posY){
        backgroundImages.add(new BackgroundImage(imageId, posX, posY));
    }
         
    private class BackgroundImage {
        private String imageId;
        private int posX, posY;
        
        public BackgroundImage(String imageId, int posX, int posY) {
            this.imageId = imageId;
            this.posX = posX;
            this.posY = posY;
        }
        
        public String getImageId() {
            return imageId;
        }
        
        public int getPosX() {
            return posX;
        }
        
        public int getPosY() {
            return posY;
        }
    }
}
