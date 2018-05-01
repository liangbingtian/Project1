package com.example.latteec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.main.personal.PersonalDelegate;

import java.util.List;

import butterknife.BindView;

/**
 * Created by liangbingtian on 2018/4/22.
 */

public class OrderListDelegate extends LatteDelegate implements ISuccess{

    private String mType = null;

    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mType = args.getString(PersonalDelegate.ORDER_TYPE);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .loader(getContext())
                .url("http://172.20.10.8:8088/userOrder/listOrder.do")
                .params("type",mType)
                .success(this)
                .build()
                .post();
    }

    @Override
    public void onSuccess(String response) {

        Log.e("USER", response);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data =
                new OrderListDataConverter().setJsonData(response).convert();
        final OrderListAdapter adapter = new OrderListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(OrderItemClickListener.create(this));

    }
}
