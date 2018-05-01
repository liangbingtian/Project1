package com.example.latteec.orderDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.detail.GoodsDetailDelegate;
import com.example.latteec.main.cart.ShopCartDataConverter;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liangbingtian on 2018/5/1.
 */

public class OrderDetailDelegate extends LatteDelegate implements ISuccess{

    @BindView(R2.id.rv_order_detail)
    RecyclerView mRecyclerView = null;

    public static OrderDetailAdapter mAdapter = null;

    private static String ARG_ORDER_ID = "ARG_ORDER_ID";

    private String orderId = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    public static OrderDetailDelegate create(@NotNull String orderId) {
        final Bundle args = new Bundle();
        args.putString(ARG_ORDER_ID, orderId);
        final OrderDetailDelegate delegate = new OrderDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            orderId = args.getString(ARG_ORDER_ID);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("http://172.20.10.8:8088/userOrder/selectOrderById.do")
                .params("orderId",orderId)
                .loader(getContext())
                .success(this)
                .build()
                .post();

    }

    @Override
    public void onSuccess(String response) {

        Log.e("USER1",response);

        final ArrayList<MultipleItemEntity> data =
                new OrderDetailDataConverter()
                        .setJsonData(response)
                        .convert();

        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new OrderDetailAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }
}
