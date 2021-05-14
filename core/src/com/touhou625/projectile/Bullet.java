package com.touhou625.projectile;

public class Bullet extends Projectile {

    public Bullet(int xStart, int yStart, int id, double angle, double[] direction) {
        super(xStart, yStart, id, angle, direction);
    }

    @Override
    public boolean isHarmful() {
        return false;
    }

}
