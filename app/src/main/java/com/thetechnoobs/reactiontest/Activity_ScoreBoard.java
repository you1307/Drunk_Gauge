package com.thetechnoobs.reactiontest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Activity_ScoreBoard extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> names = new ArrayList<>();
    List<String> times = new ArrayList<>();
    List<String> avgTimes = new ArrayList<>();
    List<String> avgReactionTimes = new ArrayList<>();
    List<String> ReactionTimes = new ArrayList<>();
    List<User> PeopleData = new ArrayList<>();
    List<String> UserIds = new ArrayList<>();
    MyAdapterScoreComp myAdapterScoreComp;
    String OrgUsersWith = Constants.REAC_IDENT;
    Button SortByCompTimeBTN, SortByReacTimeBTN;
    int FilterBTNVal = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__scoreboard);

        settup();
        getData();
    }

    public void ReloadData(){
        names.clear();
        times.clear();
        avgTimes.clear();
        avgReactionTimes.clear();
        ReactionTimes.clear();
        UserIds.clear();
        PeopleData.clear();

        recyclerView.removeAllViews();
        myAdapterScoreComp.ReOrganizeData(FilterBTNVal);
        getData();
    }


    public void DataLoaded(){
        myAdapterScoreComp = new MyAdapterScoreComp(this, PeopleData, FilterBTNVal);
        recyclerView.setAdapter(myAdapterScoreComp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void settup(){
        SortByCompTimeBTN = findViewById(R.id.CompSortBTN);
        SortByReacTimeBTN = findViewById(R.id.ReacSortBTN);
        recyclerView = findViewById(R.id.recyclerView);
        SettupFilterButtons();
        SettupOnClick();
    }

    private void SettupFilterButtons() {
        FilterBTNVal = new SaveData().getFilterButtonInt(getApplicationContext());//0 is filtering by comprehension---1 is filter by reaction
        switch (FilterBTNVal){
            case 0:
                SortByReacTimeBTN.setBackgroundResource(R.drawable.sort_btn_backgrond);
                SortByCompTimeBTN.setBackgroundResource(R.drawable.filter_btn_active_background);
                break;
            case 1:
                SortByCompTimeBTN.setBackgroundResource(R.drawable.sort_btn_backgrond);
                SortByReacTimeBTN.setBackgroundResource(R.drawable.filter_btn_active_background);
                break;
        }
    }

    private void SettupOnClick() {
        SortByCompTimeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgUsersWith = Constants.COMP_IDENT;
                ReloadData();
                SaveNewSortSelection(0);
                SettupFilterButtons();
            }
        });

        SortByReacTimeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgUsersWith = Constants.REAC_IDENT;
                ReloadData();
                SaveNewSortSelection(1);
                SettupFilterButtons();
            }
        });
    }

    private void SaveNewSortSelection(int SortBy) {
        new SaveData().saveSortSelection(getApplicationContext(), SortBy);
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
                    ReactionTimes.add(String.valueOf(snapshot.child("Users").child(UserIds.get(i)).child("ReactionTime").getValue()));
                }

                for(int i = 0; i < UserIds.size(); i++){
                    avgReactionTimes.add(String.valueOf(snapshot.child("Users").child(UserIds.get(i)).child("AverageReactionTime").getValue()));
                }
                BuildUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void BuildUser() {
        for (int i = 0; i < UserIds.size(); i++) {
            if (stringContainsNumber(UserIds.get(i))) {
                User user = new User();
                user.setName(names.get(i));
                user.setImgFastestTime((times.get(i)));
                user.setUserID(UserIds.get(i));
                user.setImgRecAverageTime(avgTimes.get(i));
                user.setFastestReactionTime(ReactionTimes.get(i));
                user.setFastestAvgReactionTime(avgReactionTimes.get(i));
                PeopleData.add(user);//userdata saved as a userObject in array
            } else {
                Log.v("INFO", "Ignored: " + UserIds.get(i));
            }
        }
        DataLoaded();
    }

    public boolean stringContainsNumber( String s ) {
        return Pattern.compile( "[0-9]" ).matcher( s ).find();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent GoToMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(GoToMain);
        finish();
    }
}