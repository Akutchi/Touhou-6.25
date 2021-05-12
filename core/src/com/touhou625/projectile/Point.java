package com.touhou625.projectile;

public class Point extends Projectile {

    public Point(int xStart, int yStart, int id) {
        super(xStart, yStart, id);
        isABullet = false;
    }

}
