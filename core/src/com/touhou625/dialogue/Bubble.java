package com.touhou625.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class Bubble {

    private static final float SCALE = 1.15f;
    private static final float INTENSITY = 0.7f;
    private static final Color COLORBOX = new Color(0, 0, 0, 0.5f);
    private static final Color COLORFONT = new Color(INTENSITY, INTENSITY, INTENSITY, 1);
    private static final BitmapFont FONT = new BitmapFont();

    private static final int XPADDING = 10;
    private static final int YPADDING = 10;

    private int xLine;
    private int yLine;
    private int width;
    private int height;
    private int indexText;
    private int indexImage;

    private String line;
    private final ArrayList<String> nextLine;
    private final ArrayList<TextureRegion> expression;

    public Bubble(ArrayList<String> listLine, ArrayList<TextureRegion> expressionsList) {
        indexText = 0;
        indexImage = 0;
        nextLine = listLine;
        expression = expressionsList;
        line = nextLine.get(0);
    }

    public TextureRegion getLastExpression(ArrayList<TextureRegion> expression, int index) {
        if (expression.get(index) != null) {
            return expression.get(index);
        }
        return getLastExpression(expression, --index);
    }


    public boolean draw(SpriteBatch g, ShapeRenderer sr, boolean changeDialogue) {

        if (changeDialogue) {
            if (indexText < nextLine.size()) {

                indexText++;
                line = nextLine.get(indexText);

                if (expression.get(++indexImage) != null) {
                    g.draw(expression.get(indexImage), 50, 50);
                }
                g.draw(getLastExpression(expression, indexImage), 50, 50);
                indexImage = indexText;
            }
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(COLORBOX);
        sr.rect(xLine, yLine, width, height);
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        g.begin();
        FONT.setColor(COLORFONT);
        FONT.getData().setScale(SCALE, SCALE);
        FONT.draw(g, line, xLine + XPADDING * 1.0f, yLine + height - YPADDING * 1.0f);
        g.end();

        return false;
    }

    public void setDimensions(int x, int y, int w, int h) {
        xLine = x;
        yLine = y;
        width = w;
        height = h;
    }
}
