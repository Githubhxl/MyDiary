package com.nexuslink.mydiary.Tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.widget.EditText;

import com.zzhoujay.glideimagegetter.GlideImageGetter;
import com.zzhoujay.richtext.RichText;

import java.io.FileNotFoundException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rye on 2017/2/5.
 */

public class EditDiaryTool {
    public static void insertImage(EditText editText, Uri imageUri, Context context){

        Drawable d = null;
        try {
            d = Drawable.createFromStream(context.getContentResolver().openInputStream(imageUri),null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //缩放倍速
        float scaleWidth = ((float) editText.getWidth())/d.getIntrinsicWidth();
        d.setBounds(0,0,editText.getWidth(), (int) (d.getIntrinsicHeight() * scaleWidth));
        ImageSpan imageSpan = new ImageSpan(d);
        String stringImage = "<img src=\"" + imageUri + "\" />";
        SpannableString spannableString = new SpannableString(stringImage);
        spannableString.setSpan(imageSpan,0,stringImage.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int index = editText.getSelectionStart();
        Editable edit = editText.getEditableText();

        if (index < 0 || index >= edit.length()) {
            edit.append(spannableString);
        } else {
            edit.insert(index, spannableString);
        }
        edit.insert(index + spannableString.length(), "\n\n");
    }

   /* public static void insertLink(String text,String url,EditText editText){
        URLSpan urlSpan = new URLSpan(url);
        String strngUrl = "<a href=\""+url+"\">"+text+"</a>";
        SpannableString spannableString = new SpannableString(strngUrl);
        spannableString.setSpan(urlSpan,0,strngUrl.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Editable edit = editText.getEditableText();
        int index = editText.getSelectionStart();
        if (index < 0 || index >= edit.length()) {
            edit.append(spannableString);
        } else {
            edit.insert(index, spannableString);
        }
    }*/

}
