package com.example.latteec.main.SearchResult;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.latte.delegates.LatteDelegate;
import com.example.latte.delegates.bottom.BottomItemDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latteec.R;
import com.example.latteec.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liangbingtian on 2018/4/28.
 */

public class SearchResultDelegate extends LatteDelegate implements ISuccess{

    @BindView(R2.id.rv_search_result)
    RecyclerView mRecyclerView = null;

    private static SearchResultAdapter mAdapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_search_result;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("search_result.json")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
       final ArrayList<MultipleItemEntity> data =
               new SearchResultDataConverter()
                       .setJsonData(response).convert();

       final LinearLayoutManager manager = new LinearLayoutManager(getContext());
       mRecyclerView.setLayoutManager(manager);
       mAdapter = new SearchResultAdapter(data);
       mRecyclerView.setAdapter(mAdapter);
    }
}
