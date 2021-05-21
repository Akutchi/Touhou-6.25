package com.touhou625.keyboard;

import static java.lang.Math.sqrt;

public interface Keyboard {

    double AMOUNT = 5;
    double DIAGONAL_AMOUNT = AMOUNT - 0.5;

    void horizontalKeyboardHandling();

    void verticalKeyboardHandling();

}
