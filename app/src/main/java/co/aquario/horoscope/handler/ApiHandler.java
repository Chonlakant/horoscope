package co.aquario.horoscope.handler;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import co.aquario.horoscope.event.GetTasksEvent;
import co.aquario.horoscope.event.SomeEvent;
import co.aquario.horoscope.event.TaskSuccessEvent;
import co.aquario.horoscope.model.TaskList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ApiHandler {

    private Context context;
    private ApiService api;
    private ApiBus apiBus;

    public ApiHandler(Context context, ApiService api,
                      ApiBus apiBus) {

        this.context = context;
        this.api = api;
        this.apiBus = apiBus;
    }

    public void registerForEvents() {
        apiBus.register(this);
    }

    @Subscribe public void onSomeEvent(SomeEvent event) {
        Log.e("HEY2!", "SomeEvent");

        Map<String, String> options = new HashMap<String, String>();
        options.put("key1", event.getVar1());
        options.put("key2", Integer.toString(event.getVar2()));

    }

    @Subscribe public void onGetTaskEvent(GetTasksEvent event) {
        api.getTask(new Callback<TaskList>() {
            @Override
            public void success(TaskList taskList, Response response) {
                Log.e("hahahaSIze",taskList.getTasks().size() + "");
                ApiBus.getInstance().post(new TaskSuccessEvent(taskList.getTasks()));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("hehehehe",error.getLocalizedMessage()+"");
            }
        });
    }





}
