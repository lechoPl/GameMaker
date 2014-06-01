package gui.structure;

import javax.swing.tree.DefaultMutableTreeNode;

public class ScreenTreeNode extends DefaultMutableTreeNode {
    private String name;
    private int id;
    
    public ScreenTreeNode(int id, String name) {
        super(name);
        this.name = name;
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
