package com.example.latteec.orderDetail;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latte.ui.recycler.MultipleRecyclerAdapter;
import com.example.latte.ui.recycler.MultipleViewHolder;
import com.example.latteec.R;

import java.util.List;

/**
 * Created by liangbingtian on 2018/5/1.
 */

public class OrderDetailAdapter extends MultipleRecyclerAdapter{

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected OrderDetailAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderDetailItemType.ORDER_DETAIL_ITEM, R.layout.item_order_detail);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case OrderDetailItemType.ORDER_DETAIL_ITEM:
                final int goodId = entity.getField(OrderDetailItemFields.GOODID);
                final String orderId = entity.getField(OrderDetailItemFields.ORDERID);
                final String title = entity.getField(OrderDetailItemFields.TITLE);
                final String desc = entity.getField(OrderDetailItemFields.DESC);
                final String thumb = entity.getField(OrderDetailItemFields.THUMB);
                final int status = entity.getField(OrderDetailItemFields.STATUS);
                final int counts = entity.getField(OrderDetailItemFields.COUNTS);
                final double price = entity.getField(OrderDetailItemFields.PRICE);

                final AppCompatImageView orderDetailImageView = holder.getView(R.id.image_item_order_detail);
                final AppCompatTextView orderDetailTitle = holder.getView(R.id.tv_item_order_detail_title);
                final AppCompatTextView orderDetailDesc = holder.getView(R.id.tv_item_order_detail_desc);
                final AppCompatTextView orderDetailPrice = holder.getView(R.id.tv_item_order_detail_price);
                final AppCompatTextView orderDetailStatus = holder.getView(R.id.tv_item_order_detail_status);

                orderDetailTitle.setText(title);
                orderDetailDesc.setText("个数: "+String.valueOf(counts));
                orderDetailPrice.setText(String.valueOf(price));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(orderDetailImageView);
                String statusText = null;
                if (status == 0){
                    statusText = "待付款";
                }else if (status == 1){
                    statusText = "已付款";
                }else if (status == 2){
                    statusText = "待收货";
                }
                orderDetailStatus.setText(statusText);
                break;
            default:
                break;
        }
    }
}
