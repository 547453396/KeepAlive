package com.keep.alive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.keep.alive.keeplive.KeepLiveManager;
import com.keep.alive.service.MyService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        try {
            startService(new Intent(this, MyService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        KeepLiveManager.startJobScheduler();
        KeepLiveManager.addAccount();
    }

    private void initView(){
        RelativeLayout rl_root = (RelativeLayout) findViewById(R.id.rl_root);
        if (!KeepLiveManager.isIgnoringBatteryOptimizations(this)){
            IgnoringBatteryHeadTips headTips = new IgnoringBatteryHeadTips(this);
            rl_root.addView(headTips.getRoot());
        }
    }
}
