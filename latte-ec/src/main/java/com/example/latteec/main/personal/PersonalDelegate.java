package com.example.latteec.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.latte.delegates.bottom.BottomItemDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.main.personal.address.AddressDelegate;
import com.example.latteec.main.personal.list.ListAdapter;
import com.example.latteec.main.personal.list.ListBean;
import com.example.latteec.main.personal.list.ListItemType;
import com.example.latteec.main.personal.order.OrderListDelegate;
import com.example.latteec.main.personal.profile.UserProfileDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/4/20.
 */

public class PersonalDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRvSettings = null;
    @BindView(R2.id.img_user_avatar)
    AppCompatImageView mImageView = null;
    @BindView(R2.id.text_user_avatar)
    AppCompatTextView mTextView = null;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();


    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;

    @Override
    public Object setLayout() {

        return R.layout.delegate_personal;
    }

    @OnClick(R2.id.tv_all_order)
    void onClickAllOrder() {
        mArgs.putString(ORDER_TYPE, "ALL");
        startOrderListByType();
    }

    @OnClick(R2.id.img_user_avatar)
    void onClickAvatar() {
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }

    private void startOrderListByType() {
        final OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(mArgs);
        getParentDelegate().getSupportDelegate().startWithPop(delegate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        RestClient.builder()
                .loader(getContext())
                .url("http://172.20.10.8:8088/UserInformationController/list.do")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject data = JSON.parseObject(response).getJSONObject("data");
                        String avatar = data.getString("avatar");
                        String name = data.getString("name");

                        Glide.with(getContext())
                                .load(avatar)
                                .apply(OPTIONS)
                                .into(mImageView);

                        mTextView.setText(name);

                    }
                })
                .build()
                .get();

        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setLatteDelegate(new AddressDelegate())
                .setText("收货地址")
                .build();

        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("系统设置")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);

        //设置RecycleView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalOnClickListener(this));

    }
}
