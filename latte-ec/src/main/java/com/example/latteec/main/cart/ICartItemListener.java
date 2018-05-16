package com.example.latteec.main.cart;

/**
 * Created by liangbingtian on 2018/4/20.
 */

public interface ICartItemListener {

    void onItemClick(double itemTotalPrice);

    void onThumbClick(int goodId);
}
