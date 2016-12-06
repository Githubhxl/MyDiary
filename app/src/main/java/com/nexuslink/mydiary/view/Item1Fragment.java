package com.nexuslink.mydiary.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nexuslink.mydiary.R;

/**
 * Created by Rye on 2016/12/3.
 */

public class Item1Fragment extends Fragment implements View.OnClickListener {
    private LinearLayout diaryLinear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_item1,container,false);
        ImageView menu = (ImageView) view.findViewById(R.id.menu);
        menu.setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.month_diary_linear);
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.item_month,null);
        diaryLinear = (LinearLayout) view1.findViewById(R.id.item_diary_linear);
        linearLayout.addView(LayoutInflater.from(getContext()).inflate(R.layout.item_diary,null));

        linearLayout.addView(view1,0);
        linearLayout.addView(LayoutInflater.from(getContext()).inflate(R.layout.item_month,null));

        return view;
    }

    private void addDiary(LinearLayout linearLayout) {
        linearLayout.addView(LayoutInflater.from(getContext()).inflate(R.layout.item_diary,null),0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu:
                /*Intent intent = new Intent(getActivity(),MenuActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.myanim,R.anim.myanim);
                getActivity().finish();*/
                addDiary(diaryLinear);
        }
    }
}
