package logic.objects;

import enums.PlayerState;
import java.awt.Color;
import java.awt.Graphics;
import logic.Pos;
import resources.GameResources;

public class PlayerObject
        extends GameObject
        implements IMovable, IKeyControlled {

    protected int moveSpeed = 2; // per milisecond
    protected PlayerState objectState = PlayerState.FALL;
    protected double vx = 0;
    protected double vy = 0;
    protected double ay = 0;
    protected boolean jumpAllowed = false;

    public PlayerObject(Pos p) {
        super(p);
    }

    public PlayerObject(Pos p, int width, int height) {
        super(p, width, height);
    }

    public PlayerState getObjectState() {
        return objectState;
    }

    public void setObjectState(PlayerState val) {
        objectState = val;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int val) {
        moveSpeed = val;
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        g.setColor(Color.yellow);
        g.fillRect(position.getX(), position.getY(), width, height);
    }

    @Override
    public int getNextXPosition(double dt) {
        return position.getX() + (int)(vx * dt);
    }

    @Override
    public int getNextYPosition(double dt) {
        return  position.getY() + (int)((vy + ay * dt) * dt);
    }

    @Override
    public void moveLeft() {
        objectState = PlayerState.MOVE_LEFT;

        vx = -moveSpeed;
    }

    @Override
    public void moveRight() {
        objectState = PlayerState.MOVE_RIGHT;

        vx = moveSpeed;
    }

    @Override
    public void moveJump() {
        if (!jumpAllowed) {
            return;
        }

        objectState = PlayerState.JUMP;
        jumpAllowed = false;
    }

    @Override
    public void moveStop() {
        objectState = PlayerState.STAND;

        vx = 0;
    }

    @Override
    public void update(double dt) {
        int tempX = getNextXPosition(dt);
        int tempY = getNextYPosition(dt);

        this.setPos(new Pos(tempX, tempY));
    }
}
