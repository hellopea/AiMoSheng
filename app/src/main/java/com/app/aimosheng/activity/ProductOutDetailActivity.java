package com.app.aimosheng.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.app.aimosheng.R;
import com.app.aimosheng.fragment.queryinfoactivity.SoFragment;
import com.app.aimosheng.utils.HttpUtil;
import com.app.aimosheng.utils.Interface;
import com.app.aimosheng.utils.JsonResult;
import com.app.aimosheng.utils.TextUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProductOutDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout header_productdetail;
    private ListView productdetail_listview;
    private View div_productdetail;
   private Intent intent;
   private EditText productdetail_sono;
   private EditText productdetail_partno;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productoutdetail);
        findViewById(R.id.exit_productoutdetail).setOnClickListener(this);


      Button clear=  findViewById(R.id.clear);
      clear.setOnClickListener(this);

        findViewById(R.id.clear).setOnClickListener(this);
       Button productdetail_query= findViewById(R.id.productdetail_query);
         intent= getIntent();
        productdetail_query.setOnClickListener(this);
        TextView productdetail_customno = findViewById(R.id.productdetail_customno);
        TextView productdetail_customname = findViewById(R.id.productdetail_customname);

        productdetail_customno.setText(intent.getStringExtra("ShipCustNo"));
        productdetail_customname.setText(intent.getStringExtra("ShipCustName"));
        productdetail_query.setBackground(getResources().getDrawable(R.drawable.round_conner));
        clear.setBackground(getResources().getDrawable(R.drawable.round_conner_light));
        header_productdetail=findViewById(R.id.header_productdetail);
        productdetail_sono=findViewById(R.id.productdetail_sono);
        productdetail_partno=findViewById(R.id.productdetail_partno);
        header_productdetail.setBackground(getResources().getDrawable(R.drawable.listview_bg));

        div_productdetail=findViewById(R.id.div_productdetail);
        productdetail_listview=findViewById(R.id.productdetail_listview);

        productdetail_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (jsonArray !=null){
                    try {
                       String msg = (String) (jsonArray.get(i).toString());
                        Intent intent2 = new Intent(ProductOutDetailActivity.this,ProductOutDetailManagerActivity.class);
                        intent2.putExtra("ShipCustNo",intent.getStringExtra("ShipCustNo"));
                        intent2.putExtra("ShipCustName",intent.getStringExtra("ShipCustName"));
                        intent2.putExtra("msg",msg);
                        startActivity(intent2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.exit_productoutdetail:
                finish();
                break;
            case R.id.clear:
               ((EditText)findViewById(R.id.productdetail_sono)).setText("");
                ((EditText)findViewById(R.id.productdetail_partno)).setText("");
                productdetail_partno.setText("");
                productdetail_sono.setText("");
                break;
            case R.id.productdetail_query:
                HashMap map = new HashMap();
                //map.put("ShipCustName", TextUtil.Text(ProductOutDetailActivity.this,R.id.productdetail_customname));
                map.put("ShipCustNo", intent.getStringExtra("ShipCustNo"));
                map.put("PartNo", productdetail_partno.getText().toString());
                map.put("SoNo", productdetail_sono.getText().toString());

                HttpUtil.getInstance().formPost(ProductOutDetailActivity.this, Interface.ORDERDETAIL, map, new HttpUtil.ICallBack() {
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


                            Toast.makeText(ProductOutDetailActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProductOutDetailActivity.this,ProductOutDetailManagerActivity.class);
                            productdetail_listview.setAdapter(new MyAdapter(result.getMsg()));

                        } else {
                            Toast.makeText(ProductOutDetailActivity.this, "获取失败,"+result.getMsg(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                break;
        }
    }
    private JSONArray jsonArray;

    //创建Fragment的适配器
    class MyAdapter extends BaseAdapter {
        private JSONObject object;


        public MyAdapter(String msg) throws JSONException {

            jsonArray = new JSONArray(msg);
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

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View convertview  =View.inflate(ProductOutDetailActivity.this,R.layout.item_productdetailactivity_listvire,null);
            productdetail_listview.setBackground(getResources().getDrawable(R.drawable.listview_bg));

            TextView item1=convertview.findViewById(R.id.item1);
            TextView item2=convertview.findViewById(R.id.item2);
            TextView item3=convertview.findViewById(R.id.item3);
            TextView item4=convertview.findViewById(R.id.item4);
            TextView item5=convertview.findViewById(R.id.item5);
            div_productdetail.setVisibility(View.VISIBLE);
            header_productdetail.setVisibility(View.VISIBLE);
            productdetail_listview.setBackground(getResources().getDrawable(R.drawable.listview_bg));

            if (i%2==1) {
                convertview.setBackgroundColor(getResources().getColor(R.color.item_grey));
            }
            try {
                item1.setText((String)(((JSONObject)jsonArray.get(i)).get("SoNo")));
                item2.setText((String)(((JSONObject)jsonArray.get(i)).get("PartNo")));
                //item3.setText((String)(((JSONObject)jsonArray.get(i)).get("Location")));
               JSONObject obj= (JSONObject) jsonArray.get(i);
             String Location= (String) obj.get("Location");

                if (Location == null) {
                    item3.setText("null");
                } else {
                    //item3.setText((String) (((JSONObject) jsonArray.get(i)).get("Location")));
                    //todo
                    item3.setText(Location);
                }

                item4.setText((int)(((JSONObject)jsonArray.get(i)).get("Quantity"))+"");
                item5.setText((int)(((JSONObject)jsonArray.get(i)).get("TrueQuantity"))+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }



            /*item_custom_watch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(ProductOutCustomActivity.this,ProductOutDetailActivity.class);
                    intent.putExtra("ShipCustNo",item_customno.getText().toString());
                    intent.putExtra("ShipCustName",item_customname.getText().toString());
                    startActivity(intent);
                }
            });*/
            return convertview;



        }
    }


}
