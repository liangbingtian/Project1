package com.example.latteec.main.SearchResult;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.ui.recycler.DataConverter;
import com.example.latte.ui.recycler.ItemType;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by liangbingtian on 2018/4/28.
 */

public class SearchResultDataConverter extends DataConverter {


    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSONArray.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String title = data.getString("title");
            final String desc = data.getString("desc");
            final double price = data.getDouble("price");
            final int id = data.getInteger("goodsId");

            final MultipleItemEntity multipleItemEntity = MultipleItemEntity
                    .builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.SEARCH_RESULT)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(SearchResultItemFields.TITLE, title)
                    .setField(SearchResultItemFields.DESC, desc)
                    .setField(SearchResultItemFields.PRICE, price)
                    .build();

            dataList.add(multipleItemEntity);
        }


        return dataList;
    }
}
