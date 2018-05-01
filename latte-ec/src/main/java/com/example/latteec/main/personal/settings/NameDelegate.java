package com.example.latteec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.example.latte.delegates.LatteDelegate;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.main.personal.profile.UserProfileDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/4/23.
 */

public class NameDelegate extends LatteDelegate{

    @BindView(R2.id.edit_user_name)
    AppCompatEditText mEditText = null;

    @OnClick(R2.id.btn_name_submit)
    void clickSubmit(){
        String username = mEditText.getText().toString();
        final UserProfileDelegate userProfileDelegate = UserProfileDelegate.create(username);
        getSupportDelegate().startWithPop(userProfileDelegate);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
