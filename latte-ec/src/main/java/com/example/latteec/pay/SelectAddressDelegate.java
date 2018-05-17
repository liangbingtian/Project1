package com.example.latteec.pay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.main.EcBottomDelegate;
import com.example.latteec.main.personal.address.AddressAdapter;
import com.example.latteec.main.personal.address.AddressDataConverter;
import com.example.latteec.orderDetail.OrderDetailDelegate;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/5/9.
 */

public class SelectAddressDelegate extends LatteDelegate{

    private  String orderId = null;
    private static String ARG_ORDER_ID = "ARG_ORDER_ID";

    public static SelectAddressDelegate create(@NotNull String orderId) {
        final Bundle args = new Bundle();
        args.putString(ARG_ORDER_ID, orderId);
        final SelectAddressDelegate delegate = new SelectAddressDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            orderId = args.getString(ARG_ORDER_ID);
        }
    }

    @BindView(R2.id.select_address)
    RecyclerView mRecycler = null;
    @OnClick(R2.id.btn_select_end)
    void onClickEnd(){
        RestClient.builder()
                .url("http://172.20.10.8:8088/userOrder/setAddress.do")
                .params("orderId",orderId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e("address", response);
                        EcBottomDelegate ecBottomDelegate = EcBottomDelegate.create(2);
                        getSupportDelegate().start(ecBottomDelegate);
                    }
                })
                .build()
                .post();


    }

    @Override
    public Object setLayout() {
        return R.layout.delete_select_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        RestClient.builder()
                .url("http://172.20.10.8:8088/userAddress/select.do")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecycler.setLayoutManager(manager);
                        final List<MultipleItemEntity> data =
                                new AddressDataConverter().setJsonData(response).convert();
                        final AddressAdapter addressAdapter = new AddressAdapter(data);
                        mRecycler.setAdapter(addressAdapter);
                    }
                })
                .build()
                .post();
    }

}
