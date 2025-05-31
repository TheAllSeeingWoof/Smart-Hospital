package kristina.pece.smarthospital.adapteri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kristina.pece.smarthospital.R;
import kristina.pece.smarthospital.model.User_pregledi;
import kristina.pece.smarthospital.model.adminUredjaj;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User_pregledi> mPregledi;

    public UserAdapter(Context context) {
        this.context = context;
        mPregledi = new ArrayList<User_pregledi>();
    }

    public void addPregled(User_pregledi element){
        mPregledi.add(element);
        notifyDataSetChanged();
    }

    public void removePregled(User_pregledi element){
        mPregledi.remove(element);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPregledi.size();
    }

    @Override
    public Object getItem(int position) {
        return mPregledi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(User_pregledi[] pregledi){
        mPregledi.clear();
        if(pregledi != null){
            for(User_pregledi pregled : pregledi) { //for each
                mPregledi.add(pregled);
            }
        }
        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_list, null);

            ViewHolder vh = new ViewHolder();
            vh.datum = convertView.findViewById(R.id.listDatum);
            vh.nazivPregleda = convertView.findViewById(R.id.listNaziv);
            convertView.setTag(vh);
        }

        User_pregledi character = (User_pregledi) getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.datum.setText(character.getDatum());
        holder.nazivPregleda.setText(character.getNazivPregleda());

        return convertView;
    }
    private class ViewHolder{
        public TextView datum;
        public TextView nazivPregleda;

    }

}

