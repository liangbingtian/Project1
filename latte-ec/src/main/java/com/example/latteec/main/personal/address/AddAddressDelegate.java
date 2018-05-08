package com.example.latteec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latteec.R;
import com.example.latteec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/5/7.
 */

public class AddAddressDelegate extends LatteDelegate{

    @BindView(R2.id.add_address_name)
    TextInputEditText addName = null;
    @BindView(R2.id.add_address_phone)
    TextInputEditText addPhone = null;
    @BindView(R2.id.add_address_message)
    TextInputEditText addMessage = null;

    private boolean checkForm() {
        final String name = addName.getText().toString();
        final String phone = addPhone.getText().toString();
        final String message = addMessage.getText().toString();
        boolean isPass = true;

        if (name.isEmpty()) {
            addName.setError("请输入姓名");
            isPass = false;
        } else {
            addName.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            addPhone.setError("手机号码错误");
            isPass = false;
        } else {
            addPhone.setError(null);
        }

        if (message.isEmpty()) {
            addMessage.setError("请输入姓名");
            isPass = false;
        } else {
            addMessage.setError(null);
        }

        return isPass;
    }

    @OnClick(R2.id.add_address_btn)
    void clickAddress(){
        if (checkForm()){
            RestClient.builder()
                    .url("http://172.20.10.8:8088/userAddress/add.do")
                    .params("name",addName.getText().toString())
                    .params("phone",addPhone.getText().toString())
                    .params("message",addMessage.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Log.e("add_address", response);
                            getSupportDelegate().startWithPop(new AddressDelegate());
                        }
                    })
                    .build()
                    .post();
        }

    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_add_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
