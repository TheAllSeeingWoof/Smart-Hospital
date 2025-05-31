package kristina.pece.smarthospital.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import kristina.pece.smarthospital.R;
import kristina.pece.smarthospital.db.DBHelper;
import kristina.pece.smarthospital.model.Korisnik;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button b4;

    EditText ime;
    EditText prezime;
    CalendarView calendar;
    RadioButton radioButt;
    RadioGroup radios;
    EditText password;
    String newDate;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ime = findViewById(R.id.imeText);
        prezime = findViewById(R.id.prezimeText);
        password = findViewById(R.id.lozinka);
        calendar = (CalendarView) findViewById(R.id.calendar);

        radios = (RadioGroup) findViewById(R.id.raioGroup);

        db = new DBHelper(this);

        CalendarView.OnDateChangeListener calendarListener = new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                newDate = dayOfMonth + "-" + month + "-" + year;
                Log.d("NEW_DATE", newDate);
            }
        };

        calendar.setOnDateChangeListener(calendarListener);


        b4 = findViewById(R.id.button4);
        b4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button4){
            View l = findViewById(R.id.layoutIspisUsername);

            String pol_s;

            String name = ime.getText().toString();
            String pname = prezime.getText().toString();
            String username_s = name + "." + pname;

            int radioId = radios.getCheckedRadioButtonId();
            radioButt = findViewById(radioId);

            String ime_s = ime.getText().toString();
            String prezime_s = prezime.getText().toString();
            try {
                pol_s = radioButt.getText().toString();
            } catch (NullPointerException nullPointerException){
                pol_s = "";
            }
            String datum_s = newDate; // I can just use newDate
            String pass_s = password.getText().toString();
            String pregledi_s = "PREGLEDI"; //ime tabele?
            Integer id_s = db.generateId();


            //provera da li postoji vec
            Korisnik korisnik = new Korisnik(ime_s, prezime_s, username_s,  pol_s, datum_s, pass_s, pregledi_s, id_s.toString());

            if(name.matches("") || pname.matches("")){
                //nije uneto ime prezime
                Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
            } else if(pol_s.equals("") || pass_s.equals("")){
                //nije uneto ime prezime
                Toast.makeText(this, "You did not enter the deets", Toast.LENGTH_SHORT).show();
            } else if(db.usernameExists(korisnik.getUsername())){

                Toast.makeText(this, "Korisnik vec postoji", Toast.LENGTH_SHORT).show();

            } else {

                // ubacimo korisnika u db
                db.insertKorisiik(korisnik);

                //ispisace ako je uspesno stavljen u db
                TextView username = findViewById(R.id.username);
                username.setText(username_s);
                l.setVisibility(View.VISIBLE);

            }
        }
    }
}