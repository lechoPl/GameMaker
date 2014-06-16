package gui.structure;

import javax.swing.tree.DefaultMutableTreeNode;

public class PlayerTreeNode extends DefaultMutableTreeNode {
    
    private int levelNumber;
    
    public PlayerTreeNode(int levelNumber, String name) {
        super(name, false);
        
        this.levelNumber = levelNumber;
    }
    
    public int getLevelNumber() {
        return levelNumber;
    }
    
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
}
