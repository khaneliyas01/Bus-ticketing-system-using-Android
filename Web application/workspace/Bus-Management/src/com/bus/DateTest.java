/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bus;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author jaydeep
 */
public class DateTest {
    
    public static void main(String[] args)throws Exception
    {
        String date = "28/11/2017";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = dateFormat.parse(date);
        String str = dateFormat.format(date1);
        System.out.println(str);
        int flag = DateTest.dateDiffernce(str);
        System.out.println(flag);
    }
    
    public static int dateDiffernce(String new1)throws Exception
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date date = new Date();
//System.out.println(dateFormat.format(date));
  //System.out.println(new1);  
     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 =  sdf.parse(new1);
        Date date2 =  sdf.parse(dateFormat.format(date));

        //System.out.println("date1 : " + sdf.format(date1));
        //System.out.println("date2 : " + sdf.format(date2));
        //System.out.println(date1.compareTo(date2));
        if (date1.compareTo(date2) > 0) {
            System.out.println("Date1 is after Date2");
        } else if (date1.compareTo(date2) < 0) {
            System.out.println("Date1 is before Date2");
        } else if (date1.compareTo(date2) == 0) {
            System.out.println("Date1 is equal to Date2");
        } else {
            System.out.println("How to get here?");
        }
        
          return date1.compareTo(date2);
        }
}

