package co.aquario.horoscope.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import co.aquario.horoscope.R;


public class ImageAdapter extends BaseAdapter {
    Context mContext;
    String[] strName;
    int[] resId;
    int h;


    public ImageAdapter(Context context, String[] strName, int[] resId,int h) {
        this.mContext= context;
        this.strName = strName;
        this.resId = resId;
        this.h = h;
    }

    public int getCount() {
        return strName.length;
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
            view = mInflater.inflate(R.layout.twelve_zodiac_item, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.textView);
        textView.setText(strName[position]);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setBackgroundResource(resId[position]);
        //view.setBackgroundColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));



        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, h));
        return view;
    }
}