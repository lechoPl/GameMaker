package logic.objects;

import java.awt.Graphics;
import logic.Pos;
import resources.GameResources;

public class StaticObject extends GameObject {

    public StaticObject(String objectName, String imageId, int width, int height) {
        super();
        this.objectName = objectName;
        this.imageId = imageId;
        this.width = width;
        this.height = height;
    }

    public StaticObject(String objectName, Pos p, String imageId, int width, int height) {
        super(p);
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
    
    public void setPosition(Pos pos) {
        this.position = pos;
    }
}
