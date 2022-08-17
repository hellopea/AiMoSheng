package com.app.aimosheng.fragment.productverifyactivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.app.aimosheng.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UnverifyFragment extends Fragment {
    private ListView listview_unverifyfragment;
    private View div_unverifyfragment;
    private LinearLayout header_unverifyfragment;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_unverify,null);
         header_unverifyfragment= view.findViewById(R.id.header_unverifyfragment);
        listview_unverifyfragment= view.findViewById(R.id.listview_unverifyfragment);
        div_unverifyfragment= view.findViewById(R.id.div_unverifyfragment);
        header_unverifyfragment.setBackground(getResources().getDrawable(R.drawable.listview_bg));
        MyReceiver myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refresh.fragment");
        getActivity().registerReceiver(myReceiver,intentFilter);


        return view;
    }

    class MyAdapter extends BaseAdapter {
        private JSONObject object;
        private JSONArray jsonArray;

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
        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View convertview  =View.inflate(getActivity(),R.layout.item_unverifyfragment,null);

            TextView item1=convertview.findViewById(R.id.item1);
            TextView item2=convertview.findViewById(R.id.item2);
            TextView item3=convertview.findViewById(R.id.item3);
            header_unverifyfragment.setVisibility(View.VISIBLE);
            div_unverifyfragment.setVisibility(View.VISIBLE);
            listview_unverifyfragment.setVisibility(View.VISIBLE);
            try {
                listview_unverifyfragment.setBackground(getResources().getDrawable(R.drawable.listview_bg));
                if (i%2==1) {
                    convertview.setBackgroundColor(getResources().getColor(R.color.item_grey));
                }
                item1.setText((String)(((JSONObject)jsonArray.get(i)).get("PartNo")));
                item2.setText((int)(((JSONObject)jsonArray.get(i)).get("Quantity"))+"");
                item3.setText((int)(((JSONObject)jsonArray.get(i)).get("TrueQuantity"))+"");
                //item3.setText((String)(((JSONObject)jsonArray.get(i)).get("Location")));

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


     private  class MyReceiver extends BroadcastReceiver{


         @Override
         public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("refresh.fragment")){

                  String msg=  intent.getStringExtra("msg");
                try {
                    MyAdapter adapter =new MyAdapter(msg);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listview_unverifyfragment.setAdapter(adapter);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
         }
     }


}
