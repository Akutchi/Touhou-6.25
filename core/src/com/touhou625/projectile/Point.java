package com.touhou625.projectile;

public class Point extends Projectile {

    public Point(int xStart, int yStart, int id, double angle, double[] direction) {
        super(xStart, yStart, id, angle, direction);
    }

    @Override
    public boolean isHarmful() {
        return true;
    }
}
