package com.example.latteec.main.personal.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.latte.ui.recycler.DataConverter;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by liangbingtian on 2018/4/22.
 */

public class OrderListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; ++i) {
            final JSONObject data = array.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String orderNo = data.getString("orderid");
            final double price = data.getDouble("price");
            final String time = data.getString("date");
            final String address = data.getString("address");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(OrderListItemType.ITEM_ORDER_LIST)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(OrderItemFields.ORDERNO, orderNo)
                    .setField(OrderItemFields.PRICE, price)
                    .setField(OrderItemFields.TIME, time)
                    .setField(OrderItemFields.ADDRESS, address)
                    .build();

            ENTITIES.add(entity);
        }


        return ENTITIES;
    }
}
