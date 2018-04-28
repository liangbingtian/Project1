package com.example.latteec.main.SearchResult;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.latte.ui.recycler.ItemType;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latte.ui.recycler.MultipleRecyclerAdapter;
import com.example.latte.ui.recycler.MultipleViewHolder;
import com.example.latteec.R;

import java.util.List;

/**
 * Created by liangbingtian on 2018/4/28.
 */

public class SearchResultAdapter extends MultipleRecyclerAdapter {

    public static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();


    protected SearchResultAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.SEARCH_RESULT, R.layout.item_shop_search_result);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ItemType.SEARCH_RESULT:
                final int id = entity.getField(MultipleFields.ID);
                final String thumb = entity.getField(MultipleFields.IMAGE_URL);
                final String title = entity.getField(SearchResultItemFields.TITLE);
                final String desc = entity.getField(SearchResultItemFields.DESC);
                final double price = entity.getField(SearchResultItemFields.PRICE);

                AppCompatImageView resultView = holder.getView(R.id.image_item_search_result);
                AppCompatTextView resultTitle = holder.getView(R.id.tv_item_search_result_title);
                AppCompatTextView resultDesc = holder.getView(R.id.tv_item_search_result_desc);
                AppCompatTextView resultPrice = holder.getView(R.id.tv_item_search_result_price);

                resultTitle.setText(title);
                resultDesc.setText(desc);
                resultPrice.setText(String.valueOf(price));
                Glide.with(mContext)
                        .applyDefaultRequestOptions(OPTIONS)
                        .load(thumb)
                        .into(resultView);

                break;
            default:
                break;
        }
    }
}
