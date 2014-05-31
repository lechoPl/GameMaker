package logic.objects;

import logic.Pos;

public class TestDynamicObject extends DynamicObject{
    protected int test = 45;
    
    public TestDynamicObject(Pos p, int width, int height) {
        super(p, width, height);
    }
}
