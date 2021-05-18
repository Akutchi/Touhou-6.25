package com.touhou625.projectile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet extends Projectile {

    public Bullet(int xStart, int yStart, int id, double angle, double[] direction) {
        super(xStart, yStart, id, angle, direction);
    }

    public Bullet(int xStart, int yStart, TextureRegion sprite, double angle, double[] direction) {
        super(xStart, yStart, sprite, angle, direction);
    }

    @Override
    public boolean isHarmful() {
        return false;
    }

}
