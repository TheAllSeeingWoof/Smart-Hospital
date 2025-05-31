package kristina.pece.smarthospital.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;


import kristina.pece.smarthospital.IMyBinder;
import kristina.pece.smarthospital.R;
import kristina.pece.smarthospital.db.DBHelper;
import kristina.pece.smarthospital.http.HttpHelper;
import kristina.pece.smarthospital.model.adminUredjaj;

import static androidx.core.graphics.drawable.IconCompat.getResources;

public class Binder extends IMyBinder.Stub {

    private int mValue = 0;
    private retCaller mCaller;
    private DBHelper db1;
    @Override
    public int getValue() throws RemoteException {
        return mValue;
    }

    @Override
    public void setValue(int value) throws RemoteException {
        mValue = value;
    }

    @Override
    public void retrieveDevices() throws RemoteException {
        mCaller = new retCaller();
        mCaller.start();
    }


    public void setDBHelper(DBHelper db) throws RemoteException {
        db1 = db;
    }

    public void stop(){
        mCaller.stop();
    }

    @Override
    public IBinder asBinder() {
        return null;
    }


    private class retCaller implements Runnable{
        private static final long PERIOD = 60000L;
        private Handler mHandler = null;
        private boolean mRun = true;

        private HttpHelper httpHelper;
        public String URL_GET = "http://26.197.247.182:8080/api/devices/";

        public void start(){
            mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(this, PERIOD);
        }

        public void stop(){
            mRun = false;
            mHandler.removeCallbacks(this);
        }

        @Override
        public void run() {

            if(!mRun) return;

            httpHelper = new HttpHelper();

//azthiszem ide kell a http thingy
            Log.d("ServiceTAG", "Hello from thread");

            try {
                JSONArray jsonobjects = httpHelper.getJSONArrayFromURL(URL_GET);
                for(int i = 0; i < jsonobjects.length(); i++) {

                    JSONObject jobject = jsonobjects.getJSONObject(i);
                    Iterator<String> iter = jobject.keys();

                    String key = iter.next();
                    String ime = jobject.getString(key);

                    key = iter.next();
                    String id = jobject.getString(key);

                    key = iter.next();
                    String state = jobject.getString(key);

                    adminUredjaj char1 = new adminUredjaj(ime, null, state.equals("on"), id);
                    if (!db1.uredjajExists(id)) {
                        db1.insertUredjaj(char1);
                    }
                }

            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


            mHandler.postDelayed(this,PERIOD);

        }
    }
}
