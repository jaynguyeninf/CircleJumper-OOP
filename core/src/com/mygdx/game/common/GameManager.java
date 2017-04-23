package com.mygdx.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.CircleJumperGame;

/**
 * Created by Jay Nguyen on 4/15/2017.
 */

public class GameManager {
    private static final String HIGH_SCORE_STRING = "highScore";

    private Preferences prefs;

    private int score;
    private int highScore;
    private int displayScore;
    private int displayHighScore;

    public static final GameManager INSTANCE = new GameManager();

    //==constructor==
    private GameManager() {
        prefs = Gdx.app.getPreferences(CircleJumperGame.class.getSimpleName());
        highScore = prefs.getInteger(HIGH_SCORE_STRING, 0);
        displayHighScore = highScore;
    }

    public void addScore(int amount) {
        score += amount;

        if (score > highScore) {
            highScore = score; // order matters
        }
    }

    public void updateDisplayScores(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(score, displayHighScore + (int) (delta * 100));
        }

        if (displayHighScore < highScore) {
            displayHighScore = Math.min(highScore, displayHighScore + (int) (delta * 100));
        }
    }

    public void updateHighScore(){
        if(score < highScore){
            return;
        }

        highScore = score;
        prefs.putInteger(HIGH_SCORE_STRING, highScore);
        prefs.flush();

    }

    public void reset() {
        score = 0;
        displayScore = 0;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public int getDisplayHighScore() {
        return displayHighScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getScore() {
        return score;
    }
}
