package logic.objects;

import java.awt.Graphics;
import logic.Pos;
import resources.GameResources;

public class EndPoint extends GameObject {

    protected Integer nextLevelId = null;

    public EndPoint(Pos p) {
        super(p);

        width = height = 50;
        zindex = -1;
    }

    public EndPoint(Pos p, int width, int height) {
        super(p, width, height);
        zindex = -1;

    }

    public void setNextLevelId(int id) {
        nextLevelId = id;
    }

    public Integer getNextLevelId() {
        return nextLevelId;
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        //default nothig! visable only in editor
    }

}
