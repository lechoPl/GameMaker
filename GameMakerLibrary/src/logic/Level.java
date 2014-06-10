package logic;

import enums.CollisionType;
import enums.PlayerState;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import logic.objects.DynamicObject;
import logic.objects.EndPoint;
import logic.objects.GameObject;
import logic.objects.SampleObject;
import resources.GameResources;
import view.IViewable;

public class Level implements Serializable, IViewable {

    protected static int IdCount = 0;
    protected final int id;

    // fields
    private String levelName;

    private int levelHeight;
    private int levelWidth;

    //temp
    private Color bgColor;

    private Background levelBackground;
    //private Background levelFronground; // player show lives etc.
    private ArrayList<GameObject> objects;     //should be dictionary or hashmap to beter get objects
    private ArrayList<DynamicObject> mobs;
    private ArrayList<EndPoint> endPoints;
    private DynamicObject player;
    private Pos playerStartPos;

    // constructors
    public Level(String levelName) {
        id = IdCount;
        IdCount++;

        this.levelName = levelName;

        levelBackground = new Background();
        objects = new ArrayList<>();
        mobs = new ArrayList<>();
        endPoints = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }

    public Dimension getSize() {
        return new Dimension(levelWidth, levelHeight);
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

    public ArrayList<GameObject> getAllObjects() {
        return objects;
    }

    public ArrayList<DynamicObject> getAllMobs() {
        return mobs;
    }

    public ArrayList<EndPoint> getAllEndPoints() {
        return endPoints;
    }

    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    public void addMob(DynamicObject obj) {
        mobs.add(obj);
    }

    public void addEndPoint(EndPoint ep) {
        endPoints.add(ep);
    }

    public void deleteObject(GameObject obj) {
        if (objects.contains(obj)) {
            objects.remove(obj);
        }

        if (mobs.contains(obj)) {
            mobs.remove(obj);
        }

        if (endPoints.contains(obj)) {
            endPoints.remove(obj);
        }
    }

    public GameObject getObject(int x, int y) {
        for (GameObject temp : getAllObjectsByZindex()) {

            if (temp.getPos().getX() < x && temp.getPos().getX() + temp.getWidth() > x
                    && temp.getPos().getY() < y && temp.getPos().getY() + temp.getHeight() > y) {
                return temp;
            }
        }

        if (getPlayer() != null && getPlayer().getPos().getX() < x && getPlayer().getPos().getX() + getPlayer().getWidth() > x
                && getPlayer().getPos().getY() < y && getPlayer().getPos().getY() + getPlayer().getHeight() > y) {
            return getPlayer();
        }

        for (EndPoint temp : getAllEndPoints()) {

            if (temp.getPos().getX() < x && temp.getPos().getX() + temp.getWidth() > x
                    && temp.getPos().getY() < y && temp.getPos().getY() + temp.getHeight() > y) {
                return temp;
            }
        }

        return null;
    }

    public GameObject getObject(int id) {
        for (GameObject obj : getAllObjectsByZindex()) {
            if (obj.getId() == id) {
                return obj;
            }
        }

        return null;
    }

    public ArrayList<GameObject> getObjectsForMobsByPosX() {
        ArrayList<GameObject> result = new ArrayList<>();

        result.addAll(this.objects);
        result.add(player);

        Collections.sort(result, new Comparator<GameObject>() {
            @Override
            public int compare(GameObject t, GameObject t1) {
                if (t.getPos().x == t1.getPos().x) {
                    return 0;
                }

                return t.getPos().x < t1.getPos().x ? -1 : 1;
            }
        });

        return result;
    }

    public ArrayList<GameObject> getObjectsForMobsByPosY() {
        ArrayList<GameObject> result = new ArrayList<>();

        result.addAll(this.objects);
        result.add(player);

        Collections.sort(result, new Comparator<GameObject>() {
            @Override
            public int compare(GameObject t, GameObject t1) {
                if (t.getPos().y == t1.getPos().y) {
                    return 0;
                }

                return t.getPos().y < t1.getPos().y ? -1 : 1;
            }
        });

        return result;
    }

    /**
     * get all object from level without player
     *
     * @return object ordered by zindex
     */
    public ArrayList<GameObject> getAllObjectsByZindex() {
        ArrayList<GameObject> result = new ArrayList<>();

        result.addAll(this.objects);
        result.addAll(this.mobs);

        Collections.sort(result, new Comparator<GameObject>() {
            @Override
            public int compare(GameObject t, GameObject t1) {
                if (t.getZindex() == t1.getZindex()) {
                    return 0;
                }

                return t.getZindex() < t1.getZindex() ? -1 : 1;
            }
        });

        return result;
    }

    /**
     * get all object from level without player
     *
     * @return object ordered by pos.x
     */
    public ArrayList<GameObject> getAllObjectsByPosX() {
        ArrayList<GameObject> result = new ArrayList<>();

        result.addAll(this.objects);
        result.addAll(this.mobs);

        Collections.sort(result, new Comparator<GameObject>() {
            @Override
            public int compare(GameObject t, GameObject t1) {
                if (t.getPos().x == t1.getPos().x) {
                    return 0;
                }

                return t.getPos().x < t1.getPos().x ? -1 : 1;
            }
        });

        return result;
    }

    /**
     * get all object from level without player
     *
     * @return object ordered by pos.y
     */
    public ArrayList<GameObject> getAllObjectsByPosY() {
        ArrayList<GameObject> result = new ArrayList<>();

        result.addAll(this.objects);
        result.addAll(this.mobs);

        Collections.sort(result, new Comparator<GameObject>() {
            @Override
            public int compare(GameObject t, GameObject t1) {
                if (t.getPos().y == t1.getPos().y) {
                    return 0;
                }

                return t.getPos().y < t1.getPos().y ? -1 : 1;
            }
        });

        return result;
    }

    public DynamicObject getPlayer() {
        return player;
    }

    public void setPlayer(DynamicObject p) {
        player = p;
        playerStartPos = new Pos(p.getPos().getX(), p.getPos().getY());
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

        if (levelBackground != null) {
            levelBackground.render(g, gameResources);
        }

        for (GameObject obj : this.getAllObjectsByZindex()) {
            obj.render(g, gameResources);
        }

        if (player != null) {
            player.render(g, gameResources);
        }
    }

    public EndPoint checkEndPoint() {
        if(player == null)
            return null;
        
        int objX1 = player.getPos().getX();
        int objY1 = player.getPos().getY();
        int objX2 = objX1 + player.getWidth();
        int objY2 = objY1 + player.getHeight();

        for (EndPoint obj : getAllEndPoints()) {
            int tempX1 = obj.getPos().getX();
            int tempY1 = obj.getPos().getY();
            int tempX2 = tempX1 + obj.getWidth();
            int tempY2 = tempY1 + obj.getHeight();
            
            if (tempY2 <= objY1 || tempY1 >= objY2) {
                continue;
            }
            if (tempX2 <= objX1 || tempX1 >= objX2) {
                continue;
            }
            
            if (objY2 > tempY1 && objY1 < tempY1) {
                return obj;
            }
            if (objY1 < tempY2 && objY2 > tempY2) {
                return obj;
            }
        }
        
        return null;
    }

    /**
     * Update position and state movable objects.
     *
     * @param deltaTime move time
     */
    public void update(double deltaTime) {
        Collision result;

        if (!player.isKilled()) {
            result = checkCollisionX(player, deltaTime, getAllObjectsByPosX());
            if (result == null) {
                player.updateX(deltaTime);
            } else {
                switch (result.type) {
                    case BACK:
                        player.setPos(new Pos(result.pos, player.getPos().getY()));
                        if (result.obj != null && DynamicObject.class.isAssignableFrom(result.obj.getClass())) {
                            player.kill();
                        }

                        break;
                    case FRONT:
                        player.setPos(new Pos(result.pos - player.getWidth(), player.getPos().getY()));

                        if (result.obj != null && DynamicObject.class.isAssignableFrom(result.obj.getClass())) {
                            player.kill();
                        }
                        break;
                    default:
                        throw new UnsupportedOperationException("Wrong collision type: " + result.type + " " + result.pos);

                }
            }

            result = checkCollisionY(player, deltaTime, getAllObjectsByPosY());
            if (result == null) {
                player.updateY(deltaTime);
            } else {
                switch (result.type) {
                    case UP:
                        player.setJumpAllowed(false);
                        player.setYSpeedValue(0);
                        player.setPos(new Pos(player.getPos().getX(), result.pos));

                        if (result.obj != null && DynamicObject.class.isAssignableFrom(result.obj.getClass())) {
                            player.kill();
                        }
                        break;
                    case DOWN:
                        player.setJumpAllowed(true);
                        player.setPos(new Pos(player.getPos().getX(), result.pos - player.getHeight()));

                        if (result.obj == null) {
                            player.kill();
                        }

                        if (result.obj != null && DynamicObject.class.isAssignableFrom(result.obj.getClass())) {
                            player.setJumpAllowed(false);
                            ((DynamicObject) (result.obj)).kill();
                        }
                        break;
                    default:
                        throw new UnsupportedOperationException("Wrong collision type: " + result.type + " " + result.pos);
                }
            }
        } else if(player.getLives() > 0) {
            player.setPos(playerStartPos);
            player.raise();
        }

        //move mobs
        ArrayList<GameObject> objectsByPosX = getObjectsForMobsByPosX();
        ArrayList<GameObject> objectsByPosY = getObjectsForMobsByPosY();

        for (DynamicObject mob : mobs) {
            if (mob.isKilled()) {
                continue;
            }

            if (mob.getObjectState() == PlayerState.STAND) {
                mob.moveLeft();
            }

            result = checkCollisionX(mob, deltaTime, objectsByPosX);
            if (result == null) {
                mob.updateX(deltaTime);
            } else {
                switch (result.type) {
                    case BACK:
                        mob.setPos(new Pos(result.pos, mob.getPos().getY()));
                        mob.moveRight();

                        if (result.obj != null && DynamicObject.class.isAssignableFrom(result.obj.getClass())) {
                            ((DynamicObject) (result.obj)).kill();
                        }
                        break;
                    case FRONT:
                        mob.setPos(new Pos(result.pos - mob.getWidth(), mob.getPos().getY()));
                        mob.moveLeft();

                        if (result.obj != null && DynamicObject.class.isAssignableFrom(result.obj.getClass())) {
                            ((DynamicObject) (result.obj)).kill();
                        }
                        break;
                    default:
                        throw new UnsupportedOperationException("Wrong collision type: " + result.type + " " + result.pos);

                }
            }

            result = checkCollisionY(mob, deltaTime, objectsByPosY);
            if (result == null) {
                mob.updateY(deltaTime);
            } else {
                switch (result.type) {
                    case UP:
                        mob.setJumpAllowed(false);
                        mob.setYSpeedValue(0);
                        mob.setPos(new Pos(mob.getPos().getX(), result.pos));

                        if (result.obj != null && DynamicObject.class.isAssignableFrom(result.obj.getClass())) {
                            ((DynamicObject) (result.obj)).kill();
                        }

                        break;
                    case DOWN:
                        mob.setJumpAllowed(true);
                        mob.setPos(new Pos(mob.getPos().getX(), result.pos - mob.getHeight()));

                        if (result.obj == null) {
                            mob.kill();
                        }

                        break;
                    default:
                        throw new UnsupportedOperationException("Wrong collision type: " + result.type + " " + result.pos);
                }
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
    public Collision checkCollisionX(DynamicObject obj, double deltaTime, ArrayList<GameObject> listObjects) {
        int objX1 = obj.getNextXPosition(deltaTime);
        int objY1 = obj.getPos().getY();
        int objX2 = objX1 + obj.getWidth();
        int objY2 = objY1 + obj.getHeight();

        for (GameObject tempObj : listObjects) {
            int tempX1 = tempObj.getPos().getX();
            int tempY1 = tempObj.getPos().getY();
            int tempX2 = tempX1 + tempObj.getWidth();
            int tempY2 = tempY1 + tempObj.getHeight();

            if (tempX1 > objX2) {
                break;
            }

            if (DynamicObject.class.isAssignableFrom(tempObj.getClass())) {
                if (((DynamicObject) tempObj).isKilled()) {
                    continue;
                }
            }

            if (tempY2 <= objY1 || tempY1 >= objY2) {
                continue;
            }
            if (tempX2 <= objX1 || tempX1 >= objX2) {
                continue;
            }

            if (objX2 > tempX1 && objX1 < tempX1) {
                return new Collision(CollisionType.FRONT, tempX1, tempObj);
            }
            if (objX1 < tempX2 && objX2 > tempX2) {
                return new Collision(CollisionType.BACK, tempX2, tempObj);
            }
        }

        if (objX1 < 0) {
            return new Collision(CollisionType.BACK, 0, null);
        }
        if (objX2 > levelWidth) {
            return new Collision(CollisionType.FRONT, levelWidth, null);
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
    public Collision checkCollisionY(DynamicObject obj, double deltaTime, ArrayList<GameObject> listObjects) {

        int objX1 = obj.getPos().getX();
        int objY1 = obj.getNextYPosition(deltaTime);
        int objX2 = objX1 + obj.getWidth();
        int objY2 = objY1 + obj.getHeight();

        for (GameObject tempObj : listObjects) {
            int tempX1 = tempObj.getPos().getX();
            int tempY1 = tempObj.getPos().getY();
            int tempX2 = tempX1 + tempObj.getWidth();
            int tempY2 = tempY1 + tempObj.getHeight();

            if (tempY1 > objY2) {
                break;
            }

            if (DynamicObject.class.isAssignableFrom(tempObj.getClass())) {
                if (((DynamicObject) tempObj).isKilled()) {
                    continue;
                }
            }

            if (tempY2 <= objY1 || tempY1 >= objY2) {
                continue;
            }
            if (tempX2 <= objX1 || tempX1 >= objX2) {
                continue;
            }

            if (objY2 > tempY1 && objY1 < tempY1) {
                return new Collision(CollisionType.DOWN, tempY1, tempObj);
            }
            if (objY1 < tempY2 && objY2 > tempY2) {
                return new Collision(CollisionType.UP, tempY2, tempObj);
            }
        }

        if (objY1 < 0) {
            return new Collision(CollisionType.UP, 0, null);
        }
        if (objY2 > levelHeight) {
            return new Collision(CollisionType.DOWN, levelHeight, null);
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

        /**
         * conflicting object
         */
        public final GameObject obj;

        public Collision(CollisionType t, int p, GameObject o) {
            type = t;
            pos = p;
            obj = o;
        }
    }

    static public Level getSampleLevel(int nextLvlId) {
        Level level = new Level("Sample level");
        level.setHeight(400);
        level.setWidth(400);
        level.setBackgroudColor(new Color(30, 30, 30));

        SampleObject obj1 = new SampleObject(new Pos(40, 40), 40, 80, Color.GREEN);
        SampleObject obj2 = new SampleObject(new Pos(10, 380), 1800, 20, Color.RED);
        SampleObject obj3 = new SampleObject(new Pos(200, 350), 50, 20, Color.RED);
        SampleObject obj4 = new SampleObject(new Pos(240, 300), 50, 20, Color.RED);

        level.addObject(obj1);
        level.addObject(obj2);
        level.addObject(obj3);
        level.addObject(obj4);

        SampleObject obj5 = new SampleObject(new Pos(50, 100), 100, 100, Color.WHITE);
        level.levelBackground.addObject(obj5);

        DynamicObject mob1 = new DynamicObject(new Pos(20, 300), 50, 50);
        level.addMob(mob1);
        
        DynamicObject player = new DynamicObject(new Pos(300, 80), 50, 50);
        player.setLives(3);
        level.setPlayer(player);

        EndPoint ep = new EndPoint(new Pos(50, 50), 100, 100);
        ep.setNextLevelId(nextLvlId);
        level.addEndPoint(ep);

        return level;
    }
    
    
    static public Level getSampleLevel() {
        Level level = new Level("Sample level2");
        level.setHeight(400);
        level.setWidth(400);
        level.setBackgroudColor(new Color(30, 30, 30));

        SampleObject obj1 = new SampleObject(new Pos(150, 40), 40, 80, Color.GREEN);
        SampleObject obj2 = new SampleObject(new Pos(10, 380), 1800, 20, Color.RED);
        SampleObject obj3 = new SampleObject(new Pos(200, 350), 50, 20, Color.RED);
        SampleObject obj4 = new SampleObject(new Pos(240, 300), 50, 20, Color.RED);

        level.addObject(obj1);
        level.addObject(obj2);
        level.addObject(obj3);
        level.addObject(obj4);

        DynamicObject mob1 = new DynamicObject(new Pos(20, 300), 50, 50);
        level.addMob(mob1);

        level.setPlayer(new DynamicObject(new Pos(100, 40), 50, 50));

        EndPoint ep = new EndPoint(new Pos(150, 150), 100, 100);
        level.addEndPoint(ep);
        
        return level;
    }
}
