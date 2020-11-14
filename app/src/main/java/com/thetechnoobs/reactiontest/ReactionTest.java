package com.thetechnoobs.reactiontest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReactionTest extends AppCompatActivity {

    ReactionTestCustomViewBtn MainBTN;
    TextView LastTimeTXT, SetPosTXT;
    List<String> Times = new ArrayList<>();
    int RoundCount = 0;

    //timer stuff
    CountDownTimer countDownTimer;
    boolean killTimer = false;
    boolean AllowTap = false;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    long startTime = 0L;
    int secs = 0;
    int milliseconds = 0;
    String CurTaskTime = "00.000";
    String ClickedTimeSave = "00.000";
    private final Handler TimerHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_test);

        settup();
        SettupOnClicks();
        StartInitialDelayTimeDelay();//start popup timer and delay timer
    }


    public void StartInitialDelayTimeDelay() {
        StartPopUpTimer();
        new CountDownTimer(5050, 1000) {//DelayWiating For popupTimer To finish
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                StartTest();//start random time test
            }
        }.start();
    }

    private void StartPopUpTimer() {
        PopupCountdownTimer popupCountdownTimer = new PopupCountdownTimer();
        popupCountdownTimer.popupDialog(this);
        popupCountdownTimer.showDialog();
    }

    private void StartTest() {
        killTimer = false;
        countDownTimer = new CountDownTimer(10000, 1000) {
            int r = 0;
            boolean Rfound = false;

            public void onTick(long millisUntilFinished) {

                Random random = new Random();

                r = random.nextInt(50);
                if (r == 20 || r == 12 || r == 10 || r == 5 || r == 25 || r == 34) {
                    startTime = SystemClock.uptimeMillis();
                    TimerHandler.postDelayed(StartTimerBackgroundCount, 0);
                    AllowTap = true;
                    MainBTN.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.stop_sign_go));
                    Rfound = true;
                    onFinish();
                }
                Log.v("testing", "" + r);
            }

            public void onFinish() {
                if (!Rfound) {
                    StartTest();
                }
            }
        }.start();
    }

    private void SettupOnClicks() {
        MainBTN.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    MainBTNClicked();
                    return true;
                }
                return false;
            }
        });
    }

    private void MainBTNClicked() {
        if(AllowTap){
            StopTimerGetTime();
            MainBTN.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.stop_sign));
            AllowTap = false;
        }else {
            countDownTimer.cancel();
            killTimer = true;
            TapedEarly();
            Toast.makeText(getApplicationContext(), "TO EARLY", Toast.LENGTH_SHORT).show();
        }
    }

    private void TapedEarly() {
        StartInitialDelayTimeDelay();
    }

    private void SaveReactionTimeFireBase() {
        SaveData saveData = new SaveData();
        saveData.SaveFastestReactionTime(ClickedTimeSave, saveData.getUserID(this), this);
    }

    private void StopTimerGetTime() {
        ClickedTimeSave = CurTaskTime;
        RoundCheck();
        Toast.makeText(this, ClickedTimeSave, Toast.LENGTH_LONG).show();


        LastTimeTXT.setText(ClickedTimeSave);
        countDownTimer.cancel();
        killTimer = true;
        SaveReactionTimeFireBase();
        StartInitialDelayTimeDelay();
    }

    @SuppressLint("SetTextI18n")
    private void RoundCheck() {
        if(RoundCount == 5){
            SaveAverageReactionTimeToFireBase();
            Times.clear();
            RoundCount = 0;
        }else{
            RoundCount++;
            Times.add(ClickedTimeSave);
        }

        SetPosTXT.setText(RoundCount+"/5");
    }

    private void SaveAverageReactionTimeToFireBase() {
        SaveData saveData = new SaveData();
        saveData.SaveAverageFastestReactionTime(Times, saveData.getUserID(this), getApplicationContext());
    }


    private void settup() {
        MainBTN = findViewById(R.id.MainBTN);
        LastTimeTXT = findViewById(R.id.CurTimeTXT);
        SetPosTXT = findViewById(R.id.SetPosTXT);
    }

    private final Runnable StartTimerBackgroundCount = new Runnable() {
        @Override
        public void run() {
            if(killTimer){
                return;
            }
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedTime % 1000);

            if (milliseconds < 100) {
                CurTaskTime = secs + ".0" + milliseconds;
            } else {
                CurTaskTime = secs + "." + milliseconds;
            }

            TimerHandler.postDelayed(this, 0);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        killTimer = true;
        countDownTimer.cancel();
        Intent GoToMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(GoToMain);
        finish();
    }

}