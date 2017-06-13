package com.mygdx.game.screens.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.asset_helpers.ButtonStyleNames;
import com.mygdx.game.asset_helpers.RegionNames;
import com.mygdx.game.common.GameManager;

/**
 * Created by Jay Nguyen on 4/18/2017.
 */

public class GameOverOverlay extends Table {

    private final OverlayCallback callback;
    private Label scoreLabel, highScoreLabel;

    public GameOverOverlay(Skin skin, OverlayCallback callback) {
        super(skin);
        this.callback = callback;
        init();
    }

    private void init() {
        defaults().pad(20);

        Image gameOverImage = new Image(getSkin(), RegionNames.GAME_OVER);

        //score table
        Table scoreTable = new Table(getSkin());
        scoreTable.setDebug(true);
        scoreTable.defaults().pad(10);

        scoreTable.add("Score:").row();
        scoreLabel = new Label("", getSkin());
        scoreTable.add(scoreLabel).row();

        scoreTable.add("Best Score:").row();
        highScoreLabel = new Label("", getSkin());
        scoreTable.add(highScoreLabel);

        scoreTable.center();

        //button table
        Table buttonTable = new Table(getSkin());
        ImageButton homeButton = new ImageButton(getSkin(), ButtonStyleNames.HOME);
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                callback.home();
            }
        });

        ImageButton restartButton = new ImageButton(getSkin(), ButtonStyleNames.RESTART);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                callback.ready();//ready + restart
            }
        });

        buttonTable.add(homeButton).left().expandX();
        buttonTable.add(restartButton).right().expandX();

        add(gameOverImage).row();
        add(scoreTable).row();
        add(buttonTable).grow().center();

        center();
        setFillParent(true);
        pack();

        updateLabels();
    }

    public void updateLabels() {
        scoreLabel.setText("" + GameManager.INSTANCE.getScore());
        highScoreLabel.setText("" + GameManager.INSTANCE.getHighScore());
    }
}
