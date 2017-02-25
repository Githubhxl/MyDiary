package com.nexuslink.mydiary.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nexuslink.mydiary.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryPage2Fragment extends Fragment {
    String[] mVals = new String[]{"暂不支持标签功能"};
    private static DiaryPage2Fragment instance;
    @BindView(R.id.edit_position)
    public
    EditText editText;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.group_weather)
    RadioGroup weatherSmoothRadioGroup;
    @BindView(R.id.group_mood)
    RadioGroup moodSmoothRadioGroup;


    public DiaryPage2Fragment() {
        // Required empty public constructor
    }

    public static DiaryPage2Fragment getInstance() {
        if (instance == null) {
            instance = new DiaryPage2Fragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary_page2, container, false);
        ButterKnife.bind(this,view);

        moodSmoothRadioGroup.check(R.id.mood_default);
        weatherSmoothRadioGroup.check(R.id.weather_default);
        tagFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View card = LayoutInflater.from(getContext()).inflate(R.layout.tv,tagFlowLayout,false);
                TextView tv = (TextView) card.findViewById(R.id.tv);
                tv.setText(s);
                return card;
            }
        });
        return view;
    }

    public EditText getEditText() {
        return editText;
    }
}
