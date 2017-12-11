package pvj.com.testlauncherdemo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pvj.com.testlauncherdemo.LauncherApplication;
import pvj.com.testlauncherdemo.bean.AppInfo;

/**
 * Created by pengweijie on 2017/12/6.
 */

public class AppInfoProvider {

    private static final String TAG = "AppInfoProvider";
    private Context mContext;
    private PackageManager mPackageManager;

    public AppInfoProvider(Context context) {
        this.mContext = context;
        mPackageManager = mContext.getPackageManager();

    }

    public List<AppInfo> loadAllAppsByBatch() {
        List<ResolveInfo> resolveInfos = null;
        List<AppInfo> mlistAppInfo = new ArrayList<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveInfos = mPackageManager.queryIntentActivities(mainIntent, 0);
        for (int i = 0; i < resolveInfos.size(); i++) {
            ResolveInfo reInfo = resolveInfos.get(i);

            String packageName = reInfo.activityInfo.packageName;  // 获得应用程序的包名
            String label =  reInfo.loadLabel(mPackageManager).toString(); // 获得应用程序的Label

       //     Drawable icon = reInfo.loadIcon(mPackageManager); // 获得应用程序图标

            if (LauncherApplication.getInstance().getPackageName().equals(packageName)) {
                // 去掉自己
              continue;
            }

            Log.d(TAG, i+"  :"+packageName) ;

            AppInfo appInfo = new AppInfo();
            appInfo.setPackageName(packageName);
            appInfo.setLabel(label);
       //     appInfo.setIcon(icon);
            appInfo.setLevel(i);
            mlistAppInfo.add(appInfo);
        }

        return mlistAppInfo ;
    }

}
