package gui.structure;

import gui.EditorFrame;
import gui.properties.DefaultPropertiesPanel;
import gui.properties.GamePropertiesPanel;
import gui.properties.LevelPropertiesPanel;
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
            
            Object node = selPath.getLastPathComponent();
            
            if(e.getClickCount() == 1) {
                if(node instanceof GameTreeNode) {
                    frame.changePropertiesPanel(new GamePropertiesPanel(frame));
                } else if(node instanceof LevelTreeNode) {
                    LevelTreeNode levelNode = (LevelTreeNode)node;
                    int nr = levelNode.getLevelNumber();
                    frame.changePropertiesPanel(new LevelPropertiesPanel(frame, nr));
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

        GameTreeNode gameNode = new GameTreeNode(game.getGameStructure().getName());

        int i = 0;
        
        for (Level level : game.getGameStructure().getLevels()) {
            LevelTreeNode levelNode = new LevelTreeNode(i, level.getName());
            gameNode.add(levelNode);
            
            i++;
        }
        
        DefaultTreeModel model = new DefaultTreeModel(gameNode);
        this.setModel(model);

        backgroundColor = this.getParent().getBackground();
        this.setBackground(backgroundColor);
    }
}
