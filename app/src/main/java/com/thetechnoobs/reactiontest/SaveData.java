package com.thetechnoobs.reactiontest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import androidx.core.content.ContextCompat;

import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import okhttp3.internal.cache.DiskLruCache;

import static android.content.Context.MODE_PRIVATE;

public class SaveData {

    public void setUserName(String userName, Context context) {
        SharedPreferences pref = context.getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor PrefE = pref.edit();
        PrefE.putString("UserName", userName);
        PrefE.apply();
    }

    public void SetUserID(String UserID, Context context){
        SharedPreferences pref = context.getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor PrefE = pref.edit();
        PrefE.putString("UserID", UserID);
        PrefE.apply();
    }

    public String getUserID(Context context){
        String UserID;

        SharedPreferences sharedPreferences = context.getSharedPreferences("User", MODE_PRIVATE);
        UserID = sharedPreferences.getString("UserID", "0");

        return UserID;
    }

    public void SetNewUserStatus(boolean NewUser, Context context){
        SharedPreferences pref = context.getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor PrefE = pref.edit();
        PrefE.putBoolean("NewUser", NewUser);
        PrefE.apply();
    }

    public String getUserName(Context context) {
        String UserName;
        SharedPreferences sharedPreferences = context.getSharedPreferences("User", MODE_PRIVATE);
        UserName = sharedPreferences.getString("UserName", "none");

        return UserName;
    }

    public void SaveUserNameAndID(String name,String UserID){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference DataRef = database.getReference();
        DataRef.child("Users").child(String.valueOf(UserID)).child("Name").setValue(name);

    }

    public void SaveAverageFastestTime(List<String> times, String UserID, Context context){
        int FinalAvecal = 0;
        String FinalTime;

        for (int i = 0; i < times.size(); i++) {
            FinalAvecal = FinalAvecal + Integer.parseInt(times.get(i).replace(".", ""));
        }

        int sec = FinalAvecal/times.size();
        sec = sec/1000;
        int milli = FinalAvecal%1000;

        if(milli<100){
            FinalTime = sec +".0"+milli;
        }else{
            FinalTime = sec+"."+milli;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Dataref = database.getReference("Users/"+UserID+"/");
        SharedPreferences pref = context.getSharedPreferences("User", MODE_PRIVATE);

        String OldTime = Objects.requireNonNull(pref.getString("AverageTime", "99.999")).replace(".", "@");
        String NewTimeE = FinalTime.replace(".", "@");

        if(isFaster(NewTimeE, OldTime)){
            Dataref.child("AverageTime").setValue(FinalTime);
            SharedPreferences.Editor prefE = pref.edit();
            prefE.putString("AverageTime", FinalTime);
            prefE.apply();
        }
    }

    public void SaveFastestTime(String NewTime, String UserID, Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference DataRef = database.getReference("Users/"+UserID+"/");

        SharedPreferences pref = context.getSharedPreferences("User", MODE_PRIVATE);

        String OldTime = Objects.requireNonNull(pref.getString("Time", "99.999")).replace(".", "@");
        String NewTimeE = NewTime.replace(".", "@");

        if(isFaster(NewTimeE, OldTime)){
            DataRef.child("Time").setValue(NewTime);
            SharedPreferences.Editor prefE = pref.edit();
            prefE.putString("Time", NewTime);
            prefE.apply();
        }
    }

    public void SaveFastestReactionTime(String NewTime, String UserID, Context context){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference DataRef = database.getReference("Users/"+UserID+"/");

        SharedPreferences pref = context.getSharedPreferences("User", MODE_PRIVATE);

        String OldTime = Objects.requireNonNull(pref.getString("ReactionTime", "99.999")).replace(".", "@");
        String NewTimeE = NewTime.replace(".", "@");

        if(isFaster(NewTimeE, OldTime)){
            DataRef.child("ReactionTime").setValue(NewTime);
            Log.v("reactionSave", "Saved: "+NewTime);//TODO Remove after debugging
            SharedPreferences.Editor prefE = pref.edit();
            prefE.putString("ReactionTime", NewTime);
            prefE.apply();
        }
    }



    public void SaveAverageFastestReactionTime(List<String> times, String UserID, Context context) {
        int FinalAvecal = 0;
        String FinalTime;

        for (int i = 0; i < times.size(); i++) {
            FinalAvecal = FinalAvecal + Integer.parseInt(times.get(i).replace(".", ""));
        }

        int sec = FinalAvecal/times.size();
        sec = sec/1000;
        int milli = FinalAvecal%1000;

        if(milli<100){
            FinalTime = sec +".0"+milli;
        }else{
            FinalTime = sec+"."+milli;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Dataref = database.getReference("Users/"+UserID+"/");
        SharedPreferences pref = context.getSharedPreferences("User", MODE_PRIVATE);

        String OldTime = Objects.requireNonNull(pref.getString("AverageReactionTime", "99.999")).replace(".", "@");
        String NewTimeE = FinalTime.replace(".", "@");

        if(isFaster(NewTimeE, OldTime)){
            Dataref.child("AverageReactionTime").setValue(FinalTime);
            SharedPreferences.Editor prefE = pref.edit();
            prefE.putString("AverageReactionTime", FinalTime);
            prefE.apply();
        }

        Log.v("AverageReactionTime", FinalTime);
    }

    public boolean isFaster(String NewTimeE, String OldTime){
        boolean faster = false;
        String[] NewTimeArray = NewTimeE.split("@", 2);
        String[] OldTimeArray = OldTime.split("@", 2);
        int NewSec, OldSec, NewMil, OldMil;
        NewSec = Integer.parseInt(NewTimeArray[0]);
        NewMil = Integer.parseInt(NewTimeArray[1]);
        OldSec = Integer.parseInt(OldTimeArray[0]);
        OldMil = Integer.parseInt(OldTimeArray[1]);


        if(NewSec<OldSec){
            faster = true;
        }else if(NewSec==OldSec){
            if(NewMil<OldMil){
                faster = true;
            }
        }

        return faster;
    }

    public int getFilterButtonInt(Context context) {
        int FilterBtn;
        SharedPreferences sharedPreferences = context.getSharedPreferences("User", MODE_PRIVATE);
        FilterBtn = sharedPreferences.getInt("sortby", 0);

        return FilterBtn;
    }

    public void saveSortSelection(Context applicationContext, int sortBy) {
        SharedPreferences pref = applicationContext.getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor PrefE = pref.edit();
        PrefE.putInt("sortby", sortBy);
        PrefE.apply();
    }
}