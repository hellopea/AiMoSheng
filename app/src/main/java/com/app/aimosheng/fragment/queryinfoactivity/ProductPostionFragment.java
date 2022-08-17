package com.app.aimosheng.fragment.queryinfoactivity;

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
import com.app.aimosheng.utils.HttpUtil;
import com.app.aimosheng.utils.Interface;
import com.app.aimosheng.utils.JsonResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProductPostionFragment extends Fragment implements View.OnClickListener {
    private EditText productpositionno;
    private ListView listview_fragment_productposition;
    private  LinearLayout header_procuctpostionlistview;
    private View div_ppfragment;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_productposition, null);
        Button query_productposition = view.findViewById(R.id.query_productposition);
        query_productposition.setBackground(getResources().getDrawable(R.drawable.round_conner));
        view.findViewById(R.id.query_productposition).setOnClickListener(this);
        listview_fragment_productposition = view.findViewById(R.id.listview_fragment_productposition);
        productpositionno = view.findViewById(R.id.productpositionno);
        div_ppfragment = view.findViewById(R.id.div_ppfragment);
        header_procuctpostionlistview= view.findViewById(R.id.header_procuctpostionlistview);
        header_procuctpostionlistview.setBackground(getResources().getDrawable(R.drawable.listview_bg));
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.query_productposition:

                String positionno = productpositionno.getText().toString().trim();
                if (positionno.equals("") || positionno == null) {
                    return;
                }
                HashMap map = new HashMap();

                map.put("PartNo", "");
                map.put("Location", positionno);
                map.put("SoNo", "");
                map.put("Type", "2");
                map.put("PageSize", "100");
                map.put("PageIndex", "1");
                HttpUtil.getInstance().formPost(getActivity(), Interface.GETORDERLIST, map, new HttpUtil.ICallBack() {
                    @Override
                    public void onResponse(JsonResult result) throws JSONException {
                        int result1 = result.getResult();
                        if (result1 == 0) {
                            Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT).show();

                            listview_fragment_productposition.setAdapter(new MyAdapter(result.getMsg()));
                        } else {
                            Toast.makeText(getActivity(), "请求失败:"+result.getMsg(), Toast.LENGTH_SHORT).show();
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
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View convertview = View.inflate(getActivity(), R.layout.item_positonfragment_listview, null);
            if (i%2==1) {
                convertview.setBackgroundColor(getResources().getColor(R.color.item_grey));
            }
            TextView item1 = convertview.findViewById(R.id.item1);
            TextView item2 = convertview.findViewById(R.id.item2);
            TextView item3 = convertview.findViewById(R.id.item3);
            header_procuctpostionlistview.setVisibility(View.VISIBLE);
            div_ppfragment.setVisibility(View.VISIBLE);

            try {
                listview_fragment_productposition.setBackground(getResources().getDrawable(R.drawable.listview_bg));
                String Location= (String) ((JSONObject) jsonArray.get(i)).get("Location");
                if (Location == null) {
                    item1.setText("null");
                } else {
                    //item3.setText((String) (((JSONObject) jsonArray.get(i)).get("Location")));
                    //todo
                    item1.setText(Location);
                }
                item2.setText((String) (((JSONObject) jsonArray.get(i)).get("PartNo")));
                //item3.setText((String)(((JSONObject)jsonArray.get(i)).get("Location")));
                item3.setText((int) (((JSONObject) jsonArray.get(i)).get("StockQty")) + "");
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
