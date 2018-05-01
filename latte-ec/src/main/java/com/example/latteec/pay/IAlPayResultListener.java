package com.example.latteec.pay;

/**
 * Created by liangbingtian on 2018/5/1.
 */

public interface IAlPayResultListener {


    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
