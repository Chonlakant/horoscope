package co.aquario.horoscope.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root1 on 8/15/15.
 */
public class Tasks {

    /**
     * id : 1
     * status : 0
     * createdAt : 2015-08-15 00:17:56
     * task : jfjfjfjfjfj
     */
    @SerializedName("id")
    private int id;
    @SerializedName("status")
    private int status;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("task")
    private String task;

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTask() {
        return task;
    }
}
