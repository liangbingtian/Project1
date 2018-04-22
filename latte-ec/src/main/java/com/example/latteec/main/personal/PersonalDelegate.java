package com.example.latteec.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.latte.delegates.bottom.BottomItemDelegate;
import com.example.latteec.R;

/**
 * Created by liangbingtian on 2018/4/20.
 */

public class PersonalDelegate extends BottomItemDelegate{

    @Override
    public Object setLayout() {

        return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
