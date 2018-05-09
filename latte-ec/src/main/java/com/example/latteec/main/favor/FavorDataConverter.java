package com.example.latteec.main.favor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.ui.recycler.DataConverter;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by liangbingtian on 2018/5/9.
 */

public class FavorDataConverter extends DataConverter {


    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final int goodId = data.getInteger("goodid");
            final String title = data.getString("title");
            final double price = data.getDouble("price");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, FavorItemType.FAVOR_ITEM)
                    .setField(FavorItemFields.GOODID, goodId)
                    .setField(FavorItemFields.PRICE, price)
                    .setField(FavorItemFields.THUMB, thumb)
                    .setField(FavorItemFields.TITLE, title)
                    .build();
            dataList.add(entity);
        }

        return dataList;
    }
}
