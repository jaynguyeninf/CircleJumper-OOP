package com.mygdx.game.configs;

/**
 * Created by Jay Nguyen on 4/13/2017.
 */

public class GameConfig {

    // == desktop (in pixels) ==
    public static final float DESKTOP_WIDTH = 480;
    public static final float DESKTOP_HEIGHT = 800;

    // == world (in world units) ==
    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 24f;

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2;

    public static final float PLANET_DIMENSION = 9f;
    public static final float PLANET_RADIUS = PLANET_DIMENSION / 2;

    public static final float PLAYER_SIZE = 1;
    public static final float PLAYER_HALF_SIZE = PLAYER_SIZE / 2;

    private GameConfig() {
    }
}
