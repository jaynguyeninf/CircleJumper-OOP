package com.mygdx.game.screens.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.utils.game.GameBase;
import com.mygdx.game.asset_helpers.AssetDescriptors;
import com.mygdx.game.configs.GameConfig;
import com.mygdx.game.screens.game.GameScreen;

/**
 * Created by Jay Nguyen on 4/15/2017.
 */

public class LoadingScreen extends ScreenAdapter {

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2;
    private static final float PROGRESS_BAR_HEIGHT = 60f;

    private final GameBase game;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean screenChanged;

    //==constructor==
    public LoadingScreen(GameBase game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        //load assets
        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.load(AssetDescriptors.SKIN);
        assetManager.load(AssetDescriptors.COIN_SOUND);
        assetManager.load(AssetDescriptors.JUMP_SOUND);
        assetManager.load(AssetDescriptors.LOSE_SOUND);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        draw();
        shapeRenderer.end();

        //flag used to make sure all things rendered
        if (screenChanged) {
            game.setScreen(new GameScreen(game));
        }
    }

    private void update(float delta) {
        progress = assetManager.getProgress();

        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                screenChanged = true;
            }
        }
    }

    private void draw() {
        float progressBarX = GameConfig.HUD_WIDTH / 2 - PROGRESS_BAR_WIDTH / 2; //centered
        float progressBarY = GameConfig.HUD_HEIGHT / 2 - PROGRESS_BAR_HEIGHT / 2;

        shapeRenderer.rect(progressBarX, progressBarY,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
