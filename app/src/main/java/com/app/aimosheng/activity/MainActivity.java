package com.app.aimosheng.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.aimosheng.R;
import com.app.aimosheng.utils.HttpUtil;
import com.app.aimosheng.utils.Interface;
import com.app.aimosheng.utils.JsonResult;
import com.app.aimosheng.utils.TextUtil;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.clear_account).setOnClickListener(this);
        askPermission();
        checkPermission();

      /*  IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("扫描条形码");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();*/
        MyBroadcastReceiver receiver =new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("myintent");
        registerReceiver(receiver,intentFilter);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:

                if (TextUtil.IsEmpty(findViewById(R.id.account)) && TextUtil.IsEmpty(findViewById(R.id.password))) {


                    HashMap map = new HashMap();
                    map.put("UserName", ((EditText) findViewById(R.id.account)).getText().toString());
                    map.put("PassWord", ((EditText) findViewById(R.id.password)).getText().toString());
                    Log.d("up",((EditText) findViewById(R.id.account)).getText().toString()+"---"+((EditText) findViewById(R.id.password)).getText().toString());
                    HttpUtil.getInstance().formPost(MainActivity.this, Interface.LOGIN, map, new HttpUtil.ICallBack() {
                        @Override
                        public void onResponse(JsonResult result) {
                            int result1 = result.getResult();
                            if (result1 == 0) {
                                Interface.USERNAME =((EditText) findViewById(R.id.account)).getText().toString();
                                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }
                        }
                    });

                } else {
                    Toast.makeText(MainActivity.this, "账号密码都不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.clear_account:
                ( (EditText) findViewById(R.id.account)).setText("");
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
     /*   Toast.makeText(this,"keycode====="+keyCode,Toast.LENGTH_SHORT).show();
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("扫描条形码");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();*/

        return super.onKeyDown(keyCode, event);
    }
    private void askPermission(){
        boolean sSRPR=ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)|
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)|
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
        Log.e("msg",Boolean.toString(sSRPR));
        if(sSRPR){
            //begin
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            },0);
            //end
        }

    }

    public Boolean checkPermission() {
        boolean isGranted = true;
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            if (this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }

            Log.i("读写权限获取"," ： "+isGranted);
            if (!isGranted) {
                this.requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
                                .ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        102);
            }
        }
        return isGranted;
    }


    public class MyBroadcastReceiver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {
            intent.putExtra("s","");
        }
    }

}