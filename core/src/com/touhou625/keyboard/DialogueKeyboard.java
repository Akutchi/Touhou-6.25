package com.touhou625.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.time.LocalTime;

public class DialogueKeyboard {

    private LocalTime timeStart;

    public DialogueKeyboard() {
        timeStart = LocalTime.now();
    }

    public int differenceInSecond(LocalTime t1, LocalTime t2) {
        return Math.abs(t1.getHour() - t2.getHour()) * 60 + Math.abs(t1.getMinute() - t2.getMinute()) * 60 +
                Math.abs(t1.getSecond() - t2.getSecond());
    }

    public boolean isKeyPressed() {

        LocalTime timeNow = LocalTime.now();

        if (differenceInSecond(timeNow, timeStart) >= 1) {
            timeStart = LocalTime.now();
            return Gdx.input.isKeyPressed(Input.Keys.W);
        }
        return false;
    }
}
