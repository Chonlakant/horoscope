package co.aquario.horoscope.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Calendar;

import co.aquario.horoscope.MainActivity;
import co.aquario.horoscope.MainApplication;
import co.aquario.horoscope.PrefManager;
import co.aquario.horoscope.R;


public class FragmentSelectDay extends BaseFragment implements DatePickerDialog.OnDateSetListener {

    private static final int LOGIN_REQUEST = 0;

    private static final String[] CONTENT2 = new String[]{"aries", "taurus", "gemini", "cancer",
            "leo", "virgo", "libra", "scorpio", "sagittarius", "capricorn", "aquarius",
            "pisces"};
    DatePickerDialog datePickerDialog;


    String id;
    ParseObject Zodiac;

    public static final String DATEPICKER_TAG = "datepicker";
    int daySelect;
    int monthSelect;
    public PrefManager prefManager;
    private ParseUser currentUser;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        prefManager = MainApplication.getPrefManager();

        if (prefManager.isLogin().getOr(false) && !prefManager.zodiac().getOr("").equals("")) {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        }
    }

    String zodiacData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        try {
            currentUser = ParseUser.getCurrentUser().fetch();
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Log.e("userinfo",currentUser.getSessionToken());
        Zodiac = new ParseObject("Zodiac");
        zodiacData = currentUser.getString("zodiacTitle");

//        Log.e("userinfo",zodiacData);

        prefManager = MainApplication.get(getActivity()).getPrefManager();

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);

        id = currentUser.getObjectId();

        if (!prefManager.isLogin().getOr(false))
            datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);

        if(currentUser.getString("zodiacTitle") != null) {
            prefManager.isLogin().put(true);
            prefManager.zodiac().put(zodiacData);
            prefManager.commit();
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        }


        return rootView;
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        //Toast.makeText(getActivity(), "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
        daySelect = day;
        monthSelect = month;
        Log.e("Day", daySelect + "");
        Log.e("Month", monthSelect + "");

        getZodiac(daySelect, monthSelect);


        Zodiac.put("ZodiacTitle", getZodiac(daySelect, monthSelect));
        Zodiac.saveInBackground();
        //PutUser
        currentUser.put("zodiacTitle", getZodiac(daySelect, monthSelect));
        currentUser.saveInBackground();

        String str = getZodiac(daySelect, monthSelect);

        prefManager.isLogin().put(true);
        prefManager.zodiac().put(str);
        prefManager.commit();

        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);

    }

    public String getZodiac(int day, int month) {
        if ((month == 2) && (day >= 21) || (month == 3) && (day <= 19)) {
            return CONTENT2[0];

        } else if ((month == 3) && (day >= 20) || (month == 4) && (day <= 20)) {
            return CONTENT2[1];

        } else if ((month == 4) && (day >= 21) || (month == 5) && (day <= 20)) {
            return CONTENT2[2];

        } else if ((month == 5) && (day >= 21) || (month == 6) && (day <= 22)) {
            return CONTENT2[3];

        } else if ((month == 6) && (day >= 23) || (month == 7) && (day <= 22)) {
            return CONTENT2[4];

        } else if ((month == 7) && (day >= 23) || (month == 8) && (day <= 22)) {
            return CONTENT2[5];

        } else if ((month == 8) && (day >= 23) || (month == 9) && (day <= 22)) {
            return CONTENT2[6];

        } else if ((month == 9) && (day >= 23) || (month == 10) && (day <= 22)) {
            return CONTENT2[7];

        } else if ((month == 10) && (day >= 22) || (month == 11) && (day <= 19)) {
            return CONTENT2[8];

        } else if ((month == 11) && (day >= 22) || (month == 0) && (day <= 21)) {
            return CONTENT2[9];

        } else if ((month == 0) && (day >= 24) || (month == 1) && (day <= 22)) {
            return CONTENT2[10];

        } else if ((month == 1) && (day >= 19) || (month == 2) && (day <= 20)) {
            return CONTENT2[11];
        } else {
            return null;

        }

    }
}