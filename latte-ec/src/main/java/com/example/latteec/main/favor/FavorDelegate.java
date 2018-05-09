package com.example.latteec.main.favor;

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

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liangbingtian on 2018/5/9.
 */

public class FavorDelegate extends LatteDelegate implements ISuccess{

    @BindView(R2.id.favor_list)
    RecyclerView mRecycler = null;
    private static FavorAdapter mAdapter = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_favor;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RestClient.builder()
                .url("http://172.20.10.8:8088/userFavor/selectAll.do")
                .loader(getContext())
                .success(this)
                .build()
                .post();
    }


    @Override
    public void onSuccess(String response) {
        Log.e("goodFavor", response );
        final ArrayList<MultipleItemEntity> data =
                new FavorDataConverter().setJsonData(response).convert();
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(manager);
        mAdapter = new FavorAdapter(data);
        mRecycler.setAdapter(mAdapter);
        mRecycler.addOnItemTouchListener(FavorItemClickListener.create(this));
    }
}
