package com.touhou625.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.touhou625.dialogue.Bubble;
import com.touhou625.dialogue.ExpressionParser;

import java.util.List;

public class Stage implements Scene {

    private boolean isDialogueOn;
    private boolean changeDialogue;

    private final int gameWidth;

    private final String name;

    private final Texture rBackground;

    private final Bubble dialogueBox;

    public Stage(String sceneBackgroundName, String stageName, List<String> lines, int gWidth) {

        name = stageName;

        isDialogueOn = true;
        changeDialogue = false;

        rBackground = new Texture(sceneBackgroundName);
        gameWidth = gWidth;

        ExpressionParser expressionParserStage1 = new ExpressionParser(lines);
        expressionParserStage1.createExpressions();

        dialogueBox = new Bubble(expressionParserStage1.getTextPurged(), expressionParserStage1.getExpressions());
        dialogueBox.setDimensions(SCENEXOFFSET, 200, SCENEWIDTH - 50, 100);
    }

    public void draw(SpriteBatch g) {
        g.begin();
        g.draw(rBackground, (gameWidth - SCENEWIDTH) / 2.0f, SCENEYOFFSET, SCENEWIDTH, SCENEHEIGHT);
        g.end();
    }

    public void drawDialogue(SpriteBatch g, ShapeRenderer sr) {
        changeDialogue = dialogueBox.draw(g, sr, changeDialogue);
    }

    public void setChangeDialogue(boolean mode) {
        changeDialogue = mode;
    }

    public void toogleDialogue() {
        isDialogueOn = !isDialogueOn;
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

    public String getName() {
        return name;
    }

    public void dispose() {
        rBackground.dispose();
    }
}
