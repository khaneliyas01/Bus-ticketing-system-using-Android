package main.com.busmanagement;

import android.app.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganesh on 11/1/2017.
 */

public class UserData {
    protected static String id,fname,lname,gender,imagename,dob,address,mobile,daten,source,dest,cardno,cvv,amount,email,flag,timen="0",datem="0",ticketid,ticketname,slat="0",slon="0";
    protected static List checkedseats = new ArrayList();
    protected static ProgressDialog pDialog;
    protected static int seatcount=0,total;
    protected static int refcount= 0,waitingflag;
    protected static int newtickets= 0;
    public static double lat;
    public static double lon;
    public static int count;
    public static boolean serviceloc;
    public static double ulat;
    public static double ulon;
}
