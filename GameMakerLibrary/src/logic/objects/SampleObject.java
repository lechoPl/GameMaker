package logic.objects;

import java.awt.Color;
import java.awt.Graphics;
import logic.Pos;

public class SampleObject extends Object {
    protected int width;
    protected int height;
    protected Color color;
    
    public SampleObject(Pos p) {
        this.position = p;
        
        width = 0;
        height = 0;
    }
    
    public SampleObject(Pos p, int width, int height) {
       this.position = p;
       this.width = width;
       this.height = height;
    }
    
    public SampleObject(Pos p, int width, int height, Color c) {
        this.position = p;
        this.width = width;
        this.height = height;
        this.color = c;
    }
    
    public void setWidth(int val) { this.width = val; }
    public int getWidth() { return this.width; }
    
    public void setHeight(int val) { this.height = val; }
    public int getHeight() { return this.height; }
    
    public void setColor(Color c) { this.color = c; }
    public Color getColor() { return this.color; }
    
    @Override
    public void redner(Graphics g) {
        g.setColor(color);
        g.fillRect(position.getX(), position.getY(), width, height);
    }

}
