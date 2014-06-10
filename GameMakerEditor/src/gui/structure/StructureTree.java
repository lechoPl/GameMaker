package gui.structure;

import gui.EditorFrame;
import gui.controller.GameActions;
import gui.properties.DefaultPropertiesPanel;
import gui.properties.GamePropertiesPanel;
import gui.properties.LevelPropertiesPanel;
import gui.properties.ObjectPropertiesPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import logic.Game;
import logic.Level;
import logic.objects.AnimatedDynamicObject;
import logic.objects.AnimatedStaticObject;
import logic.objects.DynamicObject;
import logic.objects.GameObject;
import logic.objects.SampleObject;
import logic.objects.StaticObject;

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

    private class StructureTreeMouseListener extends MouseAdapter implements ActionListener {

        private int clickInterval = 200;

        MouseEvent lastEvent;
        Timer timer;

        public StructureTreeMouseListener() {
            timer = new Timer(clickInterval, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            singleClick(lastEvent);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() > 2) {
                return;
            }

            lastEvent = e;

            if (timer.isRunning()) {
                timer.stop();
                doubleClick(lastEvent);
            } else {
                timer.restart();
            }
        }

        public void singleClick(MouseEvent e) {
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
                        } else if (node instanceof ObjectTreeNode) {
                            ObjectTreeNode objectNode = (ObjectTreeNode) node;
                            int level = objectNode.getLevelNumber();
                            int id = objectNode.getObjectId();
                            GameObject object = frame.getGame().getGameStructure().getLevels().get(level).getObject(id);
                            frame.changePropertiesPanel(new ObjectPropertiesPanel(frame, object));
                        } else {
                            frame.changePropertiesPanel(new DefaultPropertiesPanel());
                        }

                        if (node instanceof LevelTreeNode) {
                            LevelTreeNode levelNode = (LevelTreeNode) node;
                            int nr = levelNode.getLevelNumber();
                            Level newLevel = frame.getGame().getGameStructure().getLevels().get(nr);
                            frame.getGame().getGameStructure().setCurrentLevel(newLevel);
                            frame.setSelectedObject(null);
                        } else if (node instanceof ScreenTreeNode) {
                            ScreenTreeNode screenNode = (ScreenTreeNode) node;
                            Level level = frame.getGame().getGameStructure().getScreens().get(screenNode.getId());
                            frame.getGame().getGameStructure().setCurrentLevel(level);
                        } else if (node instanceof ObjectTreeNode) {
                            ObjectTreeNode objectNode = (ObjectTreeNode) node;

                            int nr = objectNode.getLevelNumber();
                            Level newLevel = frame.getGame().getGameStructure().getLevels().get(nr);
                            frame.getGame().getGameStructure().setCurrentLevel(newLevel);

                            int id = objectNode.getObjectId();
                            GameObject newObject = newLevel.getObject(id);
                            frame.setSelectedObject(newObject);
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
                    } else if (node instanceof ObjectTreeNode) {
                        ObjectTreeNode objectNode = (ObjectTreeNode) node;
                        int id = objectNode.getObjectId();

                        GameActions.selectedObject = id;
                        GameActions.selectedObjectLevel = objectNode.getLevelNumber();
                        objectPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    } else if(node.toString().equals("Levels")){
                        gamePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        }

        public void doubleClick(MouseEvent e) {
            TreePath selPath = getPathForLocation(e.getX(), e.getY());
            
            if (selPath != null) {
                Object node = selPath.getLastPathComponent();

                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.getClickCount() == 2) {
                        if (node instanceof ObjectTreeNode) {
                            ObjectTreeNode objectNode = (ObjectTreeNode) node;
                            int level = objectNode.getLevelNumber();
                            int id = objectNode.getObjectId();
                            GameObject object = frame.getGame().getGameStructure().getLevels().get(level).getObject(id);
                            frame.changePropertiesPanel(new ObjectPropertiesPanel(frame, object));
                        }

                        if (node instanceof ObjectTreeNode) {
                            ObjectTreeNode objectNode = (ObjectTreeNode) node;

                            int nr = objectNode.getLevelNumber();
                            Level newLevel = frame.getGame().getGameStructure().getLevels().get(nr);
                            frame.getGame().getGameStructure().setCurrentLevel(newLevel);

                            int id = objectNode.getObjectId();
                            GameObject newObject = newLevel.getObject(id);
                            frame.setSelectedObject(newObject);
                        }
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
    private ObjectPopupMenu objectPopupMenu;

    private Color backgroundColor;

    public StructureTree() {
        super();
    }

    public StructureTree(EditorFrame frame) {
        super();
        this.frame = frame;
        this.gamePopupMenu = new GamePopupMenu(frame);
        this.levelPopupMenu = new LevelPopupMenu(frame);
        this.objectPopupMenu = new ObjectPopupMenu(frame);

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
            
            DefaultMutableTreeNode sampleObjectsTreeNode = new DefaultMutableTreeNode("Sample objects");
            DefaultMutableTreeNode objectsTreeNode = new DefaultMutableTreeNode("Objects");
            DefaultMutableTreeNode animatedObjectsTreeNode = new DefaultMutableTreeNode("Animated objects");
            DefaultMutableTreeNode mobsTreeNode = new DefaultMutableTreeNode("Creatures");
            DefaultMutableTreeNode animatedMobsTreeNode = new DefaultMutableTreeNode("Animated creatures", true);

            for (GameObject object : level.getAllObjects()) {
                String objectName = object.getObjectName();
                if (objectName == null || objectName.isEmpty()) {
                    objectName = "untitled object";
                }

                ObjectTreeNode objectNode = new ObjectTreeNode(object.getId(), i, objectName);

                if (object instanceof SampleObject) {
                    sampleObjectsTreeNode.add(objectNode);
                } else if (object instanceof StaticObject) {
                    if (object instanceof AnimatedStaticObject) {
                        animatedObjectsTreeNode.add(objectNode);
                    } else {
                        objectsTreeNode.add(objectNode);
                    }
                }
            }

            for (DynamicObject object : level.getAllMobs()) {
                String objectName = object.getObjectName();
                if (objectName == null || objectName.isEmpty()) {
                    objectName = "untitled object";
                }

                ObjectTreeNode objectNode = new ObjectTreeNode(object.getId(), i, objectName);

                if (object instanceof AnimatedDynamicObject) {
                    animatedMobsTreeNode.add(objectNode);
                } else {
                    mobsTreeNode.add(objectNode);
                }
            }

            if (sampleObjectsTreeNode.getChildCount() > 0) {
                levelNode.add(sampleObjectsTreeNode);
            }
            if (objectsTreeNode.getChildCount() > 0) {
                levelNode.add(objectsTreeNode);
            }
            if (animatedObjectsTreeNode.getChildCount() > 0) {
                levelNode.add(animatedObjectsTreeNode);
            }
            if (mobsTreeNode.getChildCount() > 0) {
                levelNode.add(mobsTreeNode);
            }
            if (animatedMobsTreeNode.getChildCount() > 0) {
                levelNode.add(animatedMobsTreeNode);
            }

            i++;
        }

        i = 0;

        for (Level level : game.getGameStructure().getScreens()) {
            ScreenTreeNode screenNode = new ScreenTreeNode(i, level.getName());
            screensTreeNode.add(screenNode);

            i++;
        }

        gameNode.add(levelsTreeNode);
        gameNode.add(screensTreeNode);
        DefaultTreeModel model = new DefaultTreeModel(gameNode);
        model.setAsksAllowsChildren(true);
        this.setModel(model);

        backgroundColor = this.getParent().getBackground();
        this.setBackground(backgroundColor);
    }
}
