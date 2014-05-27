package logic.objects;

import enums.Direction;
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
    
    protected PlayerState objectState = PlayerState.STAND;
    protected Direction objectDirection = Direction.RIGHT;
    
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
    
    public Direction getObjectDirection() {
        return objectDirection;
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
    
    public void setYSpeedValue(double val) {
        vy = val;
    }
    
    public double getYSpeedValue() {
        return vy;
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        g.setColor(Color.yellow);
        g.fillRect(position.getX(), position.getY(), width, height);
    }

    @Override
    public int getNextXPosition(double dt) {
        updateState();
        return position.getX() + (int) (vx * dt);
    }

    @Override
    public int getNextYPosition(double dt) {
        updateState();
        return position.getY() + (int) ((vy + ay * dt) * dt);
    }

    @Override
    public void moveLeft() {
        vx = -moveSpeed;
    }

    @Override
    public void moveRight() {
        vx = moveSpeed;
    }

    @Override
    public void moveJump() {
        if (!jumpAllowed) {
            return;
        }

        jumpAllowed = false;
        vy = -jumpSpeed;
    }

    @Override
    public void moveStop() {
        vx = 0;
    }
    
    @Override
    public void update(double dt) {
        int tempX = getNextXPosition(dt);
        int tempY = getNextYPosition(dt);

        vy = vy + ay * dt;

        this.setPos(new Pos(tempX, tempY));
        
        updateState();
    }

    @Override
    public void updateX(double dt) {
        int tempX = getNextXPosition(dt);

        this.setPos(new Pos(tempX, this.getPos().getY()));
        
        updateState();
    }

    @Override
    public void updateY(double dt) {
        int tempY = getNextYPosition(dt);

        vy = vy + ay * dt;

        this.setPos(new Pos(this.getPos().getX(), tempY));
        
        updateState();
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
    
    protected void updateState() {
        if(vx == 0) {
            if(jumpAllowed)
                objectState = PlayerState.STAND;
        } else {
            if(vx > 0) objectDirection = Direction.RIGHT;
            else objectDirection = Direction.LEFT;
            
            objectState = PlayerState.MOVE;
        }
        
        if(!jumpAllowed) {
            if(vy > 0)
                objectState = PlayerState.FALL;
            else if(vy < 0)
                objectState = PlayerState.JUMP;
        }
    }
}
