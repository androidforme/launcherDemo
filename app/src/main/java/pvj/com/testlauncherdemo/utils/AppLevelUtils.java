package pvj.com.testlauncherdemo.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import pvj.com.testlauncherdemo.bean.AppInfo;

/**
 * Created by pengweijie on 2017/12/7.
 */

public class AppLevelUtils {
    private   static  String[] app= new String[] {
            "com.roadrover.settings",
            "com.roadrover.radio_v2",
            "com.roadrover.filemanager",
            "com.roadrover.bluetooth",
            "com.roadrover.multimedia",
            "com.roadrover.avm.imx6.hd"
    } ;

    private static Map<String,Integer> appMaps= new HashMap();

    static {
        for (int i = 0; i < app.length; i++) {
            appMaps.put(app[i],i);
        }
    }



    public static  void  setAppLevel(AppInfo a ){
        String packageName = a.getPackageName();
        int positon = 100 ;
        if(appMaps.containsKey(packageName)){
           positon = appMaps.get(packageName);
        };
        Log.d("level","packageName:"+packageName+"   positon:"+positon);
        a.setLevel(positon);

    }
}
