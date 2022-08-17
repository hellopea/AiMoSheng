package com.app.aimosheng.activity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.aimosheng.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.productin).setOnClickListener(this);
        findViewById(R.id.productout).setOnClickListener(this);
        findViewById(R.id.queryinfo).setOnClickListener(this);
        findViewById(R.id.productverify).setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.productin:
                intent=new Intent(MenuActivity.this,ProductInActivity.class);

                break;
            case R.id.productout:
                intent =new Intent(MenuActivity.this,ProductOutCustomActivity.class);
                break;
            case R.id.productverify:
                intent = new Intent(MenuActivity.this,ProductoutReviewActivity.class);
                break;
            case R.id.queryinfo:
                intent = new Intent(MenuActivity.this,QueryInfoActivity.class);
                break;
        }
        if (intent!=null){
            startActivity(intent);
        }
    }
}