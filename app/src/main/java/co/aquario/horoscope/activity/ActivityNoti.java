package co.aquario.horoscope.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.meetme.android.horizontallistview.HorizontalListView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.aquario.horoscope.R;
import co.aquario.horoscope.adapter.CompatAdapter;
import co.aquario.horoscope.model.Zodiac;

public class ActivityNoti extends AppCompatActivity {
    String[] compatArray;
    private int mPage;
     Dialog dialog;
    String url = "https://scontent.fbkk2-1.fna.fbcdn.net/hphotos-xtf1/v/t1.0-9/11896167_1618517688386193_534700615172435718_n.png?oh=9c6bea02ec6d3e40ed1d5b403ac4218a&oe=5678165F";
    ArrayList<Zodiac> compatList = new ArrayList<>();
    ImageView ava,photo;
    TextView characterTitleTv,character_main,character_title,dialogTv,txt;
    TextView date;
    Button popOkB,popCancelB,button;
    CompatAdapter compatAdapter;
    HorizontalListView hlvCompat;
    String characterDateTtitle;

    ParseQuery<ParseObject> query ;
    String data,data2;
    String title;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_noti);


        title = getIntent().getStringExtra("title");
        data = getIntent().getStringExtra("data");
        number = getIntent().getIntExtra("number", 0);
        data2 = getIntent().getStringExtra("data2");

        query = ParseQuery.getQuery("Daily");

        query.getInBackground("Hm5vGPVL4J", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String title = object.getString("title");
                    Date date = object.getDate("date");
                    String detail = object.getString("detail");

                    Log.e("666", title);
                    Log.e("555",date+"");
                    Log.e("777",detail);

                } else {
                    // check errors.
                }
            }
        });

        hlvCompat = (HorizontalListView) findViewById(R.id.hlvCompat);
        ava = (ImageView) findViewById(R.id.avatra);
       // character_title = (TextView) findViewById(R.id.character_title);


        button = (Button) findViewById(R.id.button);
        compatAdapter = new CompatAdapter(getApplicationContext(), compatList);
        character_main = (TextView) findViewById(R.id.character_main);
        compatArray = getStringArrayResourceByName(data + "_compat_array");

        characterDateTtitle = getStringResourceByName(data+"_date");
        characterTitleTv = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);

        for(int i = 0; i< compatArray.length ; i++) {
            String[] parts = compatArray[i].split(",");
            Zodiac zodiac = null;

            if(parts.length == 4) {
                zodiac = new Zodiac(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3]);
                compatList.add(zodiac);
            }
        }


        hlvCompat.setAdapter(compatAdapter);
        date.setText(characterDateTtitle);
       // character_title.setText(title);
        character_main.setText(data2);

        hlvCompat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ActivityZodiacDetail.class);
                intent.putExtra("title", compatList.get(i).nameEn);
                intent.putExtra("data", compatList.get(i).codeName);
                intent.putExtra("number", compatList.get(i).id);
                startActivity(intent);
            }
        });


        Picasso.with(getApplicationContext())
                .load(getDrawableResourceByName(data+"_iconapp"))
                        //.centerCrop()
                        //.resize(200, 200)
                        //.transform(new RoundedTransformation(100, 4))
                .into(ava);

        YoYo.with(Techniques.Tada)
                .duration(1500)
                .playOn(findViewById(R.id.avatra));

        ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada)
                        .duration(1500)
                        .playOn(findViewById(R.id.avatra));
            }
        });

        characterTitleTv.setText(title);


        Date d = new Date();

        String pattern = "dd MMMM yyyy";
//String pattern = "dd/MMM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(pattern,new Locale("th","th"));

        Date todayDate = Calendar.getInstance().getTime();

        String todayTxt = df.format(todayDate);
        System.out.println(todayTxt);

        characterTitleTv.setText("วันที่ " + todayTxt);

//
//        DateFormat fmt = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.DEFAULT);
//        String time = fmt.format(new Date());



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(ActivityNoti.this);
                dialog.setContentView(R.layout.popupdialog);
                dialog.setTitle("โหวต 5ดาวเพื่อขอคำทำทายเพิ่มเติม..");
                popOkB = (Button) dialog.findViewById(R.id.popOkB);
                popCancelB = (Button) dialog.findViewById(R.id.popCancelB);
                popOkB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String my_package_name = "co.aquario.horoscope&hl=en";
                        String url = "";

                        try {
                            getApplicationContext().getPackageManager().getPackageInfo("com.android.vending", 0);

                            url = "market://details?id=" + my_package_name;
                        } catch (final Exception e) {
                            url = "https://play.google.com/store/apps/details?id=co.aquario.horoscope&hl=en" + my_package_name;
                        }
                        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivity(intent);
                    }
                });
                popCancelB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }

    private String getStringResourceByName(String aString) {
        String packageName = getApplicationContext().getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        Log.e("asdf555",aString);
        return getString(resId);
    }

    private int getDrawableResourceByName(String aString) {
        String packageName = getApplicationContext().getPackageName();
        int resId = getResources().getIdentifier(aString, "drawable", packageName);
        Log.e("44444", resId + "");
        return resId;
        //return ContextCompat.getDrawable(getApplicationContext(), resId);
    }

    private String[] getStringArrayResourceByName(String aString) {
        String packageName = getApplicationContext().getPackageName();
        int resId = getResources().getIdentifier(aString, "array", packageName);
        return getResources().getStringArray(resId);
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

}
