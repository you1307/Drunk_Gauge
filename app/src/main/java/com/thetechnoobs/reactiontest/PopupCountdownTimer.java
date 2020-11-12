package com.thetechnoobs.reactiontest;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.Objects;

public class PopupCountdownTimer{
    private final Handler TimerHandler = new Handler();
    private Activity activity;
    private Dialog dialog;
    TextView DownTimerTXT;
    ImageView CountIMG;

    public void popupDialog(Activity activity) {
        this.activity = activity;

        setDialog();
        findViews();
        setData();

    }

    public void showDialog(){
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        StartDownTimer();
    }



    public void dismiss(){
        dialog.dismiss();
    }

    private void setData() {
        DownTimerTXT.setText("5");
    }

    public void findViews(){
        DownTimerTXT = dialog.findViewById(R.id.POPCountDownTXT);
    }



    public void StartDownTimer(){
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                DownTimerTXT.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                dismiss();
            }
        }.start();
    }


    private void setDialog() {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_countown);
    }


}


