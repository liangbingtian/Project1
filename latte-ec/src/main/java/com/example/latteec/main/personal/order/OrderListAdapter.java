package com.example.latteec.main.personal.order;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latte.ui.recycler.MultipleRecyclerAdapter;
import com.example.latte.ui.recycler.MultipleViewHolder;
import com.example.latteec.R;

import java.util.List;

/**
 * Created by liangbingtian on 2018/4/22.
 */

public class OrderListAdapter extends MultipleRecyclerAdapter{

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_LIST, R.layout.item_order_list);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case OrderListItemType.ITEM_ORDER_LIST:
                final AppCompatImageView imageView = holder.getView(R.id.image_order_list);
                final AppCompatTextView  title = holder.getView(R.id.tv_order_list_title);
                final AppCompatTextView  price = holder.getView(R.id.tv_order_list_price);
                final AppCompatTextView  time = holder.getView(R.id.tv_order_list_time);
                final AppCompatTextView address = holder.getView(R.id.tv_order_address);

                final String titleVal = entity.getField(OrderItemFields.ORDERNO);
                final String timeVal = entity.getField(OrderItemFields.TIME);
                final Double priceVal = entity.getField(OrderItemFields.PRICE);
                final String imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                final String addressVal = entity.getField(OrderItemFields.ADDRESS);

                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into(imageView);

                title.setText(titleVal);
                price.setText("总价: "+String.valueOf(priceVal));
                time.setText("时间: "+timeVal);
                address.setText("收货地址: "+addressVal);

                break;
            default:
                break;
        }
    }
}
