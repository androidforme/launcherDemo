package pvj.com.testlauncherdemo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;
import pvj.com.testlauncherdemo.db.RealmHelper;

public class LauncherApplication extends Application {

    private String TAG = "LauncherApplication";
    private static LauncherApplication mInstance = null;

    /**
     * 重载方法
     */
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        mInstance = this;

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .schemaVersion(RealmHelper.DB_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    public static LauncherApplication getInstance() {
        return mInstance;
    }

}
