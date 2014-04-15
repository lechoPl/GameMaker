package logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;
import logic.objects.GameObject;
import logic.objects.SampleObject;
import view.IViewable;

public class Level implements Serializable, IViewable {

    // fields
    private String levelName;
    
    private int levelHeight;
    private int levelWidth;
    
    //temp
    private Color bgColor;
    
    private Background levelBackground;
    private ArrayList<GameObject> objects;     //should be dictionary or hashmap
    
    // constructors
    public Level(String levelName) {
        this.levelName = levelName;
        
        objects = new ArrayList<GameObject>();
    }

    // setters and getters
    public void setName(String levelName) {
        this.levelName = levelName;
    }
    public String getName() {
        return this.levelName;
    }

    public void setHeight(int height) {
        this.levelHeight = height;
    }
    public int getHeight() {
        return this.levelHeight;
    }

    public void setWidth(int width) {
        this.levelWidth = width;
    }
    public int getWidth() {
        return this.levelWidth;
    }
    
    public void setBackground(Background background) {
        this.levelBackground = background;
    }
    public Background getBackground() {
        return this.levelBackground;
    }
    
    public void addObject(GameObject obj) {
        objects.add(obj);
    }
    public GameObject getObject(int x, int y) {
        for(int i=0; i<objects.size(); i++) {
            GameObject temp = objects.get(i);
            
            if(temp.getPos().getX() < x && temp.getPos().getX() + temp.getWidth() > x &&
               temp.getPos().getY() < y && temp.getPos().getY() + temp.getHeight()> y)
                return temp;
        }
        
        return null;
    }
    public GameObject getObject(int id) {
        for(int i=0; i<objects.size(); i++) {
            
            if(objects.get(i).getId() == id)
                return objects.get(i);
        }
        
        return null;
    }
    
    
    public Color getBackGroudColor() {
        return bgColor;
    }
    public void setBackgroudColor(Color c) {
        bgColor = c;
    }
    
    @Override
    public void redner(Graphics g) {
        
        //background
        g.fillRect(0, 0, levelWidth, levelHeight);
        
        for(int i=0; i<objects.size(); i++) {
            objects.get(i).redner(g);
        }
    }
    
    static public Level getSampleLevel() {
        Level level = new Level("Sample level");
        level.setHeight(400);
        level.setWidth(2000);
        
        SampleObject obj1 = new SampleObject(new Pos(20, 20), 40, 80, Color.GREEN);
        SampleObject obj2 = new SampleObject(new Pos(100, 150), 140, 180, Color.RED);
        
        level.addObject(obj1);
        level.addObject(obj2);
        
        return level;
    }
}
