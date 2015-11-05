package fr.rtone.intent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG_SERVICE = "MyService";
    Thread worker;
    boolean running = false;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        Log.i(TAG_SERVICE, "onCreate");

        worker = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    sendBroadcast(
                            new Intent("fr.rtone.intent.signal").putExtra("MESSAGE", "le message")
                    );
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });
      }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG_SERVICE, "onStartCommand");

        if (!worker.isAlive())
            running = true;
            worker.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG_SERVICE, "onDestroy");
        if (worker.isAlive()) {
            running = false;
            worker.interrupt();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
