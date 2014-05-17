package logic.objects;

import enums.PlayerState;
import java.awt.Color;
import java.awt.Graphics;
import logic.Pos;
import resources.GameResources;

public class DynamicObject
        extends GameObject
        implements IMovable, IKeyControlled {

    protected double moveSpeed = 2; // per milisecond
    protected double jumpSpeed = 3;
    protected PlayerState objectState = PlayerState.FALL;
    protected double vx = 0;
    protected double vy = 0;
    protected double ay = 0.05;
    protected boolean jumpAllowed = false;
    
    public DynamicObject() {
        super();
    }

    public DynamicObject(Pos p) {
        super(p);
    }

    public DynamicObject(Pos p, int width, int height) {
        super(p, width, height);
    }

    public PlayerState getObjectState() {
        return objectState;
    }

    public void setObjectState(PlayerState val) {
        objectState = val;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(double val) {
        moveSpeed = val;
    }

    public boolean getJumpAllowed() {
        return jumpAllowed;
    }

    public void setJumpAllowed(boolean val) {
        jumpAllowed = val;
    }
    
    public double getJumpSpeed() {
        return jumpSpeed;
    }
    
    public void setJumpSpeed(double val) {
        jumpSpeed = val;
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        g.setColor(Color.yellow);
        g.fillRect(position.getX(), position.getY(), width, height);
    }

    @Override
    public int getNextXPosition(double dt) {
        return position.getX() + (int) (vx * dt);
    }

    @Override
    public int getNextYPosition(double dt) {
        return position.getY() + (int) ((vy + ay * dt) * dt);
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
        vy = -jumpSpeed;
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

        vy = vy + ay * dt;

        this.setPos(new Pos(tempX, tempY));
    }

    @Override
    public void updateX(double dt) {
        int tempX = getNextXPosition(dt);

        this.setPos(new Pos(tempX, this.getPos().getY()));
    }

    @Override
    public void updateY(double dt) {
        int tempY = getNextYPosition(dt);

        vy = vy + ay * dt;

        this.setPos(new Pos(this.getPos().getX(), tempY));
    }

    /* olny for test, remove it later*/
    @Override
    public void moveUp() {
        vy = -moveSpeed;
    }

    @Override
    public void moveDown() {
        vy = moveSpeed;
    }
}
