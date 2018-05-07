package com.example.latteec.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.latte.delegates.bottom.BaseBottomDelegate;
import com.example.latte.delegates.bottom.BottomItemDelegate;
import com.example.latte.delegates.bottom.BottomTabBean;
import com.example.latte.delegates.bottom.ItemBuilder;
import com.example.latteec.main.cart.ShopCartDelegate;
import com.example.latteec.main.discover.DiscoverDelegate;
import com.example.latteec.main.index.IndexDelegate;
import com.example.latteec.main.personal.PersonalDelegate;
import com.example.latteec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * Created by liangbingtian on 2018/4/8.
 */

public class EcBottomDelegate extends BaseBottomDelegate {

    private int mIndexId = 0;
    private static final String ARG_INDEX_ID = "ARG_INDEX_ID";

    public static EcBottomDelegate create(@NonNull int indexId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_INDEX_ID, indexId);
        final EcBottomDelegate delegate = new EcBottomDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        final Bundle args = getArguments();
        if (args!=null){
            mIndexId= args.getInt(ARG_INDEX_ID);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
//        items.put(new BottomTabBean("{fa-compass}","发现"),new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new ShopCartDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new PersonalDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return mIndexId;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
