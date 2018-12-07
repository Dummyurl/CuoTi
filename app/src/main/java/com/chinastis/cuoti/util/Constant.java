package com.chinastis.cuoti.util;

import android.os.Environment;

import java.io.File;
import java.util.Map;

/**
 * Created by xianglong on 2018/12/5.
 */

public class Constant {

    public static String USER = "user";
    public static String USER_ID = "user";

    public static Map<String,String> classMap;
    public static Map<String,String> understandMap;


    public static final String BASE_URL = "http://192.168.199.132:8080/afterschool/";

    public static  final String PATH = Environment.getExternalStorageDirectory()+ File.separator
            +"cuoti"+File.separator;

    //intent key
    public static final String TITLE = "title";

    public static final int TYPE_VIDEO = 0;
    public static final int TYPE_PHOTO = 1;

}
