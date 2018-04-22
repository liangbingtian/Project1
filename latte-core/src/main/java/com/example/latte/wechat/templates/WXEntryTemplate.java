package com.example.latte.wechat.templates;

import com.example.latte.wechat.BaseWXEntryActivity;
import com.example.latte.wechat.LatteWeChat;

/**
 * Created by liangbingtian on 2018/3/30.
 */

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {

        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);

    }
}
