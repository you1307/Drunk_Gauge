package com.thetechnoobs.reactiontest;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static java.security.AccessController.getContext;

public class ReactionTestCustomViewBtn extends View {

    private final static int NORMAL_COLOR = Color.BLUE;
    private final static int PRESSED_COLOR = Color.RED;

    public ReactionTestCustomViewBtn(Context context) {
        super(context);
        init();
    }

    public ReactionTestCustomViewBtn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReactionTestCustomViewBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.stop_sign);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                return true;

            case MotionEvent.ACTION_UP:
                return true;
        }
        return false;
    }

    // Because we call this from onTouchEvent, this code will be executed for both
    // normal touch events and for when the system calls this using Accessibility
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
