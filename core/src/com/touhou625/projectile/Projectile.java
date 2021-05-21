package com.touhou625.projectile;

import com.badlogic.gdx.Gdx;
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

    private static final int FACTOR = 2;

    private int x;
    private int y;
    private int xCenter;
    private int yCenter;
    private final int circleRadius;
    private final int width;
    private final int height;
    private final double[] direction;

    private final double angle;

    private final Texture rSprite;
    private final Texture rHitbox;
    private final TextureRegion sprite;
    private final TextureRegion hitbox;

    protected Projectile(int xStart, int yStart, int id, double angleOfRotation, double[] directionProjectile) {

        x = xStart;
        y = yStart;

        angle = angleOfRotation;
        direction = directionProjectile;

        rHitbox = new Texture("hitbox.png");
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
                    START_BULLET_Y + HEIGHT_COLUMN_1 * row + row + 1, WIDTH_COLUMN_1, HEIGHT_COLUMN_1);
            circleRadius = 15;

        } else if (isBetween(id, 0, 8)) {

            sprite = new TextureRegion(rSprite, START_POINT_X + WIDTH_COLUMN_1 * (column - 1),
                    START_POINT_Y + HEIGHT_COLUMN_1 * row, WIDTH_COLUMN_1, HEIGHT_COLUMN_1);

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

        hitbox = new TextureRegion(rHitbox);

        int hitboxWidth = hitbox.getRegionWidth();
        int hitboxHeight = hitbox.getRegionHeight();

        xCenter = x + width / 2;
        yCenter = y + height / 2;
    }

    protected Projectile(int xStart, int yStart, TextureRegion spriteProjectile, double angleOfRotation, double[] directionProjectile) {

        x = xStart;
        y = yStart;

        angle = angleOfRotation;
        direction = directionProjectile;
        circleRadius = 20;

        rSprite = null;
        sprite = spriteProjectile;

        rHitbox = new Texture("hitbox.png");

        width = sprite.getRegionWidth();
        height = sprite.getRegionHeight();

        hitbox = new TextureRegion(rHitbox);

        int hitboxWidth = hitbox.getRegionWidth();
        int hitboxHeight = hitbox.getRegionHeight();

        xCenter = x + width / 2;
        yCenter = y + height / 2;
    }

    public void move() {

        xCenter = x + width / 2;
        yCenter = y + height / 2;

        x += direction[0];
        y += direction[1];

    }

    public void draw(SpriteBatch g) {
        g.draw(getSprite(), getX(), getY(), 0, 0, getWidth(), getHeight(), FACTOR, FACTOR, (float) angle);
    }

    public void renderHitbox(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.GREEN);
        sr.line(getX() - 5.0f, getY(), getX() + 5.0f, getY());
        sr.line(getX(), getY() - 5.0f, getX(), getY() + 5.0f);
        sr.circle(getX(), getY(), circleRadius);
        sr.end();
    }

    public void dispose() {
        rSprite.dispose();
        rHitbox.dispose();
    }

    public boolean isBetween(int x, int start, int end) {
        return x >= start && x <= end;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getxCenter() {
        return xCenter;
    }

    public int getyCenter() {
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
