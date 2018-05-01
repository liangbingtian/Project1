package com.example.latteec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.latte.delegates.LatteDelegate;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.detail.GoodsDetailDelegate;
import com.example.latteec.main.personal.list.ListAdapter;
import com.example.latteec.main.personal.list.ListBean;
import com.example.latteec.main.personal.list.ListItemType;
import com.example.latteec.main.personal.settings.NameDelegate;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liangbingtian on 2018/4/23.
 */

public class UserProfileDelegate extends LatteDelegate{

    private static String ARG_USER_NAME = "ARG_USER_NAME";

    private String userName = "未设置姓名";

    @BindView(R2.id.rv_user_profile)
    RecyclerView recyclerView = null;

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
            userName = args.getString(ARG_USER_NAME);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        final ListBean image = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl("http://orlij0hml.bkt.clouddn.com/1.jpeg")
                .build();

        final ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("姓名")
                .setValue(userName)
                .setLatteDelegate(new NameDelegate())
                .build();

        final ListBean gender = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("性别")
                .setValue("男")
                .build();

        final ListBean birth = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("生日")
                .setValue("未设置")
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
