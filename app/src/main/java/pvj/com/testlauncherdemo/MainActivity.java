package pvj.com.testlauncherdemo;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;
import pvj.com.testlauncherdemo.bean.AppInfo;
import pvj.com.testlauncherdemo.db.RealmHelper;
import pvj.com.testlauncherdemo.utils.AppInfoProvider;
import pvj.com.testlauncherdemo.utils.AppLevelUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.first_container)
    LinearLayout firstContainer;
    @BindView(R.id.second_container)
    ViewPager secondContainer;
    @BindView(R.id.getreaml)
    Button getReaml;


    private List<AppInfo> mAllAppList;

    private int curIndex;
    private View curView;

    RealmHelper mRealmHelper;

    List<View> contentViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRealmHelper = new RealmHelper();
        getAllAppList();
        refreshAppListUi();
    }


    public void getAllAppList() {
        AppInfoProvider mAppInfoProvider = new AppInfoProvider(this);
        List<AppInfo> results = mAppInfoProvider.loadAllAppsByBatch();

        List<AppInfo> list = (List<AppInfo>) mRealmHelper.findAll(AppInfo.class);

        for (int i = 0; i < results.size(); i++) {
            boolean isContains = list.contains(results.get(i));
            // 数据库未存在 插入进去
            if (!isContains) {
                AppLevelUtils.setAppLevel(results.get(i));
                mRealmHelper.insert(results.get(i));
            }

            Log.d(TAG, i + results.get(i).getPackageName() + isContains);
        }

        mAllAppList = mRealmHelper.findAllByLevel();
        //重新排序一下
        for (int i = 0; i < mAllAppList.size(); i++) {
            AppInfo tem = mAllAppList.get(i);
            tem.setLevel(i);
            mRealmHelper.updateAppInfo(tem);
        }

        mAllAppList = mRealmHelper.findAllByLevel();

    }


    public void refreshAppListUi() {
        firstContainer.removeAllViews();
        secondContainer.removeAllViews();
        contentViews.clear();

        int count = 6;

        int c = secondContainer.getCurrentItem();

        int icount = mAllAppList.size() / count + ((mAllAppList.size() % count) > 0 ? 1 : 0);

        for (int j = 0; j < icount; j++) {
            if (j > 0) {
                LinearLayout temLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                temLinearLayout.setLayoutParams(lp);
                temLinearLayout.setHorizontalGravity(LinearLayout.HORIZONTAL);
                contentViews.add(temLinearLayout);
            }

            int max = (j + 1) * count <= mAllAppList.size() ? (j + 1) * count : mAllAppList.size();

            for (int i = j * count; i < max; i++) {
                final View appView = LayoutInflater.from(this).inflate(R.layout.app_item, null);

                ImageView appIcon = (ImageView) appView.findViewById(R.id.app_icon);
                TextView appText = (TextView) appView.findViewById(R.id.app_name);

                appText.setText(mAllAppList.get(i).getLabel());
                //    appIcon.setImageDrawable(mAllAppList.get(i).getIcon());
                appView.setTag(mAllAppList.get(i).getLevel());
                appView.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        ClipData.Item item = new ClipData.Item((v.getTag() + ""));
                        ClipData data = new ClipData((v.getTag() + ""),
                                new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                                item);
                        v.startDrag(data, new View.DragShadowBuilder(v), null, 0);

                        Log.d(TAG, appView + " setVisibility(View.INVISIBLE)");

                        curView = v;
                        curView.setAlpha(0.5f);
                        curView.setScaleX(0.8f);
                        curView.setScaleY(0.8f);
                        curIndex = (Integer) v.getTag();
                        return true;
                    }
                });

                appView.setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        final int action = event.getAction();

                        switch (action) {
                            case DragEvent.ACTION_DRAG_STARTED:
                                Log.d(TAG, v.getTag() + ":STARTED");
                                return true;
                            case DragEvent.ACTION_DRAG_ENTERED:
                                Log.d(TAG, v.getTag() + ":ENTERED");
                                if (v == curView) {
                                    return true;
                                }
                                v.setScaleX(1.2f);
                                v.setScaleY(1.2f);
                                return true;
                            case DragEvent.ACTION_DRAG_LOCATION:
                                Log.d(TAG, v.getTag() + ":LOCATION");
                                return true;
                            case DragEvent.ACTION_DRAG_EXITED:
                                Log.d(TAG, v.getTag() + ":EXITED");
                                if (v == curView) {
                                    return true;
                                }
                                v.setScaleX(1f);
                                v.setScaleY(1f);
                                return true;
                            case DragEvent.ACTION_DROP:
                                Log.d(TAG, v.getTag() + ":DROP");

                                if (curIndex == (Integer) v.getTag()) {
                                    return true;
                                }

                                AppInfo a1 = mAllAppList.get(curIndex);
                                a1.setLevel((Integer) v.getTag());

                                AppInfo a2 = mAllAppList.get((Integer) v.getTag());
                                a2.setLevel(curIndex);
                                mRealmHelper.updateAppInfo(a1);
                                mRealmHelper.updateAppInfo(a2);

                                Collections.swap(mAllAppList, curIndex, (Integer) v.getTag());
                                refreshAppListUi();
                                return true;
                            case DragEvent.ACTION_DRAG_ENDED:
                                Log.d(TAG, v.getTag() + ":ENDED");
                                Log.d(TAG, appView + " setVisibility(View.VISIBLE)");
                                curView.setAlpha(1f);
                                curView.setScaleX(1f);
                                curView.setScaleY(1f);
                                return true;
                            default:
                                break;
                        }
                        return false;
                    }
                });

                if (j < 1) {
                    firstContainer.addView(appView);
                }

                if (1 <= j) {
                    ((LinearLayout) (contentViews.get(j - 1))).addView(appView);
                }
            }


        }
        secondContainer.setAdapter(new AppsPageAdapter());
        secondContainer.setCurrentItem(c);

    }

    @OnClick(R.id.getreaml)
    public void onViewClicked() {
        List<AppInfo> results = mRealmHelper.findAllByLevel();

        for (int i = 0; i < results.size(); i++) {
            Log.d(TAG, results.get(i).toString());
        }
    }


    class AppsPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return contentViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(contentViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(contentViews.get(position));
            return contentViews.get(position);
        }
    }

}
