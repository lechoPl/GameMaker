package gui.gameview;

import gui.EditorFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import logic.Game;
import logic.Level;
import logic.objects.EndPoint;
import logic.objects.GameObject;
import view.GameView;

public class LevelPreview extends GameView {

    protected GameObject selectedObject;
    protected GameObject objectToAdd;
    protected boolean bgEdit = false;
    protected boolean bgShow = true;
    protected boolean lvlShow = true;

    public final int margin = 50;

    private EditorFrame frame;

    private void initControler() {
        MouseInputGameView mouseList = new MouseInputGameView(this);

        System.out.println("init");

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(mouseList);
        this.addMouseMotionListener(mouseList);
        this.addMouseWheelListener(mouseList);
    }

    public LevelPreview() {
        setPreferredSize(new Dimension(widthDefault, heightDefault));

        initControler();
    }

    public LevelPreview(Game game, EditorFrame frame) {
        this.game = game;
        this.frame = frame;
        setPreferredSize(game.getGameStructure().getWindowSize());

        refresh();

        initControler();
    }

    public GameObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(GameObject obj) {
        selectedObject = obj;
    }

    public GameObject getObjectToAdd() {
        return objectToAdd;
    }

    public void setObjectToAdd(GameObject obj) {
        objectToAdd = obj;
    }

    public void setViewBackground(boolean val) {
        bgShow = val;
    }

    public boolean setViewBackground() {
        return bgShow;
    }

    public void setBackgroundEditable(boolean val) {
        bgEdit = val;
    }

    public boolean getBackgroundEditable() {
        return bgEdit;
    }

    public void setViewLevel(boolean val) {
        lvlShow = val;
    }

    public boolean getViewLevel() {
        return lvlShow;
    }

    public EditorFrame getFrame() {
        return this.frame;
    }

    public void refresh() {
        if (game.getGameStructure().getCurrentLevel() != null) {
            Dimension size = game.getGameStructure().getCurrentLevel().getSize();
            setPreferredSize(new Dimension(size.width + 2 * margin, size.height + 2 * margin));
        } else {
            setPreferredSize(game.getGameStructure().getWindowSize());
        }
    }

    @Override
    public void setGame(Game game) {
        super.setGame(game);
        selectedObject = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getSize().width, getSize().height);

        g.translate(margin, margin);

        if (game.getGameStructure() != null && game.getGameStructure().getCurrentLevel() != null) {
            Level currentLevel = game.getGameStructure().getCurrentLevel();

            g.setColor(currentLevel.getBackGroudColor());
            g.fillRect(0, 0, currentLevel.getWidth(), currentLevel.getHeight());

            if (bgShow && currentLevel.getBackground() != null) {
                currentLevel.getBackground().render(g, game.getGameResources());
            }
            
            if(!bgEdit) {
                Color c = currentLevel.getBackGroudColor();
                if (c != null) {
                    g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
                } else {
                    g.setColor(new Color(0,0,0, 150));
                }
                g.fillRect(0, 0, currentLevel.getWidth(), currentLevel.getHeight());
            }

            if (lvlShow) {
                for (EndPoint obj : currentLevel.getAllEndPoints()) {
                    g.setColor(Color.BLACK);
                    g.drawRect(
                            obj.getPos().getX() - 1,
                            obj.getPos().getY() - 1,
                            obj.getWidth() + 2,
                            obj.getHeight() + 2);
                    g.drawRect(
                            obj.getPos().getX() + 1,
                            obj.getPos().getY() + 1,
                            obj.getWidth() - 2,
                            obj.getHeight() - 2);

                    g.setColor(Color.ORANGE);
                    g.drawRect(
                            obj.getPos().getX(),
                            obj.getPos().getY(),
                            obj.getWidth(),
                            obj.getHeight());
                    g.drawLine(
                            obj.getPos().getX(),
                            obj.getPos().getY() + obj.getHeight() / 10,
                            obj.getPos().getX() + obj.getWidth() / 10,
                            obj.getPos().getY());
                }

                for (GameObject obj : currentLevel.getAllObjectsByZindex()) {
                    obj.render(g, game.getGameResources());
                }

                if (currentLevel.getPlayer() != null) {
                    currentLevel.getPlayer().render(g, game.getGameResources());
                }
            }
        }

        //add paint frame to selected object
        if (selectedObject != null) {
            g.setColor(Color.WHITE);
            g.drawRect(
                    selectedObject.getPos().getX() - 1,
                    selectedObject.getPos().getY() - 1,
                    selectedObject.getWidth() + 2,
                    selectedObject.getHeight() + 2);
            g.drawRect(
                    selectedObject.getPos().getX() + 1,
                    selectedObject.getPos().getY() + 1,
                    selectedObject.getWidth() - 2,
                    selectedObject.getHeight() - 2);

            g.setColor(Color.BLACK);
            g.drawRect(
                    selectedObject.getPos().getX(),
                    selectedObject.getPos().getY(),
                    selectedObject.getWidth(),
                    selectedObject.getHeight());
        }

    }
}
