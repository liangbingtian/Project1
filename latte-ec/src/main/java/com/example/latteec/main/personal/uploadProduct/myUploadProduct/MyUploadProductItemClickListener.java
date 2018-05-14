package com.example.latteec.main.personal.uploadProduct.myUploadProduct;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.latte.delegates.LatteDelegate;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latteec.detail.GoodsDetailDelegate;

/**
 * Created by liangbingtian on 2018/5/14.
 */

public class MyUploadProductItemClickListener extends SimpleClickListener{

    private final LatteDelegate DELEGATE;

    public MyUploadProductItemClickListener(LatteDelegate delegate) {
        DELEGATE = delegate;
    }

    public static MyUploadProductItemClickListener create(LatteDelegate delegate){
        return new MyUploadProductItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int goodsId = entity.getField(MyUploadProductItemFields.ID);
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(goodsId);
        DELEGATE.getSupportDelegate().start(delegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
