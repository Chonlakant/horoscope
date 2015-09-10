package co.aquario.horoscope.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.squareup.otto.Subscribe;

import java.util.Calendar;

import co.aquario.horoscope.R;
import co.aquario.horoscope.event.TaskSuccessEvent;

import co.aquario.horoscope.event.GetTasksEvent;
import co.aquario.horoscope.handler.ApiBus;

public class Tab3 extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String DATEPICKER_TAG = "datepicker";
    private int mPage;

    public static Tab3 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Tab3 fragment = new Tab3();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);
        ApiBus.getInstance().post(new GetTasksEvent());

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);

        return rootView;
    }

    @Subscribe
    public void onGetTaskSuccess(TaskSuccessEvent event) {
        Log.e("hehehe", event.tasksData.get(0).getTask());
        //Toast.makeText(getActivity(),"Check"+event.tasksData.get(0).getTask(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {

    }
}