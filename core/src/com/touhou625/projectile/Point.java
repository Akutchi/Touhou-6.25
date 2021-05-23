package com.touhou625.projectile;

public class Point extends Projectile {

    public Point(int xStart, int yStart, int id, float factor, float angle, double[] direction) {
        super(xStart, yStart, id, factor, angle, direction);
    }

    @Override
    public boolean isHarmful() {
        return false;
    }
}
