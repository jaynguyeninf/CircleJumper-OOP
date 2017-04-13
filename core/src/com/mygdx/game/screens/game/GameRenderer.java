package com.mygdx.game.screens.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.utils.ViewportUtils;
import com.jga.utils.debug.DebugCameraController;
import com.mygdx.game.configs.GameConfig;
import com.mygdx.game.entities.Planet;

/**
 * Created by Jay Nguyen on 4/13/2017.
 */

public class GameRenderer implements Disposable {

    private final GameController gameController;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private DebugCameraController debugCameraController;

    public GameRenderer(GameController gameController) {
        this.gameController = gameController;
        init();
    }

    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
    }

    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        renderDebug();

    }

    private void renderDebug() {
        ViewportUtils.drawGrid(viewport, shapeRenderer);

        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        shapeRenderer.end();
    }

    private void drawDebug() {
        Planet planet = gameController.getPlanet();
        Circle planetBounds = planet.getBounds();
        shapeRenderer.circle(planetBounds.x, planetBounds.y, planetBounds.radius, 30);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
