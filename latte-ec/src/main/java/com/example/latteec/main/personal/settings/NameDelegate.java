package com.example.latteec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;

import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
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
        final String username = mEditText.getText().toString();
        RestClient.builder()
                .url("http://172.20.10.8:8088/UserInformationController/name.do")
                .loader(getContext())
                .params("name",username)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e("ON_CROP_NAME", response);
                    }
                })
                .build()
                .post();

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
