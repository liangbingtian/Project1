package com.example.latteec.main.personal.uploadProduct.myUploadProduct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.ui.recycler.DataConverter;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by liangbingtian on 2018/5/5.
 */

public class MyUploadProductDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);

            final String title = data.getString("title");
            final String desc = data.getString("desc");
            final JSONArray banners = data.getJSONArray("banners");
            final double price = data.getDouble("price");
            final int id = data.getInteger("id");

            final ArrayList<String> bannerImages = new ArrayList<>();

            final int bannerSize = banners.size();

            for (int j = 0; j < bannerSize; j++) {
                final String banner = banners.getString(j);
                bannerImages.add(banner);
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, MyUploadProductItemType.MY_UPLOAD_PRODUCT)
                    .setField(MyUploadProductItemFields.TITLE, title)
                    .setField(MyUploadProductItemFields.DESC, desc)
                    .setField(MyUploadProductItemFields.PRICE, price)
                    .setField(MyUploadProductItemFields.ID,id)
                    .setField(MyUploadProductItemFields.BANNERS, bannerImages)
                    .build();
            ENTITIES.add(entity);
        }

        return ENTITIES;
    }
}
