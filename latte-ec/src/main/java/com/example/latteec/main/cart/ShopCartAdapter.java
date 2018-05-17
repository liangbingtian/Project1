package com.example.latteec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.latte.app.Latte;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latte.ui.recycler.MultipleRecyclerAdapter;
import com.example.latte.ui.recycler.MultipleViewHolder;
import com.example.latteec.R;
import com.example.latteec.detail.GoodsDetailDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

/**
 * Created by liangbingtian on 2018/4/19.
 */

public class ShopCartAdapter extends MultipleRecyclerAdapter {

    private boolean mIsSelectedAll = false;
    private ICartItemListener mCartItemListener = null;
    private double mTotalPrice = 0.00;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        //初始化总价
        for (MultipleItemEntity entity : data) {
            final double price = entity.getField(ShopCartItemFields.PRICE);
            final int count = entity.getField(ShopCartItemFields.COUNT);
            final double total = price * count;
            mTotalPrice = mTotalPrice + total;
        }

        //添加购物车item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        this.mIsSelectedAll = isSelectedAll;
    }

    public void setCartItemListener(ICartItemListener listener) {
        this.mCartItemListener = listener;
    }
    public double getTotalPrice(){
        return mTotalPrice;
    }
    public void setTotalPrice(double price){
        this.mTotalPrice = price;
    }
    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //先取出所有值
                final int id = entity.getField(MultipleFields.ID);
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(ShopCartItemFields.TITLE);
                final String desc = entity.getField(ShopCartItemFields.DESC);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                final double price = entity.getField(ShopCartItemFields.PRICE);

                //取出所有控件
                final AppCompatImageView imageThumb = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart);

                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imageThumb);

                //在左侧勾勾渲染之前改变选与否的状态
                entity.setField(ShopCartItemFields.IS_SELECTED, mIsSelectedAll);
                final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);

                //根据数据状态显示左侧的勾
                if (isSelected) {
                    iconIsSelected
                            .setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                } else {
                    iconIsSelected.setTextColor(Color.GRAY);
                }
                //添加左侧勾的点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final boolean currentSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                        if (currentSelected) {
                            iconIsSelected.setTextColor(Color.GRAY);
                            entity.setField(ShopCartItemFields.IS_SELECTED, false);
                        } else {
                            iconIsSelected
                                    .setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                            entity.setField(ShopCartItemFields.IS_SELECTED, true);
                        }
                    }
                });

                //添加加减事件
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);
                        final int goodId = entity.getField(MultipleFields.ID);
                        if (Integer.parseInt(tvCount.getText().toString()) > 1) {

                            int countNum = Integer.parseInt(tvCount.getText().toString());
                            countNum--;
                            entity.setField(ShopCartItemFields.COUNT,countNum);
                            tvCount.setText(String.valueOf(countNum));
                            if (mCartItemListener != null) {
                                mCartItemListener.onItemClickMin(price);
                            }
                            RestClient.builder()
                                    .url("http://172.20.10.8:8088/userCart/change.do")
                                    .loader(mContext)
                                    .params("goodId",goodId)
                                    .params("count", countNum)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {

                                        }
                                    })
                                    .build()
                                    .post();
                        }
                    }
                });

                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentCount = entity.getField(ShopCartItemFields.COUNT);
                        final int goodId = entity.getField(MultipleFields.ID);
                        int countNum = Integer.parseInt(tvCount.getText().toString());
                        countNum++;
                        entity.setField(ShopCartItemFields.COUNT,countNum);
                        tvCount.setText(String.valueOf(countNum));
                        if (mCartItemListener != null) {

                            mCartItemListener.onItemClickPlus(price);
                        }
                        RestClient.builder()
                                .url("http://172.20.10.8:8088/userCart/change.do")
                                .loader(mContext)
                                .params("goodId",goodId)
                                .params("count", countNum)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {

                                    }
                                })
                                .build()
                                .post();
                    }
                });

                imageThumb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCartItemListener != null) {
                            final int goodId = entity.getField(MultipleFields.ID);
                            mCartItemListener.onThumbClick(goodId);
                        }
                    }
                });

                break;
            default:
                break;
        }
    }
}
