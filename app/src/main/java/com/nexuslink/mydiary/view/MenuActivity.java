package com.nexuslink.mydiary.view;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nexuslink.mydiary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.item_phone)
    LinearLayout linearLayoutPhone;
    @BindView(R.id.item_diary)
    LinearLayout linearLayoutDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        linearLayoutDiary.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_diary:
                Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.myanim2,R.anim.myanim2);
                finish();
        }
    }
}
