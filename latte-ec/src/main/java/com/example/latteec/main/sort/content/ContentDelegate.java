package com.example.latteec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.main.EcBottomDelegate;
import com.example.latteec.main.sort.SortDelegate;
import com.example.latteec.orderDetail.OrderDetailAdapter;
import com.example.latteec.orderDetail.OrderDetailDataConverter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liangbingtian on 2018/4/16.
 */

public class ContentDelegate extends LatteDelegate {

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;
    private static SectionAdapter mAdapter = null;


    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView;

    public static ContentDelegate newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate contentDelegate = new ContentDelegate();
        contentDelegate.setArguments(args);
        return contentDelegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    private void initData() {
        RestClient.builder()
                .url("sort_content_data_"+mContentId+".json")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final ArrayList<MultipleItemEntity> data =
                                new SectionDataConverter()
                                        .setJsonData(response)
                                        .convert();

                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        mAdapter = new SectionAdapter(data);
                        mRecyclerView.setAdapter(mAdapter);
                        final SortDelegate sortDelegate = getParentDelegate();
                        mRecyclerView.addOnItemTouchListener(SectionIndexClickListener.create(sortDelegate.getParentDelegate()));
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        initData();
    }
}
