package com.thetechnoobs.reactiontest.astroidGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.thetechnoobs.reactiontest.MainActivity;
import com.thetechnoobs.reactiontest.R;
import com.thetechnoobs.reactiontest.SaveData;

import java.util.Objects;

public class EndGameScreen extends Activity {
    TextView ScoreTXT;
    Button RestartBtn, EndGameBtn;
    int userScore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Settup();
        LoadScore();
        AutoSaveNewScore();
    }

    private void AutoSaveNewScore() {
        SaveData saveData = new SaveData();
        saveData.SaveAsteroidGameHighScore(this, saveData.getUserID(this), userScore);
    }

    private void LoadScore() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String scoreTemp = Objects.requireNonNull(bundle.get("score")).toString();
            userScore = Integer.parseInt(scoreTemp);
            ScoreTXT.setText(scoreTemp);
        }else{
            Log.v("testing", "Error Loading Score");
        }

    }


    public void Settup(){
        ScoreTXT = findViewById(R.id.ScoreTXTView);
        RestartBtn = findViewById(R.id.RestartGameBTN);
        EndGameBtn = findViewById(R.id.EndGameBTN);

        SettupOnclickLiseners();
    }

    private void SettupOnclickLiseners() {
        RestartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestartGame();
            }
        });

        EndGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndGame();
            }
        });


    }

    private void EndGame() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void RestartGame() {
        startActivity(new Intent(this, GameActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
