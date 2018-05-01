package com.example.latteec.orderDetail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.ui.recycler.DataConverter;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by liangbingtian on 2018/5/1.
 */

public class OrderDetailDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String desc = data.getString("descr");
            final String title = data.getString("title");
            final String orderId = data.getString("orderid");
            final int goodId = data.getInteger("goodid");
            final int status = data.getInteger("status");
            final double price = data.getDouble("price");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, OrderDetailItemType.ORDER_DETAIL_ITEM)
                    .setField(OrderDetailItemFields.THUMB, thumb)
                    .setField(OrderDetailItemFields.DESC, desc)
                    .setField(OrderDetailItemFields.TITLE, title)
                    .setField(OrderDetailItemFields.ORDERID, orderId)
                    .setField(OrderDetailItemFields.GOODID, goodId)
                    .setField(OrderDetailItemFields.STATUS, status)
                    .setField(OrderDetailItemFields.PRICE, price)
                    .build();

            dataList.add(entity);
        }

        return dataList;
    }
}
