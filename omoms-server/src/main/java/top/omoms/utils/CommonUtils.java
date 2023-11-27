package top.omoms.utils;

import com.google.gson.Gson;

import java.util.UUID;

public class CommonUtils {


    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static final Gson GSON = new Gson();

}
