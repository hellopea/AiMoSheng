package com.app.aimosheng.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.app.aimosheng.utils.TextUtil;

import java.util.HashMap;

public class ProductInActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText product_quality;
    private EditText sono_producton;
    private EditText productin_postion;
    private EditText productin_scan;
    private EditText barcode;
    private boolean textchanged =false;
    private boolean text1changed =false;
    private boolean text2changed =false;
    private String text1 ="";
    private String text2 ="";
    private String text3 ="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productin);
        findViewById(R.id.exit_productin).setOnClickListener(this);
        productin_postion  =findViewById(R.id.productin_postion);
        findViewById(R.id.commit_productin).setOnClickListener(this);
         product_quality = findViewById(R.id.product_quality);
        productin_scan = findViewById(R.id.productin_scan);
        barcode = findViewById(R.id.barcode);
        sono_producton = findViewById(R.id.sono_producton);

        text1= productin_postion.getText().toString();
        text3= barcode.getText().toString();
        text2=productin_scan.getText().toString() ;


         //product_quality = findViewById(R.id.product_quality);
         //product_quality = findViewById(R.id.product_quality);

        //product_quality.setFocusable(true);

        //productin_scan.addTextChangedListener(new MyWathcer());

       /* productin_postion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(productin_postion.getText().toString()==null||productin_postion.getText().toString().equals("")){
                    productin_postion.setText(charSequence.toString());
                    text1 =charSequence.toString();
                    text1changed = true;
                }else if (productin_postion.getText().toString()!=null&&!productin_postion.getText().toString().equals("")&&
                        productin_scan.getText().toString()==null||productin_scan.getText().toString().equals("")){
                    if (text1changed) {
                    }else {
                        text2 =charSequence.toString();
                        productin_scan.setText(charSequence.toString());
                    }
                }else if (productin_postion.getText().toString()==null&&productin_postion.getText().toString().equals("")&&
                        productin_scan.getText().toString()==null&&productin_scan.getText().toString().equals("")&&
                        barcode.getText().toString()==null||barcode.getText().toString().equals("")
                ){
                    String response = charSequence.toString();
                    String[] results = response.split("-");
                    if (results.length <= 2) {
                        if (!textchanged) {
                            Toast.makeText(ProductInActivity.this, "条形码中数据格式不正确", Toast.LENGTH_SHORT).show();
                        }else {
                            textchanged =false;
                        }
                    } else if (results.length == 3) {
                        textchanged =true;
                        barcode.setText(results[0]);
                        sono_producton.setText(results[2]);
                        product_quality.setText(results[1]);

                    } else if (results.length == 4) {
                        textchanged =true;
                        String s = results[0];
                        for (int k=1;k< results.length-2;k++){
                            s=s+"-"+results[k];
                        }

                        barcode.setText(s);
                        sono_producton.setText(results[results.length-1]);
                        product_quality.setText(results[results.length-2]);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        productin_postion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                text1 =charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        productin_scan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                text2 = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    text3 = charSequence.toString();
                    String ss=  sono_producton.getText().toString();
                    if (!ss.equals("")){
                        charSequence="";
                        return;
                    }
                    String response = charSequence.toString();
                    String[] results = response.split("-");
                    if (results.length <= 2) {
                        if (!textchanged) {
                            Toast.makeText(ProductInActivity.this, "条形码中数据格式不正确", Toast.LENGTH_SHORT).show();
                        } else {
                            textchanged = false;
                        }
                    } else if (results.length == 3) {
                        if (!textchanged) {
                            textchanged = true;
                            barcode.setText(results[0]);
                            sono_producton.setText(results[2]);
                            product_quality.setText(results[1]);
                        }

                    } else if (results.length > 3) {
                        if (!textchanged) {
                            textchanged = true;
                            String s = results[0];
                            for (int k = 1; k < results.length - 2; k++) {
                                s = s + "-" + results[k];
                            }

                            barcode.setText(s);
                            sono_producton.setText(results[results.length - 1]);
                            product_quality.setText(results[results.length - 2]);
                        }
                    }
                }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_productin:
                finish();
                break;
            case R.id.commit_productin:

                HashMap map = new HashMap();
               /* map.put("PartNo", TextUtil.Text(ProductInActivity.this,R.id.productin_postion));
                map.put("Location",TextUtil.Text(ProductInActivity.this,R.id.productin_scan));
                map.put("Quantity",TextUtil.Text(ProductInActivity.this,R.id.product_quality));
                map.put("SoNo",TextUtil.Text(ProductInActivity.this,R.id.sono));
                map.put("Barcode",TextUtil.Text(ProductInActivity.this,R.id.barcode));
                map.put("UserName", Interface.USERNAME);*/
                //物料条码
                map.put("PartNo", TextUtil.Text(ProductInActivity.this,R.id.barcode));
                //库位
                map.put("Location",  TextUtil.Text(ProductInActivity.this,R.id.productin_postion));
                map.put("Quantity",  TextUtil.Text(ProductInActivity.this,R.id.product_quality));
                map.put("SoNo",  TextUtil.Text(ProductInActivity.this,R.id.sono_producton));
                map.put("Barcode",  TextUtil.Text(ProductInActivity.this,R.id.productin_scan));
                map.put("UserName", Interface.USERNAME);

                HttpUtil.getInstance().formPost(ProductInActivity.this, Interface.INWAVEHOUSE, map, new HttpUtil.ICallBack() {
                    @Override
                    public void onResponse(JsonResult result) {
                        int result1 = result.getResult();
                        if (result1 == 0) {
                            text1 = "";
                            text2 = "";
                            text3 = "";
                            Toast.makeText(ProductInActivity.this, "入库成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductInActivity.this, "入库失败："+result.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==10036) {
            if (productin_postion.getText().toString().equals("")) {
                productin_postion.setFocusable(true);
                productin_postion.setFocusableInTouchMode(true);
                productin_postion.requestFocus();
            } else if (productin_scan.getText().toString().equals("")) {

                productin_scan.setFocusable(true);
                productin_scan.setFocusableInTouchMode(true);
                productin_scan.requestFocus();
            } else if (barcode.getText().toString().equals("")) {
                barcode.setFocusable(true);
                barcode.setFocusableInTouchMode(true);
                barcode.requestFocus();
            } else {
                Button commit_productin = findViewById(R.id.commit_productin);
                commit_productin.setFocusable(true);
                commit_productin.setFocusableInTouchMode(true);
                commit_productin.requestFocus();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public class MyWathcer implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String response = charSequence.toString();
            String[] results = response.split("-");
            if (results.length <= 2) {
                if (!textchanged) {
                    Toast.makeText(ProductInActivity.this, "条形码中数据格式不正确", Toast.LENGTH_SHORT).show();
                }else {
                    textchanged =false;
                }
            } else if (results.length == 3) {
                textchanged =true;
                barcode.setText(results[0]);
                sono_producton.setText(results[2]);
                product_quality.setText(results[1]);

            } else if (results.length == 4) {
                textchanged =true;
                String s = results[0];
                for (int k=1;k< results.length-2;k++){
                    s=s+"-"+results[k];
                }

                barcode.setText(s);
                sono_producton.setText(results[results.length-1]);
                product_quality.setText(results[results.length-2]);

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
