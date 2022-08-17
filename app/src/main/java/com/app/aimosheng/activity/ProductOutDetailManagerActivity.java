package com.app.aimosheng.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.app.aimosheng.R;
import com.app.aimosheng.utils.HttpUtil;
import com.app.aimosheng.utils.Interface;
import com.app.aimosheng.utils.JsonResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ProductOutDetailManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView productmanager_customno;
    private TextView productmanager_position;
    private TextView productmanager_scan;
    private TextView productmanager_sono;
    private EditText productmanager_barcode;
    private TextView productmanager_no;
    private TextView productmanager_allocated;
    private String PartNo;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetailmanager);
        findViewById(R.id.exit_productoutmanager).setOnClickListener(this);
       Button sealing=findViewById(R.id.sealing);
       Button commit_productout=findViewById(R.id.commit_productout);
        sealing.setBackground(getResources().getDrawable(R.drawable.round_conner_fengpan));
        commit_productout.setBackground(getResources().getDrawable(R.drawable.round_conner_commitout));


        findViewById(R.id.sealing).setOnClickListener(this);
        findViewById(R.id.commit_productout).setOnClickListener(this);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");


        try {
            JSONObject message = new JSONObject(msg);
            PartNo = (String) message.get("PartNo");
            productmanager_customno = findViewById(R.id.productmanager_customno);
            productmanager_position = findViewById(R.id.productmanager_position);
            productmanager_scan = findViewById(R.id.productmanager_scan);
            productmanager_sono = findViewById(R.id.productmanager_sono);
            productmanager_barcode = findViewById(R.id.productmanager_barcode);
            productmanager_no = findViewById(R.id.productmanager_no);
            productmanager_allocated = findViewById(R.id.productmanager_allocated);

            productmanager_customno.setText(intent.getStringExtra("ShipCustNo"));
            productmanager_position.setText((String) message.get("Location"));
            productmanager_scan.setText("");
            productmanager_sono.setText((String) message.get("SoNo"));
            productmanager_barcode.setText("");
            //productmanager_barcode.setText((String)message.get("PartNo"));

            productmanager_no.setText((int) message.get("Quantity") + "");
            productmanager_allocated.setText((int) message.get("TrueQuantity") + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_productoutmanager:
                finish();
                break;

            case R.id.sealing:
                HashMap map = new HashMap();
                map.put("ShipCustNo", getIntent().getStringExtra("ShipCustNo"));
                map.put("UserName", Interface.USERNAME);
                HttpUtil.getInstance().formPost(ProductOutDetailManagerActivity.this, Interface.SEALORDER, map, new HttpUtil.ICallBack() {
                    @Override
                    public void onResponse(JsonResult result) {
                        int result1 = result.getResult();
                        if (result1 == 0) {
                            Intent intent = new Intent(ProductOutDetailManagerActivity.this, QRcodeActivity.class);
                            String QRmsg = getIntent().getStringExtra("ShipCustNo") + "|"
                                    + getIntent().getStringExtra("ShipCustName") + "|"
                                    + PartNo + "|"
                                    + getTime()
                            ;
                            intent.putExtra("QRmsg",QRmsg);
                            startActivity(intent);
                            Toast.makeText(ProductOutDetailManagerActivity.this, "封盘成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductOutDetailManagerActivity.this, "封盘失败:"+result.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case R.id.commit_productout:
                HashMap map2 = new HashMap();
                map2.put("Location", productmanager_position.getText().toString());
                map2.put("SoNo", productmanager_sono.getText().toString());
                map2.put("Barcode", productmanager_barcode.getText().toString());
                map2.put("PartNo", PartNo);
                map2.put("Quantity", productmanager_no.getText().toString());
                map2.put("UserName", Interface.USERNAME);
                HttpUtil.getInstance().formPost(ProductOutDetailManagerActivity.this, Interface.EXWAVEHOUSE, map2, new HttpUtil.ICallBack() {
                    @Override
                    public void onResponse(JsonResult result) {
                        int result1 = result.getResult();
                        if (result1 == 0) {

                            Toast.makeText(ProductOutDetailManagerActivity.this, "出库成功", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ProductOutDetailManagerActivity.this, "出库失败:"+result.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       /* productmanager_barcode.setFocusable(true);
        productmanager_barcode.setFocusableInTouchMode(true);
        productmanager_barcode.requestFocus();*/

        return super.onKeyDown(keyCode, event);
    }

    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String t = format.format(new Date());
        return  t;
    }




}
