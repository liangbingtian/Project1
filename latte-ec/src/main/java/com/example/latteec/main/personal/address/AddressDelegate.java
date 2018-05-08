package com.example.latteec.main.personal.address;

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

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/4/24.
 */

public class AddressDelegate extends LatteDelegate implements ISuccess{

    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView = null;
    @OnClick(R2.id.icon_address_add)
    void clickAddressAdd(){
         getSupportDelegate().startWithPop(new AddAddressDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RestClient.builder()
                .url("http://172.20.10.8:8088/userAddress/select.do")
                .loader(getContext())
                .success(this)
                .build()
                .post();
    }

    @Override
    public void onSuccess(String response) {
        Log.e("address", response);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data =
                new AddressDataConverter().setJsonData(response).convert();
        final AddressAdapter addressAdapter = new AddressAdapter(data);
        mRecyclerView.setAdapter(addressAdapter);
    }
}
