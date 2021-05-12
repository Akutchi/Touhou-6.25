package com.touhou625.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.touhou625.figure.Figure;

public class HorizontalKeyboard implements Keyboard {

    private final Figure marisa;

    public HorizontalKeyboard(Figure figure) {
        super();
        marisa = figure;
    }

    @Override
    public void horizontalKeyboardHandling() {

        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        double movement = AMOUNT;

        if(up || down){
            movement = DIAGONAL_AMOUNT;
        }

        if (left) {
            marisa.changeCurrentSpriteListToMotion(-1);
            marisa.move((int) -movement, 0);
        } else if (right) {
            marisa.changeCurrentSpriteListToMotion(1);
            marisa.move((int) movement, 0);
        } else {
            marisa.changeCurrentSpriteListToMotion(0);
        }
    }

    @Override
    public void verticalKeyboardHandling() throws UnsupportedOperationException {

        /* because we only care about horizontal movment */

    }
}
