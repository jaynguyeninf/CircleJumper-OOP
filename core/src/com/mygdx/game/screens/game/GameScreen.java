package com.mygdx.game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.jga.utils.game.GameBase;

/**
 * Created by Jay Nguyen on 4/13/2017.
 */

public class GameScreen extends ScreenAdapter {

    private final GameBase game;
    private final AssetManager assetManager;

    private GameController gameController;
    private GameRenderer gameRenderer;

    // == constructor
    public GameScreen(GameBase game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        gameController = new GameController();
        gameRenderer = new GameRenderer(gameController);
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameController.update(delta);
        gameRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width,height);
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
