package com.touhou625.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DialogueKeyboard {

    public DialogueKeyboard() {

    }

    public boolean changeDialogue() {
        return Gdx.input.isKeyPressed(Input.Keys.W);
    }
}
