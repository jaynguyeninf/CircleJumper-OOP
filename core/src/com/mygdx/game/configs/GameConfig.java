package com.mygdx.game.configs;

/**
 * Created by Jay Nguyen on 4/13/2017.
 */

public class GameConfig {

    // == desktop (in pixels) ==
    public static final float DESKTOP_WIDTH = 600;
    public static final float DESKTOP_HEIGHT = 800;

    // == HUD ==
    public static final float HUD_WIDTH = 600;
    public static final float HUD_HEIGHT = 800f;

    // == world (in world units) ==
    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 24f;

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2;

    public static final float PLANET_DIMENSION = 9f;
    public static final float PLANET_RADIUS = PLANET_DIMENSION / 2;

    public static final float START_ANGLE = -90;
    public static final float MONSTER_SIZE = 1;
    public static final float MONSTER_HALF_SIZE = MONSTER_SIZE / 2;
    public static final float MONSTER_START_ANGLER_SPEED = 45;
    public static final float MONSTER_MAX_SPEED = 2;
    public static final float MONSTER_START_ACCELERATION = 4;

    public static final float COIN_SIZE = 1;
    public static final float COIN_HALF_SIZE = COIN_SIZE / 2;
    public static final float COIN_SPAWN_TIME = 1.25f;
    public static final int MAX_COINS = 2;
    public static final int COIN_SCORE = 10;

    public static final float OBSTACLE_SIZE = 1f;
    public static final float OBSTACLE_HALF_SIZE = OBSTACLE_SIZE / 2;
    public static final float OBSTACLE_SPAWN_TIME = 0.75f;
    public static final int MAX_OBSTACLES = 3;
    public static final int OBSTACLE_SCORE = 5;

    public static final float START_WAIT_TIME = 3;
    public static final float MIN_ANGLE_DISTANCE = 60;

    public static final float FLOATING_SCORE_DURATION = .75f;


    private GameConfig() {
    }
}
