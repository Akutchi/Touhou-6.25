package com.touhou625.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.touhou625.dialogue.Bubble;

import java.util.ArrayList;

public class Stage implements Scene {

    private boolean isDialogueOn;
    private boolean changeDialogue;

    private final int gameWidth;
    private int index;

    private final String[] nextLine;

    private final Texture rBackground;

    private final Bubble dialogueBox;

    public Stage(String sceneBackgroundName, String[] lines, int gWidth) {

        isDialogueOn = true;
        changeDialogue = false;

        index = 0;
        nextLine = lines;

        rBackground = new Texture(sceneBackgroundName);
        gameWidth = gWidth;

        dialogueBox = new Bubble();
        dialogueBox.setDimensions(SCENEXOFFSET + 50, 200, SCENEWIDTH - 50, 100);
        dialogueBox.setLine(nextLine[index]);

    }

    public void draw(SpriteBatch g) {
        g.begin();
        g.draw(rBackground, (gameWidth - SCENEWIDTH) / 2.0f, SCENEYOFFSET, SCENEWIDTH, SCENEHEIGHT);
        g.end();
    }

    public void drawDialogue(SpriteBatch g, ShapeRenderer sr) {

        if (changeDialogue) {
            if (index < nextLine.length) {
                dialogueBox.setLine(nextLine[index]);
                index++;
            }
            toogleDialogue();
            index = 0;
        }

        dialogueBox.draw(g, sr);
    }

    public void toogleDialogue() {
        isDialogueOn = !isDialogueOn;
    }

    public void setChangeDialogue(boolean mode) {
        changeDialogue = mode;
    }

    public boolean isDialogueOn() {
        return isDialogueOn;
    }

    public int getX() {
        return SCENEXOFFSET;
    }

    public int getY() {
        return SCENEYOFFSET;
    }

    public int getWidth() {
        return SCENEWIDTH;
    }

    public int getHeight() {
        return SCENEHEIGHT;
    }

    public void dispose() {
        rBackground.dispose();
    }
}
