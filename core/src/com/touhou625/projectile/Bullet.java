package com.touhou625.projectile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet extends Projectile {

    public Bullet(int xStart, int yStart, int id, float factor, float angle, double[] direction) {
        super(xStart, yStart, id, factor, angle, direction);
    }

    public Bullet(int xStart, int yStart, float factor, TextureRegion sprite, float angle, double[] direction) {
        super(xStart, yStart, sprite, factor, angle, direction);
    }

    @Override
    public boolean isHarmful() {
        return true;
    }

}
