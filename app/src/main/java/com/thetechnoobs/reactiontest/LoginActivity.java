package com.thetechnoobs.reactiontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    String UserName, UserID;
    String DefaultTime = "59.999";
    int DefaultAsteroidScore = 2;
    List<String> DefaultAvgTimes = new ArrayList<>();
    EditText UserNameEdit;
    Button DoneBTN;
    SaveData saveData = new SaveData();
    RandomItems randomItems = new RandomItems();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        settup();

    }

    public void settup(){
        UserNameEdit = findViewById(R.id.NameEditText);
        DoneBTN = findViewById(R.id.DoneBTN);

        DoneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DonePushed();
            }
        });
    }

    public void SaveData(){
        DefaultAvgTimes.add("60.982");
        UserID = randomItems.RandomIdGen();//generate random id

        saveData.SetUserID(UserID, this);//save user ID to sharedpref
        saveData.setUserName(UserName, this);//save user Name to sharedpref
        saveData.SetNewUserStatus(false, this);//save new user status to sharedpref

        saveData.SaveUserNameAndID(UserName, UserID);//save User name and ID to firebase

        //make sure default values are set
       SetDefaultValues();
    }

    public void SetDefaultValues(){
        saveData.SaveFastestTime(DefaultTime, saveData.getUserID(this), this);//save a default value so its not null
        saveData.SaveAverageFastestTime(DefaultAvgTimes, UserID, getApplicationContext());//save a default avg time so its not null
        saveData.SaveFastestReactionTime(DefaultTime, UserID, getApplicationContext());//save a default reaction time so its not null
        saveData.SaveAverageFastestReactionTime(DefaultAvgTimes, UserID, getApplicationContext());//save a default avg time so its not null
        saveData.SaveAsteroidGameHighScore(this, UserID, DefaultAsteroidScore);//save a default value for asteroid game so its not null
    }

    public void GotoMainPage(){
        Intent GoToMain = new Intent(this, MainActivity.class);
        startActivity(GoToMain);
        finish();
    }

    public void DonePushed(){

        if(UserNameEdit.getText().toString().isEmpty()||UserNameEdit.getText().toString().equals(" ")){
            Toast.makeText(this,"Please input a valid name, Dickhead", Toast.LENGTH_LONG).show();
        }else{
            UserName = UserNameEdit.getText().toString();
            SaveData();
            GotoMainPage();
        }
    }



}