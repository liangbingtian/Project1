package com.example.latteec.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.date.DateDialogUtil;
import com.example.latte.util.callback.CallbackManager;
import com.example.latte.util.callback.CallbackType;
import com.example.latte.util.callback.IGlobalCallback;
import com.example.latteec.R;
import com.example.latteec.main.personal.list.ListBean;


/**
 * Created by liangbingtian on 2018/4/23.
 */

public class UserProfileClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    private String[] mGenders = new String[]{"男", "女", "保密"};

    private String mUrl = null;

    public UserProfileClickListener(LatteDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
                //开始照相机，或选择图片
                CallbackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(final Uri args) {
                                Log.e("ON_CROP", String.valueOf(args));
                                final ImageView avatar = view.findViewById(R.id.img_arrow_avatar);
                                Glide.with(DELEGATE)
                                        .load(args)
                                        .into(avatar);

                                RestClient.builder()
                                        .url("http://172.20.10.8:8088/UserInformationController/upload.do")
                                        .loader(DELEGATE.getContext())
                                        .file(args.getPath())
                                        .success(new ISuccess() {
                                            @Override
                                            public void onSuccess(String response) {
                                                Log.e("ON_CROP_UPLOAD", response);
                                                final String url = JSON.parseObject(response).getJSONObject("data")
                                                        .getString("url");
                                                mUrl = url;

                                                RestClient.builder()
                                                        .url("http://172.20.10.8:8088/UserInformationController/avatar.do")
                                                        .loader(DELEGATE.getContext())
                                                        .params("url", url)
                                                        .success(new ISuccess() {
                                                            @Override
                                                            public void onSuccess(String response) {
                                                                Log.e("ON_CROP_AVATAR", response);
                                                            }
                                                        })
                                                        .build()
                                                        .post();
                                            }
                                        })
                                        .build()
                                        .upload();
                            }
                        });
                DELEGATE.startCameraWithCheck();
                break;
            case 2:
                final LatteDelegate nameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().startWithPop(nameDelegate);
                break;
            case 3:
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        RestClient.builder()
                                .url("http://172.20.10.8:8088/UserInformationController/gender.do")
                                .loader(DELEGATE.getContext())
                                .params("gender", mGenders[which])
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        Log.e("ON_CROP_GENDER", response);
                                    }
                                })
                                .build()
                                .post();

                        dialog.cancel();
                    }
                });
                break;
            case 4:
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String data) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(data);
                        RestClient.builder()
                                .url("http://172.20.10.8:8088/UserInformationController/birth.do")
                                .loader(DELEGATE.getContext())
                                .params("birth", data)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        Log.e("ON_CROP_BIRTH", response);
                                    }
                                })
                                .build()
                                .post();
                    }
                });
                dateDialogUtil.showDialog(DELEGATE.getContext());
                break;
            default:
                break;
        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders, 0, listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
