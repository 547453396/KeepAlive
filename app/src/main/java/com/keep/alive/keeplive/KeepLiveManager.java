package com.keep.alive.keeplive;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import com.keep.alive.ApplicationManager;
import com.keep.alive.keeplive.account.SyncAccountUtils;

public class KeepLiveManager {
    private static final long PERIODIC_TIME = 60 * 1000;
    public static final int IGNORINGBATTERY = 9999;

    public static void startKeepLiveActivity() {
        Intent keepLive = new Intent(ApplicationManager.ctx, KeepLiveActivity.class);
        keepLive.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ApplicationManager.ctx.startActivity(keepLive);
    }

    public static void finishKeepLiveActivity() {
        if (KeepLiveActivity.mInstance != null){
            KeepLiveActivity.mInstance.finish();
        }
    }

    /**
     * JobScheduler保活
     * 该方案主要适用于 Android5.0 以上版本手机。
     * 该方案在 Android5.0 以上版本中不受 forcestop 影响，被强制停止的应用依然可以被拉活，
     * 在Android5.0 以上版本拉活效果非常好。
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startJobScheduler() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int job = 1;
                JobInfo.Builder builder = new JobInfo.Builder(job,
                        new ComponentName(ApplicationManager.ctx, JobSchedulerService.class));
                builder.setPeriodic(PERIODIC_TIME);
                builder.setPersisted(true);

                JobScheduler jobScheduler = (JobScheduler) ApplicationManager.ctx.
                        getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(builder.build());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加同步账号
     * 该方案适用于所有的 Android 版本，包括被 forestop 掉的进程也可以进行拉活。
     * 最新 Android 版本（Android N）中系统好像对账户同步这里做了变动，该方法不再有效。
     */
    public static void addAccount() {
        try {
            SyncAccountUtils.createSyncAccount(ApplicationManager.ctx);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 是否忽略电池优化判断
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isIgnoringBatteryOptimizations(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                String packageName = activity.getPackageName();
                PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
                return pm.isIgnoringBatteryOptimizations(packageName);
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }catch (NoSuchFieldError error){
                error.printStackTrace();
                return true;
            }
        }else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void openIgnoringBatteryOptimizations(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                String packageName = activity.getPackageName();
                PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
                if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    activity.startActivityForResult(intent, IGNORINGBATTERY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }catch (NoSuchFieldError error){
                error.printStackTrace();
            }
        }
    }
}
