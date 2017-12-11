package pvj.com.testlauncherdemo.db;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import pvj.com.testlauncherdemo.bean.AppInfo;

/**
 * Created by pengweijie on 2017/12/7.
 */

public class RealmHelper extends BaseRealmHelper{


    public static final String DB_NAME = "launcher.realm";
    public static final int DB_VERSION = 1 ;


    public RealmHelper() {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder()
                .name(DB_NAME)
                .schemaVersion(RealmHelper.DB_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build());
    }



    public List<AppInfo> findAllByLevel() {

        return (List<AppInfo>) findAllByLevel(AppInfo.class,"level");
    }

    /**
     * update （改）
     */
    public void updateAppInfo(AppInfo appInfo) {
        AppInfo appInfo2 = mRealm.where(AppInfo.class).equalTo("packageName", appInfo.getPackageName()).findFirst();
        mRealm.beginTransaction();
        appInfo2.setLevel(appInfo.getLevel());
        mRealm.commitTransaction();
    }
}
