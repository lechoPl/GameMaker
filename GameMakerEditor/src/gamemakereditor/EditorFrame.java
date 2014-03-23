package gamemakereditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class EditorFrame extends JFrame {
    
    private JMenu testMenu = new JMenu("File");
    private JMenuItem testItem = new JMenuItem("temp");
    private JMenuBar menu = new JMenuBar();
    
    private JSplitPane horizontalPane = 
            new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JSplitPane verticalLeftPane = 
            new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JSplitPane verticalRightPane = 
            new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    
    private JPanel gameContent = new JPanel();
    private JPanel gameStructure = new JPanel();
    private JPanel gameProperties = new JPanel();
    private JPanel gameToolbox = new JPanel();
    
    public EditorFrame() {
        super("GameMaker Editor");
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
        
        this.setLayout(new BorderLayout());
        
        // --- MENU ---
        menu.add(testMenu);
        testMenu.add(testItem);
        this.add(menu, BorderLayout.PAGE_START);
        
        this.add(horizontalPane, BorderLayout.CENTER);
        
        horizontalPane.add(verticalLeftPane);
        horizontalPane.add(verticalRightPane);
        
        // --- STRUCTURE ---
        gameStructure.setBackground(Color.yellow);
        verticalLeftPane.add(gameStructure);
        gameStructure.setSize(500, 200);
        
        // --- GAME CONTENT ---
        gameContent.setBackground(Color.blue);
        verticalRightPane.add(gameContent);
        
        // --- PROPERTIES ---
        gameProperties.setBackground(Color.green);
        verticalLeftPane.add(gameProperties);
        
        // --- TOOLBOX ---
        gameToolbox.setBackground(Color.red);
        verticalRightPane.add(gameToolbox);
    }
}
