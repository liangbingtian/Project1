package com.example.latteec.main.personal.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.detail.GoodsDetailDelegate;
import com.example.latteec.main.EcBottomDelegate;
import com.example.latteec.main.personal.PersonalDelegate;
import com.example.latteec.main.personal.list.ListAdapter;
import com.example.latteec.main.personal.list.ListBean;
import com.example.latteec.main.personal.list.ListItemType;
import com.example.latteec.main.personal.settings.NameDelegate;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/4/23.
 */

public class UserProfileDelegate extends LatteDelegate implements ISuccess{

    private static String ARG_USER_NAME = "ARG_USER_NAME";

//    private String userName = "未设置姓名";
//
//    private String mAvatar = "http://172.20.10.8/ftp/d427ff3c-013a-4b6f-8df5-0f082c119bb1.jpg";
//
//    private String mGender = "男";
//
//    private String mBirth = "未设置";

    @BindView(R2.id.rv_user_profile)
    RecyclerView recyclerView = null;

    @OnClick(R2.id.text_view_back)
    void clickBack(){
        getSupportDelegate().start(new EcBottomDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    public static UserProfileDelegate create(@NotNull String userName) {
        final Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, userName);
        final UserProfileDelegate delegate = new UserProfileDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
//            userName = args.getString(ARG_USER_NAME);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {


        RestClient.builder()
                .url("http://172.20.10.8:8088/UserInformationController/list.do")
                .loader(getContext())
                .success(this)
                .build()
                .get();

//        final ListBean image = new ListBean.Builder()
//                .setItemType(ListItemType.ITEM_AVATAR)
//                .setId(1)
//                .setImageUrl(mAvatar)
//                .build();
//
//        final ListBean name = new ListBean.Builder()
//                .setItemType(ListItemType.ITEM_NORMAL)
//                .setId(2)
//                .setText("姓名")
//                .setValue(userName)
//                .setLatteDelegate(new NameDelegate())
//                .build();
//
//        final ListBean gender = new ListBean.Builder()
//                .setItemType(ListItemType.ITEM_NORMAL)
//                .setId(3)
//                .setText("性别")
//                .setValue(mGender)
//                .build();
//
//        final ListBean birth = new ListBean.Builder()
//                .setItemType(ListItemType.ITEM_NORMAL)
//                .setId(4)
//                .setText("生日")
//                .setValue(mBirth)
//                .build();
//
//        final List<ListBean> data = new ArrayList<>();
//        data.add(image);
//        data.add(name);
//        data.add(gender);
//        data.add(birth);
//
//        //设置RecycleView
//        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(manager);
//        final ListAdapter adapter = new ListAdapter(data);
//        recyclerView.setAdapter(adapter);
//        recyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }

    @Override
    public void onSuccess(String response) {
        Log.e("ON_CROP_LIST", response);
        final JSONObject data1 = JSON.parseObject(response).getJSONObject("data");
        String mAvatar = data1.getString("avatar");
        Log.e("avatar", mAvatar);
        String mName = data1.getString("name");
        String mGender = data1.getString("gender");
        String mBirth = data1.getString("birth");
        Log.e("AVATAR", mAvatar);

        final ListBean image = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl(mAvatar)
                .build();

        final ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("姓名")
                .setValue(mName)
                .setLatteDelegate(new NameDelegate())
                .build();

        final ListBean gender = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("性别")
                .setValue(mGender)
                .build();

        final ListBean birth = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("生日")
                .setValue(mBirth)
                .build();


        final List<ListBean> data = new ArrayList<>();
        data.add(image);
        data.add(name);
        data.add(gender);
        data.add(birth);

        //设置RecycleView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }

}
