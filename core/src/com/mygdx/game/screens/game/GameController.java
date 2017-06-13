package com.mygdx.game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.mygdx.game.common.FloatingScore;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.GameState;
import com.mygdx.game.common.SoundListener;
import com.mygdx.game.configs.GameConfig;
import com.mygdx.game.entities.Coin;
import com.mygdx.game.entities.Monster;
import com.mygdx.game.entities.Obstacle;
import com.mygdx.game.entities.Planet;
import com.mygdx.game.screens.menu.OverlayCallback;

/**
 * Created by Jay Nguyen on 4/13/2017.
 */

public class GameController {

    private static final Logger log = new Logger(GameController.class.getSimpleName(), Logger.DEBUG);

    private final Array<Coin> coins = new Array<Coin>();
    private final Pool<Coin> coinPool = Pools.get(Coin.class, 10);
    private final Array<Obstacle> obstacles = new Array<Obstacle>();
    private final Pool<Obstacle> obstaclePool = Pools.get(Obstacle.class, 10);
    private final SoundListener soundListener;
    private final Array<FloatingScore> floatingScores = new Array<FloatingScore>();
    private Pool<FloatingScore> floatingScorePool = Pools.get(FloatingScore.class);

    private Planet planet;
    private Monster monster;

    private float monsterStartX;
    private float monsterStartY;
    private float coinTimer;
    private float obstacleTimer;
    private float startWaitTimer = GameConfig.START_WAIT_TIME;
    private float animationTime;

    private GameState gameState = GameState.MENU;
    private OverlayCallback callback;

    // == constructor ==
    public GameController(SoundListener soundListener) {
        this.soundListener = soundListener;
        init();
    }


    private void init() {
        planet = new Planet();
        planet.setPosition(
                GameConfig.WORLD_CENTER_X - GameConfig.PLANET_RADIUS,
                GameConfig.WORLD_CENTER_Y - GameConfig.PLANET_RADIUS);

        monsterStartX = GameConfig.WORLD_CENTER_X - GameConfig.MONSTER_HALF_SIZE;
        monsterStartY = GameConfig.WORLD_CENTER_Y + GameConfig.PLANET_RADIUS;

        monster = new Monster();
        monster.setPosition(monsterStartX, monsterStartY);

        callback = new OverlayCallback() {
            @Override
            public void home() {
                gameState = GameState.MENU;
            }

            @Override
            public void ready() {
                restart();
                gameState = GameState.READY;
            }
        };
    }

    public void update(float delta) {

        if (gameState.isReady() && startWaitTimer > 0) { //wait few secs for user to get ready
            startWaitTimer -= delta;

            if (startWaitTimer <= 0) { //switch to playing state when countdown is over
                gameState = GameState.PLAYING;
            }
        }

        if (!gameState.isPlaying()) { //if not playing, then don't render
            return;
        }

        animationTime += delta;
        log.debug("animationTime = " + animationTime);

        GameManager.INSTANCE.updateDisplayScores(delta); //update scores

        if (Gdx.input.justTouched() && monster.isWalking()) {
            soundListener.jump();
            monster.jump();
        }

        monster.update(delta);

        for (Obstacle obstacle : obstacles) {
            obstacle.update(delta);
        }

        for (Coin coin : coins) {
            coin.update(delta);
        }

        for (int i = 0; i < floatingScores.size; i++) {
            FloatingScore floatingScore = floatingScores.get(i);
            floatingScore.update(delta);

            if (floatingScore.isFinished()) {
                floatingScorePool.free(floatingScore);
                floatingScores.removeIndex(i);
            }
        }

        spawnObstacles(delta);
        spawnCoins(delta);
        checkCollisions();
    }

    private void spawnObstacles(float delta) {
        obstacleTimer += delta;

        if (obstacleTimer < GameConfig.OBSTACLE_SPAWN_TIME) {
            return;
        }

        obstacleTimer = 0;

        if (obstacles.size == 0) {
            addObstacles();
        }
    }

    private void addObstacles() {
        int count = MathUtils.random(2, GameConfig.MAX_OBSTACLES);

        for (int i = 0; i < count; i++) {
            //spawn obstacles behind the monster
            float randomAngle = monster.getAngleDegree() - i * GameConfig.MIN_ANGLE_DISTANCE - MathUtils.random(60, 80);

            boolean canSpawn = !isObstacleNearBy(randomAngle) && !isCoinNearBy(randomAngle) && !isMonsterNearBy(randomAngle);

            if (canSpawn) {
                Obstacle obstacle = obstaclePool.obtain();
                obstacle.setAngleDegree(randomAngle);
                obstacles.add(obstacle);
            }
        }
    }

    private void spawnCoins(float delta) {
        coinTimer += delta;

        if (coinTimer < GameConfig.COIN_SPAWN_TIME) { //wait for coinTimer to reach COIN_SPAWN_TIME
            return;
        }

        coinTimer = 0; //then set it back to 0 as delay

        if (coins.size == 0) {
            addCoins();
        }

    }

    private void addCoins() {
        int count = MathUtils.random(GameConfig.MAX_COINS);

        for (int i = 0; i < count; i++) {
            float randomAngle = MathUtils.random(360f);

            boolean canSpawn = !isCoinNearBy(randomAngle) && !isMonsterNearBy(randomAngle);

            if (canSpawn) {
                Coin coin = coinPool.obtain();

                if (isObstacleNearBy(randomAngle)) {
                    coin.setOffSet(true);
                }

                coin.setAngleDegree(randomAngle);
                coins.add(coin);
            }
        }

    }

    private boolean isCoinNearBy(float angle) {
        // check that there are no coins nearby min distance
        for (Coin coin : coins) {
            float angleDegree = coin.getAngleDegree();

            float difference = Math.abs(Math.abs(angleDegree) - Math.abs(angle)); //get positive number difference

            if (difference < GameConfig.MIN_ANGLE_DISTANCE) {
                return true;
            }
        }

        return false;
    }

    private boolean isMonsterNearBy(float angle) {
        float playerDifference = Math.abs(Math.abs(monster.getAngleDegree() - Math.abs(angle)));

        if (playerDifference < GameConfig.MIN_ANGLE_DISTANCE) {
            return true;
        }

        return false;
    }

    private boolean isObstacleNearBy(float angle) {
        for (Obstacle obstacle : obstacles) {
            float angleDegree = obstacle.getAngleDegree();

            float difference = Math.abs(Math.abs(angleDegree) - Math.abs(angle));

            if (difference < GameConfig.MIN_ANGLE_DISTANCE) {
                return true;
            }
        }
        return false;
    }

    private void checkCollisions() {
        // collision monster <-> coins
        for (int i = 0; i < coins.size; i++) {
            Coin coin = coins.get(i);

            if (Intersector.overlaps(monster.getBounds(), coin.getBounds())) {
                soundListener.hitCoin();
                GameManager.INSTANCE.addScore(GameConfig.COIN_SCORE);
                addFloatingScore(GameConfig.COIN_SCORE);
                coinPool.free(coin);
                coins.removeIndex(i);
            }
        }

        // collision monster <-> sensor
        for (int i = 0; i < obstacles.size; i++) {
            Obstacle obstacle = obstacles.get(i);

            if (Intersector.overlaps(monster.getBounds(), obstacle.getSensor())) {
                GameManager.INSTANCE.addScore(GameConfig.OBSTACLE_SCORE);
                addFloatingScore(GameConfig.OBSTACLE_SCORE);
                obstaclePool.free(obstacle);
                obstacles.removeIndex(i);

            } else if (Intersector.overlaps(monster.getBounds(), obstacle.getBounds())) {
                soundListener.lose();
                gameState = GameState.GAME_OVER;
            }

        }
    }

    public void restart() {
        coinPool.freeAll(coins);
        coins.clear();

        obstaclePool.freeAll(obstacles);
        obstacles.clear();

        floatingScorePool.freeAll(floatingScores);
        floatingScores.clear();

        monster.reset();
        monster.setPosition(monsterStartX, monsterStartY);

        GameManager.INSTANCE.updateHighScore(); //persist scores before reset();
        GameManager.INSTANCE.reset();
        startWaitTimer = GameConfig.START_WAIT_TIME;
        animationTime = 0;
        gameState = GameState.READY;
    }

    private void addFloatingScore(int score) {
        FloatingScore floatingScore = floatingScorePool.obtain();
        floatingScore.setStartPosition(GameConfig.HUD_WIDTH/2, GameConfig.HUD_HEIGHT/2);
        floatingScore.setScore(score);
        floatingScores.add(floatingScore);
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Monster getMonster() {
        return monster;
    }

    public Array<Coin> getCoins() {
        return coins;
    }

    public float getStartWaitTimer() {
        return startWaitTimer;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public GameState getGameState() {
        return gameState;
    }

    public OverlayCallback getCallback() {
        return callback;
    }

    public Array<FloatingScore> getFloatingScores() {
        return floatingScores;
    }
}
