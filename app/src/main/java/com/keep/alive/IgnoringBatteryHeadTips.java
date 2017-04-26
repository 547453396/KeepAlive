package com.keep.alive;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.keep.alive.keeplive.KeepLiveManager;

public class IgnoringBatteryHeadTips implements View.OnClickListener{

    private Activity context;
    private View root;
    private RelativeLayout rl_root;
    private ImageView iv_close;

    public View getRoot() {
        return root;
    }

    public IgnoringBatteryHeadTips(Activity context) {
        this.context = context;
        init();
    }

    private void init() {
        root = LayoutInflater.from(context).inflate(R.layout.ignoring_battery_tips_view, null);
        rl_root = (RelativeLayout) root.findViewById(R.id.rl_root);
        rl_root.setOnClickListener(this);
        iv_close = (ImageView) root.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == rl_root){
            KeepLiveManager.openIgnoringBatteryOptimizations(context);
        }else if (v == iv_close){
            closeIgnoringBattery();
        }
    }

    public void closeIgnoringBattery(){
        rl_root.setVisibility(View.GONE);
    }
}
