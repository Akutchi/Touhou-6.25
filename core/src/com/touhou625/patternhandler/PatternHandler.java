package com.touhou625.patternhandler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.touhou625.figure.Figure;
import com.touhou625.math.Matrix;
import com.touhou625.projectile.Bullet;
import com.touhou625.projectile.Projectile;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.Iterator;

public class PatternHandler {

    private static final float FACTOR_SHURIKEN = 1.5f;
    private static final float FACTOR_SELF_BULLET = 2f;

    private int borderX;
    private int borderY;
    private int borderXEnd;
    private int borderYEnd;

    private boolean flowerHalted;
    private boolean spiralHalted;

    private final double[] initialDirection = {0.0, -4.0};

    private final ArrayList<Projectile> projectileList = new ArrayList<>();
    private final Matrix matrix;

    public PatternHandler() {
        flowerHalted = false;
        spiralHalted = false;
        matrix = new Matrix();
    }

    private double[] spiralCoordinate(int xOrigin, int yOrigin, float angle) {
        double[] coords = new double[2];

        double k = 3;

        double angleInRadian = angle * Math.PI / 180;

        coords[0] = xOrigin + angle / k * cos(angleInRadian);
        coords[1] = yOrigin + angle / k * sin(angleInRadian);

        return coords;

    }

    public void generateProjectile(double xOrigin, double yOrigin, int id, float angle) {

        matrix.initalizeMatrix(angle);
        double[] direction = matrix.rotation(initialDirection);

        Bullet b = new Bullet(xOrigin, yOrigin, id, FACTOR_SHURIKEN, angle, direction);
        projectileList.add(b);
    }

    public void generateProjectile(double xOrigin, double yOrigin, TextureRegion sprite, float angle, double[] initialDirection) {

        matrix.initalizeMatrix(angle);
        double[] direction = matrix.rotation(initialDirection);

        Bullet bullet = new Bullet(xOrigin, yOrigin, FACTOR_SELF_BULLET, sprite, angle, direction);
        projectileList.add(bullet);
    }

    private void generatePatternPart(double xOrigin, double yOrigin, float angleFlower) {
        generateProjectile(xOrigin, yOrigin, 65, angleFlower);
    }

    public void generateCircle(int xOrigin, int yOrigin) {
        float angle = 5.0f;
        int number = (int) (360 / angle);
        if (!flowerHalted) {
            for (int i = 0; i < number; i++) {
                generatePatternPart(xOrigin, yOrigin, angle * i);
            }
            flowerHalted = true;
        }
    }

    public void generateSpiral(int xOrigin, int yOrigin) {
        float angle = 10.0f;
        int number = 300;
        if (!spiralHalted) {
            for (int i = 0; i < number; i++) {
                double[] coords = spiralCoordinate(xOrigin, yOrigin, angle * i);
                generatePatternPart(coords[0], coords[1], angle * i);
            }
            spiralHalted = true;
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
        return p.getX() < borderX || p.getX() > borderXEnd;
    }

    public boolean collisionWallHeight(Projectile p) {
        return p.getY() < borderY || p.getY() > borderYEnd;
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

    public void getBorder(int xoffsetBackground, int yoffsetBackground, int wallWidth, int wallHeight) {
        borderX = xoffsetBackground;
        borderY = yoffsetBackground;
        borderXEnd = xoffsetBackground + wallWidth;
        borderYEnd = yoffsetBackground + wallHeight;

    }
}
