package com.example.latteec.main.SearchResult;

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

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by liangbingtian on 2018/4/28.
 */

public class SearchResultDelegate extends LatteDelegate implements ISuccess{


    @BindView(R2.id.rv_search_result)
    RecyclerView mRecyclerView = null;

    private static SearchResultAdapter mAdapter = null;

    private static final String ARG_GOODS_NAME = "ARG_GOODS_NAME";

    private String mGoodsName = null;

    public static SearchResultDelegate create(@NotNull String name) {
        final Bundle args = new Bundle();
        args.putString(ARG_GOODS_NAME, name);
        final SearchResultDelegate delegate = new SearchResultDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mGoodsName = args.getString(ARG_GOODS_NAME);
            Toast.makeText(getContext(), "名字" + mGoodsName, Toast.LENGTH_SHORT).show();
        }
    }


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
                .url("http://172.20.10.8:8088/userProduct/search.do")
                .params("name",mGoodsName)
                .loader(getContext())
                .success(this)
                .build()
                .post();
    }

    @Override
    public void onSuccess(String response) {
        Log.e("user", response);
       final ArrayList<MultipleItemEntity> data =
               new SearchResultDataConverter()
                       .setJsonData(response).convert();

       final LinearLayoutManager manager = new LinearLayoutManager(getContext());
       mRecyclerView.setLayoutManager(manager);
       mAdapter = new SearchResultAdapter(data);
       mRecyclerView.setAdapter(mAdapter);
//       final EcBottomDelegate ecBottomDelegate = getParentDelegate();
       mRecyclerView.addOnItemTouchListener(SearchResultItemClickListener.create(this));
    }
}
