package com.nexuslink.mydiary.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nexuslink.mydiary.R;

public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setSize(200,200);
        drawable.setCornerRadius(5);
        drawable.setStroke(1, Color.parseColor("#cccccc"));
        drawable.setColor(Color.parseColor("#eeeeee"));

    }
}
