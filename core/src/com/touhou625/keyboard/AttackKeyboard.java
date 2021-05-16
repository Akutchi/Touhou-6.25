package com.touhou625.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.touhou625.figure.Figure;
import com.touhou625.patternhandler.PatternHandler;


public class AttackKeyboard implements Keyboard {

    private int temporisation;

    private final double[] direction = {0.0, -5.0};

    private final Figure marisa;
    private final PatternHandler handler;


    public AttackKeyboard(Figure figure) {
        super();
        marisa = figure;
        handler = new PatternHandler();
        temporisation = 0;
    }

    public void attackKeyboardHandling() {
        boolean isAttacking = Gdx.input.isKeyPressed(Input.Keys.W);

        if (isAttacking) {
            if (temporisation % 5 == 0) {
                handler.generateProjectile(marisa.getX(), marisa.getY(), 65, 180, direction);
                temporisation = 0;
            }
            temporisation++;
        }
    }

    public void drawMissiles(SpriteBatch g) {
        handler.drawProjectiles(g);
    }

    @Override
    public void horizontalKeyboardHandling() {
        /* because we do not care about movement here*/
    }

    @Override
    public void verticalKeyboardHandling() {
        /* because we do not care about movement here*/
    }
}
