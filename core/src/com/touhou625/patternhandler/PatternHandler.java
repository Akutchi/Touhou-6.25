package com.touhou625.patternhandler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.touhou625.figure.Figure;
import com.touhou625.math.Matrix;
import com.touhou625.projectile.Bullet;
import com.touhou625.projectile.Projectile;

import java.util.ArrayList;

public class PatternHandler {

    private int x;
    private int y;

    private final ArrayList<Projectile> projectileList = new ArrayList<>();
    private final double[] initialDirection = {0.0, -1.0};
    private final Matrix matrix;

    public PatternHandler(int xOrigin, int yOrigin) {
        matrix = new Matrix();
        x = xOrigin;
        y = yOrigin;

    }

    public void generateProjectile(int id, double angle) {

        matrix.initalizeMatrix(angle);
        double[] direction = matrix.rotation(initialDirection);

        projectileList.add(new Bullet(x, y, id, angle, direction));
    }

    public void generateFlower() {

        for (int i = 0; i < 10; i++) {
            generateProjectile(65, -(360 / 10f) * i);
        }
    }

    public void drawProjectiles(SpriteBatch g) {

        for (Projectile p : projectileList) {
            p.move();
            p.draw(g);
            p.renderHitbox(g);
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
}
