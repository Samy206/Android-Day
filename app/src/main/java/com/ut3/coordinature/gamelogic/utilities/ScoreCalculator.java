package com.ut3.coordinature.gamelogic.utilities;

import android.content.SharedPreferences;

import com.ut3.coordinature.activities.MainMenuActivity;
import com.ut3.coordinature.entities.characters.impl.Player;

public class ScoreCalculator {

    private final SharedPreferences sharedPreferences;

    public ScoreCalculator(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public long calculateScore(int obstaclePassed){
        return ( obstaclePassed / 1);
    }

    public void updateScore(int obstaclePassed) {

        long score = calculateScore(obstaclePassed);
        long previousMaxScore = sharedPreferences.getLong(MainMenuActivity.SHARED_PREF, 0);

        if(score > previousMaxScore) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(MainMenuActivity.SHARED_PREF, score);
            editor.commit();
            editor.apply();
        }


    }

}
