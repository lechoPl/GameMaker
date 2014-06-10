/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.gameview;

import gui.CustomTabbedPane;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import logic.Game;
import logic.Pos;
import logic.objects.DynamicObject;
import logic.objects.GameObject;
import logic.objects.StaticObject;

/**
 *
 * @author Pawel
 */
public class MousePopupMenu extends JPopupMenu {

    private Game game;
    private Pos pos;
    private JMenu addMenu;
    private LevelPreview preview;

    public MousePopupMenu(LevelPreview preview) {
        this.preview = preview;
        this.game = preview.getGame();
    }

    private void setMenu(boolean freshSelect) {
        if (addMenu != null) {
            addMenu.removeAll();
        }

        addMenu = new JMenu("Add");
        AddCreatureAction addCreatureAction = new AddCreatureAction();
        AddObjectAction addObjectAction = new AddObjectAction();
        AddPlayerAction addPlayerAction = new AddPlayerAction();

        JMenu objectsSectionMenu = new JMenu("Object");
        for (Entry<String, StaticObject> entry : game.getGameResources().getObjects().entrySet()) {
            JMenuItem menuAddObject = new JMenuItem(entry.getKey());
            menuAddObject.addActionListener(addObjectAction);
            menuAddObject.setActionCommand(entry.getKey());
            objectsSectionMenu.add(menuAddObject);
        }

        JMenu creaturesSectionMenu = new JMenu("Creature");
        for (Entry<String, DynamicObject> entry : game.getGameResources().getCreatures().entrySet()) {
            JMenuItem menuAddCreature = new JMenuItem(entry.getKey());
            menuAddCreature.addActionListener(addCreatureAction);
            menuAddCreature.setActionCommand(entry.getKey());
            creaturesSectionMenu.add(menuAddCreature);
        }
        
        JMenu playerSectionMenu = new JMenu("Player");
        for (Entry<String, DynamicObject> entry : game.getGameResources().getCreatures().entrySet()) {
            JMenuItem menuAddPlayer = new JMenuItem(entry.getKey());
            menuAddPlayer.addActionListener(addPlayerAction);
            menuAddPlayer.setActionCommand(entry.getKey());
            playerSectionMenu.add(menuAddPlayer);
        }

        addMenu.add(objectsSectionMenu);
        addMenu.add(creaturesSectionMenu);
        addMenu.add(playerSectionMenu);
        
        add(addMenu);
        
        if(freshSelect) {
            ObjectRemoveAction objectRemoveAction = new ObjectRemoveAction();
            
            JMenuItem deleteMenu = new JMenuItem("Delete");
            deleteMenu.addActionListener(objectRemoveAction);
            add(deleteMenu);
        }
    }

    public void show(Component invoker, int x, int y, Point pos, boolean freshSelect) {
        setMenu(freshSelect);

        super.show(invoker, x, y);

        this.pos = new Pos((int) pos.getX(), (int) pos.getY());
    }

    private class AddObjectAction extends AbstractAction {

        public AddObjectAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            StaticObject obj;
            try {
                obj = (StaticObject) game.getGameResources().getObjects().get(e.getActionCommand()).copy();
                obj.setPos(pos);
                game.getGameStructure().getCurrentLevel().addObject(obj);
                
                preview.setSelectedObject(obj);
                preview.getFrame().refreshStructureTree();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(MousePopupMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class AddCreatureAction extends AbstractAction {

        public AddCreatureAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DynamicObject obj;
            try {
                obj = (DynamicObject) game.getGameResources().getCreatures().get(e.getActionCommand()).copy();
                obj.setPos(pos);
                game.getGameStructure().getCurrentLevel().addMob(obj);
                
                preview.setSelectedObject(obj);
                preview.getFrame().refreshStructureTree();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(MousePopupMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class AddPlayerAction extends AbstractAction {

        public AddPlayerAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DynamicObject obj;
            try {
                obj = (DynamicObject) game.getGameResources().getCreatures().get(e.getActionCommand()).copy();
                obj.setPos(pos);
                game.getGameStructure().getCurrentLevel().setPlayer(obj);
                game.getGameStructure().getPlayerController().setControlledObject(obj);
                
                preview.setSelectedObject(obj);
                preview.getFrame().refreshStructureTree();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(MousePopupMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class ObjectRemoveAction extends AbstractAction {

        public ObjectRemoveAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(preview.getSelectedObject() != null) {
                game.getGameStructure().getCurrentLevel().deleteObject(preview.getSelectedObject());
                preview.setSelectedObject(null);
                
                preview.getFrame().refreshStructureTree();
            }
        }
    }
}
