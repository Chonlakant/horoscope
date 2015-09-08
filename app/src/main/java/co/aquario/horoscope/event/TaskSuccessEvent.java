package co.aquario.horoscope.event;

import java.util.List;

import co.aquario.horoscope.model.Tasks;

/**
 * Created by Mac on 3/2/15.
 */
public class TaskSuccessEvent {
    public List<Tasks> tasksData;

    public TaskSuccessEvent(List<Tasks> tasksData) {
        this.tasksData = tasksData;
    }
}
