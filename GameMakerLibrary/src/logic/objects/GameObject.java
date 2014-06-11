package logic.objects;

import java.io.Serializable;
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
        id = -1;

        position = new Pos(0, 0);
        width = 0;
        height = 0;
    }

    GameObject(Pos p) {
        position = p;
        width = 0;
        height = 0;
    }

    GameObject(Pos p, int width, int height) {
        position = p;
        this.width = width;
        this.height = height;
    }

    public synchronized void setId(int val) {
        id = val;
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

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
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
        return o;
    }

    public int getZindex() {
        return zindex;
    }

    public void setZindex(int val) {
        zindex = val;
    }
}
