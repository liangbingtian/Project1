package com.example.latteec.main.personal.uploadProduct;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.widget.AutoPhotoLayout;
import com.example.latte.ui.widget.IUploadCallBack;
import com.example.latte.util.callback.CallbackManager;
import com.example.latte.util.callback.CallbackType;
import com.example.latte.util.callback.IGlobalCallback;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.main.EcBottomDelegate;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/5/4.
 */

public class UploadProductDelegate extends LatteDelegate implements IUploadCallBack{

    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;
    @BindView(R2.id.user_upload_product_name)
    TextInputEditText mProductName = null;
    @BindView(R2.id.user_upload_name)
    TextInputEditText mUserName = null;
    @BindView(R2.id.user_upload_phone)
    TextInputEditText mUserPhone = null;
    @BindView(R2.id.user_upload_product_desc)
    AppCompatEditText mProductDesc = null;
    @BindView(R2.id.user_upload_product_price)
    AppCompatEditText mProductPrice = null;

    private static ArrayList<String> bannerImages = new ArrayList<>();

    @OnClick(R2.id.user_upload_btn)
    void clickUploadProduct() {
        if (checkForm()) {
            String bannerImagesStr = StringUtils.join(bannerImages, ",");
            RestClient.builder()
                    .url("http://172.20.10.8:8088/userUploadProduct/insert.do")
                    .loader(getContext())
                    .params("title", mProductName.getText().toString())
                    .params("desc", mProductDesc.getText().toString())
                    .params("name", mUserName.getText().toString())
                    .params("phone", mUserPhone.getText().toString())
                    .params("banners", bannerImagesStr)
                    .params("price",Double.valueOf(mProductPrice.getText().toString()))
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Toast.makeText(getContext(), "发布成功", Toast.LENGTH_SHORT).show();
                            Log.e("UPLOAD_RESULT", response);
                            EcBottomDelegate delegate = EcBottomDelegate.create(3);
                            getSupportDelegate().start(delegate);
                        }
                    })
                    .build()
                    .post();

        }
    }

    private boolean checkForm() {
        final String userName = mUserName.getText().toString();
        final String userPhone = mUserPhone.getText().toString();
        final String productName = mProductName.getText().toString();
        final String productDesc = mProductDesc.getText().toString();
        final String productPrice = mProductPrice.getText().toString();

        boolean isPass = true;

        if (userName.isEmpty()) {
            mUserName.setError("请输入发布者的姓名哦");
            isPass = false;
        } else {
            mUserName.setError(null);
        }

        if (userPhone.isEmpty() || userPhone.length() != 11) {
            mUserPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mUserPhone.setError(null);
        }

        if (productName.isEmpty()) {
            mProductName.setError("请输入要发布的商品的名称");
            isPass = false;
        } else {
            mProductName.setError(null);
        }

        if (productDesc.isEmpty()) {
            mProductDesc.setError("请输入要发布商品的详情");
            isPass = false;
        } else {
            mProductDesc.setError(null);
        }

        if (productPrice.isEmpty()) {
            mProductPrice.setError("价格不能为空");
            isPass = false;
        } else {
            mProductPrice.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_upload;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mAutoPhotoLayout.setDelegate(this);
        mAutoPhotoLayout.setUploadCallBack(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) {
                        Log.e("ON_CROP", String.valueOf(args));
                        RestClient.builder()
                                .url("http://172.20.10.8:8088/UserInformationController/upload.do")
                                .loader(getContext())
                                .file(args.getPath())
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        Log.e("ON_CROP_UPLOAD", response);
                                        final String url = JSON.parseObject(response).getJSONObject("data")
                                                .getString("url");
                                        bannerImages.add(url);
                                        Log.e("bannerImages", bannerImages.toString());
                                    }
                                })
                                .build()
                                .upload();
                        mAutoPhotoLayout.onCropTarget(args);
                    }
                });
    }

    @Override
    public void removePhoto(int deleteId) {
        bannerImages.remove(deleteId);
        Log.e("bannerImage", bannerImages.toString());
    }
}
