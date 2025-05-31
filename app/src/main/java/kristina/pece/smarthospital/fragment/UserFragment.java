package kristina.pece.smarthospital.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import kristina.pece.smarthospital.R;
import kristina.pece.smarthospital.db.DBHelper;
import kristina.pece.smarthospital.model.User_pregledi;
import kristina.pece.smarthospital.adapteri.UserAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1); //username
            mParam2 = getArguments().getString(ARG_PARAM2); // maybe password
        }
    }

    private ListView list;
    private UserAdapter adapter;

    DBHelper db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        list = v.findViewById(R.id.list);
        list.setEmptyView(v.findViewById(R.id.emptyText));

        adapter = new UserAdapter(v.getContext());
        list.setAdapter(adapter);

        db = new DBHelper(v.getContext());


        String m_id = db.getId(db.readKorisnik(mParam1));
/*
        User_pregledi char1 = new User_pregledi("01.00.2020", "opsti pregled", m_id);
        User_pregledi char2 = new User_pregledi("01.11.2020", "opsti pregled2", m_id);
        User_pregledi char3 = new User_pregledi("02.20.2021", "opsti pregled3", m_id);
        User_pregledi char4 = new User_pregledi("04.10.2021", "opsti pregled4", m_id);
        User_pregledi char5 = new User_pregledi("15.12.2022", "opsti pregled5", m_id);
        User_pregledi char6 = new User_pregledi("02.20.2023", "opsti pregled6", m_id);
        User_pregledi char7 = new User_pregledi("04.10.2024", "opsti pregled7", m_id);
        User_pregledi char8 = new User_pregledi("15.12.2025", "opsti pregled8", m_id);

        db.insertPregled(char1);
        db.insertPregled(char2);
        db.insertPregled(char3);
        db.insertPregled(char4);
        db.insertPregled(char5);
        db.insertPregled(char6);
        db.insertPregled(char7);
        db.insertPregled(char8);
*/

        User_pregledi pregledi[] = db.readIDPregledi(m_id);
        adapter.update(pregledi);

        return v;
    }
}