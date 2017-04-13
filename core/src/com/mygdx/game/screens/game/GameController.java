package com.mygdx.game.screens.game;

import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.configs.GameConfig;
import com.mygdx.game.entities.Planet;
import com.mygdx.game.entities.Player;

/**
 * Created by Jay Nguyen on 4/13/2017.
 */

public class GameController {

    private static final Logger log = new Logger(GameController.class.getSimpleName(), Logger.DEBUG);

    private Planet planet;
    private Player player;

    // == constructor ==
    public GameController() {
        init();
    }


    private void init() {
        planet = new Planet();
        planet.setPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        player = new Player();
        player.setPosition(
                GameConfig.WORLD_CENTER_X,
                GameConfig.WORLD_CENTER_Y + GameConfig.PLANET_RADIUS + GameConfig.PLAYER_HALF_SIZE);
    }

    public void update(float delta) {

    }

    public Planet getPlanet() {
        return planet;
    }

    public Player getPlayer() {
        return player;
    }
}
