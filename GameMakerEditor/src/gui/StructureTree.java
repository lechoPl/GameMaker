package gui;

import gui.properties.DefaultPropertiesPanel;
import gui.properties.GamePropertiesPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import logic.Game;
import logic.Level;
import view.IGameFrame;

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

    private class StructureTreeMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            TreePath selPath = getPathForLocation(e.getX(), e.getY());
            
            if(e.getClickCount() == 1) {
                if(selPath.getPathCount() == 1) {
                    frame.changePropertiesPanel(new GamePropertiesPanel(frame));
                } else {
                    frame.changePropertiesPanel(new DefaultPropertiesPanel());
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    private EditorFrame frame;

    private Color backgroundColor;

    public StructureTree() {
        super();
    }

    public StructureTree(EditorFrame frame) {
        super();
        this.frame = frame;

        this.addMouseListener(new StructureTreeMouseListener());
        this.setCellRenderer(new StructureTreeCellRenderer());
    }

    public void reload() {
        Game game = frame.getGame();

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(game.getGameStructure().getName());

        for (Level level : game.getGameStructure().getLevels()) {
            DefaultMutableTreeNode levelNode = new DefaultMutableTreeNode(level.getName());
            rootNode.add(levelNode);
        }

        DefaultTreeModel model = new DefaultTreeModel(rootNode);
        this.setModel(model);

        backgroundColor = this.getParent().getBackground();
        this.setBackground(backgroundColor);
    }
}
