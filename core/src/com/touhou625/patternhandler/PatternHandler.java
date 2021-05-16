package com.touhou625.patternhandler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.touhou625.figure.Figure;
import com.touhou625.math.Matrix;
import com.touhou625.projectile.Bullet;
import com.touhou625.projectile.Projectile;

import java.util.ArrayList;

public class PatternHandler {

    private final double[] initialDirection = {0.0, 1.0};
    private final ArrayList<Projectile> projectileList = new ArrayList<>();
    private final Matrix matrix;

    public PatternHandler() {
        matrix = new Matrix();
    }

    public void generateProjectile(int xOrigin, int yOrigin, int id, double angle) {

        matrix.initalizeMatrix(angle);
        double[] direction = matrix.rotation(initialDirection);

        Bullet b = new Bullet(xOrigin, yOrigin, id, angle, direction);
        projectileList.add(b);
    }

    public void generateProjectile(int xOrigin, int yOrigin, int id, double angle, double[] initialDirection) {

        matrix.initalizeMatrix(angle);
        double[] direction = matrix.rotation(initialDirection);
        Bullet bullet = new Bullet(xOrigin, yOrigin, id, angle, direction);
        projectileList.add(bullet);
    }

    public void generateFlower(int xOrigin, int yOrigin) {

        for (int i = 0; i < 10; i++) {
            generateProjectile(xOrigin, yOrigin, 65, -(360 / 10f) * i);
        }
    }

    public void drawProjectiles(SpriteBatch g) {

        for (Projectile p : projectileList) {
            p.move();
            p.draw(g);
        }
    }

    public void handleCollision(ArrayList<Figure> figureList) {
        // TODO a opti d'urgence
        for (Figure f : figureList) {
            for (Projectile p : projectileList) {
                if (p.isHarmful() && !f.isNotTooClose(p) && !f.getInvinsible()) {
                    f.blink();
                }
            }
        }
    }

    public void dispose() {
        for (Projectile p : projectileList) {
            p.dispose();
        }
    }

    public ArrayList<Projectile> getProjectileList() {
        return projectileList;
    }
}
