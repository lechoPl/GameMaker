package logic;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import logic.objects.GameObject;
import resources.GameResources;
import view.IViewable;

public class Background implements Serializable, IViewable {

    protected ArrayList<GameObject> objectList;

    public Background() {
        objectList = new ArrayList<>();
    }

    public ArrayList<GameObject> getObjectListByZindex() {
        ArrayList<GameObject> result = new ArrayList<>();

        result.addAll(objectList);

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
    
    public GameObject getObject(int x, int y) {
        for (GameObject temp : getObjectListByZindex()) {

            if (temp.getPos().getX() < x && temp.getPos().getX() + temp.getWidth() > x
                    && temp.getPos().getY() < y && temp.getPos().getY() + temp.getHeight() > y) {
                return temp;
            }
        }

        return null;
    }

    public void addObject(GameObject obj) {
        objectList.add(obj);
    }

    public void deleteObject(GameObject obj) {
        objectList.remove(obj);
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        for (GameObject obj : getObjectListByZindex()) {
            obj.render(g, gameResources);
        }
    }

}
