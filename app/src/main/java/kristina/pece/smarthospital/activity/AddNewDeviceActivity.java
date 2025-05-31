package kristina.pece.smarthospital.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import kristina.pece.smarthospital.R;
import kristina.pece.smarthospital.db.DBHelper;
import kristina.pece.smarthospital.http.HttpHelper;
import kristina.pece.smarthospital.model.adminUredjaj;

public class AddNewDeviceActivity extends AppCompatActivity implements View.OnClickListener {

    EditText ime;
    EditText tip;
    Button sacuvaj;

    private DBHelper db;
    private HttpHelper httpHelper;

    String URL = "http://26.197.247.182:8080/api/device";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_device);

        db = new DBHelper(this);
        httpHelper = new HttpHelper();

        ime = findViewById(R.id.noviUredajIme);
        tip = findViewById(R.id.noviUredjajTip);
        sacuvaj = findViewById(R.id.sacuvajNoviUredjaj);

        sacuvaj.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject device1 = new JSONObject();
                    try {

                        Integer ids = db.generateUredjajId();

                        device1.put("name", ime.getText().toString());
                        device1.put("id", ids.toString());
                        device1.put("state", "off");
                        device1.put("type", tip.getText().toString());



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonobject = httpHelper.postJSONObjectFromURLPLUS(URL, device1);


                    String message = jsonobject.getString("message");
                    int code = jsonobject.getInt("code");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (code != 200){
                                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                            }

                            Bitmap bitmap = BitmapFactory.decodeResource(v.getResources(), R.drawable.thermo);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                            byte[] img = bos.toByteArray();

                            adminUredjaj char1 = null;
                            try {
                                char1 = new adminUredjaj(ime.getText().toString(), img, false, device1.getString("id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            db.insertUredjaj(char1);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        finish();

    }
}