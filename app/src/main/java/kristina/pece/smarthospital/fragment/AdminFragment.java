package kristina.pece.smarthospital.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import kristina.pece.smarthospital.activity.AddNewDeviceActivity;
import kristina.pece.smarthospital.activity.DeviceActivity;
import kristina.pece.smarthospital.R;
import kristina.pece.smarthospital.activity.LoginActivity;
import kristina.pece.smarthospital.activity.MainActivity;
import kristina.pece.smarthospital.adapteri.AdminAdapter;
import kristina.pece.smarthospital.db.DBHelper;
import kristina.pece.smarthospital.model.adminUredjaj;
import kristina.pece.smarthospital.http.HttpHelper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static String URL = "http://26.197.247.182:8080/api/device";
    public static String URL_GET = "http://26.197.247.182:8080/api/devices/";
    public static String URL_UREDJAJ = "http://26.197.247.182:8080/api/device/";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment newInstance(String param1, String param2) {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private ListView list;
    private AdminAdapter adapter;
    private HttpHelper httpHelper;
    EditText nazivUredjaja;
    ImageView imageView;
    byte[] img;
    Bitmap b;
    Switch sw;
    DBHelper db;

    static boolean loaded = false;

    @Override
    public void onResume() {
        super.onResume();

        Log.i("Resuming", "back to my first activity");

        //Reload data
        adminUredjaj uredjaji[] = db.readAllUredjaji();
        adapter.update(uredjaji);

        adapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin, container, false);
        TextView neaktivan = v.findViewById(R.id.neaktivan);


        httpHelper = new HttpHelper();

        nazivUredjaja = v.findViewById(R.id.listUredjaj);
        imageView =(ImageView) v.findViewById(R.id.listAdminImg);
        sw = v.findViewById(R.id.adminSwitch);

        Bitmap bitmap = BitmapFactory.decodeResource(v.getResources(), R.drawable.thermo);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] img = bos.toByteArray();

        db = new DBHelper(v.getContext());

        list = v.findViewById(R.id.ADMINlist);
        list.setEmptyView(v.findViewById(R.id.emptyText));

        adapter = new AdminAdapter(v.getContext());
        list.setAdapter(adapter);

        v.findViewById(R.id.addDevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), AddNewDeviceActivity.class);
                startActivity(i);

            }
        });


/*
        // ovako sam unela uredjaje u db
        adminUredjaj char1 = new adminUredjaj("thermostat1", img, false );

        db.insertUredjaj(char1);
*/

        adminUredjaj uredjaji[] = db.readAllUredjaji();
        adapter.update(uredjaji);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adminUredjaj uredjaj = (adminUredjaj) adapter.getItem(position);

                Intent i = new Intent( getActivity(), DeviceActivity.class);

                i.putExtra("ID", uredjaj.getId());

                startActivity(i);

            }
        });

        return v;

    }
}