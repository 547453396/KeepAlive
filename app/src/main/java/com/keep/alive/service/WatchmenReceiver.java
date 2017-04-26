package com.keep.alive.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 进程守护广播接收器，这个类监听一些系统常用广播，然后启动后台进程，该类不做其他用途.
 * 目前监听的有：
 *     WifiManager.WIFI_STATE_CHANGED_ACTION;
 *     WifiManager.NETWORK_STATE_CHANGED_ACTION;
 *     ConnectivityManager.CONNECTIVITY_ACTION;
 * 保活新增action
 *  android.intent.action.BOOT_COMPLETED 开机广播
 *  android.intent.action.USER_PRESENT 解锁
 *  android.intent.action.ACTION_POWER_CONNECTED 连接外部电源时
 *  android.intent.action.ACTION_POWER_DISCONNECTED 断开连接电源
 *
 * */
public class WatchmenReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			context.startService(new Intent(context, MyService.class));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
