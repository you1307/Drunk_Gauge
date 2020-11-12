package com.thetechnoobs.reactiontest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Comprehention_test extends AppCompatActivity {

    Button B1x1, B1x2, B1x3, B2x1, B2x2, B2x3, B3x1, B3x2, B3x3, B4x1, B4x2, B4x3, B5x1, B5x2, B5x3, B6x1, B6x2, B6x3;
    String[] buttons = {"B1x1", "B1x2", "B1x3", "B2x1", "B2x2", "B2x3", "B3x1", "B3x2", "B3x3", "B4x1", "B4x2", "B4x3", "B5x1", "B5x2", "B5x3", "B6x1", "B6x2", "B6x3"};
    ImageView MainImg;
    String CurTaskTime;
    TextView taskTime, SetPositionTXT;
    int Rounds;
    boolean Killme = false;
    String SetPosition = "/10";
    int setLength = 10;
    boolean ShowTimer = false;
    String impotantBTN;
    List<String> Times = new ArrayList<>();
    SaveData saveData = new SaveData();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    long startTime = 0L;
    int secs = 0;
    int milliseconds = 0;
    private Handler TimerHandler = new Handler();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprehention_test);

        settup();
        getNewSet();
    }

    public void getNewSet() {
        setRandomButtons();
        setImportantButton();
        startTime = SystemClock.uptimeMillis();
        TimerHandler.postDelayed(updateTimerThread, 0);
    }

    public String setImportantImg() {
        RandomItems randomItems = new RandomItems();
        String Img = randomItems.randomItem();
        String uri;

        //try to find img and set it to image view
        try {
            uri = "@drawable/" + Img;
            int imageresource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = ResourcesCompat.getDrawable(getResources(), imageresource, null);
            MainImg.setImageDrawable(res);

        } catch (Exception e) {
            e.printStackTrace();
            getNewSet();
        }
        return Img;
    }

    public void coorectSeletion() {
        Rounds++;

        SetPosition = Rounds + "/10";
        SetPositionTXT.setText(SetPosition);

        Times.add(CurTaskTime);
        Log.v("AverageTime", "" + Times);
        getNewSet();

        saveData.SaveFastestTime(CurTaskTime, saveData.getUserID(this), this);//save fastest time

        if (Rounds == setLength) {
            saveData.SaveAverageFastestTime(Times, saveData.getUserID(this), this);//save fastest average time
            Rounds = 0;
            Times.clear();

            SetPosition = Rounds + "/10";
            SetPositionTXT.setText(SetPosition);

            Toast.makeText(getApplicationContext(), "Done with set!", Toast.LENGTH_LONG).show();
        }
    }

    public void setRandomButtons() {
        RandomItems randomItems = new RandomItems();
        for (String button : buttons) {

            switch (button) {
                case "B1x1":
                    B1x1.setText(randomItems.randomFillerItem());
                case "B1x2":
                    B1x2.setText(randomItems.randomFillerItem());
                case "B1x3":
                    B1x3.setText(randomItems.randomFillerItem());
                case "B2x1":
                    B2x1.setText(randomItems.randomFillerItem());
                case "B2x2":
                    B2x2.setText(randomItems.randomFillerItem());
                case "B2x3":
                    B2x3.setText(randomItems.randomFillerItem());
                case "B3x1":
                    B3x1.setText(randomItems.randomFillerItem());
                case "B3x2":
                    B3x2.setText(randomItems.randomFillerItem());
                case "B3x3":
                    B3x3.setText(randomItems.randomFillerItem());
                case "B4x1":
                    B4x1.setText(randomItems.randomFillerItem());
                case "B4x2":
                    B4x2.setText(randomItems.randomFillerItem());
                case "B4x3":
                    B4x3.setText(randomItems.randomFillerItem());
                case "B5x1":
                    B5x1.setText(randomItems.randomFillerItem());
                case "B5x2":
                    B5x2.setText(randomItems.randomFillerItem());
                case "B5x3":
                    B5x3.setText(randomItems.randomFillerItem());
                case "B6x1":
                    B6x1.setText(randomItems.randomFillerItem());
                case "B6x2":
                    B6x2.setText(randomItems.randomFillerItem());
                case "B6x3":
                    B6x3.setText(randomItems.randomFillerItem());

            }

        }
    }

    public void setImportantButton() {
        RandomItems randomItems = new RandomItems();
        String randombutton = randomItems.randomButton();
        impotantBTN = setImportantImg();

        Log.d("Important Button", randombutton + " : " + impotantBTN);

        switch (randombutton) {
            case "B1x1":
                B1x1.setText(impotantBTN);
                break;
            case "B1x2":
                B1x2.setText(impotantBTN);
                break;
            case "B1x3":
                B1x3.setText(impotantBTN);
                break;
            case "B2x1":
                B2x1.setText(impotantBTN);
                break;
            case "B2x2":
                B2x2.setText(impotantBTN);
                break;
            case "B2x3":
                B2x3.setText(impotantBTN);
                break;
            case "B3x1":
                B3x1.setText(impotantBTN);
                break;
            case "B3x2":
                B3x2.setText(impotantBTN);
                break;
            case "B3x3":
                B3x3.setText(impotantBTN);
                break;
            case "B4x1":
                B4x1.setText(impotantBTN);
                break;
            case "B4x2":
                B4x2.setText(impotantBTN);
                break;
            case "B4x3":
                B4x3.setText(impotantBTN);
                break;
            case "B5x1":
                B5x1.setText(impotantBTN);
                break;
            case "B5x2":
                B5x2.setText(impotantBTN);
                break;
            case "B5x3":
                B5x3.setText(impotantBTN);
                break;
            case "B6x1":
                B6x1.setText(impotantBTN);
                break;
            case "B6x2":
                B6x2.setText(impotantBTN);
                break;
            case "B6x3":
                B6x3.setText(impotantBTN);
                break;
        }


    }

    public void checkIfCorrect(View view) {

        switch (view.getId()) {
            case R.id.BT_1x1:
                if (B1x1.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_1x2:
                if (B1x2.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_1x3:
                if (B1x3.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_2x1:
                if (B2x1.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_2x2:
                if (B2x2.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_2x3:
                if (B2x3.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_3x1:
                if (B3x1.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_3x2:
                if (B3x2.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_3x3:
                if (B3x3.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_4x1:
                if (B4x1.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_4x2:
                if (B4x2.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_4x3:
                if (B4x3.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_5x1:
                if (B5x1.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_5x2:
                if (B5x2.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_5x3:
                if (B5x3.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_6x1:
                if (B6x1.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_6x2:
                if (B6x2.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
            case R.id.BT_6x3:
                if (B6x3.getText().toString().equals(impotantBTN)) {
                    coorectSeletion();
                    break;
                }
                break;
        }
    }

    public void settup() {

        SetPositionTXT = findViewById(R.id.SetPositionTXT);
        taskTime = findViewById(R.id.Timer);
        MainImg = findViewById(R.id.MainImage);

        B1x1 = findViewById(R.id.BT_1x1);
        B1x2 = findViewById(R.id.BT_1x2);
        B1x3 = findViewById(R.id.BT_1x3);

        B2x1 = findViewById(R.id.BT_2x1);
        B2x2 = findViewById(R.id.BT_2x2);
        B2x3 = findViewById(R.id.BT_2x3);

        B3x1 = findViewById(R.id.BT_3x1);
        B3x2 = findViewById(R.id.BT_3x2);
        B3x3 = findViewById(R.id.BT_3x3);

        B4x1 = findViewById(R.id.BT_4x1);
        B4x2 = findViewById(R.id.BT_4x2);
        B4x3 = findViewById(R.id.BT_4x3);

        B5x1 = findViewById(R.id.BT_5x1);
        B5x2 = findViewById(R.id.BT_5x2);
        B5x3 = findViewById(R.id.BT_5x3);

        B6x1 = findViewById(R.id.BT_6x1);
        B6x2 = findViewById(R.id.BT_6x2);
        B6x3 = findViewById(R.id.BT_6x3);

        LoadSettings();
    }

    public void LoadSettings() {
        SharedPreferences GetSettings = this.getSharedPreferences("Settings", MODE_PRIVATE);
        ShowTimer = GetSettings.getBoolean("ShowTimer", false);


        if (ShowTimer) {
            taskTime.setVisibility(View.VISIBLE);
        } else {
            taskTime.setVisibility(View.INVISIBLE);
        }
    }

    public void buttonClicked(View v) {
        checkIfCorrect(v);
    }

    private Runnable updateTimerThread = new Runnable() {
        @SuppressLint("SetTextI18n")
        public void run() {

            if(Killme){
                return;
            }

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedTime % 1000);

            if (secs == 59) {
                getNewSet();
            }

            if (milliseconds < 100) {
                taskTime.setText(secs + ".0" + milliseconds);
                CurTaskTime = secs + ".0" + milliseconds;
            } else {
                taskTime.setText(secs + "." + milliseconds);
                CurTaskTime = secs + "." + milliseconds;
            }

            TimerHandler.postDelayed(this, 0);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Killme = true;
        Intent GoToMainMenu = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(GoToMainMenu);
        finish();
    }
}