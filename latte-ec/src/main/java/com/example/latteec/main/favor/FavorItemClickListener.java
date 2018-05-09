package com.example.latteec.main.favor;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.latte.delegates.LatteDelegate;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latteec.detail.GoodsDetailDelegate;

/**
 * Created by liangbingtian on 2018/5/9.
 */

public class FavorItemClickListener extends SimpleClickListener{

    private LatteDelegate DELEGATE;

    private FavorItemClickListener(LatteDelegate delegate){
        this.DELEGATE = delegate;
    }

    public static FavorItemClickListener create(LatteDelegate delegate){
        return new FavorItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final int goodId = entity.getField(FavorItemFields.GOODID);
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(goodId);
        DELEGATE.getSupportDelegate().startWithPop(delegate);
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
