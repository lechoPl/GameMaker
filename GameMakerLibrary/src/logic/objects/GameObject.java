package logic.objects;

import java.io.Serializable;
import logic.GameStructure;
import logic.Pos;
import view.IViewable;

public abstract class GameObject implements IViewable, Serializable, Cloneable {
    protected int id;
    
    protected String objectName;
    protected String imageId;

    protected Pos position;
    protected int width;
    protected int height;
    
    protected int zindex = 0;

    GameObject() {
        id = GameStructure.OBJ_ID_COUNT;
        GameStructure.OBJ_ID_COUNT++;

        position = new Pos(0, 0);
        width = 0;
        height = 0;
    }

    GameObject(Pos p) {
        id = GameStructure.OBJ_ID_COUNT;
        GameStructure.OBJ_ID_COUNT++;

        position = p;
        width = 0;
        height = 0;
    }

    GameObject(Pos p, int width, int height) {
        id = GameStructure.OBJ_ID_COUNT;
        GameStructure.OBJ_ID_COUNT++;

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
        return objectName;
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
        GameObject o = (GameObject) clone();
        o.setId();
        return o;
    }
    
    public int getZindex() {
        return zindex;
    }
    
    public void setZindex(int val) {
        zindex = val;
    }
    
    public void setId() {
        id = GameStructure.OBJ_ID_COUNT;
        GameStructure.OBJ_ID_COUNT++;
    }
}
