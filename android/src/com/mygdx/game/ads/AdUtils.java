package com.mygdx.game.ads;

import com.google.android.gms.ads.AdRequest;

/**
 * Created by Jay Nguyen on 4/21/2017.
 */

public class AdUtils {

    private static final String TEST_DEVICE = "0570BBEEA38EC6E1FEB5E351573AAEA7";

    public static AdRequest buildRequest() {
        return new AdRequest.Builder().addTestDevice(TEST_DEVICE).build();
    }

    private AdUtils() {
    }

}
