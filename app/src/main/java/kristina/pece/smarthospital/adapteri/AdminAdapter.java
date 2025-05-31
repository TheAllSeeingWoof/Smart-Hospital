package kristina.pece.smarthospital.adapteri;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import kristina.pece.smarthospital.R;
import kristina.pece.smarthospital.db.DBHelper;
import kristina.pece.smarthospital.http.HttpHelper;
import kristina.pece.smarthospital.model.adminUredjaj;

public class  AdminAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<adminUredjaj> mUredjaji;

    public AdminAdapter(Context context) {
        this.mContext = context;
        mUredjaji = new ArrayList<adminUredjaj>();
    }

    public void addUredjaj(adminUredjaj element){
        mUredjaji.add(element);
        notifyDataSetChanged();
    }

    public void removeUredjaj(adminUredjaj element){
        mUredjaji.remove(element);
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return mUredjaji.size();
    }

    @Override
    public Object getItem(int position) {
        return mUredjaji.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(adminUredjaj[] uredjaji){
        mUredjaji.clear();

        if(uredjaji != null){
            for(adminUredjaj uredjaj : uredjaji) { //for each
                mUredjaji.add(uredjaj);
            }
        }
        notifyDataSetChanged();

    }

    CompoundButton cb;
    DBHelper db;
    HttpHelper httpHelper;

    public static String URL = "http://26.197.247.182:8080/api/device/";


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.admin_list, null);

            ViewHolder vh = new ViewHolder();
            vh.nazivUredjaj = convertView.findViewById(R.id.listUredjaj);
            vh.neaktivan = convertView.findViewById(R.id.neaktivan);
            vh.img = convertView.findViewById(R.id.listAdminImg);
            vh.sw = convertView.findViewById(R.id.adminSwitch);
            convertView.setTag(vh);
        }

        adminUredjaj uredjaj = (adminUredjaj) getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        db = new DBHelper(mContext);
        httpHelper = new HttpHelper();

        Bitmap b1 = BitmapFactory.decodeByteArray(uredjaj.getImg(), 0, uredjaj.getImg().length);

        holder.sw.setTag(position);

        holder.nazivUredjaj.setText(uredjaj.getNazivUredjaj());
        holder.img.setImageBitmap(b1);
        holder.sw.setChecked(uredjaj.getSw());
        holder.neaktivan.setVisibility(uredjaj.getSw() ? View.INVISIBLE : View.VISIBLE);

        holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (Integer)buttonView.getTag();
                adminUredjaj u = (adminUredjaj) getItem(pos);

                String id = u.getId();
                String URL_CHECKED = URL + id + "/" + (isChecked ? "on" : "off");

                u.setSw(isChecked);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject j = new JSONObject();
                            JSONObject jsonobject = httpHelper.postJSONObjectFromURLPLUS(URL_CHECKED, j);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                holder.neaktivan.setVisibility(u.getSw() ? View.INVISIBLE : View.VISIBLE);

                db.updateDB(u, isChecked);


            }
        });
        return convertView;
    }

    private class ViewHolder{
        public TextView nazivUredjaj;
        public TextView neaktivan;
        public ImageView img;
        public Switch sw;

    }
}

