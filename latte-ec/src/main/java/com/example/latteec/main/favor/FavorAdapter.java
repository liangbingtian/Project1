package com.example.latteec.main.favor;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latte.ui.recycler.MultipleRecyclerAdapter;
import com.example.latte.ui.recycler.MultipleViewHolder;
import com.example.latteec.R;
import com.example.latteec.orderDetail.OrderDetailItemType;

import java.util.List;

/**
 * Created by liangbingtian on 2018/5/9.
 */

public class FavorAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected FavorAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(FavorItemType.FAVOR_ITEM, R.layout.item_favor_product);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case FavorItemType.FAVOR_ITEM:
                final int goodId = entity.getField(FavorItemFields.GOODID);
                final String thumb = entity.getField(FavorItemFields.THUMB);
                final String title = entity.getField(FavorItemFields.TITLE);
                final double price = entity.getField(FavorItemFields.PRICE);

                final AppCompatImageView imageFavor = holder.getView(R.id.image_item_favor);
                final AppCompatTextView titleFavor = holder.getView(R.id.item_favor_title);
                final AppCompatTextView priceFavor = holder.getView(R.id.item_favor_price);

                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imageFavor);
                titleFavor.setText(title);
                priceFavor.setText(String.valueOf(price));
                break;
            default:
                break;
        }
    }
}
