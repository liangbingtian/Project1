package com.example.latteec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.latte.ui.recycler.MultipleItemEntity;
import com.example.latte.ui.recycler.MultipleRecyclerAdapter;
import com.example.latte.ui.recycler.MultipleViewHolder;
import com.example.latteec.R;

import java.util.List;

/**
 * Created by liangbingtian on 2018/4/16.
 */

public class SectionAdapter extends MultipleRecyclerAdapter{

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    protected SectionAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ContentItemType.CONTENT_ITEM,R.layout.item_section_content);
    }


    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ContentItemType.CONTENT_ITEM:
                final int goodsId = entity.getField(ContentITemFields.GOODS_ID);
                final String goodsName = entity.getField(ContentITemFields.GOODS_NAME);
                final String goodsThumb = entity.getField(ContentITemFields.GOODS_THUMB);

                final AppCompatImageView contentView = holder.getView(R.id.iv);
                final AppCompatTextView contentText = holder.getView(R.id.tv);
                Glide.with(mContext)
                        .load(goodsThumb)
                        .apply(OPTIONS)
                        .into(contentView);
                contentText.setText(goodsName);
                break;
            default:
                break;
        }
    }
}
