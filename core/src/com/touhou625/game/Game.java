package com.touhou625.game;

import com.touhou625.figure.Figure;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.touhou625.keyboard.HorizontalKeyboard;
import com.touhou625.keyboard.VerticalKeyboard;
import com.touhou625.projectile.Bullet;
import com.touhou625.projectile.Projectile;

import java.util.ArrayList;


public class Game extends ApplicationAdapter {

    private static final int FACTOR = 3;
    private static final int SIDEBARWIDTH = 255 * FACTOR;
    private static final int SIDEBARHEIGHT = 255 * FACTOR;
    private static final int BACKGROUNDWIDTH = 460;
    private static final int BACKGROUNDHEIGHT = 765;

    private ArrayList<Figure> figureList = new ArrayList<>();
    private ArrayList<Projectile> projectileList = new ArrayList<>();

    private Texture rSideBar;
    private Texture rBackgroundStage1;

    private TextureRegion sideBar;

    private SpriteBatch graphics;
    private SpriteBatch graphicsFigure;
    private SpriteBatch graphicsProjectile;

    private Figure marisa;
    private HorizontalKeyboard horizontalKeyboard;
    private VerticalKeyboard verticalKeyboard;


    public void drawProjectiles(SpriteBatch g) {

        for (Projectile p : projectileList) {
            p.move(0, -1);
            p.draw(g);
            p.renderHitbox(g);
        }
    }

    @Override
    public void create() {
        rSideBar = new Texture("sideBar.png");
        rBackgroundStage1 = new Texture("Th06MistyLake.jpg");
        sideBar = new TextureRegion(rSideBar, 542, 4, 255, 255);
        graphics = new SpriteBatch();
        graphicsFigure = new SpriteBatch();
        graphicsProjectile = new SpriteBatch();

        marisa = new Figure("Marisa.png", 5);
        marisa.setBorderWidth(BACKGROUNDWIDTH - 15);
        marisa.setBorderHeight(BACKGROUNDHEIGHT - 20);

        Bullet b2 = new Bullet(250, 460, 65);
        Bullet b3 = new Bullet(220, 460, 65);

        figureList.add(marisa);
        projectileList.add(b2);
        projectileList.add(b3);

        horizontalKeyboard = new HorizontalKeyboard(marisa);
        verticalKeyboard = new VerticalKeyboard(marisa);
    }


    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        graphics.begin();

        graphics.draw(sideBar, 0, 0, SIDEBARWIDTH, SIDEBARHEIGHT);
        graphics.draw(rBackgroundStage1, 0, 0, BACKGROUNDWIDTH, BACKGROUNDHEIGHT);

        graphics.end();

        horizontalKeyboard.horizontalKeyboardHandling();
        verticalKeyboard.verticalKeyboardHandling();


        graphicsFigure.begin();

        marisa.draw(graphicsFigure);
        marisa.renderHitbox(graphicsFigure);

        graphicsFigure.end();


        graphicsProjectile.begin();

        drawProjectiles(graphicsProjectile);

        // TODO a opti d'urgence
        for (Figure f : figureList) {
            for (Projectile p : projectileList) {
                if (p.isBullet() && !f.isNotTooClose(p) && !f.getInvinsible()) {
                    f.blink();
                }
            }
        }

        graphicsProjectile.end();
    }

    @Override
    public void dispose() {
        rSideBar.dispose();
        rBackgroundStage1.dispose();

        for (Projectile p : projectileList) {
            p.dispose();
        }
        marisa.dispose();
        graphics.dispose();
        graphicsFigure.dispose();
    }
}
