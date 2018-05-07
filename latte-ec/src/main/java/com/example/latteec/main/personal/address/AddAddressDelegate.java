package com.example.latteec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.latte.delegates.LatteDelegate;
import com.example.latteec.R;
import com.example.latteec.R2;

import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/5/7.
 */

public class AddAddressDelegate extends LatteDelegate{
    @OnClick(R2.id.add_address_btn)
    void clickAddress(){
        getSupportDelegate().startWithPop(new AddressDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_add_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
