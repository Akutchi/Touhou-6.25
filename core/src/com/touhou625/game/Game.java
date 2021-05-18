package com.touhou625.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.touhou625.figure.Figure;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.touhou625.keyboard.AttackKeyboard;
import com.touhou625.keyboard.HorizontalKeyboard;
import com.touhou625.keyboard.VerticalKeyboard;
import com.touhou625.patternhandler.PatternHandler;
import com.touhou625.projectile.Projectile;

import java.util.ArrayList;


public class Game extends ApplicationAdapter {

    private static final int FACTOR = 3;
    private static final int SIDEBARWIDTH = 255 * FACTOR;
    private static final int SIDEBARHEIGHT = 255 * FACTOR;
    private static final int BACKGROUNDWIDTH = 460;
    private static final int BACKGROUNDHEIGHT = 765;

    private final ArrayList<Figure> figureList = new ArrayList<>();

    private Texture rSideBar;
    private Texture rBackgroundStage1;

    private TextureRegion sideBar;

    private SpriteBatch graphics;
    private SpriteBatch graphicsFigure;
    private SpriteBatch graphicsProjectile;
    private ShapeRenderer sr;

    private Figure marisa;
    private PatternHandler handler;
    private HorizontalKeyboard horizontalKeyboard;
    private VerticalKeyboard verticalKeyboard;
    private AttackKeyboard attackKeyboard;


    @Override
    public void create() {
        rSideBar = new Texture("sideBar.png");
        rBackgroundStage1 = new Texture("Th06MistyLake.jpg");
        sideBar = new TextureRegion(rSideBar, 542, 4, 255, 255);
        graphics = new SpriteBatch();
        graphicsFigure = new SpriteBatch();
        graphicsProjectile = new SpriteBatch();
        sr = new ShapeRenderer();

        marisa = new Figure("Marisa.png", 0);
        marisa.setBorderWidth(BACKGROUNDWIDTH - 15);
        marisa.setBorderHeight(BACKGROUNDHEIGHT - 20);
        figureList.add(marisa);

        horizontalKeyboard = new HorizontalKeyboard(marisa);
        verticalKeyboard = new VerticalKeyboard(marisa);
        attackKeyboard = new AttackKeyboard(marisa, BACKGROUNDWIDTH - 15, BACKGROUNDHEIGHT - 20);

        handler = new PatternHandler(BACKGROUNDWIDTH - 15, BACKGROUNDHEIGHT - 20);

        Gdx.gl.glLineWidth(1);
    }


    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        handler.generateFlower(230, 500);

        graphics.begin();

        graphics.draw(sideBar, 0, 0, SIDEBARWIDTH, SIDEBARHEIGHT);
        graphics.draw(rBackgroundStage1, 0, 0, BACKGROUNDWIDTH, BACKGROUNDHEIGHT);

        graphics.end();

        horizontalKeyboard.horizontalKeyboardHandling();
        verticalKeyboard.verticalKeyboardHandling();
        attackKeyboard.attackKeyboardHandling();


        graphicsFigure.begin();

        marisa.draw(graphicsFigure);

        graphicsFigure.end();


        graphicsProjectile.begin();

        //handler.drawProjectiles(graphicsProjectile);
        //handler.handleCollisionFigure(figureList);
        attackKeyboard.drawMissiles(graphicsProjectile);

        graphicsProjectile.end();

        marisa.renderHitbox(sr);
    }

    @Override
    public void dispose() {
        rSideBar.dispose();
        rBackgroundStage1.dispose();
        handler.dispose();
        marisa.dispose();
        graphics.dispose();
        graphicsFigure.dispose();
    }
}
