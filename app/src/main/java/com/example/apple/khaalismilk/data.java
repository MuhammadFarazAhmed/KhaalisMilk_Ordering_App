package com.example.apple.khaalismilk;

/**
 * Created by apple on 10/26/16.
 */

public class data {


    private static String date;
    private static String message;
    private static  String status;


    public data(String date,String message)
    {
        this.date = date;
        this.message = message;

    }
    public data(String date,String message,String status)
    {
        this.date = date;
        this.message = message;
        this.status=status;
    }

    public static String getDate()
    {
        return date;
    }
    public static String getMessage()
    {
        return message;
    }
    public static String getStatus()
    {
        return status;
    }


}
