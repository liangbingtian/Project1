package com.example.latteec.pay;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.example.latte.ui.LatteLoader;

/**
 * Created by liangbingtian on 2018/5/1.
 */

public class PayAsyncTask extends AsyncTask<String, Void, String> {

    private final Activity ACTIVITY;
    private final IAlPayResultListener LISTENER;

    //订单支付成功
    public static final String AL_PAY_STATUS_SUCCESS = "9000";
    //订单支付
    public static final String AL_PAY_STATUS_PAYING = "8000";
    //订单支付失败
    public static final String AL_PAY_STATUS_FAIL = "4000";
    //用户中途取消
    public static final String AL_PAY_STATUS_CANCEL = "6001";
    //网络错误
    public static final String AL_PAY_STATUS_CONNECT_ERROR = "6002";


    public PayAsyncTask(Activity activity, IAlPayResultListener listener) {
        this.ACTIVITY = activity;
        this.LISTENER = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        final String alPaySign = params[0];
        final PayTask payTask = new PayTask(ACTIVITY);
        return payTask.pay(alPaySign, true);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        LatteLoader.stopLoading();
        final PayResult payResult = new PayResult(result);
        //支付宝返回此次支付结果及加签，建议对支付宝签名信息拿的是签约的时候支付宝提供的公钥验签
        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();
        //我们可以打印log进行验证
//        Log.e(TAG, "onPostExecute: ", );

        switch (resultStatus) {
            case AL_PAY_STATUS_SUCCESS:
                if (LISTENER != null) {
                    LISTENER.onPaySuccess();
                }
                break;
            case AL_PAY_STATUS_PAYING:
                if (LISTENER != null) {
                    LISTENER.onPaying();
                }
                break;
            case AL_PAY_STATUS_FAIL:
                if (LISTENER != null) {
                    LISTENER.onPayFail();
                }
                break;
            case AL_PAY_STATUS_CANCEL:
                if (LISTENER != null) {
                    LISTENER.onPayCancel();
                }
                break;
            case AL_PAY_STATUS_CONNECT_ERROR:
                if (LISTENER != null) {
                    LISTENER.onPayConnectError();
                }
                break;
            default:
                break;
        }
    }
}
