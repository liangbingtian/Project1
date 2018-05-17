package com.example.latteec.main.index.search;

import com.alibaba.fastjson.JSONArray;
import com.example.latte.ui.recycler.DataConverter;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * Created by liangbingtian on 2018/4/24.
 */

public class SearchDataConverter extends DataConverter {

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final String jsonStr = LattePreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if (!jsonStr.equals("")) {
            final JSONArray array = JSONArray.parseArray(jsonStr);
            final int size = array.size();
                for (int i = 0; i < size; i++) {
                    final String historyItemText = array.getString(i);
                    final MultipleItemEntity entity = MultipleItemEntity.builder()
                            .setItemType(SearchItemType.ITEM_SEARCH)
                            .setField(MultipleFields.TEXT, historyItemText)
                            .build();

                    ENTITIES.add(entity);
                }
        }
        return ENTITIES;
    }
}
