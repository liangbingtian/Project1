package com.example.latteec.main.personal.uploadProduct;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.latte.delegates.LatteDelegate;
import com.example.latte.ui.widget.AutoPhotoLayout;
import com.example.latte.util.callback.CallbackManager;
import com.example.latte.util.callback.CallbackType;
import com.example.latte.util.callback.IGlobalCallback;
import com.example.latteec.R;
import com.example.latteec.R2;

import java.net.URI;

import butterknife.BindView;

/**
 * Created by liangbingtian on 2018/5/4.
 */

public class UploadProductDelegate extends LatteDelegate{

    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_upload;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
          mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) {
                         mAutoPhotoLayout.onCropTarget(args);
                    }
                });
    }
}
