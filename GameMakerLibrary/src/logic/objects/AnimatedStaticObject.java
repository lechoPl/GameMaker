package logic.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import resources.GameResources;

public class AnimatedStaticObject extends StaticObject {
    private int currentFrame = 0;
    private int framesCount;
    private long lastFrameChange;   // When last frame change occured
    private int frequency;          // Time between each frame

    public AnimatedStaticObject(String objectName, String imageId, int width, int height, int frequency, int frameCount) {
        super(objectName, imageId, width, height);

        this.frequency = frequency;
        this.framesCount = frameCount;
    }

    private void nextFrame() {
        if (System.currentTimeMillis() - lastFrameChange > frequency) {
            currentFrame = (currentFrame + 1) % framesCount;
            lastFrameChange = System.currentTimeMillis();
        }
    }

    private BufferedImage getFrame(GameResources gameResources) {
        BufferedImage img = gameResources.getImage(imageId);
        return img.getSubimage(currentFrame * (img.getWidth() / framesCount), 0, img.getWidth() / framesCount, img.getHeight());
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        nextFrame();
        g.drawImage(getFrame(gameResources), position.getX(), position.getY(), width, height, null);
    }
}
