package co.aquario.horoscope.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.nineoldandroids.animation.Animator;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.horoscope.R;
import co.aquario.horoscope.activity.ActivityZodiacDetail;
import co.aquario.horoscope.adapter.CelebsEnAdapter;
import co.aquario.horoscope.adapter.CelebsTnAdapter;
import co.aquario.horoscope.adapter.CompatAdapter;
import co.aquario.horoscope.event.GetTasksEvent;
import co.aquario.horoscope.handler.ApiBus;
import co.aquario.horoscope.model.Zodiac;

import co.aquario.horoscope.adapter.BadAdapter;
import co.aquario.horoscope.adapter.GoodAdapter;

public class FragmentZodiacDetail extends BaseFragment {

    private static final int LOGIN_REQUEST = 0;
    ImageView icon;
    TextView titleTv;
    TextView characterTitleTv;
    TextView characterMainTv;
    TextView compatTitleTv;
    TextView goodTitleTv;
    TextView badTitleTv;
    TextView celesThTv;
    TextView celesEnTv;
    TextView characterDateTv;


    String data;
    String title;


    String[] compatArray;
    String[] goodTitleMain;
    String[] badTitleMain;
    String[] celebsEn;
    String[] celebsTh;

    String compatTitle;
    String goodTitle;
    String badTitle;
    String celebsThTitle;
    String celebsEnTitle;
    String characterDateTtitle;
    //ListView
    HorizontalListView hlvCompat;
    HorizontalListView hlvGood;
    HorizontalListView hlvBad;
    ListView hlvCelesTH;
    HorizontalListView hlvCelesEn;
    //Adapter
    CompatAdapter compatAdapter;
    GoodAdapter goodAdapter;
    BadAdapter badAdapter;
    CelebsTnAdapter celebsTnAdapter;
    CelebsEnAdapter celebsEnAdapter;

    ArrayList<Zodiac> compatList = new ArrayList<>();


    private ParseUser currentUser;


    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private Button loginOrLogoutButton;
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static FragmentZodiacDetail newInstance(int page, String title, String data) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("title", title);
        args.putString("data", data);
        FragmentZodiacDetail fragment = new FragmentZodiacDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
            title = getArguments().getString("title");
            data = getArguments().getString("data");

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_zodiac_detail, container, false);
        ApiBus.getInstance().post(new GetTasksEvent());

        titleTextView = (TextView) rootView.findViewById(R.id.profile_title);
        emailTextView = (TextView) rootView.findViewById(R.id.profile_email);
        nameTextView = (TextView) rootView.findViewById(R.id.profile_name);

        titleTv = (TextView) rootView.findViewById(R.id.title);
        characterTitleTv = (TextView) rootView.findViewById(R.id.character_title);
        characterMainTv = (TextView) rootView.findViewById(R.id.character_main);

        compatTitleTv = (TextView) rootView.findViewById(R.id.compat_title);
        characterDateTv = (TextView) rootView.findViewById(R.id.character_date);
        goodTitleTv = (TextView) rootView.findViewById(R.id.good_title);
        badTitleTv = (TextView) rootView.findViewById(R.id.bad_title);

        celesThTv = (TextView) rootView.findViewById(R.id.celes_th);
        celesEnTv = (TextView) rootView.findViewById(R.id.celes_en);


        hlvCompat = (HorizontalListView) rootView.findViewById(R.id.hlvCompat);
        hlvGood = (HorizontalListView) rootView.findViewById(R.id.hlvGood);
        hlvBad = (HorizontalListView) rootView.findViewById(R.id.hlvBad);
        hlvCelesTH = (ListView) rootView.findViewById(R.id.hlvCelesTH);
        hlvCelesEn = (HorizontalListView) rootView.findViewById(R.id.hlvCelesEn);


        titleTv.setText(title);


        characterTitleTv.setText(getStringResourceByName(data + "_character_title"));
        characterMainTv.setText(getStringResourceByName(data + "_main"));


        compatArray = getStringArrayResourceByName(data + "_compat_array");
        compatTitle = getStringResourceByName(data + "_compat_title");
        characterDateTtitle = getStringResourceByName(data + "_date");
        goodTitle = getStringResourceByName(data + "_good_title");
        badTitle = getStringResourceByName(data + "_bad_title");
        celebsThTitle = getStringResourceByName(data + "_thai");
        celebsEnTitle = getStringResourceByName(data + "_wold");

        goodTitleMain = getStringArrayResourceByName(data + "_good_main_array");
        badTitleMain = getStringArrayResourceByName(data + "_bad_main_array");
        celebsEn = getStringArrayResourceByName(data + "_world_celeb_array");
        celebsTh = getStringArrayResourceByName(data + "_thai_celeb_array");

        Log.e("56780", compatTitle + "");

        for (int i = 0; i < compatArray.length; i++) {
            String[] parts = compatArray[i].split(",");
            Zodiac zodiac = null;

            if (parts.length == 4) {
                zodiac = new Zodiac(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                compatList.add(zodiac);
            }
        }

        compatAdapter = new CompatAdapter(getActivity(), compatList);
        goodAdapter = new GoodAdapter(getActivity(), goodTitleMain);
        badAdapter = new BadAdapter(getActivity(), badTitleMain);
        celebsTnAdapter = new CelebsTnAdapter(getActivity(), celebsTh);
        celebsEnAdapter = new CelebsEnAdapter(getActivity(), celebsEn);

        hlvCompat.setAdapter(compatAdapter);
        hlvGood.setAdapter(goodAdapter);
        hlvBad.setAdapter(badAdapter);
        hlvCelesTH.setAdapter(celebsTnAdapter);
        hlvCelesEn.setAdapter(celebsEnAdapter);

        //setListViewHeightBasedOnItems(hlvGood);
        //setListViewHeightBasedOnItems(hlvBad);
        setListViewHeightBasedOnItems(hlvCelesTH);

        compatTitleTv.setText(compatTitle);
        goodTitleTv.setText(goodTitle);
        badTitleTv.setText(badTitle);
        celesThTv.setText(celebsThTitle);
        celesEnTv.setText(celebsEnTitle);
        characterDateTv.setText(characterDateTtitle);


        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "font/supermarket.ttf");

        compatTitleTv.setTypeface(face);
        goodTitleTv.setTypeface(face);
        badTitleTv.setTypeface(face);
        celesThTv.setTypeface(face);
        celesEnTv.setTypeface(face);
        characterTitleTv.setTypeface(face);

        ImageView ava = (ImageView) rootView.findViewById(R.id.avatra);
        Picasso.with(getActivity())
                .load(getDrawableResourceByName(data + "_iconapp"))
//                .centerCrop()
//                .resize(200, 200)
//                .transform(new RoundedTransformation(100, 4))
                .into(ava);

        YoYo.with(Techniques.Tada)
                .duration(2100)
                .playOn(rootView.findViewById(R.id.avatra));

        ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada)
                        .duration(2100)
                        .playOn(rootView.findViewById(R.id.avatra));
            }
        });

        String packageName = getActivity().getPackageName();
        //String a = getResources().getIdentifier("taurus_world_celeb_array", "string", packageName);
        String[] a = getResources().getStringArray(R.array.taurus_world_celeb_array);

        hlvCompat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ActivityZodiacDetail.class);
                intent.putExtra("title", compatList.get(i).nameEn);
                intent.putExtra("data", compatList.get(i).codeName);
                intent.putExtra("number", compatList.get(i).id);
                startActivity(intent);
            }
        });


        return rootView;
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

    private String getStringResourceByName(String aString) {
        String packageName = getActivity().getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        Log.e("asdf555", aString);
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


}