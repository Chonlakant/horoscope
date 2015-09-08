package co.aquario.horoscope.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskList {


    /**
     * error : false
     * tasks : [{"id":1,"status":0,"createdAt":"2015-08-15 00:17:56","task":"jfjfjfjfjfj"},{"id":2,"status":0,"createdAt":"2015-08-15 00:18:01","task":"akakaka"}]
     */
    @SerializedName("error")
    private boolean error;
    @SerializedName("tasks")
    private List<Tasks> Tasks;

    public void setError(boolean error) {
        this.error = error;
    }

    public void setTasks(List<Tasks> Tasks) {
        this.Tasks = Tasks;
    }

    public boolean isError() {
        return error;
    }

    public List<Tasks> getTasks() {
        return Tasks;
    }

    public class TasksEntity {
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
}

