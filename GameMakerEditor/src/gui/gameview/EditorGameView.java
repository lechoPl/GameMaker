package gui.gameview;

import gui.EditorFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import logic.Game;
import logic.objects.GameObject;

public class EditorGameView extends JPanel implements Runnable {

    protected LevelPreview levelPreview;
    protected JScrollPane scrollPane;
    protected JPanel viewOptionsPanel;

    protected JCheckBox chb_editbg = new JCheckBox("Edit background");
    protected JCheckBox chb_showBg = new JCheckBox("Show background", true);
    protected JCheckBox chb_showObjects = new JCheckBox("Show objects", true);

    protected Thread levelRefreshThread;
    
    public EditorGameView(Game g, EditorFrame frame) {

        levelPreview = new LevelPreview(g, frame);
        scrollPane = new JScrollPane(levelPreview);

        initViewOptionPanel();

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(viewOptionsPanel, BorderLayout.PAGE_END);

        levelRefreshThread = new Thread(this);
        levelRefreshThread.start();
    }

    public void initViewOptionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(chb_editbg);
        panel.add(chb_showBg);
        panel.add(chb_showObjects);

        chb_showBg.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (levelPreview != null) {
                    levelPreview.setViewBackground(chb_showBg.isSelected());
                    levelPreview.setSelectedObject(null);
                }
            }
        });

        chb_editbg.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (levelPreview != null) {
                    levelPreview.setBackgroundEditable(chb_editbg.isSelected());
                    levelPreview.setSelectedObject(null);

                }
            }
        });

        chb_showObjects.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (levelPreview != null) {
                    levelPreview.setViewLevel(chb_showObjects.isSelected());
                    levelPreview.setSelectedObject(null);

                }
            }
        });

        viewOptionsPanel = new JPanel();
        viewOptionsPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        viewOptionsPanel.add(panel);

    }

    public GameObject getSelectedObject() {
        return levelPreview.getSelectedObject();
    }

    public void setSelectedObject(GameObject obj) {
        levelPreview.setSelectedObject(obj);
    }

    public GameObject getObjectToAdd() {
        return levelPreview.getObjectToAdd();
    }

    public void setObjectToAdd(GameObject obj) {
        levelPreview.setObjectToAdd(obj);
    }

    public void setGame(Game game) {
        levelPreview.setGame(game);
    }

    @Override
    public void run() {
        while (true) {

            repaint();
            if (levelPreview != null) {
                levelPreview.refresh();
                levelPreview.repaint();
            }

            scrollPane.revalidate();

            try {
                sleep(100);
            } catch (InterruptedException ex) {
            }
        }
    }
}
