package com.keep.alive.keeplive;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 1像素界面
 * 监控手机锁屏解锁事件，在屏幕锁屏时启动1个像素的 Activity，在用户解锁时将Activity销毁掉。
 * 通过该方案，可以使进程的优先级在屏幕锁屏时间由4提升为最高优先级1
 * 缺点：上线后有用户反馈解锁可能会出现假死现象，即该界面没有被销毁，后去除该方式
 */
public class KeepLiveActivity extends Activity {

    public static final String TAG = "KeepLiveActivity";
    public static KeepLiveActivity mInstance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = 1;
        params.height = 1;
        window.setAttributes(params);
        mInstance = this;
        Log.i(TAG, "KeepLiveActivity onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "KeepLiveActivity onDestroy");
    }
}
