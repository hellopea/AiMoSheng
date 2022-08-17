package com.app.aimosheng.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.aimosheng.R;
import com.app.aimosheng.utils.HttpUtil;
import com.app.aimosheng.utils.Interface;
import com.app.aimosheng.utils.JsonResult;
import com.app.aimosheng.utils.TextUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProductOutCustomActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private Button qurey_productcustom;
    private View div_productoutfragmentlistview;
    private LinearLayout header_procuctcustomlistview;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productoutcustom);
        qurey_productcustom = findViewById(R.id.qurey_productcustom);
        header_procuctcustomlistview = findViewById(R.id.header_procuctcustomlistview);
        div_productoutfragmentlistview = findViewById(R.id.div_productoutfragmentlistview);
        qurey_productcustom.setOnClickListener(this);
        findViewById(R.id.exit_productoutout).setOnClickListener(this);
        listView = findViewById(R.id.listview_custom);

        findViewById(R.id.qurey_productcustom).setOnClickListener(this);
        listView.setBackground(getResources().getDrawable(R.drawable.listview_bg));
        header_procuctcustomlistview.setBackground(getResources().getDrawable(R.drawable.listview_bg));
        qurey_productcustom.setBackground(getResources().getDrawable(R.drawable.round_conner));
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_productoutout:
                finish();
                break;
            case R.id.qurey_productcustom:
                HashMap map = new HashMap();
                map.put("ShipCustName", TextUtil.Text(ProductOutCustomActivity.this, R.id.customerno));
                map.put("PageSize", "100");
                map.put("PageIndex", "1");
                HttpUtil.getInstance().formPost(ProductOutCustomActivity.this, Interface.ORDERLIST, map, new HttpUtil.ICallBack() {
                    @Override
                    public void onResponse(JsonResult result) throws JSONException {
                        int result1 = result.getResult();
                        if (result1 == 0) {
                            String msg = result.getMsg();
                            /*handler = new MyHandler();
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("msg", msg);
                            message.setData(bundle);*/
                            listView.setVisibility(View.VISIBLE);
                            MyAdapter adapter = new MyAdapter(msg);
                            listView.setAdapter(adapter);

                            Toast.makeText(ProductOutCustomActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductOutCustomActivity.this, "获取失败："+result.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String result = bundle.getString("msg");

            /*MyAdapter adapter = new MyAdapter(result);
            ListView listView = findViewById(R.id.listview_custom);
            listView.setAdapter(adapter);*/
        }


    }

    //创建Fragment的适配器
    class MyAdapter extends BaseAdapter {
        private JSONObject object;
        private JSONArray jsonArray;

        public MyAdapter(String msg) throws JSONException {
            object = new JSONObject(msg);
            jsonArray = (JSONArray) object.get("List");
        }

        @Override
        public int getCount() {
            return jsonArray.length();
        }

        @Override
        public Object getItem(int i) {
            try {
                return jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View convertview = View.inflate(ProductOutCustomActivity.this, R.layout.item_custom, null);
            div_productoutfragmentlistview.setVisibility(View.VISIBLE);
            header_procuctcustomlistview.setVisibility(View.VISIBLE);
            TextView item_customno = convertview.findViewById(R.id.item_customno);
            TextView item_customname = convertview.findViewById(R.id.item_customname);
            TextView item_custom_watch = convertview.findViewById(R.id.item_custom_watch);

            if (i%2==1) {
                convertview.setBackgroundColor(getResources().getColor(R.color.item_grey));
            }

            try {
                item_customno.setText((String) (((JSONObject) jsonArray.get(i)).get("ShipCustNo")));
                item_customname.setText((String) (((JSONObject) jsonArray.get(i)).get("ShipCustName")));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            item_custom_watch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProductOutCustomActivity.this, ProductOutDetailActivity.class);
                    intent.putExtra("ShipCustNo", item_customno.getText().toString());
                    intent.putExtra("ShipCustName", item_customname.getText().toString());
                    startActivity(intent);
                }
            });
            return convertview;


        }
    }
}
