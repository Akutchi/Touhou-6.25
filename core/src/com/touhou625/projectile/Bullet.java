package com.touhou625.projectile;

public class Bullet extends Projectile {

    public Bullet(int xStart, int yStart, int id) {
        super(xStart, yStart, id);
        isABullet = true;
    }

}
