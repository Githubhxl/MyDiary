package com.nexuslink.mydiary.view;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nexuslink.mydiary.R;
import com.nexuslink.mydiary.data.DataTools;
import com.nexuslink.mydiary.data.MyDiaryDatabaseHelper;
import com.nexuslink.mydiary.model.DiaryModel;
import com.nexuslink.mydiary.presenter.IItem3Presenter;
import com.nexuslink.mydiary.presenter.IPresenter;
import com.nexuslink.mydiary.presenter.Item3PresenterImpl;
import com.nexuslink.mydiary.presenter.PresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rye on 2016/12/3.
 */

public class Item3Fragment extends Fragment implements View.OnClickListener,IItem3View{
    private EditText editTitle;
    private EditText editeContent;
    private IItem3Presenter presenter;

    private TextView test;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_item3,container,false);
        editTitle = (EditText) view.findViewById(R.id.edit_title);
        editeContent = (EditText) view.findViewById(R.id.edit_content);
        presenter = new Item3PresenterImpl(this);
        //测试
        test = (TextView) view.findViewById(R.id.test);
        test.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test:
                presenter.saveDiary(getContext(),editTitle.getText().toString(),editeContent.getText().toString());
                jumptoItem1Frag();
                break;
        }
    }

    private void jumptoItem1Frag() {
        getFragmentManager().beginTransaction().replace(R.id.framlayout,new Item1Fragment()).commit();
    }
}
