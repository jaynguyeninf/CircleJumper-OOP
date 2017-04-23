package com.mygdx.game;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.jga.utils.ads.AdController;
import com.mygdx.game.ads.AdUnitIds;
import com.mygdx.game.ads.AdUtils;

public class AndroidLauncher extends AndroidApplication implements AdController {

    private AdView bannerAdView;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAds();
        initUi();

    }

    private void initAds() {
        bannerAdView = new AdView(this);
        bannerAdView.setId(R.id.adViewId);
        bannerAdView.setAdUnitId(AdUnitIds.BANNER_ID);
        bannerAdView.setAdSize(AdSize.SMART_BANNER);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(AdUnitIds.INTERSTITIAL_ID);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                loadInterstitial();
            }
        });

        loadInterstitial(); //need to call
    }

    private void initUi() {
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        View gameView = initializeForView(new CircleJumperGame(this), config); //assign to View gameView

        RelativeLayout relativeLayout = new RelativeLayout(this); //basically a table

        //ad view params
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        //game view params
        RelativeLayout.LayoutParams gameParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        gameParams.addRule(RelativeLayout.ABOVE, bannerAdView.getId()); //game is below ad without getting cut out off anything

        //add views to layout
        relativeLayout.addView(bannerAdView, adParams);
        relativeLayout.addView(gameView, gameParams);

        setContentView(relativeLayout);//need to setContentView
    }

    @Override
    protected void onResume() {
        super.onResume(); //keep this
        bannerAdView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bannerAdView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerAdView.destroy();
    }

    @Override
    public void showBanner() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });
    }

    private void loadBanner() {
        if (isNetworkConnected()) {
            bannerAdView.loadAd(AdUtils.buildRequest());
        }
    }

    @Override
    public void showInterstitial() { //can call in game, for example: when game is over or loses life
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showInterstitialInternal();
            }
        });
    }

    private void showInterstitialInternal(){
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }

    private void loadInterstitial(){
        if(isNetworkConnected()){
            interstitialAd.loadAd(AdUtils.buildRequest());
        }
    }

    @Override
    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected(); //need this or when there's no network, game willcrash
    }
}
