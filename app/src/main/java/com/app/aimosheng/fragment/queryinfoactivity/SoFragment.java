package com.app.aimosheng.fragment.queryinfoactivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.app.aimosheng.R;
import com.app.aimosheng.activity.QueryInfoActivity;
import com.app.aimosheng.utils.HttpUtil;
import com.app.aimosheng.utils.Interface;
import com.app.aimosheng.utils.JsonResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SoFragment extends Fragment implements View.OnClickListener {
    private EditText sonumber_sofragment;
    private ListView listview_fragment_so;
    private LinearLayout header_sofragment;
    private View div_sofragment;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_so, null);
        listview_fragment_so= view.findViewById(R.id.listview_fragment_so);
        header_sofragment= view.findViewById(R.id.header_sofragment);
        header_sofragment.setBackgroundColor(getResources().getColor(R.color.item_grey));
        header_sofragment.setBackground(getResources().getDrawable(R.drawable.listview_bg));
        Button query_sofragment = view.findViewById(R.id.query_sofragment);
         div_sofragment = view.findViewById(R.id.div_sofragment);
        query_sofragment.setOnClickListener(this);
        query_sofragment.setBackground(getResources().getDrawable(R.drawable.round_conner));
        sonumber_sofragment = view.findViewById(R.id.sonumber_sofragment);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.query_sofragment:
               String sono= sonumber_sofragment.getText().toString().trim();
                if (sono.equals("")|| sono==null){
                    return;
                }
                HashMap map= new HashMap();

                map.put("PartNo","");
                map.put("Location","");
                map.put("SoNo",sono);
                map.put("Type","1");
                map.put("PageSize","100");
                map.put("PageIndex","1");
                HttpUtil.getInstance().formPost(getActivity(), Interface.GETORDERLIST, map, new HttpUtil.ICallBack() {
                    @Override
                    public void onResponse(JsonResult result) throws JSONException {
                        int result1 = result.getResult();
                        if (result1 == 0) {
                            Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT).show();

                            listview_fragment_so.setAdapter(new MyAdapter(result.getMsg()));
                        }else {
                            Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
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

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View convertview  =View.inflate(getActivity(),R.layout.item_sofragment_listview,null);

            TextView item1=convertview.findViewById(R.id.item1);
            TextView item2=convertview.findViewById(R.id.item2);
            TextView item3=convertview.findViewById(R.id.item3);
            TextView item4=convertview.findViewById(R.id.item4);
            TextView item5=convertview.findViewById(R.id.item5);
            header_sofragment.setVisibility(View.VISIBLE);
            div_sofragment.setVisibility(View.VISIBLE);

            try {
                listview_fragment_so.setBackground(getResources().getDrawable(R.drawable.listview_bg));
                if (i%2==1) {
                    convertview.setBackgroundColor(getResources().getColor(R.color.item_grey));
                }
                item1.setText((String)(((JSONObject)jsonArray.get(i)).get("SoNo")));
                item2.setText((String)(((JSONObject)jsonArray.get(i)).get("PartNo")));
                //item3.setText((String)(((JSONObject)jsonArray.get(i)).get("Location")));
               String Location= (String) ((JSONObject) jsonArray.get(i)).get("Location");
                if (Location == null) {
                    item3.setText("null");
                } else {
                    //item3.setText((String) (((JSONObject) jsonArray.get(i)).get("Location")));
                    //todo
                    item3.setText(Location);
                }
                ;
                item4.setText((int)(((JSONObject)jsonArray.get(i)).get("Quantity"))+"");
                item5.setText((int)(((JSONObject)jsonArray.get(i)).get("StockQty"))+"");
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
