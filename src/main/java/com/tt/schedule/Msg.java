package com.tt.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tt on 2016/12/26.
 */
public class Msg {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String msg;
    private Date date;

    public Msg(String msg, Date date) {
        this.msg = msg;
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%s  -  %s",dateFormat.format(date),msg);
    }
}
