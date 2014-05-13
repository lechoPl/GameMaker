package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import logic.objects.IKeyControlled;

public class PlayerController extends KeyAdapter {

    protected IKeyControlled controlledObject;

    protected int jumpKey = KeyEvent.VK_SPACE;
    protected int rightKey = KeyEvent.VK_RIGHT;
    protected int leftKey = KeyEvent.VK_LEFT;

    public IKeyControlled getControlledObject() {
        return controlledObject;
    }

    public void setControlledObject(IKeyControlled obj) {
        controlledObject = obj;
    }

    public void setJumpKey(int keyCode) {
        jumpKey = keyCode;
    }

    public int getJumpKey() {
        return jumpKey;
    }

    public void setRightKey(int keyCode) {
        rightKey = keyCode;
    }

    public int getRightKey() {
        return rightKey;
    }

    public void setLeftKey(int keyCode) {
        leftKey = keyCode;
    }

    public int getLeftKey() {
        return leftKey;
    }

    protected void keyMapping(KeyEvent ke) {
        if (controlledObject == null) {
            return;
        }
        if (ke.getKeyCode() == rightKey) {
            controlledObject.moveRight();
        }
        
        if (ke.getKeyCode() == jumpKey) {
            controlledObject.moveJump();
        }
        
        if (ke.getKeyCode() == leftKey) {
            controlledObject.moveLeft();
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        keyMapping(ke);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (controlledObject == null) {
            return;
        }

        controlledObject.moveStop();
    }

}
