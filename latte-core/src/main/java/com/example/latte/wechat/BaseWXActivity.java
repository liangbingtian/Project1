package com.example.latte.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by liangbingtian on 2018/4/2.
 */

public abstract class BaseWXActivity extends AppCompatActivity implements IWXAPIEventHandler{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //必须写在onCreate方法中.
        LatteWeChat.getInstance().getWXAPI().handleIntent(getIntent(),this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LatteWeChat.getInstance().getWXAPI().handleIntent(getIntent(),this);

    }
}
