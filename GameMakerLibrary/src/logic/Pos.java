package logic;

import java.io.Serializable;

public class Pos implements Serializable {
    
    protected int x;
    protected int y;
    
    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Pos(Pos p) {
        this.x = p.getX();
        this.y = p.getY();
    }
    
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    
    public void setX(int val) { this.x = val; }
    public void setY(int val) { this.y = val; }
}
