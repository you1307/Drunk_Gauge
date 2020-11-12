package com.thetechnoobs.reactiontest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    CheckBox ShowTaskTimer;
    Button SaveAndExitBTN;
    boolean ShowTimer;
    Button settingBTN, ScoreBoardBTN;
    TextView UserIDTXT, UserNameTXT;
    SaveData saveData = new SaveData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Settup();
        SetUserData();
    }

    @SuppressLint("SetTextI18n")
    public void SetUserData(){
        UserNameTXT.setText("User Name: "+ saveData.getUserName(this));
        UserIDTXT.setText("User ID: "+ saveData.getUserID(this));

    }

    public void Settup(){
        UserIDTXT = findViewById(R.id.UserIDTXT);
        UserNameTXT = findViewById(R.id.UserNameTXT);

        ShowTaskTimer = findViewById(R.id.ShowTimerCheckBox);

        SharedPreferences GetSettings = this.getSharedPreferences("Settings", MODE_PRIVATE);
        ShowTaskTimer.setChecked(GetSettings.getBoolean("ShowTimer", false));

        OnclickLiseners();

    }

    public void OnclickLiseners(){

        ShowTaskTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
                SharedPreferences.Editor prefE = sharedPreferences.edit();

                if(ShowTaskTimer.isChecked()){
                    prefE.putBoolean("ShowTimer", true).apply();
                }else{
                    prefE.putBoolean("ShowTimer", false).apply();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent GoToMainMenu = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(GoToMainMenu);
        finish();
    }
}