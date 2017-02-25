package com.nexuslink.mydiary.Custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nexuslink.mydiary.R;

public class MyDiaryDialog extends Dialog {
  /*  private TextView title;*/
    public static final int MODEL_NOTIFY = 0;
    public static final int MODEL_ADD_DIARY_TITLE = 1;
    public static final int MODEL_ADD_URL = 2;

    private int model;
    private TextView textMessage;
    private EditText editText1;
    private EditText editText2;
    private TextView title1;
    private TextView title2;
    private Button buttonOk;
    private Button buttonCancel;

    private onCancelClickListener onCancelClickListener;
    private onOkClickListener onOkClickListener;

    public void setModel(int model) {
        this.model = model;
        if(model == MyDiaryDialog.MODEL_ADD_DIARY_TITLE){
            //添加日记标题
            getTextMessage().setVisibility(View.GONE);
            getTitle2().setVisibility(View.GONE);
            getEditText2().setVisibility(View.GONE);
            getTitle1().setText("标题");
        }else if(model == MyDiaryDialog.MODEL_ADD_URL){
            //添加url的Dialog
        }else if(model == MyDiaryDialog.MODEL_NOTIFY){
            //提醒的Dialog
        }
    }

    public TextView getTextMessage() {
        return textMessage;
    }

    public TextView getTitle1() {
        return title1;
    }

    public TextView getTitle2() {
        return title2;
    }

    public EditText getEditText2() {
        return editText2;
    }

    public EditText getEditText1() {
        return editText1;
    }

    public void setOnCancelClickListener(MyDiaryDialog.onCancelClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    public void setOnOkClickListener(MyDiaryDialog.onOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public MyDiaryDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    public interface onCancelClickListener{
        void onCancelClick();
    }

    public interface onOkClickListener{
        void onOkClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mydiary);
        setCancelable(false);
        initView();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOkClickListener != null){
                    onCancelClickListener.onCancelClick();
                }
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOkClickListener != null){
                    onOkClickListener.onOkClick();
                }

            }
        });


    }

    private void initView() {
        textMessage = (TextView) findViewById(R.id.dialog_message);
        title1 = (TextView) findViewById(R.id.title1);
        title2 = (TextView) findViewById(R.id.title2);
        editText1 = (EditText) findViewById(R.id.view1_dialog);
        editText2 = (EditText) findViewById(R.id.view2_dialog);
        buttonCancel = (Button) findViewById(R.id.no);
        buttonOk = (Button) findViewById(R.id.yes);
    }
}
