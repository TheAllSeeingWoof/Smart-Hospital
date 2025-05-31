package kristina.pece.smarthospital;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import kristina.pece.smarthospital.activity.Binder;
import kristina.pece.smarthospital.activity.MyBinder;

public class MyService extends Service {

    private MyBinder binder = null;

    public MyService() {
    }

    @Override
    public IMyBinder onBind(Intent intent) {
        if(binder == null){
            binder = new MyBinder();
        }
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        binder.stop();
        return super.onUnbind(intent);
    }
}