package co.aquario.horoscope.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import co.aquario.horoscope.R;
import co.aquario.horoscope.RoundedTransformation;



public class GoodAdapter extends BaseAdapter {
    Context mContext;
    String[] strCompat;


    public GoodAdapter(Context context, String[] strCompat) {
        this.mContext= context;
        this.strCompat = strCompat;

    }

    public int getCount() {
        return strCompat.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.item_array_good2, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.compat_main);
        textView.setText(strCompat[position]);
       // ImageView ava = (ImageView) view.findViewById(R.id.avatra);


//        Picasso.with(mContext)
//                .load("https://jobbkk.com/upload/variety/horo/20150217_1030.png")
//                .into(ava);

//        Picasso.with(mContext)
//                .load("http://www.sleevepaper.com/images/Fourth-element_Horoscopr.gif")
//                .centerCrop()
//                .resize(200, 200)
//                .transform(new RoundedTransformation(100, 4))
//                .into(ava);

        return view;
    }
}