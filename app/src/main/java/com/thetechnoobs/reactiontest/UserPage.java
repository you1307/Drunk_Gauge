package com.thetechnoobs.reactiontest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserPage extends AppCompatActivity {
    String TargetUserID;
    TextView UserNameTXT, UserIDTXT, imgRecontitionAvgTXT, imgFastestTimeTXT, ReactionTimeFastestTXT, ReactionTestAvgTimesTXT, AsteroidHighScoreTXT;
    List<String> names = new ArrayList<>();
    List<String> times = new ArrayList<>();
    List<String> avgTimes = new ArrayList<>();
    List<String> ReactionTestAverageTimes = new ArrayList<>();
    List<String> ReactionTestFastestTimes = new ArrayList<>();
    List<String> AsteroidHighScores = new ArrayList<>();
    List<User> PeopleData = new ArrayList<>();
    List<String> UserIds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        TargetUserID = getIntent().getStringExtra("User");

        settup();
        getData();
    }

    public void settup(){
        UserNameTXT = findViewById(R.id.UsersPageNameTXT);
        UserIDTXT = findViewById(R.id.UP_UserIDTXT);
        imgRecontitionAvgTXT = findViewById(R.id.ImgReconitionAvgTXT);
        imgFastestTimeTXT = findViewById(R.id.UP_ImgFastestTimeTXT);
        ReactionTimeFastestTXT = findViewById(R.id.ReactionTimeFastestTXT);
        ReactionTestAvgTimesTXT = findViewById(R.id.AvgReactionTestTXT);
        AsteroidHighScoreTXT = findViewById(R.id.AsteroidHighScoreTXT);
    }

    private void GetUserInfo() {
        for(int i = 0; i < PeopleData.size() ; i++){
            if(PeopleData.get(i).getUserID().equals(TargetUserID)){
                setUserData(i);
                break;
            }
        }
    }

    private void setUserData(int pos) {
        UserNameTXT.setText(PeopleData.get(pos).getName().toUpperCase());
        UserIDTXT.setText(PeopleData.get(pos).getUserID());
        imgRecontitionAvgTXT.setText(PeopleData.get(pos).getImgRecAverageTime());
        imgFastestTimeTXT.setText(PeopleData.get(pos).getImgFastestTime());
        ReactionTimeFastestTXT.setText(PeopleData.get(pos).getFastestReactionTime());
        ReactionTestAvgTimesTXT.setText(PeopleData.get(pos).getFastestAvgReactionTime());
        AsteroidHighScoreTXT.setText(PeopleData.get(pos).getAsteroidHighScore());
    }



    public void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference DataRef = database.getReference();

        DataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keyNode : snapshot.child("Users").getChildren()) {
                    UserIds.add(keyNode.getKey());//Store all user ids in array
                }

                for (int i = 0; i < UserIds.size(); i++) {
                    names.add((String) snapshot.child("Users").child(UserIds.get(i)).child("Name").getValue());//store all names in array
                }

                for (int i = 0; i < UserIds.size(); i++) {
                    times.add(String.valueOf(snapshot.child("Users").child(UserIds.get(i)).child("Time").getValue()));//store all times in array
                }

                for (int i = 0; i < UserIds.size(); i++) {
                    avgTimes.add(String.valueOf(snapshot.child("Users").child(UserIds.get(i)).child("AverageTime").getValue()));
                }

                for(int i = 0; i < UserIds.size(); i++){
                    ReactionTestFastestTimes.add(String.valueOf(snapshot.child("Users").child(UserIds.get(i)).child("ReactionTime").getValue()));
                }

                for(int i = 0; i < UserIds.size(); i++){
                    ReactionTestAverageTimes.add(String.valueOf(snapshot.child("Users").child(UserIds.get(i)).child("AverageReactionTime").getValue()));
                }

                for(int i = 0; i < UserIds.size(); i++){
                    AsteroidHighScores.add(String.valueOf(snapshot.child("Users").child(UserIds.get(i)).child("AsteroidScore").getValue()));
                }
                BuildUser();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void BuildUser(){
        for (int i = 0; i < UserIds.size(); i++) {
            if(stringContainsNumber(UserIds.get(i))){
                User user = new User();

                user.setName(names.get(i));
                user.setImgFastestTime(times.get(i));
                user.setUserID(UserIds.get(i));
                user.setImgRecAverageTime(avgTimes.get(i));
                user.setFastestReactionTime(ReactionTestFastestTimes.get(i));
                user.setFastestAvgReactionTime(ReactionTestAverageTimes.get(i));
                user.setAsteroidHighScore(AsteroidHighScores.get(i));
                PeopleData.add(user);//userdata saved as a userObject in array
            }else{
                Log.v("INFO", "Ignored: "+UserIds.get(i));
            }
        }
        GetUserInfo();
    }

    public boolean stringContainsNumber( String s ) {
        return Pattern.compile( "[0-9]" ).matcher( s ).find();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), Activity_ScoreBoard.class);
        startActivity(intent);
        finish();
    }
}