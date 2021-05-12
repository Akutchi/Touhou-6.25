package com.touhou625.figure;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.touhou625.projectile.Projectile;

import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Figure {

    private static final int WIDTH = 30;
    private static final int HEIGHT = 35;
    private static final int XOFFSET_MOTIONLESS = 5;
    private static final int XOFFSET_MOTION = 171;
    private static final int YOFFSET = 14;
    private static final int SCALEUP = 17;
    private static final int TRANSLATION = 2;

    private static final float STEP = 0.2f;


    private int x;
    private int y;
    private int xCenter;
    private int yCenter;
    private int borderWidth;
    private int borderHeight;
    private int indexSprite;
    private int temporisationSprite;
    private int signForAlphaChannel;
    private int numberOfBlink;
    private final int circleRadius;
    private final int hitboxWidth;
    private final int hitboxHeight;

    private float temporisationAlpha;

    private boolean invinsible;

    private final Texture rawSprite;
    private final Texture rawSpriteReversed;
    private final Texture rHitbox;
    private final TextureRegion hitbox;

    private ArrayList<TextureRegion> currentSpriteList;
    private final ArrayList<TextureRegion> spriteMotionlessList = new ArrayList<>();
    private final ArrayList<TextureRegion> spriteMotionLeftList = new ArrayList<>();
    private final ArrayList<TextureRegion> spriteMotionRightList = new ArrayList<>();

    public Figure(String pathToImage, int cRadius) {

        x = 230;
        y = 180;
        xCenter = x + WIDTH / 2;
        yCenter = y + HEIGHT / 2;
        circleRadius = cRadius;
        indexSprite = 0;

        temporisationAlpha = 1.0f;
        signForAlphaChannel = 1;
        numberOfBlink = 0;

        invinsible = false;

        rawSprite = new Texture(pathToImage);
        rawSpriteReversed = new Texture(pathToImage.substring(0, pathToImage.length() - 4) + "_reversed.png");

        TextureRegion spriteMotionless;
        TextureRegion spriteMotionLeft;
        TextureRegion spriteMotionRight;

        for (int i = 0; i < 5; i++) {
            spriteMotionless = new TextureRegion(rawSprite, i * (WIDTH + TRANSLATION) + XOFFSET_MOTIONLESS, YOFFSET,
                    WIDTH, HEIGHT);
            spriteMotionLeft = new TextureRegion(rawSprite, i * (WIDTH + TRANSLATION) + XOFFSET_MOTION, YOFFSET,
                    WIDTH, HEIGHT);
            spriteMotionRight = new TextureRegion(rawSpriteReversed, i * (WIDTH + TRANSLATION) + XOFFSET_MOTIONLESS,
                    YOFFSET, WIDTH, HEIGHT);
            spriteMotionlessList.add(spriteMotionless);
            spriteMotionLeftList.add(spriteMotionLeft);
            spriteMotionRightList.add(spriteMotionRight);
        }

        currentSpriteList = spriteMotionlessList;

        rHitbox = new Texture("hitbox.png");
        hitbox = new TextureRegion(rHitbox);

        hitboxWidth = hitbox.getRegionWidth();
        hitboxHeight = hitbox.getRegionHeight();
    }

    public void move(int dx, int dy) {

        if (0 < x + dx && x + dx + WIDTH < borderWidth) {
            x += dx;
        }

        if (0 < y + dy && y + dy + HEIGHT < borderHeight) {
            y += dy;
        }

        xCenter = x + WIDTH / 2;
        yCenter = y + HEIGHT / 2;
    }

    public boolean collision(Projectile p) {

        double dx = pow(p.getxCenter() - xCenter * 1.f, 2);
        double dy = pow(p.getyCenter() - yCenter * 1.f, 2);

        return sqrt(dx + dy) < circleRadius + p.getcircleRadius();
    }

    public void draw(SpriteBatch g) {

        Color color = g.getColor();

        if (invinsible) {
            g.setColor(color.r, color.g, color.b, modifyAlpha());
        } else {
            g.setColor(color.r, color.g, color.b, 1f);
        }

        g.draw(getSprite(), x, y, WIDTH + SCALEUP * 1f, HEIGHT + SCALEUP * 1f);
        incrementIndexSprite();
    }

    public void renderHitbox(SpriteBatch g) {
        g.draw(hitbox, xCenter - hitboxWidth / 2f, yCenter - hitboxHeight / 2f, circleRadius, circleRadius);
    }

    public void dispose() {
        rawSprite.dispose();
        rawSpriteReversed.dispose();
        rHitbox.dispose();
    }

    public void setBorderWidth(int border) {
        borderWidth = border;
    }

    public void setBorderHeight(int border) {
        borderHeight = border;
    }

    public float modifyAlpha() {

        // 1 blink per 5 "second"
        if (numberOfBlink >= 15) {
            numberOfBlink = 0;
            invinsible = false;
            return 1.0f;
        }

        if (temporisationAlpha < 0f) {
            signForAlphaChannel = -1;
        } else if (temporisationAlpha >= 1f) {
            signForAlphaChannel = 1;
            numberOfBlink++;
        }

        if (temporisationSprite % 5 == 0f) {
            temporisationAlpha = temporisationAlpha - STEP * signForAlphaChannel;
        }

        return temporisationAlpha;
    }

    public void blink() {
        invinsible = true;
    }

    public void incrementIndexSprite() {

        if (temporisationSprite == 10) {
            indexSprite = (indexSprite + 1) % currentSpriteList.size();
            temporisationSprite = 0;
        }
        temporisationSprite++;
    }

    public void changeCurrentSpriteListToMotion(int direction) {

        switch (direction) {
            case -1:
                currentSpriteList = spriteMotionLeftList;
                break;
            case 1:
                currentSpriteList = spriteMotionRightList;
                break;
            default:
                currentSpriteList = spriteMotionlessList;
                break;
        }
    }

    public TextureRegion getSprite() {
        return currentSpriteList.get(indexSprite);
    }

    public boolean getInvinsible() {
        return invinsible;
    }
}
