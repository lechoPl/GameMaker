package view;

import java.awt.Dimension;
import java.awt.Graphics;
import logic.GameStructure;
import logic.objects.GameObject;

public class Camera {

    public static void setCamera(Graphics g, GameStructure game) {
        if (game == null) {
            return;
        }

        if (game.getCurrentLevel() != null) {

            int xTranslate = 0;
            int yTranslate = 0;

            int lvlWidth = game.getCurrentLevel().getWidth();
            int lvlHeight = game.getCurrentLevel().getHeight();

            Dimension windowSize = game.getWindowSize();

            if (windowSize.width > lvlWidth) {
                xTranslate += (windowSize.width - lvlWidth) / 2;
            }

            if (windowSize.height > lvlHeight) {
                yTranslate += (windowSize.getHeight() - lvlHeight) / 2;
            }

            if (game.getCurrentLevel().getPlayer() != null) {
                GameObject followedObject = game.getCurrentLevel().getPlayer();

                int halfWindowWidth = windowSize.width / 2;
                if (followedObject.getPos().getX() > halfWindowWidth) {
                    if (followedObject.getPos().getX() < lvlWidth - halfWindowWidth) {

                        xTranslate -= (followedObject.getPos().getX() - halfWindowWidth);
                    } else if (lvlWidth > windowSize.width) {

                        xTranslate -= (lvlWidth - windowSize.width);
                    }
                }

                int halfWindowHeight = windowSize.height / 2;
                if (followedObject.getPos().getY() > halfWindowHeight) {
                    if (followedObject.getPos().getY() < lvlHeight - halfWindowHeight) {

                        yTranslate -= (followedObject.getPos().getY() - halfWindowHeight);
                    } else if (lvlHeight > windowSize.height) {

                        yTranslate -= (lvlHeight - windowSize.height);
                    }
                }
            }

            g.translate(xTranslate, yTranslate);
        }
    }
}
