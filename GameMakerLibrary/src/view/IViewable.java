package view;

import java.awt.Graphics;
import resources.GameResources;

public interface IViewable {
    public void render(Graphics g, GameResources gameResources);
}
