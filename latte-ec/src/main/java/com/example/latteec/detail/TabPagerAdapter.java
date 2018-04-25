package com.example.latteec.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by liangbingtian on 2018/4/25.
 * FragmentStatePagerAdapter 和普通的PagerAdapter有本质上的区别，就是它并不会保留每个Pager上的状态
 * 也就是说当我们的页面销毁的时候，我们的数据状态也会随之销毁,用于在用户快速操作等情况时，我们的数据进行更新
 * 我们不会对我们的数据进行缓存，所以要使用这个Adapter
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<String> TAB_TITLE = new ArrayList<>();
    //传入图片组
    private final ArrayList<ArrayList<String>> PICTURES = new ArrayList<>();

    public TabPagerAdapter(FragmentManager fm, JSONObject data) {
        super(fm);
        //获取tabs的信息
        final JSONArray tabs = data.getJSONArray("tabs");
        final int size = tabs.size();
        for (int i = 0; i < size; i++) {
            final JSONObject eachTab = tabs.getJSONObject(i);
            final String name = eachTab.getString("name");
            final JSONArray pictureUrls = eachTab.getJSONArray("pictures");
            final ArrayList<String> eachTabPicturesArray = new ArrayList<>();
            //存储每个图片
            final int pictureSize = pictureUrls.size();
            for (int j = 0; j < pictureSize; j++){
                eachTabPicturesArray.add(pictureUrls.getString(j));
            }
            TAB_TITLE.add(name);
            PICTURES.add(eachTabPicturesArray);
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return ImageDelegate.create(PICTURES.get(0));
        }else if (position == 1){
            return ImageDelegate.create(PICTURES.get(1));
        }
        return null;
    }

    @Override
    public int getCount() {

        return TAB_TITLE.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLE.get(position);
    }
}
