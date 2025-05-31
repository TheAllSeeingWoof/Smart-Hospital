package kristina.pece.smarthospital.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import kristina.pece.smarthospital.R;
import kristina.pece.smarthospital.db.DBHelper;
import kristina.pece.smarthospital.fragment.AdminFragment;
import kristina.pece.smarthospital.fragment.UserFragment;
import kristina.pece.smarthospital.http.HttpHelper;

public class LoginActivity extends AppCompatActivity{

    private UserFragment userF;
    private AdminFragment adminF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String user = getIntent().getStringExtra("USERNAME");

        userF = UserFragment.newInstance(user, "andHere");
        adminF = AdminFragment.newInstance("whatToPutHere", "andHere");

        if(user.matches("admin")){//equals
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_placeholder, adminF,"AdminFragmen")
                    .commit();

        }else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_placeholder, userF,"UserFragmen")
                    .commit();

        }

    }
}