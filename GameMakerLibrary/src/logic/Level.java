package logic;

import enums.CollisionType;
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
    private ArrayList<GameObject> objects;
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

    /**
     * Update position and state movable objects.
     *
     * @param deltaTime move time
     */
    public void update(double deltaTime) {
        Collision result = checkCollisionX(player, deltaTime);

        if (result == null) {
            player.updateX(deltaTime);
        } else {
            switch (result.type) {
                case BACK:
                    player.setPos(new Pos(result.pos, player.getPos().getY()));
                    break;
                case FRONT:
                    player.setPos(new Pos(result.pos - player.getWidth(), player.getPos().getY()));
                    break;
                default:
                    throw new UnsupportedOperationException("Wrong collision type: " + result.type + " " + result.pos);

            }
        }

        result = checkCollisionY(player, deltaTime);
        if (result == null) {
            player.updateY(deltaTime);
        } else {
            switch (result.type) {
                case UP:
                    player.setJumpAllowed(false);
                    player.setYSpeedValue(0);
                    player.setPos(new Pos(player.getPos().getX(), result.pos));
                    break;
                case DONW:
                    player.setJumpAllowed(true);
                    player.setPos(new Pos(player.getPos().getX(), result.pos - player.getHeight()));
                    break;
                default:
                    throw new UnsupportedOperationException("Wrong collision type: " + result.type + " " + result.pos);
            }
        }
    }

    /**
     * Check obj collision on axe X
     *
     * @param obj
     * @param deltaTime
     * @return
     */
    public Collision checkCollisionX(DynamicObject obj, double deltaTime) {
        int objX1 = obj.getNextXPosition(deltaTime);
        int objY1 = obj.getPos().getY();
        int objX2 = objX1 + obj.getWidth();
        int objY2 = objY1 + obj.getHeight();

        if (objX1 < 0) {
            return new Collision(CollisionType.BACK, 0);
        }
        if (objX2 > levelWidth) {
            return new Collision(CollisionType.FRONT, levelWidth);
        }

        for (GameObject tempObj : objects) {
            int tempX1 = tempObj.getPos().getX();
            int tempY1 = tempObj.getPos().getY();
            int tempX2 = tempX1 + tempObj.getWidth();
            int tempY2 = tempY1 + tempObj.getHeight();

            if (tempY2 <= objY1 || tempY1 >= objY2) {
                continue;
            }
            if (tempX2 <= objX1 || tempX1 >= objX2) {
                continue;
            }

            if (objX2 > tempX1 && objX1 < tempX1) {
                return new Collision(CollisionType.FRONT, tempX1);
            }
            if (objX1 < tempX2 && objX2 > tempX2) {
                return new Collision(CollisionType.BACK, tempX2);
            }
        }

        return null;
    }

    /**
     * Check obj collision on axe Y
     *
     * @param obj
     * @param deltaTime
     * @return
     */
    public Collision checkCollisionY(DynamicObject obj, double deltaTime) {

        int objX1 = obj.getPos().getX();
        int objY1 = obj.getNextYPosition(deltaTime);
        int objX2 = objX1 + obj.getWidth();
        int objY2 = objY1 + obj.getHeight();

        if (objY1 < 0) {
            return new Collision(CollisionType.UP, 0);
        }
        if (objY2 > levelHeight) {
            return new Collision(CollisionType.DONW, levelHeight);
        }

        for (GameObject tempObj : objects) {
            int tempX1 = tempObj.getPos().getX();
            int tempY1 = tempObj.getPos().getY();
            int tempX2 = tempX1 + tempObj.getWidth();
            int tempY2 = tempY1 + tempObj.getHeight();

            if (tempY2 <= objY1 || tempY1 >= objY2) {
                continue;
            }
            if (tempX2 <= objX1 || tempX1 >= objX2) {
                continue;
            }

            if (objY2 > tempY1 && objY1 < tempY1) {
                return new Collision(CollisionType.DONW, tempY1);
            }
            if (objY1 < tempY2 && objY2 > tempY2) {
                return new Collision(CollisionType.UP, tempY2);
            }
        }

        return null;
    }

    private class Collision {

        /**
         * Collision Type
         */
        public final CollisionType type;

        /**
         * x or y coordinate wher collision was detected
         */
        public final int pos;

        public Collision(CollisionType t, int p) {
            type = t;
            pos = p;
        }
    }

    static public Level getSampleLevel() {
        Level level = new Level("Sample level");
        level.setHeight(400);
        level.setWidth(400);

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
