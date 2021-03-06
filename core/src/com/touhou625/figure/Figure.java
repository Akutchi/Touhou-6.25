package com.touhou625.figure;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.touhou625.projectile.Projectile;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Figure {

    private static final int WIDTH = 24;
    private static final int HEIGHT = 35;
    private static final int XOFFSET_MOTIONLESS = 11;
    private static final int XOFFSET_MOTION = 171;
    private static final int YOFFSET = 14;
    private static final int SCALEUP = 17;
    private static final int TRANSLATION = 8;

    private static final float STEP = 0.2f;
    private static final float SCALEUPPROJECTILE = 17f;


    private int x;
    private int y;
    private int xCenter;
    private int yCenter;
    private int borderX;
    private int borderY;
    private int borderXEnd;
    private int borderYEnd;
    private int indexSprite;
    private int temporisationSprite;
    private int signForAlphaChannel;
    private int numberOfBlink;

    private final float circleRadius;

    private float temporisationAlpha;

    private boolean invinsible;

    private final Texture rawSprite;
    private final Texture rawSpriteReversed;

    private final Texture rawProjectileSprite = new Texture("PatchouliProjectile.png");
    private final TextureRegion projectileSprite = new TextureRegion(rawProjectileSprite, rawProjectileSprite.getWidth(),
            rawProjectileSprite.getHeight(), rawProjectileSprite.getWidth() * SCALEUPPROJECTILE,
            rawProjectileSprite.getHeight() * SCALEUPPROJECTILE);

    private List<TextureRegion> currentSpriteList;
    private final List<TextureRegion> spriteMotionlessList = new ArrayList<>();
    private final List<TextureRegion> spriteMotionLeftList = new ArrayList<>();
    private final List<TextureRegion> spriteMotionRightList = new ArrayList<>();

    public Figure(String pathToImage, int cRadius) {

        x = 930;
        y = 280;
        xCenter = x + (WIDTH + SCALEUP) / 2;
        yCenter = y + (HEIGHT + SCALEUP) / 2;
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
            spriteMotionRight = new TextureRegion(rawSprite, i * (WIDTH + TRANSLATION) + XOFFSET_MOTION,
                    YOFFSET, WIDTH, HEIGHT);
            spriteMotionlessList.add(spriteMotionless);
            spriteMotionLeftList.add(spriteMotionLeft);
            spriteMotionRight.flip(true, false);
            spriteMotionRightList.add(spriteMotionRight);
        }

        currentSpriteList = spriteMotionlessList;
    }

    public void move(int dx, int dy) {

        if (borderX < (x + dx) && (x + dx + WIDTH + SCALEUP) < borderXEnd) {
            x += dx;
        }

        if (borderY < (y + dy) && (y + dy + HEIGHT + SCALEUP) < borderYEnd) {
            y += dy;
        }

        xCenter = x + (WIDTH + SCALEUP) / 2;
        yCenter = y + (HEIGHT + SCALEUP) / 2;
    }

    public boolean isTooClose(Projectile p) {

        double dx = pow((p.getxCenter() - xCenter) * 1.f, 2);
        double dy = pow((p.getyCenter() - yCenter) * 1.f, 2);

        return sqrt(dx + dy) < (circleRadius + p.getcircleRadius());
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

    public void renderHitbox(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.GREEN);
        sr.line(getxCenter() - circleRadius, getyCenter(), getxCenter() + circleRadius, getyCenter());
        sr.line(getxCenter(), getyCenter() - circleRadius, getxCenter(), getyCenter() + circleRadius);
        sr.circle(getxCenter(), getyCenter(), circleRadius);
        sr.end();
    }

    public void dispose() {
        rawSprite.dispose();
        rawSpriteReversed.dispose();
    }

    public int getxCenter() {
        return xCenter;
    }

    public int getyCenter() {
        return yCenter;
    }

    public void setBorder(int x, int y, int width, int height) {
        borderX = x;
        borderY = y;
        borderXEnd = x + width;
        borderYEnd = y + height;
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
