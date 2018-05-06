package com.example.latteec.main.personal.uploadProduct.myUploadProduct;

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

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liangbingtian on 2018/5/5.
 */

public class MyUploadProductDelegate extends LatteDelegate implements ISuccess{

    @BindView(R2.id.upload_product_recycler)
    RecyclerView mRecycler = null;

    private static MyUploadProductAdapter mAdapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_search_upload_product;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("http://172.20.10.8:8088/userUploadProduct/select.do")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data =
                new MyUploadProductDataConverter().setJsonData(response).convert();
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(manager);
        mAdapter = new MyUploadProductAdapter(data);
        mRecycler.setAdapter(mAdapter);
    }
}
