package com.example.latteec.main.cart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.latte.delegates.bottom.BottomItemDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.detail.GoodsDetailDelegate;
import com.example.latteec.main.EcBottomDelegate;
import com.example.latteec.pay.FastPay;
import com.example.latteec.pay.IAlPayResultListener;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/4/19.
 */

public class ShopCartDelegate extends BottomItemDelegate implements ISuccess
        , ICartItemListener, IAlPayResultListener {

    private ShopCartAdapter mAdapter = null;

    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;
    private double mTotalPrice = 0.00;

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll() {
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            mIconSelectAll
                    .setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            mIconSelectAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            //更新RecycleView的显示状态
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }

        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem() {
        double deleteTotal = 0.00;
        final List<MultipleItemEntity> data = mAdapter.getData();
        //要删除的数据
        List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                final double totalPrice = (double) entity.getField(ShopCartItemFields.PRICE) * (int) entity.getField(ShopCartItemFields.COUNT);
                deleteTotal = deleteTotal + totalPrice;
                deleteEntities.add(entity);
            }
        }
        for (int i = 0; i < deleteEntities.size(); i++) {
            final int goodId = deleteEntities.get(i).getField(MultipleFields.ID);
            RestClient.builder()
                    .url("http://172.20.10.8:8088/userCart/deleteByGoodId.do")
                    .loader(getContext())
                    .params("goodId", goodId)
                    .build()
                    .post();

            int DataCount = data.size();
            int currentPosition = deleteEntities.get(i).getField(ShopCartItemFields.POSITION);
            if (currentPosition < data.size()) {
                mAdapter.remove(currentPosition);
                for (; currentPosition < DataCount - 1; currentPosition++) {
                    int rawItemPos = data.get(currentPosition).getField(ShopCartItemFields.POSITION);
                    data.get(currentPosition).setField(ShopCartItemFields.POSITION, rawItemPos - 1);
                }
            }
        }
        double price = Double.valueOf(mTvTotalPrice.getText().toString());
        mTvTotalPrice.setText(String.valueOf(price - deleteTotal));
        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        mTvTotalPrice.setText("0.00");
        RestClient.builder()
                .url("http://172.20.10.8:8088/userCart/deleteAll.do")
                .loader(getContext())
                .build()
                .post();

        checkItemCount();
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay() {

        createOrder();

    }

    //创建订单
    private void createOrder() {
//        final WeakHashMap<String,Object> orderParams = new WeakHashMap<>();
//        orderParams.put("userid",213);
//        orderParams.put("amount",0.01);
//        orderParams.put("comment","测试支付");
//        orderParams.put("type",1);
//        orderParams.put("ordertype",0);

        RestClient.builder()
                .url("http://172.20.10.8:8088/userOrder/createOrder.do")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付过程,生成订单
//                        Log.e(TAG, "onSuccess: ", );
                        final String orderId = JSON.parseObject(response).getString("message");
                        FastPay.create(ShopCartDelegate.this, mAdapter, mTvTotalPrice)
                                .setPayResultListener(ShopCartDelegate.this)
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();
    }

    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            @SuppressLint("RestrictedApi") final View studView = mStubNoItem.inflate();
            final AppCompatTextView tvToBuy = studView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentDelegate().getSupportDelegate().start(new EcBottomDelegate());
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mIconSelectAll.setTag(0);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("http://172.20.10.8:8088/userCart/list.do")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter()
                        .setJsonData(response)
                        .convert();
        mAdapter = new ShopCartAdapter(data);
        mAdapter.setCartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
//        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
//        mRecyclerView.addOnItemTouchListener(ShopCartItemClickListener.create(ecBottomDelegate));
        mTotalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        checkItemCount();
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }

    @Override
    public void onThumbClick(int goodId) {
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(goodId);
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
