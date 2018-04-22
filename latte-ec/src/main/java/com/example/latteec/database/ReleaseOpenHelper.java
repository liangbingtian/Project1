package com.example.latteec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by liangbingtian on 2018/3/26.
 */

public class ReleaseOpenHelper extends DaoMaster.OpenHelper {


    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }
}
