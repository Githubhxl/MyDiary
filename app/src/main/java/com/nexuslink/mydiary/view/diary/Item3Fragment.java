package com.nexuslink.mydiary.view.diary;

import android.content.Intent;
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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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
    private ImageView addPhoto;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_item3,container,false);
        editTitle = (EditText) view.findViewById(R.id.edit_title);
        editeContent = (EditText) view.findViewById(R.id.edit_content);
        presenter = new Item3PresenterImpl(this);
        finish = (ImageView) view.findViewById(R.id.finish);
        addPhoto = (ImageView) view.findViewById(R.id.add_photo);
        frag3 = view.findViewById(R.id.frag3);
        addPhoto.setOnClickListener(this);
        finish.setOnClickListener(this);
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
}
