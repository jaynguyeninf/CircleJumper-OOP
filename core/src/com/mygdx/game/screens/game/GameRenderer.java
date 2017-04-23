package com.mygdx.game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.utils.ViewportUtils;
import com.jga.utils.debug.DebugCameraController;
import com.mygdx.game.asset_helpers.AssetDescriptors;
import com.mygdx.game.asset_helpers.RegionNames;
import com.mygdx.game.common.FloatingScore;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.GameState;
import com.mygdx.game.configs.GameConfig;
import com.mygdx.game.entities.Coin;
import com.mygdx.game.entities.Monster;
import com.mygdx.game.entities.Obstacle;
import com.mygdx.game.entities.Planet;
import com.mygdx.game.screens.menu.GameOverOverlay;
import com.mygdx.game.screens.menu.MenuOverlay;

/**
 * Created by Jay Nguyen on 4/13/2017.
 */

public class GameRenderer implements Disposable {

    private static final Logger log = new Logger(GameRenderer.class.getSimpleName(), Logger.DEBUG);

    private final GameController gameController;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final GlyphLayout glyphLayout = new GlyphLayout();

    private DebugCameraController debugCameraController;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private TextureRegion backgroundRegion, planetRegion;
    private Animation<TextureRegion> obstacleAnimation, monsterAnimation, coinAnimation;
    private Stage hudStage;
    private MenuOverlay menuOverlay;
    private GameOverOverlay gameOverOverlay;


    public GameRenderer(GameController gameController, AssetManager assetManager, SpriteBatch batch) {
        this.gameController = gameController;
        this.assetManager = assetManager;
        this.batch = batch;
        init();
    }

    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        shapeRenderer = new ShapeRenderer();

        //get assets
        font = assetManager.get(AssetDescriptors.FONT);
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        planetRegion = gameplayAtlas.findRegion(RegionNames.PLANET);
        Skin skin = assetManager.get(AssetDescriptors.SKIN);

        //animations
        obstacleAnimation = new Animation<TextureRegion>(0.1f,
                gameplayAtlas.findRegions(RegionNames.OBSTACLE),
                Animation.PlayMode.LOOP_PINGPONG);
        monsterAnimation = new Animation<TextureRegion>(0.2f,
                gameplayAtlas.findRegions(RegionNames.MONSTER),
                Animation.PlayMode.LOOP_PINGPONG);
        coinAnimation = new Animation<TextureRegion>(0.2f,
                gameplayAtlas.findRegions(RegionNames.COIN),
                Animation.PlayMode.LOOP_PINGPONG);

        hudStage = new Stage(hudViewport, batch);
        menuOverlay = new MenuOverlay(skin, gameController.getCallback()); //table
        gameOverOverlay = new GameOverOverlay(skin, gameController.getCallback()); //table


        hudStage.addActor(menuOverlay);
        hudStage.addActor(gameOverOverlay);

        Gdx.input.setInputProcessor(hudStage);

        //debug camera
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
    }

    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        renderGamePlay(delta);
//        renderDebug();
        renderHud();

    }

    private void renderGamePlay(float delta) {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawGamePlay(delta);

        batch.end();
    }

    private void drawGamePlay(float delta) {


        //draw background
        batch.draw(backgroundRegion, 0, 0, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);

        //obstacle
        Array<Obstacle> obstacles = gameController.getObstacles();
        TextureRegion obstacleRegion = obstacleAnimation.getKeyFrame(gameController.getAnimationTime());
        for (Obstacle obstacle : obstacles) {
            batch.draw(obstacleRegion,
                    obstacle.getX(), obstacle.getY(),
                    0, 0,
                    obstacle.getWidth(), obstacle.getHeight(),
                    1, 1,
                    GameConfig.START_ANGLE - obstacle.getAngleDegree());
        }

        //draw planet
        Planet planet = gameController.getPlanet();
        batch.draw(planetRegion, planet.getX(), planet.getY(), planet.getWidth(), planet.getHeight());


        //coin
        Array<Coin> coins = gameController.getCoins();
        TextureRegion coinRegion = coinAnimation.getKeyFrame(gameController.getAnimationTime());
        for (Coin coin : coins) {
            batch.draw(coinRegion,
                    coin.getX(), coin.getY(),
                    0, 0,
                    coin.getWidth(), coin.getHeight(),
                    coin.getScale(), coin.getScale(),
                    GameConfig.START_ANGLE - coin.getAngleDegree());
        }

        //monster
        Monster monster = gameController.getMonster();
        TextureRegion monsterRegion = monsterAnimation.getKeyFrame(gameController.getAnimationTime());
        batch.draw(monsterRegion,
                monster.getX(), monster.getY(),
                0, 0,
                monster.getWidth(), monster.getHeight(),
                1, 1,
                GameConfig.START_ANGLE - monster.getAngleDegree());
    }

    private void renderDebug() {
        ViewportUtils.drawGrid(viewport, shapeRenderer);

        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        shapeRenderer.end();
    }

    private void renderHud() {
        hudViewport.apply();

        menuOverlay.setVisible(false);//default as false unless game is over/menu state
        gameOverOverlay.setVisible(false);

        GameState gameState = gameController.getGameState();
        if (gameState.isPlayingOrReady()) {

            batch.setProjectionMatrix(hudViewport.getCamera().combined);
            batch.begin();
            drawHud();
            batch.end();
            return;
        }

        if (gameState.isMenu() && !menuOverlay.isVisible()) {
            menuOverlay.updateLabels();
            menuOverlay.setVisible(true);
        } else if (gameState.isGameOver() && !gameOverOverlay.isVisible()) {
            gameOverOverlay.updateLabels();
            gameOverOverlay.setVisible(true);
        }

        hudStage.act();
        hudStage.draw();
    }

    private void drawDebug() {
        //draw planet
        shapeRenderer.setColor(Color.RED);
        Planet planet = gameController.getPlanet();
        Circle planetBounds = planet.getBounds();
        shapeRenderer.circle(planetBounds.x, planetBounds.y, planetBounds.radius, 30);

        //draw monster
        shapeRenderer.setColor(Color.BLUE);
        Monster monster = gameController.getMonster();
        Rectangle monsterBounds = monster.getBounds();
        shapeRenderer.rect(monsterBounds.x, monsterBounds.y,
                0, 0,
                monsterBounds.width, monsterBounds.height,
                1, 1,
                GameConfig.START_ANGLE - monster.getAngleDegree()); // constant - monster's angle position


        //draw coins
        shapeRenderer.setColor(Color.YELLOW);
        for (Coin coin : gameController.getCoins()) {
            Rectangle coinBounds = coin.getBounds();
            shapeRenderer.rect(coinBounds.x, coinBounds.y,
                    0, 0,
                    coinBounds.width, coinBounds.height,
                    coin.getScale(), coin.getScale(),
                    GameConfig.START_ANGLE - coin.getAngleDegree());
        }

        //draw obstacles
        for (Obstacle obstacle : gameController.getObstacles()) {
            //obstacle
            shapeRenderer.setColor(Color.GREEN);
            Rectangle obstacleBounds = obstacle.getBounds();
            shapeRenderer.rect(obstacleBounds.x, obstacleBounds.y,
                    0, 0,
                    obstacleBounds.width, obstacleBounds.height,
                    1, 1,
                    GameConfig.START_ANGLE - obstacle.getAngleDegree());


            //sensor
            shapeRenderer.setColor(Color.WHITE);
            Rectangle sensorBounds = obstacle.getSensor();
            shapeRenderer.rect(sensorBounds.x, sensorBounds.y,
                    0, 0,
                    sensorBounds.width, sensorBounds.height,
                    1, 1,
                    GameConfig.START_ANGLE - obstacle.getSensorAngleDegree());

        }
    }

    private void drawHud() {
        float padding = 20;

        //draw high score
        String highScoreText = "High Score: " + GameManager.INSTANCE.getDisplayHighScore();
        glyphLayout.setText(font, highScoreText);
        font.draw(batch, glyphLayout,
                padding,
                GameConfig.HUD_HEIGHT - glyphLayout.height);

        //draw score
        String scoreText = "Score: " + GameManager.INSTANCE.getDisplayScore();
        glyphLayout.setText(font, scoreText);
        font.draw(batch, glyphLayout,
                GameConfig.HUD_WIDTH - glyphLayout.width - padding,
                GameConfig.HUD_HEIGHT - glyphLayout.height);

        //draw start timer
        float startWaitTimer = gameController.getStartWaitTimer();

        if (startWaitTimer >= 0) {
            int waitTime = (int) startWaitTimer;
            String waitTimeText = waitTime == 0 ? "GO!" : "" + waitTime; // ? = if  and : = else
            glyphLayout.setText(font, waitTimeText);
            font.draw(batch, glyphLayout,
                    GameConfig.HUD_WIDTH / 2 - glyphLayout.width / 2,
                    GameConfig.HUD_HEIGHT / 2 + glyphLayout.height / 2);
        }

        Color oldFontColor = new Color(font.getColor());

        //draw floating score
        for (FloatingScore floatingScore : gameController.getFloatingScores()) {
            glyphLayout.setText(font, floatingScore.getScoreString());
            font.setColor(floatingScore.getColor());
            font.draw(batch, glyphLayout,
                    floatingScore.getX()  - glyphLayout.width / 2,
                    floatingScore.getY()  - glyphLayout.height / 2);
        }

        font.setColor(oldFontColor);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
