package com.touhou625.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.touhou625.figure.Figure;
import com.touhou625.patternhandler.PatternHandler;


public class AttackKeyboard implements Keyboard {

    private int timing;

    private final int temporisation = 2;

    private final double[] direction = {0.0, -40.0};

    private final Texture rawSpriteProjectile = new Texture("PatchouliProjectile.png");
    private final TextureRegion spriteProjectile = new TextureRegion(rawSpriteProjectile, rawSpriteProjectile.getWidth(),
            rawSpriteProjectile.getHeight());

    private final Figure marisa;
    private final PatternHandler handler;


    public AttackKeyboard(Figure figure, int borderWidth, int borderHeight) {
        super();
        marisa = figure;
        handler = new PatternHandler(borderWidth, borderHeight);
        timing = 0;
    }

    public void attackKeyboardHandling() {
        boolean isAttacking = Gdx.input.isKeyPressed(Input.Keys.W);

        if (isAttacking) {
            if (timing % temporisation == 0) {
                handler.generateProjectile(marisa.getX(), marisa.getY(), spriteProjectile, 180, direction);
                timing = 0;
            }
            timing++;
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
