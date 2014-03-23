package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

public class EditorFrame extends JFrame {
    
    private JMenu testMenu = new JMenu("File");
    private JMenuItem testItem = new JMenuItem("temp");
    private JMenuBar menu = new JMenuBar();
    
    private CustomJSplitPane horizontalPane = 
            new CustomJSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private CustomJSplitPane verticalLeftPane = 
            new CustomJSplitPane(JSplitPane.VERTICAL_SPLIT);
    private CustomJSplitPane verticalRightPane = 
            new CustomJSplitPane(JSplitPane.VERTICAL_SPLIT);
    
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
        gameStructure.add(new JLabel("project tree"));
        gameStructure.add(new JTree());
        verticalLeftPane.add(gameStructure);
        gameStructure.setSize(500, 200);
        
        // --- GAME CONTENT ---
        gameContent.add(new JLabel("game preview"));
        verticalRightPane.add(gameContent);
        
        // --- PROPERTIES ---
        gameProperties.add(new JLabel("selected object properties"));
        verticalLeftPane.add(gameProperties);
        
        // --- TOOLBOX ---
        gameToolbox.add(new JLabel("toolbox"));
        verticalRightPane.add(gameToolbox);
    }
}
