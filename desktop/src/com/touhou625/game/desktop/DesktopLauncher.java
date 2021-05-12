package com.touhou625.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.touhou625.game.Game;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Touhou 6.25";
        config.width = 765;
        config.height = 765;
        new LwjglApplication(new Game(), config);
    }
}
