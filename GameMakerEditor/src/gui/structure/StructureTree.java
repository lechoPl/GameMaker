package gui.structure;

import gui.EditorFrame;
import gui.controller.GameActions;
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

            if (selPath != null) {
                Object node = selPath.getLastPathComponent();

                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.getClickCount() == 1) {
                        if (node instanceof GameTreeNode) {
                            frame.changePropertiesPanel(new GamePropertiesPanel(frame));
                        } else if (node instanceof LevelTreeNode) {
                            LevelTreeNode levelNode = (LevelTreeNode) node;
                            Level level = frame.getGame().getGameStructure().getLevels().get(levelNode.getLevelNumber());
                            frame.changePropertiesPanel(new LevelPropertiesPanel(frame, level));
                        } else if (node instanceof ScreenTreeNode) {
                            ScreenTreeNode screenNode = (ScreenTreeNode) node;
                            Level level = frame.getGame().getGameStructure().getScreens().get(screenNode.getId());
                            frame.changePropertiesPanel(new LevelPropertiesPanel(frame, level));
                        } else {
                            frame.changePropertiesPanel(new DefaultPropertiesPanel());
                        }
                    } else if (e.getClickCount() == 2) {
                        if (node instanceof LevelTreeNode) {
                            LevelTreeNode levelNode = (LevelTreeNode) node;
                            int nr = levelNode.getLevelNumber();
                            Level newLevel = frame.getGame().getGameStructure().getLevels().get(nr);
                            frame.getGame().getGameStructure().setCurrentLevel(newLevel);
                            frame.refreshGamePreview();
                        } else if (node instanceof ScreenTreeNode) {
                            ScreenTreeNode screenNode = (ScreenTreeNode) node;
                            Level level = frame.getGame().getGameStructure().getScreens().get(screenNode.getId());
                            frame.getGame().getGameStructure().setCurrentLevel(level);
                            frame.refreshGamePreview();
                        }
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (node instanceof GameTreeNode) {
                        gamePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    } else if (node instanceof LevelTreeNode) {
                        LevelTreeNode levelNode = (LevelTreeNode) node;
                        int nr = levelNode.getLevelNumber();

                        GameActions.selectedLevel = nr;
                        levelPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
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
    private GamePopupMenu gamePopupMenu;
    private LevelPopupMenu levelPopupMenu;

    private Color backgroundColor;

    public StructureTree() {
        super();
    }

    public StructureTree(EditorFrame frame) {
        super();
        this.frame = frame;
        this.gamePopupMenu = new GamePopupMenu(frame);
        this.levelPopupMenu = new LevelPopupMenu(frame);

        this.addMouseListener(new StructureTreeMouseListener());
        this.setCellRenderer(new StructureTreeCellRenderer());
    }

    public void reload() {
        Game game = frame.getGame();

        GameTreeNode gameNode = new GameTreeNode(game.getGameStructure().getName());
        DefaultMutableTreeNode levelsTreeNode = new DefaultMutableTreeNode("Levels");
        DefaultMutableTreeNode screensTreeNode = new DefaultMutableTreeNode("Screens");

        int i = 0;

        for (Level level : game.getGameStructure().getLevels()) {
            LevelTreeNode levelNode = new LevelTreeNode(i, level.getName());
            levelsTreeNode.add(levelNode);

            i++;
        }
        
        i = 0;
        for(Level level : game.getGameStructure().getScreens()) {
            ScreenTreeNode screenNode = new ScreenTreeNode(i, level.getName());
            screensTreeNode.add(screenNode);
            
            i++;
        }
        
        gameNode.add(levelsTreeNode);
        gameNode.add(screensTreeNode);
        DefaultTreeModel model = new DefaultTreeModel(gameNode);
        this.setModel(model);

        backgroundColor = this.getParent().getBackground();
        this.setBackground(backgroundColor);
    }
}
