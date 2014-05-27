package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import logic.objects.IKeyControlled;

public class PlayerController extends KeyAdapter implements Serializable {

    protected IKeyControlled controlledObject;

    protected int jumpKey = KeyEvent.VK_SPACE;
    protected int rightKey = KeyEvent.VK_RIGHT;
    protected int leftKey = KeyEvent.VK_LEFT;

    /* olny for test, remove it later*/
    protected int upKey = KeyEvent.VK_UP;
    protected int downKey = KeyEvent.VK_DOWN;

    protected final int numberOfKeys = 5;

    /**
     * list of key statuses: 0 - jump, 1 - right, 2 - left, 3 - up, 5 - down
     */
    protected boolean[] keySatuses;

    public PlayerController() {
        super();

        keySatuses = new boolean[numberOfKeys];
        for (int i = 0; i < numberOfKeys; i++) {
            keySatuses[i] = false;
        }
    }

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

    protected synchronized void setKeyVal(KeyEvent ke, boolean val) {

        if (ke.getKeyCode() == jumpKey) {
            keySatuses[0] = val;
        }

        if (ke.getKeyCode() == rightKey) {
            keySatuses[1] = val;
        }

        if (ke.getKeyCode() == leftKey) {
            keySatuses[2] = val;
        }

        /* olny for test, remove it later */
        if (ke.getKeyCode() == upKey) {
            keySatuses[3] = val;
        }
        if (ke.getKeyCode() == downKey) {
            keySatuses[4] = val;
        }
    }

    public synchronized void pull() {
        if (controlledObject == null) {
            return;
        }

        if (keySatuses[0]) {
            controlledObject.moveJump();
        }

        if (keySatuses[1]) {
            controlledObject.moveRight();
        }

        if (keySatuses[2]) {
            controlledObject.moveLeft();
        }

        if ((!keySatuses[1] && !keySatuses[2]) || (keySatuses[1] && keySatuses[2])) {
            controlledObject.moveStop();
        }

        /* olny for test, remove it later */
        if (keySatuses[3]) {
            controlledObject.moveUp();
        }

        if (keySatuses[4]) {
            controlledObject.moveDown();
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        setKeyVal(ke, true);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        setKeyVal(ke, false);
    }

}
