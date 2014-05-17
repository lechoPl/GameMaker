package gui.structure;

import javax.swing.tree.DefaultMutableTreeNode;

public class LevelTreeNode extends DefaultMutableTreeNode {

    private int levelNumber;
    
    public LevelTreeNode(int number, String name) {
        super(name);
        
        this.setLevelNumber(number);
    }
    
    public int getLevelNumber() {
        return this.levelNumber;
    }
    
    public void setLevelNumber(int number) {
        this.levelNumber = number;
    }
}
