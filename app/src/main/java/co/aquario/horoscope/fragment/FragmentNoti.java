package co.aquario.horoscope.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.meetme.android.horizontallistview.HorizontalListView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import co.aquario.horoscope.R;
import co.aquario.horoscope.adapter.CompatAdapter;
import co.aquario.horoscope.event.GetTasksEvent;
import co.aquario.horoscope.event.TaskSuccessEvent;
import co.aquario.horoscope.handler.ApiBus;
import co.aquario.horoscope.model.Zodiac;


public class FragmentNoti extends BaseFragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    String[] compatArray;
    private int mPage;

    String url = "https://scontent.fbkk2-1.fna.fbcdn.net/hphotos-xtf1/v/t1.0-9/11896167_1618517688386193_534700615172435718_n.png?oh=9c6bea02ec6d3e40ed1d5b403ac4218a&oe=5678165F";
    ArrayList<Zodiac> compatList = new ArrayList<>();
    ImageView ava,photo;
    TextView characterTitleTv,character_main,character_title,dialogTv,txt;
    TextView date;
    Button popOkB,popCancelB,button;
    CompatAdapter compatAdapter;
    HorizontalListView hlvCompat;
    String data;
    String title;
    String characterDateTtitle;

    ParseQuery<ParseObject> query ;

//    public static FragmentNoti newInstance(int page,String title,String data) {
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
//        args.putString("title",title);
//        args.putString("data", data);
//        FragmentNoti fragment = new FragmentNoti();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mPage = getArguments().getInt(ARG_PAGE);
//            title = getArguments().getString("title");
//            data = getArguments().getString("data");
//
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_noti, container, false);
        ApiBus.getInstance().post(new GetTasksEvent());

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            mPage = getArguments().getInt("number");
            title = getArguments().getString("title");
            data = getArguments().getString("data");
        }

        query = ParseQuery.getQuery("Daily");

        query.getInBackground("Hm5vGPVL4J", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String title = object.getString("title");
                    Date date = object.getDate("date");
                    String detail = object.getString("detail");

                    Log.e("666",title);
                    Log.e("555",date+"");
                    Log.e("777",detail);

                } else {
                    // check errors.
                }
            }
        });

        hlvCompat = (HorizontalListView) rootView.findViewById(R.id.hlvCompat);
        ava = (ImageView) rootView.findViewById(R.id.avatra);
        character_title = (TextView) rootView.findViewById(R.id.character_title);


        button = (Button) rootView.findViewById(R.id.button);
        compatAdapter = new CompatAdapter(getActivity(), compatList);
        character_main = (TextView) rootView.findViewById(R.id.character_main);
        compatArray = getStringArrayResourceByName(data + "_compat_array");

        characterDateTtitle = getStringResourceByName(data+"_date");
        characterTitleTv = (TextView) rootView.findViewById(R.id.title);
        date = (TextView) rootView.findViewById(R.id.date);

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
        character_title.setText(title);
        character_main.setText(getStringResourceByName(data + "_main"));

        Picasso.with(getActivity())
                .load(getDrawableResourceByName(data+"_iconapp"))
                        //.centerCrop()
                        //.resize(200, 200)
                        //.transform(new RoundedTransformation(100, 4))
                .into(ava);

        characterTitleTv.setText(title);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
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
                            //Check whether Google Play store is installed or not:
                           getActivity().getPackageManager().getPackageInfo("com.android.vending", 0);

                            url = "market://details?id=" + my_package_name;
                        } catch ( final Exception e ) {
                            url = "https://play.google.com/store/apps/details?id=co.aquario.horoscope&hl=en" + my_package_name;
                        }
//Open the app page in Google Play store:
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

        return rootView;
    }

    @Subscribe
    public void onGetTaskSuccess(TaskSuccessEvent event) {
        Log.e("hehehe", event.tasksData.get(0).getTask());
       // Toast.makeText(getActivity(),"Check"+event.tasksData.get(0).getTask(),Toast.LENGTH_LONG).show();
    }

    private String getStringResourceByName(String aString) {
        String packageName = getActivity().getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        Log.e("asdf555",aString);
        return getString(resId);
    }

    private int getDrawableResourceByName(String aString) {
        String packageName = getActivity().getPackageName();
        int resId = getResources().getIdentifier(aString, "drawable", packageName);
        Log.e("44444", resId + "");
        return resId;
        //return ContextCompat.getDrawable(getApplicationContext(), resId);
    }

    private String[] getStringArrayResourceByName(String aString) {
        String packageName = getActivity().getPackageName();
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