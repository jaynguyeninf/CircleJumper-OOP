package com.mygdx.game;

import com.jga.utils.game.GameBase;
import com.jga.utils.ads.AdController;
import com.mygdx.game.screens.loading.LoadingScreen;

public class CircleJumperGame extends GameBase {

    public CircleJumperGame(AdController adController){
        super(adController);
    }

    @Override
    public void postCreate() {
        getAdController().showBanner();
        setScreen(new LoadingScreen(this));
    }
}
