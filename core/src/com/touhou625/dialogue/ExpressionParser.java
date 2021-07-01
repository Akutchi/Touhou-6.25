package com.touhou625.dialogue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {

    private List<String> rawText;
    private final List<String> textPurged = new ArrayList<>();
    private final List<TextureRegion> patchouliExpressions = new ArrayList<>();
    private final List<TextureRegion> marisaExpressions = new ArrayList<>();
    private final List<TextureRegion> expressions = new ArrayList<>();

    public ExpressionParser(List<String> text) {

        int x = 0;
        int y = 0;

        rawText = text;

        Texture rawExpressionPatchouli = new Texture("patchouli_expression.png");
        Texture rawExpressionMarisa = new Texture("marisa_expression.jpg");

        int height = 233;
        int width = 128;
        int[] offestPatchouli = {0, 285};
        for (Integer offset : offestPatchouli) {
            for (int i = 0; i < 2; i++) {
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
                marisaExpressions.add(new TextureRegion(rawExpressionMarisa, x + width, y + height));
                x += width;
            }
            y = offset;
        }
    }

    public void createExpressions() {

        for (String line : rawText) {
            String[] splittedLine = line.split("/");

            if (splittedLine.length > 1) {

                String expressionMacro = splittedLine[0];
                String actualText = splittedLine[1];
                textPurged.add(actualText);

                System.out.println(expressionMacro);

                if (expressionMacro.contains("P")) {
                    expressions.add(patchouliExpressions.get(Integer.parseInt(expressionMacro.substring(2, 3)) - 1));
                } else if (expressionMacro.contains("M")) {
                    expressions.add(marisaExpressions.get(Integer.parseInt(expressionMacro.substring(2, 3)) - 1));
                } else {
                    expressions.add(null);
                }

            } else {
                textPurged.add(line);
            }
        }
    }

    public List<String> getTextPurged() {
        return textPurged;
    }

    public List<TextureRegion> getExpressions() {
        return expressions;
    }
}
