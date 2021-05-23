package com.touhou625.patternhandler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.touhou625.figure.Figure;
import com.touhou625.math.Matrix;
import com.touhou625.projectile.Bullet;
import com.touhou625.projectile.Projectile;

import java.util.ArrayList;
import java.util.Iterator;

public class PatternHandler {

    private static final float FACTOR_SHURIKEN = 1.5f;
    private static final float FACTOR_SELF_BULLET = 2f;

    private int timing;
    private final int borderWidth;
    private final int borderHeight;

    private boolean flowerHalted;

    private final double[] initialDirection = {0.0, -4.0};

    private final ArrayList<Projectile> projectileList = new ArrayList<>();
    private final Matrix matrix;

    public PatternHandler(int wallWidth, int wallHeight) {
        borderWidth = wallWidth;
        borderHeight = wallHeight;
        timing = 0;
        flowerHalted = false;
        matrix = new Matrix();
    }

    public void generateProjectile(int xOrigin, int yOrigin, int id, float angle) {

        matrix.initalizeMatrix(angle);
        double[] direction = matrix.rotation(initialDirection);

        Bullet b = new Bullet(xOrigin, yOrigin, id, FACTOR_SHURIKEN, angle, direction);
        projectileList.add(b);
    }

    public void generateProjectile(int xOrigin, int yOrigin, TextureRegion sprite, float angle, double[] initialDirection) {

        matrix.initalizeMatrix(angle);
        double[] direction = matrix.rotation(initialDirection);
        Bullet bullet = new Bullet(xOrigin, yOrigin, FACTOR_SELF_BULLET, sprite, angle, direction);
        projectileList.add(bullet);
    }

    public void generateFlowerPart(int xOrigin, int yOrigin, float angleFlower) {
        generateProjectile(xOrigin, yOrigin, 65, angleFlower);
    }

    public void generateFlower(int xOrigin, int yOrigin) {
        float angle = 10.0f;
        int number = (int) (360.0f / angle);
        if (!flowerHalted) {
            for (int i = 0; i < number; i++) {
                generateFlowerPart(xOrigin, yOrigin, angle * i);
            }
            flowerHalted = true;
        }
    }

    public void drawProjectiles(SpriteBatch g) {

        for (Iterator<Projectile> iterator = projectileList.iterator(); iterator.hasNext(); ) {
            Projectile p = iterator.next();

            if (collisionWall(p)) {
                iterator.remove();
                continue;
            }
            p.move();
            p.draw(g);
        }
    }

    public void renderHitbox(ShapeRenderer sr) {
        for (Projectile p : projectileList) {
            p.renderHitbox(sr);
        }
    }

    public boolean collisionWallWidth(Projectile p) {
        return p.getX() < 0 || p.getX() > borderWidth;
    }

    public boolean collisionWallHeight(Projectile p) {
        return p.getY() < 0 || p.getY() > borderHeight;
    }

    public boolean collisionWall(Projectile p) {
        return collisionWallWidth(p) || collisionWallHeight(p);
    }

    public void handleCollisionFigure(ArrayList<Figure> figureList) {
        // TODO a opti d'urgence
        for (Figure f : figureList) {
            for (Projectile p : projectileList) {
                if (p.isHarmful() && f.isTooClose(p) && !f.getInvinsible()) {
                    System.out.println("touched\n");
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
