package com.example.latteec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.ui.recycler.DataConverter;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangbingtian on 2018/4/16.
 */

public class SectionDataConverter extends DataConverter {

//    final List<SectionBean> convert(String json) {
//        final List<SectionBean> dataList = new ArrayList<>();
//        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");
//
//        final int size = dataArray.size();
//        for (int i = 0; i < size; i++) {
//            final JSONObject data = dataArray.getJSONObject(i);
//            final int id = data.getInteger("id");
//            final String title = data.getString("section");
//
//            //添加title
//            final SectionBean sectionTitleBean = new SectionBean(true, title);
//            sectionTitleBean.setId(id);
//            sectionTitleBean.setIsMore(true);
//            dataList.add(sectionTitleBean);
//
//            final JSONArray goods = data.getJSONArray("goods");
//            //商品内容循环
//            final int goodSize = goods.size();
//            for (int j = 0; j < goodSize; ++j) {
//                final JSONObject contentItem = goods.getJSONObject(j);
//                final int goodsId = contentItem.getInteger("goods_id");
//                final String goodsName = contentItem.getString("goods_name");
//                final String goodsThumb = contentItem.getString("goods_thumb");
//                //获取内容
//                final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
//                itemEntity.setGoodsId(goodsId);
//                itemEntity.setGoodsName(goodsName);
//                itemEntity.setGoodsThumb(goodsThumb);
//                //添加内容
//                dataList.add(new SectionBean(itemEntity));
//            }
//            //商品内容循环结束
//        }
//        //Section循环结束
//        return dataList;
//    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int goodsId = data.getInteger("goods_id");
            final String goodsName = data.getString("goods_name");
            final String goodsThumb = data.getString("goods_thumb");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,ContentItemType.CONTENT_ITEM)
                    .setField(ContentITemFields.GOODS_ID,goodsId)
                    .setField(ContentITemFields.GOODS_NAME,goodsName)
                    .setField(ContentITemFields.GOODS_THUMB,goodsThumb)
                    .build();

            dataList.add(entity);
        }
        return dataList;
    }
}
