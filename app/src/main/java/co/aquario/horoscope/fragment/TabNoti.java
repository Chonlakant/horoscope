package co.aquario.horoscope.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.astuetz.PagerSlidingTabStrip;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.otto.Subscribe;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.aquario.horoscope.MainActivity;
import co.aquario.horoscope.R;
import co.aquario.horoscope.activity.ActivityNoti;
import co.aquario.horoscope.activity.ActivityZodiacDetail;
import co.aquario.horoscope.adapter.ImageAdapter;
import co.aquario.horoscope.event.GetTasksEvent;
import co.aquario.horoscope.event.TaskSuccessEvent;
import co.aquario.horoscope.handler.ApiBus;
import co.aquario.horoscope.model.Daily;

public class TabNoti extends BaseFragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final String[] CONTENT = new String[]{"ARIES", "TAURUS", "GEMINI", "CANCER",
            "LEO", "VIRGO", "LIBRA", "SCORPIO", "SAGITTARIUS", "CAPRICORN", "AQUARIUS",
            "PISCES"};


    private static final String[] CONTENT2 = new String[]{"aries", "taurus", "gemini", "cancer",
            "leo", "virgo", "libra", "scorpio", "sagittarius", "capricorn", "aquarius",
            "pisces"};

    private static final String[] CONTENT3 = new String[]{"ราศีเมษ", "ราศีพฤษภ", "ราศีเมถุน", "ราศีกรกฎ",
            "ราศีสิงห์", "ราศีกันย์", "ราศีตุลย์", "ราศีพิจิก", "ราศีธนู", "ราศีมังกร", "ราศีกุมภ์",
            "ราศีมีน"};

    private static final String[] CONTENT4 = new String[]{"(21 มี.ค. - 19 เม.ย.)", "(20 เม.ย. - 20 พ.ค.)", "(21 พ.ค. - 20 มิ.ย.)", "(21 มิ.ย. - 22 ก.ค.)",
            "(23 ก.ค. - 22 ส.ค.)", "(23 ส.ค. - 22 ก.ย.)", "(22 ก.ย. - 23 ต.ค.)", "(23 ต.ค. - 21 พ.ย.)", "(22 พ.ย. - 21 ธ.ค.)", "(22 ธ.ค. - 19 ม.ค.)", "(20 ม.ค. - 18 ก.พ.)",
            "(19 ก.พ. - 20 มี.ค.)"};

    private static final int[] ICONS = new int[]{R.drawable.aries, R.drawable.taurus,
            R.drawable.gemini, R.drawable.cancer, R.drawable.leo, R.drawable.virgo,
            R.drawable.libra, R.drawable.scorpio, R.drawable.sagittarius, R.drawable.capricorn,
            R.drawable.aquarius, R.drawable.pisces};
    ParseQuery<Daily> query;
    ImageAdapter imageAdapter;
    GridView gridView;
    private int mPage;
    Button popOkB;
    Dialog dialog;

    public static TabNoti newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TabNoti fragment = new TabNoti();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    Toolbar toolbar;
    int toolbarHeight = 0;
    int tabHeight = 0;

    PagerSlidingTabStrip mainTab;
    String titleParse;
    Date dateParse;
    String detailParse;
    String data;
    List<Daily> dailyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main_zodiac, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridview);


//        final String mystring2 = getResources().getString(R.string.aquarius_title);

        toolbar = ((MainActivity) getActivity()).getToolbar();
        mainTab = ((MainActivity) getActivity()).tabsStrip;

        if (toolbar != null) {
            toolbarHeight = toolbar.getHeight();
        }

        if (mainTab != null) {
            tabHeight = mainTab.getHeight();
        }

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x / 4;
        //int height = (size.y - toolbarHeight - tabHeight - 320) / 4;
        int height = (size.y ) / 5;

        imageAdapter = new ImageAdapter(getActivity(), CONTENT4, ICONS, height);
        gridView.setAdapter(imageAdapter);

        query = ParseQuery.getQuery("Daily");

//        query.getInBackground("Hm5vGPVL4J", new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if (e == null) {
//                     titleParse = object.getString("title");
//                     dateParse = object.getDate("date");
//                     detailParse = object.getString("detail");
//
//                    Log.e("666", titleParse);
//                    Log.e("555", dateParse + "");
//                    Log.e("777", detailParse);
//
//                } else {
//                    // check errors.
//                }
//            }
//        });

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_YEAR, 1); // <--
        Date tomorrow = cal.getTime();

        Calendar tmrCal = Calendar.getInstance();
        tmrCal.setTime(now);

        tmrCal.add(Calendar.DAY_OF_YEAR, -1); // <--
        Date yesterday = tmrCal.getTime();

        Log.e("yesterday",yesterday+"");

        //พรุ่งนี้
        //query.whereGreaterThanOrEqualTo("date",  Calendar.getInstance().getTime());
        //query.whereLessThan("date",  tomorrow);

        //วันนี้
        query.whereGreaterThanOrEqualTo("date", yesterday);

        query.whereLessThan("date", Calendar.getInstance().getTime());

        query.orderByAscending("order");

        query.findInBackground(new FindCallback<Daily>() {

            @Override
            public void done(List<Daily> events, ParseException e) {


                if (e == null) {

                    if (events.size() != 0) {
                        dailyList = events;

                        Log.d("Events", "Total Events:  " + events.size());
                        for (int i = 0; i < events.size(); i++) {
                            Daily daily = events.get(i);
                            System.out.println("ดวงของราศี " + daily.getTitle() + ":  " + daily.getDetail());
                        }

                    }

                } else {
                    Log.d("Events", "Error:  " + e.getMessage());
                }

            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                data = dailyList.get(i).getDetail();

                if (data != null) {
                    Intent intent = new Intent(getActivity(), ActivityNoti.class);
                    Log.e("Data_555", data);
                    intent.putExtra("title", CONTENT3[i]);
                    intent.putExtra("data", CONTENT2[i]);
                    intent.putExtra("data2", data);
                    intent.putExtra("number", i);
                    startActivity(intent);
                } else {
                    dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialog);
                    popOkB = (Button) dialog.findViewById(R.id.popOkB);
                    dialog.setTitle("ยังไม่มีข้อมูล..");
                    popOkB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

            }
        });


        Log.e("bbbb", height + "");
        Log.e("bbb", toolbarHeight + "");
        Log.e("bbbb", tabHeight +"");


        return rootView;
    }

    @Subscribe
    public void onGetTaskSuccess(TaskSuccessEvent event) {
        Log.e("hehehe", event.tasksData.get(0).getTask());
        //Toast.makeText(getActivity(),"Check"+event.tasksData.get(0).getTask(),Toast.LENGTH_LONG).show();
    }

}