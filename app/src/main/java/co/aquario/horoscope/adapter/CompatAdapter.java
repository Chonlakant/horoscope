package co.aquario.horoscope.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.horoscope.R;
import co.aquario.horoscope.model.Zodiac;


public class CompatAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Zodiac> compatList;
    //String[] avatar;


    public CompatAdapter(Context context, ArrayList<Zodiac> compatList) {
        this.mContext = context;
        this.compatList = compatList;
        // this.avatar = avatar;

    }

    public int getCount() {
        return compatList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Zodiac zodiac = compatList.get(position);

        if (view == null)
            view = mInflater.inflate(R.layout.item_array, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.compat_main);
        textView.setText(compatList.get(position).nameTh);

        ImageView ava = (ImageView) view.findViewById(R.id.avatra);


//        Picasso.with(mContext)
//                .load("https://jobbkk.com/upload/variety/horo/20150217_1030.png")
//                .into(ava);

        if (zodiac != null) {
            Picasso.with(mContext)
                    .load(getDrawableResourceByName(zodiac.codeName))

                    .into(ava);
        } else {

        }


        return view;
    }

    private int getDrawableResourceByName(String aString) {
        String packageName = mContext.getPackageName();
        int resId = mContext.getResources().getIdentifier(aString, "drawable", packageName);
        Log.e("44444", resId + "");
        return resId;
        //return ContextCompat.getDrawable(getApplicationContext(), resId);
    }

}