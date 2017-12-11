package pvj.com.testlauncherdemo.bean;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

//Model类 ，用来存储应用程序信息
public class AppInfo extends RealmObject {


    //private Drawable icon; // 应用程序图像

    private String label; // 存放应用程序名

    private String packageName; // 存放应用程序包名

    private String versionName;

    private long firstInstallTime; // 安装时间

    private int level = -1 ;


    public long getFirstInstallTime() {
        return firstInstallTime;
    }



    public AppInfo() {
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AppInfo) {
            AppInfo mAppInfo = (AppInfo) o;
            return getPackageName().equals(mAppInfo.getPackageName());
        } else {
            return false;
        }
    }

//    public Drawable getIcon() {
//        return icon;
//    }
//
//    public void setIcon(Drawable icon) {
//        this.icon = icon;
//    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setFirstInstallTime(long firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int index) {
        this.level = index;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "label='" + label + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", firstInstallTime=" + firstInstallTime +
                ", level=" + level +
                '}';
    }
}
