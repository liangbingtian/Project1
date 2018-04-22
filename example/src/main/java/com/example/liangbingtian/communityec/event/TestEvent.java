package com.example.liangbingtian.communityec.event;

import android.widget.Toast;

import com.example.latte.delegates.web.event.Event;

/**
 * Created by liangbingtian on 2018/4/18.
 */

public class TestEvent extends Event{
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(),params,Toast.LENGTH_LONG).show();
        return null;
    }
}
