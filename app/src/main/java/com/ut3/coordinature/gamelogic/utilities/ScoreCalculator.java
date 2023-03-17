package com.ut3.coordinature.gamelogic.utilities;

import android.content.SharedPreferences;

import com.ut3.coordinature.entities.characters.impl.Player;

public class ScoreCalculator {

    private final SharedPreferences sharedPreferences;
    private Player player;

    public ScoreCalculator(SharedPreferences sharedPreferences, Player player) {
        this.sharedPreferences = sharedPreferences;
        this.player = player;
    }

    public long calculateScore(Long startTime){
        return ( player.getObstaclePassed() / 3);
    }

    public void updateScore(Long startTime) {
        long score = calculateScore(startTime);
        long previousMaxScore = sharedPreferences.getLong(MainMenuActivity.SHARED_PREF, 0);

        if(score > previousMaxScore) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(MainMenuActivity.SHARED_PREF, score);
            editor.commit();
            editor.apply();
        }


    }

}
