package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.CircleJumperGame;
import com.jga.utils.ads.AdController;
import com.mygdx.game.configs.GameConfig;
import com.mygdx.game.desktop.ads.DesktopAdController;

public class DesktopLauncher {

    private static final AdController AD_CONTROLLER = new DesktopAdController();

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) GameConfig.DESKTOP_WIDTH;
        config.height = (int) GameConfig.DESKTOP_HEIGHT;

        new LwjglApplication(new CircleJumperGame(AD_CONTROLLER), config);
    }
}
