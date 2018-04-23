package com.example.latteec.main;

import android.graphics.Color;

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

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"),new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}","分类"),new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}","发现"),new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"),new ShopCartDelegate());
        items.put(new BottomTabBean("{fa-user}","我的"),new PersonalDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
