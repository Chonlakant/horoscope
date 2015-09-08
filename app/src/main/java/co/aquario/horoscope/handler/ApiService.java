package co.aquario.horoscope.handler;

import co.aquario.horoscope.model.TaskList;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by matthewlogan on 9/3/14.
 */
public interface ApiService {

    @GET("/v1/tasks")
    public void getTask(Callback<TaskList> responseJson);
}
