package com.nexuslink.mydiary.view;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.nexuslink.mydiary.R;
import com.nexuslink.mydiary.data.DataTools;
import com.nexuslink.mydiary.data.MyDiaryDatabaseHelper;
import com.nexuslink.mydiary.model.DiaryModel;
import com.nexuslink.mydiary.presenter.IItem3Presenter;
import com.nexuslink.mydiary.presenter.IPresenter;
import com.nexuslink.mydiary.presenter.Item3PresenterImpl;
import com.nexuslink.mydiary.presenter.PresenterImpl;

/**
 * Created by Rye on 2016/12/3.
 */

public class Item3Fragment extends Fragment implements View.OnClickListener,IItem3View{
    private EditText editTitle;
    private EditText editeContent;
    private IItem3Presenter presenter;

    private ImageView finish;
    private ImageView addPhoto;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_item3,container,false);
        editTitle = (EditText) view.findViewById(R.id.edit_title);
        editTitle.setGravity(Gravity.CENTER);
        editeContent = (EditText) view.findViewById(R.id.edit_content);
        presenter = new Item3PresenterImpl(this);
        finish = (ImageView) view.findViewById(R.id.finish);
        addPhoto = (ImageView) view.findViewById(R.id.add_photo);
        addPhoto.setOnClickListener(this);
        finish.setOnClickListener(this);

        return view;
    }

    private void addPic(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth()* 10/25, drawable.getIntrinsicHeight()* 10/25);
        SpannableString spannable = new SpannableString(editeContent.getText().toString()+"[photo]");
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(span, editeContent.getText().length(),editeContent.getText().length()+"[photo]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        editeContent.setText(spannable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finish:
                presenter.saveDiary(getContext(),editTitle.getText().toString(),editeContent.getText().toString());
                editTitle.setText("");
                editeContent.setText("");
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.iPresenter.changeFragment(new Item1Fragment());
                mainActivity.iPresenter.ItemChange(1);
                break;
            case R.id.add_photo:
                new AlertView("添加", "选择照片路径", "取消", null,
                        new String[]{ "图库中选取", "拍照"},
                        getContext(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        switch (position){
                            case 0:
                                Drawable drawable = getResources().getDrawable(R.drawable.wall);
                                addPic(drawable);
                                break;
                            case 1:
                                break;
                        }
                    }
                }).show();
                break;
        }
    }
}
