package com.example.latteec.main.sort.content;

/**
 * Created by liangbingtian on 2018/4/16.
 */

public class SectionContentItemEntity {

    private int mGoodsId = 0;
    private String mGoodsName = null;
    //商品的缩略图
    private String mGoodsThumb = null;

    public String getGoodsThumb() {
        return mGoodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.mGoodsThumb = goodsThumb;
    }

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        this.mGoodsId = goodsId;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public void setGoodsName(String goodsName) {
        this.mGoodsName = goodsName;
    }
}
