package com.example.latteec.main.personal.uploadProduct.myUploadProduct;

import android.support.v7.widget.AppCompatTextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.latte.ui.banner.BannerCreator;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latte.ui.recycler.MultipleRecyclerAdapter;
import com.example.latte.ui.recycler.MultipleViewHolder;
import com.example.latteec.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangbingtian on 2018/5/5.
 */

public class MyUploadProductAdapter extends MultipleRecyclerAdapter {

    private boolean mIsInitBanner = false;

    protected MyUploadProductAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(MyUploadProductItemType.MY_UPLOAD_PRODUCT, R.layout.item_upload_product);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case MyUploadProductItemType.MY_UPLOAD_PRODUCT:
                final String title = entity.getField(MyUploadProductItemFields.TITLE);
                final String desc = entity.getField(MyUploadProductItemFields.DESC);
                final ArrayList<String> bannerImages = entity.getField(MyUploadProductItemFields.BANNERS);

                final AppCompatTextView resultTitle = holder.getView(R.id.item_upload_product_title);
                final AppCompatTextView resultDesc = holder.getView(R.id.item_upload_product_desc);
                final ConvenientBanner<String> resultConvenientBanner = holder.getView(R.id.item_upload_product_banners);

                resultTitle.setText(title);
                resultDesc.setText(desc);
                BannerCreator.setDefault(resultConvenientBanner, bannerImages, this);
                mIsInitBanner = true;

                break;
            default:
                break;
        }
    }
}
