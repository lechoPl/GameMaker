package logic.objects;

public interface IMovable {
    /* dt is delta time */

    public int getNextXPosition(double dt);

    public int getNextYPosition(double dt);

    public void update(double dt);
}
