package com.keep.alive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.keep.alive.keeplive.KeepLiveManager;
import com.keep.alive.service.MyService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            startService(new Intent(this, MyService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        KeepLiveManager.startJobScheduler();
        KeepLiveManager.addAccount();
    }
}
