package com.touhou625.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Bubble {

    private static final float INTENSITY = 0.7f;
    private static final Color COLORBOX = new Color(0, 0, 0, 0.5f);
    private static final Color COLORFONT = new Color(INTENSITY, INTENSITY, INTENSITY, 1);
    private static final BitmapFont FONT = new BitmapFont();

    private static final int dx = 10;
    private static final int dy = 10;

    private int xLine;
    private int yLine;
    private int width;
    private int height;

    private String line;

    public Bubble() {
    }

    public void draw(SpriteBatch g, ShapeRenderer sr) {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(COLORBOX);
        sr.rect(xLine, yLine, width, height);
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        g.begin();
        FONT.setColor(COLORFONT);
        FONT.draw(g, line, xLine + dx * 1.0f, yLine + height - dy * 1.0f);
        g.end();
    }

    public void setDimensions(int x, int y, int w, int h) {
        xLine = x;
        yLine = y;
        width = w;
        height = h;
    }

    public void setLine(String text) {
        line = text;
    }
}
