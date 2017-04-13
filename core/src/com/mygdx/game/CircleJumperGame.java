package com.mygdx.game;

import com.jga.utils.game.GameBase;
import com.mygdx.game.screens.game.GameScreen;

public class CircleJumperGame extends GameBase {

    @Override
    public void postCreate() {
        setScreen(new GameScreen(this));
    }
}
