package kristina.pece.smarthospital.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpCookie;

import kristina.pece.smarthospital.R;
import kristina.pece.smarthospital.http.HttpHelper;
import kristina.pece.smarthospital.model.adminUredjaj;

public class DeviceActivity extends AppCompatActivity {

    TextView naziv;
    TextView id;
    TextView stanje;
    TextView tip;
    HttpHelper httpHelper;

    public static String URL_UREDJAJ = "http://26.197.247.182:8080/api/device/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        naziv = findViewById(R.id.nazivUredjaja);
        id = findViewById(R.id.idUredjaja);
        stanje = findViewById(R.id.stanjeUredjaja);
        tip = findViewById(R.id.tipUredjaja);

        httpHelper = new HttpHelper();

        String URL_ID = URL_UREDJAJ + getIntent().getStringExtra("ID");


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = httpHelper.getJSONObjectFromURL(URL_ID);

                    String jnaziv = jsonObject.getString("name");
                    String jid = jsonObject.getString("id");
                    String jstate = jsonObject.getString("state");
                    String jtype = jsonObject.getString("type");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            naziv.setText(jnaziv);
                            id.setText(jid);
                            stanje.setText(jstate);
                            tip.setText(jtype);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}