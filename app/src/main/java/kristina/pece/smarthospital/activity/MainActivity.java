package kristina.pece.smarthospital.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener , ServiceConnection {

    private HttpHelper httpHelper;
    public static String URL_GET = "http://26.197.247.182:8080/api/devices/";

    Button b1;
    Button b2;
    Button b3;
    EditText user;
    EditText pass;

    private IMyBinder mService = null;
    private static final String TAG = "ServiceTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

    }
    DBHelper db = new DBHelper(this);

    @Override
    public void onClick(View v) {

        Bitmap bitmap = BitmapFactory.decodeResource(v.getResources(), R.drawable.thermo);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] img = bos.toByteArray();

        httpHelper = new HttpHelper();


        if(v.getId() == R.id.button1){

            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);


        } else if(v.getId() == R.id.button2){
            View l = findViewById(R.id.hiddenRegistracija);
            l.setVisibility(View.VISIBLE);

        }else if(v.getId() == R.id.button3){
            user = findViewById(R.id.username);
            pass = findViewById(R.id.password);

            if(user.getText().toString().equals("admin")){

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonobjects = httpHelper.getJSONArrayFromURL(URL_GET);
                            for(int i = 0; i < jsonobjects.length(); i++){

                                JSONObject jobject = jsonobjects.getJSONObject(i);
                                Iterator<String> iter = jobject.keys();

                                String key = iter.next();
                                String ime = jobject.getString(key);

                                key = iter.next();
                                String id = jobject.getString(key);

                                key = iter.next();
                                String state = jobject.getString(key);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adminUredjaj char1 = new adminUredjaj(ime, img, state.equals("on"), id);
                                        if(!db.uredjajExists(id)){
                                            db.insertUredjaj(char1);
                                        }
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();




                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.putExtra("USERNAME", user.getText().toString());

                startActivity(i);
            }else if(db.usernameExists(user.getText().toString())) {

                if(pass.getText().toString().equals(db.getPassword(user.getText().toString()))){
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.putExtra("USERNAME", user.getText().toString());

                    startActivity(i);
                }

                Toast.makeText(this, "Pogresan password!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Korisnik ne postoji, pokusajte ponovo!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onServiceConnected(ComponentName name, IMyBinder service) {
        mService = IMyBinder.Stub.asInterface(service);

        try {
            mService.retrieveDevices();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d(TAG,"onServiceDisconnected - Service failure");
        mService = null;
    }
}