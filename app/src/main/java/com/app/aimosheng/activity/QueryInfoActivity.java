package com.app.aimosheng.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.aimosheng.R;
import com.app.aimosheng.fragment.queryinfoactivity.ProductFragment;
import com.app.aimosheng.fragment.queryinfoactivity.ProductPostionFragment;
import com.app.aimosheng.fragment.queryinfoactivity.SoFragment;
import com.app.aimosheng.utils.HttpUtil;
import com.app.aimosheng.utils.Interface;
import com.app.aimosheng.utils.JsonResult;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryInfoActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList list;
    private MyAdapter adapter;
    private String[] titles = {"SO单查询", "货位查询", "物料查询"};
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryinfo);
        findViewById(R.id.exit_productoutquery).setOnClickListener(this);

      InitView();
      InitData();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void InitData() {


    }
  /*  private ArrayList tabs = new ArrayList(
            "首页" to R.drawable.ic_launcher_foreground,
            "商场" to R.drawable.ic_launcher_foreground,
            "我的" to R.drawable.ic_launcher_foreground
    )*/

    private void InitView() {
        ViewPager queryactivity_viewpager= findViewById(R.id.queryactivity_viewpager);
        TabLayout qureyactivity_tablayout= findViewById(R.id.qureyactivity_tablayout);
        queryactivity_viewpager.setOffscreenPageLimit(2);
        //页面，数据源，里面是创建的三个页面（Fragment）
        list = new ArrayList<>();
        list.add(new SoFragment());
        list.add(new ProductPostionFragment());
        list.add(new ProductFragment());
        //ViewPager的适配器，获得Fragment管理器
        adapter = new MyAdapter(getSupportFragmentManager());
        queryactivity_viewpager.setAdapter(adapter);
        qureyactivity_tablayout.setupWithViewPager(queryactivity_viewpager);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.exit_productoutquery:
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
}
