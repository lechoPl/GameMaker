package logic.objects;

import logic.Pos;
import view.IViewable;

public abstract class Object implements IViewable {
    protected Pos position = null;
    
    Object() {
        position = new Pos(0,0);
    }
    
    Object(Pos p) {
        position = p;
    }
    
    public Pos getPos() { return position; }
    public void setPos(Pos p) { position = p; }
}
