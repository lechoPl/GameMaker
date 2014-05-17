package logic;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import logic.objects.GameObject;
import logic.objects.DynamicObject;
import logic.objects.SampleObject;
import resources.GameResources;
import view.IViewable;

public class Level implements Serializable, IViewable {

    // fields
    private String levelName;

    private int levelHeight;
    private int levelWidth;

    //temp
    private Color bgColor;

    private Background levelBackground;
    private ArrayList<GameObject> objects;     //should be dictionary or hashmap to beter get objects
    private DynamicObject player;

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
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);

            if (temp.getPos().getX() < x && temp.getPos().getX() + temp.getWidth() > x
                    && temp.getPos().getY() < y && temp.getPos().getY() + temp.getHeight() > y) {
                return temp;
            }
        }

        return null;
    }

    public GameObject getObject(int id) {
        for (int i = 0; i < objects.size(); i++) {

            if (objects.get(i).getId() == id) {
                return objects.get(i);
            }
        }

        return null;
    }

    public DynamicObject getPlayer() {
        return player;
    }

    public void setPlayer(DynamicObject p) {
        player = p;
    }

    public Color getBackGroudColor() {
        return bgColor;
    }

    public void setBackgroudColor(Color c) {
        bgColor = c;
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {

        //background
        g.setColor(bgColor);
        g.fillRect(0, 0, levelWidth, levelHeight);

        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).render(g, gameResources);
        }

        player.render(g, gameResources);
    }

    public void update(double deltaTime) {
        if (!checkCollisionX(player, deltaTime)) {
            player.updateX(deltaTime);
        }

        if (!checkCollisionY(player, deltaTime)) {
            player.updateY(deltaTime);
        }
        else {
            player.setJumpAllowed(true);
        }
    }

    public boolean checkCollisionX(DynamicObject obj, double deltaTime) {

        int objX1 = obj.getNextXPosition(deltaTime);
        int objY1 = obj.getPos().getY();
        int objX2 = objX1 + obj.getWidth();
        int objY2 = objY1 + obj.getHeight();

        return checkCollision(objX1, objY1, objX2, objY2);
    }

    public boolean checkCollisionY(DynamicObject obj, double deltaTime) {

        int objX1 = obj.getPos().getX();
        int objY1 = obj.getNextYPosition(deltaTime);
        int objX2 = objX1 + obj.getWidth();
        int objY2 = objY1 + obj.getHeight();

        return checkCollision(objX1, objY1, objX2, objY2);
    }

    public boolean checkCollision(int x1, int y1, int x2, int y2) {
        if(x1 < 1 || x2 > levelWidth)
            return true;
        if(y1 < 1 || y2 > levelHeight)
            return true;
        
        // obj corners: (X1, Y1), (X2, Y2), (X1, Y2), (X2, Y2)
        for (GameObject tempObj : objects) {
            int tempX1 = tempObj.getPos().getX();
            int tempY1 = tempObj.getPos().getY();
            int tempX2 = tempX1 + tempObj.getWidth();
            int tempY2 = tempY1 + tempObj.getHeight();

            if (tempY2 <= y1 || tempY1 >= y2) {
                continue;
            }
            if (tempX2 <= x1 || tempX1 >= x2) {
                continue;
            }
            return true;
        }

        return false;
    }

    static public Level getSampleLevel() {
        Level level = new Level("Sample level");
        level.setHeight(400);
        level.setWidth(2000);

        SampleObject obj1 = new SampleObject(new Pos(40, 40), 40, 80, Color.GREEN);
        SampleObject obj2 = new SampleObject(new Pos(0, 380), 2000, 20, Color.RED);
        SampleObject obj3 = new SampleObject(new Pos(200, 350), 50, 20, Color.RED);
        SampleObject obj4 = new SampleObject(new Pos(240, 300), 50, 20, Color.RED);


        level.addObject(obj1);
        level.addObject(obj2);
        level.addObject(obj3);
        level.addObject(obj4);
        
        level.setPlayer(new DynamicObject(new Pos(80, 80), 50, 50));

        return level;
    }
}
