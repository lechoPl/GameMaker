package logic.objects;

import enums.Direction;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import resources.GameResources;

public class AnimatedDynamicObject extends DynamicObject {
    public static final int ANIM_LINES = 3;
    private static final int KILLED_FRAMES_LIMIT = 10;
    private static final int KILLED_FRAME_FREQUENCY = 150;
    
    private String objectName;
    private int currentFrame = 0;
    private int framesCount;
    private long lastFrameChange;   // When last frame change occured
    private int frequency;          // Time between each frame

    public AnimatedDynamicObject(String objectName, String imageId, int width, int height, int frequency, int frameCount) {
        super();

        this.objectName = objectName;
        this.imageId = imageId;
        this.width = width;
        this.height = height;
        this.frequency = frequency;
        this.framesCount = frameCount;
    }

    private void nextFrame() {
        if (System.currentTimeMillis() - lastFrameChange > frequency || (isKilled() && System.currentTimeMillis() - lastFrameChange > KILLED_FRAME_FREQUENCY)) {
            if(!isKilled()) {
                currentFrame = (currentFrame + 1) % framesCount;
            } else if(killedAgoInFrames < KILLED_FRAMES_LIMIT) {
                killedAgoInFrames++;
            }
            lastFrameChange = System.currentTimeMillis();
        }
    }

    private BufferedImage getFrame(GameResources gameResources) {
        BufferedImage img = gameResources.getImage(imageId);
        
        switch (getObjectState()) {
            case STAND:
                img = img.getSubimage(currentFrame * (img.getWidth() / framesCount), img.getHeight() / ANIM_LINES, img.getWidth() / framesCount, img.getHeight() / ANIM_LINES);
                break;
            case MOVE:
                img = img.getSubimage(currentFrame * (img.getWidth() / framesCount), 0, img.getWidth() / framesCount, img.getHeight() / ANIM_LINES);
                break;
            default:
                img = img.getSubimage(currentFrame * (img.getWidth() / framesCount), 2 * (img.getHeight() / ANIM_LINES), img.getWidth() / framesCount, img.getHeight() / ANIM_LINES);
                break;
        }

        if (getObjectDirection() != Direction.RIGHT) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-img.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            img = op.filter(img, null);
        }

        
        return img;
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        nextFrame();        
        if(!isKilled() || (killedAgoInFrames < KILLED_FRAMES_LIMIT && killedAgoInFrames % 2 == 0)) {
            g.drawImage(getFrame(gameResources), position.getX(), position.getY(), width, height, null);
        }
    }
}
