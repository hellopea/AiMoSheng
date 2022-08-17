package com.app.aimosheng.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.app.aimosheng.R;
import com.app.aimosheng.utils.ColorUtils;

import java.nio.Buffer;

public class MyDialog extends Dialog{
    @SuppressLint("ResourceAsColor")
    public MyDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog);
        Button cancel_button=findViewById(R.id.cancel_button);
        Button confirm_button=findViewById(R.id.confirm_button);
        cancel_button.setBackgroundColor(getContext().getResources().getColor(R.color.item_grey));
        confirm_button.setBackgroundColor(getContext().getResources().getColor(R.color.button_query));
        findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


}
