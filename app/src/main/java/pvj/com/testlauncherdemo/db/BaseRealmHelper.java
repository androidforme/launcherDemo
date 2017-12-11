package pvj.com.testlauncherdemo.db;

import org.json.JSONArray;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * @DateTime: 2016-07-13 09:33
 * @Author: duke
 * @Deacription: dao
 */
public class BaseRealmHelper  {
    public Realm mRealm;

    /**
     * 添加(性能优于下面的saveOrUpdate（）方法)
     *
     * @param object
     * @return 保存或者修改是否成功
     */
    public boolean insert(RealmObject object) {
        try {
            mRealm.beginTransaction();
            mRealm.insert(object);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }

    /**
     * 添加(性能优于下面的saveOrUpdateBatch（）方法)
     *
     * @param list
     * @return 批量保存是否成功
     */
    public boolean insert(List<? extends RealmObject> list) {
        try {
            mRealm.beginTransaction();
            mRealm.insert(list);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }

    /**
     * 添加或者修改(性能优于下面的saveOrUpdate（）方法)
     *
     * @param object
     * @return 保存或者修改是否成功
     */
    public boolean insertOrUpdate(RealmObject object) {
        try {
            mRealm.beginTransaction();
            mRealm.insertOrUpdate(object);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }




    /**
     * 添加或者修改(性能优于下面的saveOrUpdateBatch（）方法)
     *
     * @param list
     * @return 保存或者修改是否成功
     */
    public boolean insertOrUpdateBatch(List<? extends RealmObject> list) {
        try {
            mRealm.beginTransaction();
            mRealm.insertOrUpdate(list);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }

    /**
     * 添加或者修改
     *
     * @param object
     * @return 已经保存的对象
     */
    public <E extends RealmObject> E saveOrUpdate(E object) {
        E savedE = null;
        try {
            mRealm.beginTransaction();
            savedE = mRealm.copyToRealmOrUpdate(object);
            mRealm.commitTransaction();
            return savedE;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return savedE;
        }
    }

    /**
     * 批量添加或者修改
     *
     * @param list
     * @return 全部保存成功 或 全部失败
     */
    public boolean saveOrUpdateBatch(List<? extends RealmObject> list) {
        try {
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(list);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }


    /**
     * save or update RealmObject from json data
     *
     * @param jsonObject json数据
     * @param clazz 具体类型
     * @return 已经保存的对象
     */
    public RealmObject saveOrUpdateFromJSON(Class<? extends RealmObject> clazz, String jsonObject) {
        RealmObject RealmObject = null;
        try {
            mRealm.beginTransaction();
            RealmObject = mRealm.createOrUpdateObjectFromJson(clazz, jsonObject);
            mRealm.commitTransaction();
            return RealmObject;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return RealmObject;
        }
    }

    /**
     * batch save or update from json array
     *
     * @param json json数组
     * @param clazz 类型
     * @return 批量保存json对象是否成功
     */
    public boolean saveOrUpdateFromJSONBatch(Class<? extends RealmObject> clazz, JSONArray json) {
        try {
            mRealm.beginTransaction();
            mRealm.createOrUpdateAllFromJson(clazz, json);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }

    /**
     * 删除当前表中所有数据
     *
     * @param clazz
     * @return
     */
    public boolean deleteAll(Class<? extends RealmObject> clazz) {
        try {
            mRealm.beginTransaction();
            mRealm.delete(clazz);
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }

    /**
     * 按照id删除制定的记录
     * @param clazz 类型
     * @param idFieldName id字段的名称
     * @param id id字段值
     * @return
     */
    public boolean deleteById(Class<? extends RealmObject> clazz, String idFieldName, int id) {
        try {
            mRealm.beginTransaction();
            mRealm.where(clazz).equalTo(idFieldName, id).findAll().deleteFirstFromRealm();
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }

    /**
     * 查询所有
     * @return 返回结果集合
     */
    public RealmResults<? extends RealmObject> findAll(Class<? extends RealmObject> clazz) {
        return mRealm.where(clazz).findAll();
    }

    /**
     * query （查询所有）
     */
    public List<? extends RealmObject> findAllByLevel(Class<? extends RealmObject> clazz,String key) {
        RealmResults<? extends RealmObject> results = findAll(clazz);
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //增序排列
        results=results.sort(key);
//        //降序排列
//        dogs=dogs.sort("id", Sort.DESCENDING);
        return mRealm.copyFromRealm(results);
    }

    /**
     * 清空数据库
     *
     * @return
     */
    public boolean clearDatabase() {
        try {
            mRealm.beginTransaction();
            mRealm.deleteAll();
            mRealm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mRealm.cancelTransaction();
            return false;
        }
    }
}