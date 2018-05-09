package com.example.latteec.pay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.main.EcBottomDelegate;
import com.example.latteec.main.personal.address.AddressAdapter;
import com.example.latteec.main.personal.address.AddressDataConverter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/5/9.
 */

public class SelectAddressDelegate extends LatteDelegate implements ISuccess{

    @BindView(R2.id.select_address)
    RecyclerView mRecycler = null;
    @OnClick(R2.id.btn_select_end)
    void onClickEnd(){
        EcBottomDelegate ecBottomDelegate = EcBottomDelegate.create(2);
        getSupportDelegate().start(ecBottomDelegate);
    }

    @Override
    public Object setLayout() {
        return R.layout.delete_select_address;
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
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(manager);
        final List<MultipleItemEntity> data =
                new AddressDataConverter().setJsonData(response).convert();
        final AddressAdapter addressAdapter = new AddressAdapter(data);
        mRecycler.setAdapter(addressAdapter);
    }
}
