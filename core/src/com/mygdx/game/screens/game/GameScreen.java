package com.mygdx.game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Logger;
import com.jga.utils.game.GameBase;
import com.mygdx.game.asset_helpers.AssetDescriptors;
import com.mygdx.game.common.SoundListener;

/**
 * Created by Jay Nguyen on 4/13/2017.
 */

public class GameScreen extends ScreenAdapter {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final GameBase game;
    private final AssetManager assetManager;
    private final SoundListener soundListener;

    private GameController gameController;
    private GameRenderer gameRenderer;

    private Sound coinSound, jumpSound, loseSound;

    // == constructor
    public GameScreen(final GameBase game) {
        this.game = game;
        assetManager = game.getAssetManager();
        soundListener = new SoundListener() {
            @Override
            public void hitCoin() {
                coinSound.play(0.5f);
            }

            @Override
            public void jump() {
                jumpSound.play(0.5f);
            }

            @Override
            public void lose() {
                loseSound.play(0.5f);
                game.getAdController().showInterstitial(); //show ad when loses
            }
        };
    }

    @Override
    public void show() {
        coinSound = assetManager.get(AssetDescriptors.COIN_SOUND);
        jumpSound = assetManager.get(AssetDescriptors.JUMP_SOUND);
        loseSound = assetManager.get(AssetDescriptors.LOSE_SOUND);

        gameController = new GameController(soundListener);
        gameRenderer = new GameRenderer(gameController, game.getAssetManager(), game.getBatch());
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameController.update(delta);
        gameRenderer.render(delta);

        log.debug("render calls = " + game.getBatch().renderCalls);
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }
}
