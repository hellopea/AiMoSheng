package com.app.aimosheng.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.aimosheng.R;
import com.app.aimosheng.utils.EncodingUtils;

import java.util.Calendar;

public class QRcodeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Intent intent = getIntent();
        String QRmsg = intent.getStringExtra("QRmsg");
        findViewById(R.id.exit_qractivity).setOnClickListener(this);
       String[] msgs= QRmsg.split("\\|");
       if (msgs.length==4) {
           ImageView imageView = findViewById(R.id.qr_image);
           TextView printtime = findViewById(R.id.printtime);
           TextView name = findViewById(R.id.name);
           TextView number = findViewById(R.id.number);


           Bitmap bitmap = EncodingUtils.createQRCode(QRmsg, 100, 100, null);
           if (bitmap != null) {
               imageView.setImageBitmap(bitmap);
           } else {
               Toast.makeText(this, "生成二维码失败", Toast.LENGTH_SHORT).show();
           }
           number.setText(msgs[0]);
           name.setText(msgs[1]);
           printtime.setText("打印时间：" + msgs[3]);
       }else {
           Toast.makeText(this,"检查所传参数",Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_qractivity:
                finish();
                break;
        }
    }
}
