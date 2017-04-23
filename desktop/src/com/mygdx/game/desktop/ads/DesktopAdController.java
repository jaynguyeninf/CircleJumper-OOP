package com.mygdx.game.desktop.ads;

import com.badlogic.gdx.utils.Logger;
import com.jga.utils.ads.AdController;

/**
 * Created by Jay Nguyen on 4/20/2017.
 */

public class DesktopAdController implements AdController {

    private static final Logger log = new Logger(DesktopAdController.class.getSimpleName(), Logger.DEBUG);


    @Override
    public void showBanner() {
        log.debug("show banner");
    }

    @Override
    public void showInterstitial() {
        log.debug("show interstitial");
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }
}
