package com.keep.alive;

import android.app.Application;
import android.content.Context;

import com.keep.alive.service.MyReceiver1;
import com.keep.alive.service.MyReceiver2;
import com.keep.alive.service.MyService;
import com.keep.alive.service.MyService2;
import com.marswin89.marsdaemon.DaemonClient;
import com.marswin89.marsdaemon.DaemonConfigurations;

public class ApplicationManager extends Application {
	public static Context ctx;

    @Override
    public void attachBaseContext(final Context base) {
        super.attachBaseContext(base);
        try {
            DaemonClient mDaemonClient = new DaemonClient(createDaemonConfigurations());
            mDaemonClient.onAttachBaseContext(base);
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }

    private DaemonConfigurations createDaemonConfigurations(){
        DaemonConfigurations.DaemonConfiguration configuration1 = new DaemonConfigurations.DaemonConfiguration(
                getPackageName() + ":remote",
                MyService.class.getCanonicalName(),
                MyReceiver1.class.getCanonicalName());
        DaemonConfigurations.DaemonConfiguration configuration2 = new DaemonConfigurations.DaemonConfiguration(
                getPackageName() + ":pushservice",
                MyService2.class.getCanonicalName(),
                MyReceiver2.class.getCanonicalName());
        DaemonConfigurations.DaemonListener listener = new MyDaemonListener();
        return new DaemonConfigurations(configuration1, configuration2, listener);
    }


    class MyDaemonListener implements DaemonConfigurations.DaemonListener{
        @Override
        public void onPersistentStart(Context context) {
        }

        @Override
        public void onDaemonAssistantStart(Context context) {
        }

        @Override
        public void onWatchDaemonDaed() {
        }
    }

    @Override
	public void onCreate() {
		super.onCreate();
        ctx = getApplicationContext();
    }

}