package co.aquario.horoscope.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by root1 on 9/8/15.
 */
@ParseClassName("Daily")
public class Daily extends ParseObject {
//    public String objectId;
//    public String title;
//    public Date date;
//    public String detail;

    public Daily() {

    }

    @Override
    public String getObjectId() {
        return getObjectId();
    }

    @Override
    public void setObjectId(String objectId) {
        put("objectId",objectId);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title",title);
    }

    public Date getDate() {
        return getDate("date");
    }

    public void setDate(Date date) {
        put("date",date);
    }

    public String getDetail() {
        return getString("detail");
    }

    public void setDetail(String detail) {
        put("detail",detail);
    }
}
