package gui.structure;

import javax.swing.tree.DefaultMutableTreeNode;

public class ObjectTreeNode extends DefaultMutableTreeNode {

    private int objectId;
    private int levelNumber;

    public ObjectTreeNode(int objectId, int levelNumber, String name) {
        super(name, false);

        this.objectId = objectId;
        this.levelNumber = levelNumber;
    }

    public int getObjectId() {
        return this.objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }
    
    public int getLevelNumber() {
        return this.levelNumber;
    }
    
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
}
