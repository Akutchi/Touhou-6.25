package com.touhou625.dialogue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class ExpressionParser {

    private final ArrayList<TextureRegion> patchouliExpressions = new ArrayList<>();
    private final ArrayList<TextureRegion> marisaExpressions = new ArrayList<>();
    private ArrayList<String> textPurged = new ArrayList<>();
    private ArrayList<TextureRegion> expressions = new ArrayList<>();

    public ExpressionParser() {

        int x = 0;
        int y = 0;

        Texture rawExpressionPatchouli = new Texture("patchouli_expression.jpg");
        Texture rawExpressionMarisa = new Texture("marisa_expression.jpg");

        int[] offestPatchouli = {0, 285};
        for (Integer offset : offestPatchouli) {
            for (int i = 0; i < 2; i++) {
                int height = 270;
                int width = 174;
                patchouliExpressions.add(new TextureRegion(rawExpressionPatchouli, x + width, y + height));
                x += width;
            }
            y = offset;
        }

        x = 0;
        y = 0;

        int[] offestMarisa = {0, 261, 520};
        for (Integer offset : offestMarisa) {
            for (int i = 0; i < 2; i++) {
                int height = 270;
                int width = 174;
                marisaExpressions.add(new TextureRegion(rawExpressionMarisa, x + width, y + height));
                x += width;
            }
            y = offset;
        }
    }

    public void createExpressions(ArrayList<String> text) {

        for (String line : text) {
            String[] splittedLine = line.split("/");

            if (splittedLine.length > 1) {

                String expressionMacro = splittedLine[0];
                String actualText = splittedLine[1];
                textPurged.add(actualText);

                if (expressionMacro.contains("M")) {
                    expressions.add(patchouliExpressions.get(Integer.parseInt(expressionMacro.substring(1, 1))));
                }

                expressions.add(marisaExpressions.get(Integer.parseInt(expressionMacro.substring(1, 1))));
            }
            textPurged.add(line);
            expressions.add(null);
        }
    }

    public ArrayList<String> getTextPurged() {
        return textPurged;
    }

    public ArrayList<TextureRegion> getExpressions() {
        return expressions;
    }
}
