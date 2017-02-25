package com.nexuslink.mydiary.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nexuslink.mydiary.Custom.MyDiaryDialog;
import com.nexuslink.mydiary.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryPage1Fragment extends Fragment {
    private String currentTitle = "";
    private static DiaryPage1Fragment instance;
    @BindView(R.id.diary_title)
    TextView titleView;
    @BindView(R.id.edit_diary)
    public EditText editDiary;
    @BindView(R.id.add_diary)
    TextView addDiary;
    @BindView(R.id.card_no_diary)
    CardView cardView;


    @OnClick(R.id.add_diary) void startInput(){
        final MyDiaryDialog myDiaryDialog = new MyDiaryDialog(getContext());
        myDiaryDialog.setOnCancelClickListener(new MyDiaryDialog.onCancelClickListener() {
            @Override
            public void onCancelClick() {
                myDiaryDialog.dismiss();
            }
        });
        myDiaryDialog.setOnOkClickListener(new MyDiaryDialog.onOkClickListener() {
            @Override
            public void onOkClick() {
                myDiaryDialog.dismiss();
                String title = myDiaryDialog.getEditText1().getText().toString();
                currentTitle = title;
                titleView.setText(title);
                showAddCard(false);
            }
        });
        myDiaryDialog.show();
        myDiaryDialog.setModel(MyDiaryDialog.MODEL_ADD_DIARY_TITLE);
    }

    public static DiaryPage1Fragment getInstance(){
        if(instance == null){
            instance = new DiaryPage1Fragment();
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_page1, container, false);
        ButterKnife.bind(this,view);
        titleView.setText(currentTitle);
        Log.d("tag","title"+currentTitle);
        if(titleView.getText() != ""){
            showAddCard(false);
        }else{
            showAddCard(true);
        }
        return view;
    }

    public void showAddCard(Boolean isShow){
        if(isShow){
            cardView.setVisibility(View.VISIBLE);
            editDiary.setVisibility(View.GONE);
            titleView.setVisibility(View.GONE);
        }else {
            cardView.setVisibility(View.GONE);
            editDiary.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.VISIBLE);
        }
    }

    public EditText getEditDiary() {
        return editDiary;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }
}
