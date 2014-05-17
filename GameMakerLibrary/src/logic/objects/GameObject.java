package logic.objects;

import java.io.Serializable;
import logic.Pos;
import view.IViewable;

public abstract class GameObject implements IViewable, Serializable, Cloneable {

    protected static int IdCount = 0;
    protected final int id;
    
    protected String objectName;
    protected String imageId;

    protected Pos position;
    protected int width;
    protected int height;

    GameObject() {
        id = IdCount;
        IdCount++;

        position = new Pos(0, 0);
        width = 0;
        height = 0;
    }

    GameObject(Pos p) {
        id = IdCount;
        IdCount++;

        position = p;
        width = 0;
        height = 0;
    }

    GameObject(Pos p, int width, int height) {
        id = IdCount;
        IdCount++;

        position = p;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public Pos getPos() {
        return position;
    }

    public void setPos(Pos p) {
        position = p;
    }
    
    public String getObjectName() {
        return imageId;
    }
    
    public void setObjectName(String objectName){
        this.objectName = objectName;
    }
    
    public String getImageId() {
        return imageId;
    }
    
    public void setImageId(String imageId){
        this.imageId = imageId;
    }

    public void setWidth(int val) {
        this.width = val;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int val) {
        this.height = val;
    }

    public int getHeight() {
        return this.height;
    }
    
    public GameObject copy() throws CloneNotSupportedException {
        return (GameObject) clone();
    }
}
