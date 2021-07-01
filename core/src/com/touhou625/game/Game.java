package com.touhou625.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.touhou625.dialogue.ExpressionParser;
import com.touhou625.dialogue.TextParser;
import com.touhou625.figure.Figure;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.touhou625.keyboard.AttackKeyboard;
import com.touhou625.keyboard.DialogueKeyboard;
import com.touhou625.keyboard.HorizontalKeyboard;
import com.touhou625.keyboard.VerticalKeyboard;
import com.touhou625.patternhandler.PatternHandler;
import com.touhou625.scene.Stage;

import java.beans.Expression;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Game extends ApplicationAdapter {

    private static final int GAMEWIDTH = 1920;
    private static final String PATH = "./core/assets/";

    private final ArrayList<Figure> figureList = new ArrayList<>();
    private final ArrayList<PatternHandler> handlerList = new ArrayList<>();

    private SpriteBatch graphics;
    private SpriteBatch graphicsFigure;
    private SpriteBatch graphicsProjectile;
    private SpriteBatch graphicsDialogue;
    private ShapeRenderer sr;

    private Stage stage1;
    private Figure marisa;
    private PatternHandler handler;
    private HorizontalKeyboard horizontalKeyboard;
    private VerticalKeyboard verticalKeyboard;
    private AttackKeyboard attackKeyboard;
    private DialogueKeyboard dialogueKeyboard;

    private TextParser textParserStage1;

    @Override
    public void create() {
        graphics = new SpriteBatch();
        graphicsFigure = new SpriteBatch();
        graphicsProjectile = new SpriteBatch();
        graphicsDialogue = new SpriteBatch();
        sr = new ShapeRenderer();

        try {
            textParserStage1 = new TextParser(PATH + "Text_Stage1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> stage1Lines = textParserStage1.parseText();

        stage1 = new Stage("Library.jpg", "Introduction", stage1Lines, GAMEWIDTH);

        marisa = new Figure("Marisa.png", 5);
        marisa.setBorder((GAMEWIDTH - stage1.getWidth()) / 2, stage1.getY(), stage1.getWidth(), stage1.getHeight());
        figureList.add(marisa);

        horizontalKeyboard = new HorizontalKeyboard(marisa);
        verticalKeyboard = new VerticalKeyboard(marisa);
        attackKeyboard = new AttackKeyboard(marisa, stage1.getX(), stage1.getY(), stage1.getWidth(), stage1.getHeight());
        dialogueKeyboard = new DialogueKeyboard();

        handler = new PatternHandler();
        handler.getBorder(stage1.getX(), stage1.getY(), stage1.getWidth(), stage1.getHeight());
        handlerList.add(handler);

        Gdx.gl.glLineWidth(1);
    }

    public void dialogueRendering() {
        stage1.setChangeDialogue(dialogueKeyboard.isKeyPressed());
        stage1.drawDialogue(graphicsDialogue, sr);
    }

    public void battleRendering() {
        horizontalKeyboard.horizontalKeyboardHandling();
        verticalKeyboard.verticalKeyboardHandling();
        attackKeyboard.attackKeyboardHandling();

        graphicsFigure.begin();
        marisa.draw(graphicsFigure);
        graphicsFigure.end();

        graphicsProjectile.begin();
        for (PatternHandler h : handlerList) {
            h.drawProjectiles(graphicsProjectile);
            h.handleCollisionFigure(figureList);
        }
        attackKeyboard.drawMissiles(graphicsProjectile);
        graphicsProjectile.end();

        marisa.renderHitbox(sr);
        attackKeyboard.renderHitbox(sr);
        for (PatternHandler h : handlerList) {
            h.renderHitbox(sr);
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);

        handler.generateSpiral(810, 800);
        stage1.draw(graphics);

        if ("Introduction".equals(stage1.getName())) {
            dialogueRendering();
        } else {
            if (stage1.isDialogueOn()) {
                dialogueRendering();
            }
            battleRendering();
        }
    }

    @Override
    public void dispose() {
        stage1.dispose();
        handler.dispose();
        marisa.dispose();
        graphics.dispose();
        graphicsFigure.dispose();
    }
}
