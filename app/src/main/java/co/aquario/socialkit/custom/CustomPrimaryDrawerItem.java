package co.aquario.socialkit.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import co.aquario.socialkit.R;

/**
 * Created by Mac on 6/22/15.
 */
public class CustomPrimaryDrawerItem extends PrimaryDrawerItem {

    private int backgroundRes = -1;
    private int backgroundColor = 0;




    public CustomPrimaryDrawerItem withBackgroundRes(int backgroundRes) {
        this.backgroundRes = backgroundRes;
        return this;
    }

    public CustomPrimaryDrawerItem withBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    private ImageView channelMenu;
    private ImageView sociallMenu;
    private ImageView videoMenu;
    private ImageView photoMenu;

    View view;

    @Override
    public View convertView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        //use the logic of our PrimaryDrawerItem
        //convertView = super.convertView(inflater, convertView, parent);
        convertView = inflater.inflate(R.layout.header_drawer, parent, false);
        view = convertView;

        if (backgroundColor != 0) {
            convertView.setBackgroundColor(backgroundColor);
        } else if (backgroundRes != -1) {
            convertView.setBackgroundResource(backgroundRes);
        }

        //View header = LayoutInflater.from().inflate(R.layout.header_drawer, null);

        return convertView;
    }

    public View getMenuHeader() {
        return view;
    }

}
