package com.thetechnoobs.reactiontest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.thetechnoobs.reactiontest.astroidGame.GameActivity;

public class MainActivity extends AppCompatActivity {
    ImageButton ComprehentionBTN, ReactionBTN, ScoreBoardBTN, SettingsBTN;
    Button AstroidShooterBTN;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIfNewUser();
        Settup();
        OnclickLiseners();
    }

    private void OnclickLiseners() {
        ComprehentionBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToComprehensionTest = new Intent(getApplicationContext(), Comprehention_test.class);
                startActivity(GoToComprehensionTest);
                finish();
            }
        });

        ReactionBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToReactionTest = new Intent(getApplicationContext(), ReactionTest.class);
                startActivity(GoToReactionTest);
                finish();
            }
        });

        ScoreBoardBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToScoreBorad = new Intent(getApplicationContext(), Activity_ScoreBoard.class);
                startActivity(GoToScoreBorad);
                finish();
            }
        });

        SettingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToSettings = new Intent(getApplicationContext(), Settings.class);
                startActivity(GoToSettings);
                finish();
            }
        });

        AstroidShooterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToAstroidGame = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(GoToAstroidGame);
                finish();
            }
        });

    }

    public void checkIfNewUser(){
        SharedPreferences preferences = this.getSharedPreferences("User", MODE_PRIVATE);
        boolean NewUser = preferences.getBoolean("NewUser", true);

        if(NewUser){
            Intent GoToSigninPage = new Intent(this, LoginActivity.class);
            startActivity(GoToSigninPage);
            finish();

        }
    }

    private void Settup() {
        SettingsBTN = findViewById(R.id.SettingsBTN);
        ComprehentionBTN = findViewById(R.id.ComprehensionBTN);
        ReactionBTN = findViewById(R.id.ReactionTestBTN);
        ScoreBoardBTN = findViewById(R.id.GoToScoreBoardBTN);
        AstroidShooterBTN = findViewById(R.id.AstroidShooterBTN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

