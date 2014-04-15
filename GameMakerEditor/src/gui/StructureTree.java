package gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import logic.Game;
import logic.Level;

public class StructureTree extends JTree {

    private class StructureTreeCellRenderer extends DefaultTreeCellRenderer {

        public StructureTreeCellRenderer() {
            super();
        }

        @Override
        public Component getTreeCellRendererComponent(JTree pTree, Object pValue, boolean pIsSelected, boolean pIsExpanded, boolean pIsLeaf, int pRow, boolean pHasFocus) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) pValue;

            super.getTreeCellRendererComponent(pTree, pValue, pIsSelected, pIsExpanded, pIsLeaf, pRow, pHasFocus);

            setBackgroundNonSelectionColor(backgroundColor);
            setBackgroundSelectionColor(Color.white);

            return (this);
        }
    }

    private Game game;
    
    private Color backgroundColor;

    public StructureTree() {
        super();
    }

    public StructureTree(Game game) {
        super();
        this.game = game;

        this.setCellRenderer(new StructureTreeCellRenderer());
    }

    public void reload() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(game.getName());

        for (Level level : game.getLevels()) {
            DefaultMutableTreeNode levelNode = new DefaultMutableTreeNode(level.getName());
            rootNode.add(levelNode);
        }

        DefaultTreeModel model = new DefaultTreeModel(rootNode);
        this.setModel(model);

        backgroundColor = this.getParent().getBackground();
        this.setBackground(backgroundColor);
    }
}
