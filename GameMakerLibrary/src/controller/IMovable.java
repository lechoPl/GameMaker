package controller;

public interface IMovable {
    /* dt is delta time */

    public int getNextXPosition(double dt);

    public int getNextYPosition(double dt);

    
    /* update <=> updateX and updateY */
    public void update(double dt);
    
    public void updateX(double dt);
    
    public void updateY(double dt);
}
