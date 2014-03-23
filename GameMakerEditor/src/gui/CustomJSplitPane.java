package gui;

import javax.swing.JSplitPane;

public class CustomJSplitPane extends JSplitPane {
    private final int dividerSize = 4;
        
    public CustomJSplitPane(int i) {
        super(i);
        
        this.setDividerSize(dividerSize);
        this.setContinuousLayout(true);
        this.setBorder( null );
	this.setDividerLocation( 200 );
    }
}
