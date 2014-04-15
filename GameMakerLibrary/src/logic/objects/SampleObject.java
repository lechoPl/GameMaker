package logic.objects;

import java.awt.Color;
import java.awt.Graphics;
import logic.Pos;

public class SampleObject extends GameObject {

    protected Color color;

    public SampleObject(Pos p) {
        super(p);
    }

    public SampleObject(Pos p, int width, int height) {
        super(p, width, height);
    }

    public SampleObject(Pos p, int width, int height, Color c) {
        this.position = p;
        this.width = width;
        this.height = height;
        this.color = c;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public void redner(Graphics g) {
        g.setColor(color);
        g.fillRect(position.getX(), position.getY(), width, height);
    }

}
