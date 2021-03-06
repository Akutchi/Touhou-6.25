package com.touhou625.projectile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Projectile {

    /**
     * Projectile ripped by GabrielWoj :
     * https://www.spriters-resource.com/pc_computer/touhoukoumakyoutheembodimentofscarletdevil/
     */

    private static final int START_POINT_X = 332;
    private static final int START_POINT_Y = 27;
    private static final int START_BULLET_X = 332;
    private static final int START_BULLET_Y = 45;
    private static final int START_BIGBULLET_X = 332;
    private static final int START_BIGBULLET_Y = 161;

    private static final int WIDTH_COLUMN_1 = 16;
    private static final int HEIGHT_COLUMN_1 = 16;
    private static final int WIDTH_COLUMN_3 = 32;
    private static final int HEIGHT_COLUMN_3 = 32;

    private static final int TRANSLATION = 1;

    private final int circleRadius;
    private final int width;
    private final int height;

    private double x;
    private double y;
    private double xCenter;
    private double yCenter;
    
    private final double[] direction;

    private final float factor;
    private final float angle;

    private final Texture rSprite;
    private final TextureRegion sprite;


    protected Projectile(double xStart, double yStart, int id, float zoom, float angleOfRotation, double[] directionProjectile) {

        x = xStart;
        y = yStart;

        angle = angleOfRotation;
        factor = zoom;
        direction = directionProjectile;

        rSprite = new Texture("projectile.png");

        /*
        The principle of this block is to get a sprite based on an id.
        for the points, each sprite is numbered from 0 to 8
        for the bullets, from 16 to 96
        for the big bullets, from 112 to 352
         */
        int row = id / 16 == 0 ? 0 : id / 16;
        int column = row == 0 ? id : id - 16 * row;

        if (isBetween(id, 16, 96)) {

            sprite = new TextureRegion(rSprite, START_BULLET_X + WIDTH_COLUMN_1 * (column - 1),
                    START_BULLET_Y + HEIGHT_COLUMN_1 * row + row * TRANSLATION, WIDTH_COLUMN_1, HEIGHT_COLUMN_1);
            circleRadius = 7;

        } else if (isBetween(id, 0, 8)) {

            sprite = new TextureRegion(rSprite, START_POINT_X + WIDTH_COLUMN_1 * (column - 1),
                    START_POINT_Y + HEIGHT_COLUMN_1 * row + row * TRANSLATION, WIDTH_COLUMN_1, HEIGHT_COLUMN_1);
            circleRadius = 15;

        } else {

            sprite = new TextureRegion(rSprite, START_BIGBULLET_X + WIDTH_COLUMN_3 * column,
                    START_BIGBULLET_Y + HEIGHT_COLUMN_3 * (row - 7) + 1, WIDTH_COLUMN_3, HEIGHT_COLUMN_3);
            circleRadius = 10;
        }

        if (isBetween(column, 112, 352)) {
            width = WIDTH_COLUMN_3;
            height = HEIGHT_COLUMN_3;

        } else {
            width = WIDTH_COLUMN_1;
            height = HEIGHT_COLUMN_1;
        }

        xCenter = x + width / 2.0;
        yCenter = y + height / 2.0;
    }

    protected Projectile(double xStart, double yStart, TextureRegion spriteProjectile, float zoom, float angleOfRotation, double[] directionProjectile) {

        x = xStart;
        y = yStart;

        angle = angleOfRotation;
        factor = zoom;
        direction = directionProjectile;
        circleRadius = 10;

        rSprite = null;
        sprite = spriteProjectile;

        width = sprite.getRegionWidth();
        height = sprite.getRegionHeight();

        xCenter = x + width / 2.0;
        yCenter = y + height / 2.0;
    }

    public void move() {

        x += direction[0];
        y += direction[1];

        xCenter = x + width / 2.0;
        yCenter = y + height / 2.0;

    }

    public void draw(SpriteBatch g) {
        g.draw(getSprite(), (float) x, (float) y, width / 2.0f, height / 2.0f, getWidth(), getHeight(),
                factor, factor, angle);
    }

    public void renderHitbox(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.GREEN);
        sr.line((float) getxCenter() - circleRadius, (float) getyCenter(), (float) getxCenter() + circleRadius,
                (float) getyCenter());
        sr.line((float) getxCenter(), (float) getyCenter() - circleRadius, (float) getxCenter(),
                (float) getyCenter() + circleRadius);
        sr.circle((float) getxCenter(), (float) getyCenter(), circleRadius);
        sr.end();
    }

    public void dispose() {
        rSprite.dispose();
    }

    public boolean isBetween(int x, int start, int end) {
        return x >= start && x <= end;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getxCenter() {
        return xCenter;
    }

    public double getyCenter() {
        return yCenter;
    }

    public int getcircleRadius() {
        return circleRadius;
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public abstract boolean isHarmful();
}
