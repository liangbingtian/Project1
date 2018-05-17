package com.example.latteec.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.main.cart.ShopCartAdapter;
import com.example.latteec.main.personal.address.AddressDelegate;

import butterknife.BindView;

import static com.blankj.utilcode.util.Utils.getContext;

/**
 * Created by liangbingtian on 2018/5/1.
 */

public class FastPay implements View.OnClickListener {

    AppCompatTextView mTvTotalPrice = null;

    private ShopCartAdapter adapter = null;
    //设置支付回调监听
    private IAlPayResultListener mIAlPayResultListener = null;
    private Activity mActivity = null;

    private AlertDialog mDialog = null;
    private String mOrderId = null;
    private LatteDelegate delegate = null;

    private FastPay(LatteDelegate delegate,ShopCartAdapter shopCartAdapter,AppCompatTextView mTvTotalPrice) {
        this.mActivity = delegate.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
        this.adapter = shopCartAdapter;
        this.mTvTotalPrice = mTvTotalPrice;
        this.delegate = delegate;
    }

    public static FastPay create(LatteDelegate delegate, ShopCartAdapter adapter,AppCompatTextView mTvTotalPrice) {

        return new FastPay(delegate,adapter,mTvTotalPrice);
    }

    public void beginPayDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_dialog_pay_alpay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
        }
    }

    public FastPay setPayResultListener(IAlPayResultListener listener){
         this.mIAlPayResultListener = listener;
         return this;
    }

    public FastPay setOrderId(String orderId){
        this.mOrderId = orderId;
        return this;
    }

    public final void alPay(String orderId) {
        //访问服务器获得的签名串
        final String stringUrl = ""+orderId;
        //获取签名字符串
        RestClient.builder()
                .url(stringUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String paySign = JSON.parseObject(response).getString("result");
                        //必须是异步调用客户端支付宝接口
                        final PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity, mIAlPayResultListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign);
                    }
                })
                .build()
                .post();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_dialog_pay_alpay) {
            adapter.getData().clear();
            mTvTotalPrice.setText("0.0");
            adapter.notifyDataSetChanged();
            RestClient.builder()
                    .url("http://172.20.10.8:8088/userCart/deleteAll.do")
                    .loader(mActivity)
                    .build()
                    .post();
            mDialog.cancel();
            SelectAddressDelegate selectAddressDelegate = SelectAddressDelegate.create(mOrderId);
            delegate.getParentDelegate().getSupportDelegate().start(selectAddressDelegate);

        } else if (id == R.id.btn_dialog_pay_wechat) {
            mDialog.cancel();
        } else if (id == R.id.btn_dialog_pay_cancel) {
            mDialog.cancel();
        }

    }
}
