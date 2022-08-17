package com.app.aimosheng.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.aimosheng.R;
import com.app.aimosheng.fragment.productverifyactivity.UnverifyFragment;
import com.app.aimosheng.fragment.productverifyactivity.VerifyFragment;
import com.app.aimosheng.fragment.queryinfoactivity.ProductFragment;
import com.app.aimosheng.fragment.queryinfoactivity.ProductPostionFragment;
import com.app.aimosheng.fragment.queryinfoactivity.SoFragment;
import com.app.aimosheng.utils.HttpUtil;
import com.app.aimosheng.utils.Interface;
import com.app.aimosheng.utils.JsonResult;
import com.app.aimosheng.view.MyDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductoutReviewActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tablayout_productverify;
    private ViewPager viewpager_productverify;
    private ArrayList list;
    private MyAdapter adapter;
    private String[] titles = {"待复核", "已复核"};
    private  EditText customno_review;
    private  EditText customname_review;
    private  EditText barcode_verify;
    private String PartNO;
    private boolean textchanged = false;
    private boolean barcodeinvalid = false;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productoutreview);
        tablayout_productverify = findViewById(R.id.tablayout_productverify);
        viewpager_productverify = findViewById(R.id.viewpager_productverify);
        findViewById(R.id.exit_productverify).setOnClickListener(this);
        tablayout_productverify=  findViewById(R.id.tablayout_productverify);
        viewpager_productverify=  findViewById(R.id.viewpager_productverify);
         list = new ArrayList<>();
        adapter = new MyAdapter(getSupportFragmentManager());
        list.add(new UnverifyFragment());
        list.add(new VerifyFragment());
        viewpager_productverify.setAdapter(adapter);
        tablayout_productverify.setupWithViewPager(viewpager_productverify);



        customno_review = findViewById(R.id.customno_review);
        customname_review = findViewById(R.id.customname_review);
        barcode_verify = findViewById(R.id.barcode_verify);

        customno_review.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String msg= charSequence.toString();
                String[] msgs=msg.split("\\|");
                if (msgs.length==4){
                    customno_review.setText(msgs[0]);
                    customname_review.setText(msgs[1]);
                    PartNO =msgs[2];
                }else {

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (customno_review.getText().toString()!=null&&customno_review.getText().toString()!=""&&
                customname_review.getText().toString()!=null&&customno_review.getText().toString()!=""&&
                        barcode_verify.getText().toString()!=null&&barcode_verify.getText().toString()!=""){
                    //netrequest();
                }

            }
        });

       /* barcode_verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String response = charSequence.toString();
                if (!response.equals("")&&!barcodeinvalid) {
                    String[] results = response.split("-");

                    if (results.length <= 2) {
                        if (!textchanged) {
                            Toast.makeText(ProductoutReviewActivity.this, "条形码中数据格式不正确", Toast.LENGTH_SHORT).show();
                        } else {
                            textchanged = false;
                        }
                    } else if (results.length == 3) {
                        if (!textchanged) {
                            textchanged = true;
                            barcode_verify.setText(results[0]);
                        }
                        if (customno_review.getText().toString()!=null&&customno_review.getText().toString()!=""&&
                                customname_review.getText().toString()!=null&&customno_review.getText().toString()!=""&&
                                barcode_verify.getText().toString()!=null&&barcode_verify.getText().toString()!=""){
                            if (!barcodeinvalid) {
                                check(results);
                            }

                        }
                    } else if (results.length > 3) {
                        if (!textchanged) {
                            textchanged = true;
                            String s = results[0];
                            for (int k = 1; k < results.length - 2; k++) {
                                s = s + "-" + results[k];
                            }
                            if (results.length >= 5) {
                                barcodeinvalid = true;
                            }
                            barcode_verify.setText(s);

                            if (customno_review.getText().toString()!=null&&customno_review.getText().toString()!=""&&
                                    customname_review.getText().toString()!=null&&customno_review.getText().toString()!=""&&
                                    barcode_verify.getText().toString()!=null&&barcode_verify.getText().toString()!=""){

                                check(results);

                            }

                        }
                    }
                }else {
                    barcodeinvalid = false;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
                *//*if (customno_review.getText().toString()!=null&&customno_review.getText().toString()!=""&&
                        customname_review.getText().toString()!=null&&customno_review.getText().toString()!=""&&
                        barcode_verify.getText().toString()!=null&&barcode_verify.getText().toString()!=""){

                        check();

                }*//*
            }
        });*/


        barcode_verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){

                }else {
                    String response = charSequence.toString();
                    String[] results = response.split("-");
                    check(results);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.Q)
    void check(String[] msg){
        HashMap map2 = new HashMap();
        if (msg.length==3){

            map2.put("Barcode", msg[0]);
        }else {
            String s = msg[0];
            for (int k = 1; k < msg.length - 2; k++) {
                s = s + "-" + msg[k];
            }
            map2.put("Barcode", s);
        }
        map2.put("SoNo", msg[1]);
        map2.put("PartNo", PartNO);
        map2.put("Quantity", msg[2]);
        map2.put("UserName", Interface.USERNAME);
        HttpUtil.getInstance().formPost(ProductoutReviewActivity.this, Interface.PALLETlISTCHECK, map2, new HttpUtil.ICallBack() {
            @Override
            public void onResponse(JsonResult result) {
                int result1 = result.getResult();
                if (result1 == 0) {
                    Toast.makeText(ProductoutReviewActivity.this, "复核成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductoutReviewActivity.this, "复核失败:"+result.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void netrequest(){
        HashMap map = new HashMap();

        //todo
       // map.put("ShipCustNo", customno_review.getText().toString());
       // map.put("PalletCode", "");

        map.put("ShipCustNo", "150451321");
        map.put("PalletCode", "1");
        HttpUtil.getInstance().formPost(ProductoutReviewActivity.this, Interface.PALLETlIST, map, new HttpUtil.ICallBack() {
            @Override
            public void onResponse(JsonResult result) {
                int result1 = result.getResult();
                if (result1 == 0) {
                    Toast.makeText(ProductoutReviewActivity.this, "盘点明细列表接口测试成功", Toast.LENGTH_SHORT).show();
                   int state= result.getShipCustState();
                   //0未完成  3已完成
                    Intent intent = new Intent();
                   if (state==0){

                       Dialog dialog =new MyDialog(ProductoutReviewActivity.this);
                       dialog.show();
                   }else {

                   }
                    intent.putExtra("state",state);
                    intent.putExtra("msg",result.getMsg());
                    intent.setAction("refresh.fragment");
                    sendBroadcast(intent);
                } else {
                    Toast.makeText(ProductoutReviewActivity.this, "盘点明细列表接口测试失败:"+result.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

           /* case R.id.check:
                HashMap map2 = new HashMap();
                map2.put("SoNo", "1");
                map2.put("Barcode", "1");
                map2.put("PartNo", "1");
                map2.put("Quantity", "1");
                map2.put("UserName", "1");
                HttpUtil.getInstance().formPost(ProductoutReviewActivity.this, Interface.PALLETlISTCHECK, map2, new HttpUtil.ICallBack() {
                    @Override
                    public void onResponse(JsonResult result) {
                        int result1 = result.getResult();
                        if (result1 == 0) {
                            Toast.makeText(ProductoutReviewActivity.this, "复核成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductoutReviewActivity.this, "复核失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                break;
            case R.id.sealingdetail:
                HashMap map = new HashMap();
                map.put("ShipCustNo", "1");
                map.put("PalletCode", "1");
                HttpUtil.getInstance().formPost(ProductoutReviewActivity.this, Interface.PALLETlIST, map, new HttpUtil.ICallBack() {
                    @Override
                    public void onResponse(JsonResult result) {
                        int result1 = result.getResult();
                        if (result1 == 0) {
                            Toast.makeText(ProductoutReviewActivity.this, "盘点明细列表接口测试成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductoutReviewActivity.this, "盘点明细列表接口测试失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                break;*/

            case R.id.exit_productverify:

                finish();
                break;
        }
    }
    //创建Fragment的适配器
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        //获得每个页面的下标
        @Override
        public Fragment getItem(int position) {
            return (Fragment)list.get(position);
        }
        //获得List的大小
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        textchanged = false;
        if (keyCode==10036){
            if (customno_review.getText().toString().equals("")){
                customno_review.setFocusable(true);
                customno_review.setFocusableInTouchMode(true);
                customno_review.requestFocus();
            }else if (barcode_verify.getText().toString().equals("")){
                customno_review.setFocusable(true);
                customno_review.setFocusableInTouchMode(true);
                barcode_verify.requestFocus();
            }else {
                barcode_verify.setText("");
            }



        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode==10036){
           if (customno_review.isFocused()){


           }
        }

        return super.onKeyUp(keyCode, event);
    }
}
