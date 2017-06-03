package com.damian.salonapp.services.sentto;

import java.util.Date;

/**
 * Created by damian on 3/6/17.
 */

public class SentTo {
    private String phoneNum,msg;
    private Date time;
    public SentTo(String phoneNum,String msg,Date time){
            this.phoneNum=phoneNum;
            this.msg=msg;
            this.time=time;
    }

    public String getPhoneNum(){return this.phoneNum;}
    public String getMsg(){return this.msg;}
    public String getDateTime(){return this.time.toString();}



}
