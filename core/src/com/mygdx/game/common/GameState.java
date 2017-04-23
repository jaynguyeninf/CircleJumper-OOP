package com.mygdx.game.common;

/**
 * Created by Jay Nguyen on 4/18/2017.
 */

public enum GameState {
    MENU,
    READY,
    PLAYING,
    GAME_OVER;

    public boolean isMenu() {
        return this == MENU;
    }

    public boolean isReady() {
        return this == READY;
    }

    public boolean isPlaying() {
        return this == PLAYING;
    }

    public boolean isGameOver() {
        return this == GAME_OVER;
    }

    public boolean isPlayingOrReady(){
        return isPlaying() || isReady();
    }
}
