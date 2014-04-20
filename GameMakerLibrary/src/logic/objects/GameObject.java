package logic.objects;

import logic.Pos;
import view.IViewable;

public abstract class GameObject implements IViewable {

    protected static int IdCount = 0;
    protected final int id;

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
}