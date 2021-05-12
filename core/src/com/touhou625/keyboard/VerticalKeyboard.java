package com.touhou625.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.touhou625.figure.Figure;

public class VerticalKeyboard implements Keyboard {

    private final Figure marisa;

    public VerticalKeyboard(Figure figure) {
        super();
        marisa = figure;
    }

    @Override
    public void horizontalKeyboardHandling() throws UnsupportedOperationException {

        /* empty method because we only care about vertical movment */

    }

    @Override
    public void verticalKeyboardHandling() {

        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        double movement = AMOUNT;

        if (left || right) {
            movement = DIAGONAL_AMOUNT;
        }

        if (up) {
            marisa.move(0, (int) movement);
        } else if (down) {
            marisa.move(0, (int) -movement);
        } else {
            marisa.changeCurrentSpriteListToMotion(0);
        }
    }
}
