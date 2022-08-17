package com.app.aimosheng.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

public class TextUtil {
    public static boolean IsEmpty(EditText view) {
        if (view != null ) {

            String s=view.getText().toString();
            if (s.equals("")||s==null){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }

    public static String Text(Activity context, int id){
        TextView textView = context.findViewById(id);
        return textView.getText().toString();
    }

}
