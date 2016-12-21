package com.nexuslink.mydiary.view.diary;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.nexuslink.mydiary.R;
import com.nexuslink.mydiary.presenter.IItem3Presenter;
import com.nexuslink.mydiary.presenter.Item3PresenterImpl;
import com.nexuslink.mydiary.view.MainActivity;
import com.nexuslink.mydiary.view.entries.Item1Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rye on 2016/12/3.
 */

public class Item3Fragment extends Fragment implements View.OnClickListener,IItem3View {
    private EditText editTitle;
    private EditText editeContent;
    private IItem3Presenter presenter;
    private int TAKE_PHOTO = 0;
    private int CROP_PHOTO = 1;
    private int SELECT_PIC = 2;
    private int CROP_PHOTO2 = 3;
    private Uri imageUri;
    private Uri imagUri2;
    private View frag3;
    private ImageView finish;
    private ImageView position;
    private ImageView voice;
    private ImageView addPhoto;
    private AppCompatSpinner moodSpiner;
    private AppCompatSpinner weatherSpiner;
    private List<Integer> moodList;
    private List<Integer> weatherList;
    private LinearLayout extra;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_item3,container,false);
        editTitle = (EditText) view.findViewById(R.id.edit_title);
        editeContent = (EditText) view.findViewById(R.id.edit_content);
        presenter = new Item3PresenterImpl(this);
        finish = (ImageView) view.findViewById(R.id.finish);
        addPhoto = (ImageView) view.findViewById(R.id.add_photo);
        position = (ImageView) view.findViewById(R.id.position);
        voice = (ImageView) view.findViewById(R.id.voice);
        moodSpiner = (AppCompatSpinner) view.findViewById(R.id.mood_spinner);
        weatherSpiner = (AppCompatSpinner) view.findViewById(R.id.weather_spinner);
        moodList = Arrays.asList(R.drawable.ic_mood_happy,R.drawable.ic_mood_soso,R.drawable.ic_mood_unhappy);
        weatherList = Arrays.asList(R.drawable.ic_weather_cloud,R.drawable.ic_weather_foggy,R.drawable.ic_weather_rainy,
        R.drawable.ic_weather_snowy,R.drawable.ic_weather_sunny,R.drawable.ic_weather_windy);

        extra = (LinearLayout) view.findViewById(R.id.Extra);
        moodSpiner.setAdapter(new MyMoodAdapter(moodList));
        weatherSpiner.setAdapter(new MyMoodAdapter(weatherList));
        frag3 = view.findViewById(R.id.frag3);
        addPhoto.setOnClickListener(this);
        finish.setOnClickListener(this);
        position.setOnClickListener(this);
        voice.setOnClickListener(this);
        return view;
    }

    private void addPic(Drawable drawable) {
        drawable.setBounds(0,0,drawable.getIntrinsicWidth()*2, drawable.getIntrinsicHeight()*2);
        int start = editeContent.getSelectionStart();
        SpannableString spannable = new SpannableString(editeContent.getText().insert(editeContent.getSelectionStart(),"[photo]"));
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(span,start,start+ 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
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
                new AlertView("添加照片",null, "取消", null,
                        new String[]{ "图库中选取", "拍照"},
                        getContext(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        switch (position){
                            case 0:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent,SELECT_PIC);
                                /*startActivityForResult(intent,CROP_PHOTO);*/
                                break;
                            case 1:
                                getDrawable();
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.voice:
                new AlertView("添加声音",null, "取消", null,
                        new String[]{ "录制声音", "添加音乐"},
                        getContext(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        switch (position){
                            case 0:

                                break;
                            case 1:
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.voice_card,null);
                                extra.addView(view);
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.position:
                new AlertView("添加位置",null, "取消", null,
                        new String[]{ "当前位置", "自定义位置"},
                        getContext(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        switch (position){
                            case 0:
                                View view = LayoutInflater.from(getContext()).inflate(R.layout.position_card,null);
                                extra.addView(view);
                                break;
                            case 1:

                                break;
                        }
                    }
                }).show();
                break;
        }
    }

    private Drawable getDrawable() {
        File file = new File(Environment.getExternalStorageDirectory(),"photo.jpg");
        if(file.exists()){
            file.delete();
        }else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageUri = Uri.fromFile(file);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
        return  null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if(resultCode == getActivity().RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,CROP_PHOTO);
                }
                break;
            case 1:
                if(resultCode == getActivity().RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        Drawable drawable = new BitmapDrawable(bitmap);
                        addPic(drawable);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                /*Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Drawable drawable = new BitmapDrawable(bitmap);
                addPic(drawable);*/
                try{
                    imagUri2 = data.getData();
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imagUri2,"image/*");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imagUri2);
                    startActivityForResult(intent,CROP_PHOTO2);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
            case 3:
                if(resultCode == getActivity().RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imagUri2));
                        Drawable drawable = new BitmapDrawable(bitmap);
                        addPic(drawable);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private class MyMoodAdapter implements SpinnerAdapter {
        private List<Integer> moodList;
        public MyMoodAdapter(List<Integer> moodList) {
            this.moodList = moodList;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_mood,parent,false);
            ImageView imageView = (ImageView) view.findViewById(R.id.mood_image);
            imageView.setImageDrawable(getActivity().getDrawable(moodList.get(position)));
            return view;

        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return moodList.size();
        }

        @Override
        public Object getItem(int position) {
            return moodList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_mood,parent,false);
            ImageView imageView = (ImageView) view.findViewById(R.id.mood_image);
            imageView.setImageDrawable(getActivity().getDrawable(moodList.get(position)));
            return view;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}
