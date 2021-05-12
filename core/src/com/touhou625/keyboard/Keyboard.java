package com.touhou625.keyboard;

import static java.lang.Math.sqrt;

public interface Keyboard {

    double AMOUNT = 3;
    double DIAGONAL_AMOUNT = 2 * sqrt(AMOUNT);

    void horizontalKeyboardHandling();

    void verticalKeyboardHandling();

}
