package com.example.latteec.main.personal.list;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.latteec.R;

import java.util.List;

/**
 * Created by liangbingtian on 2018/4/22.
 */

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {


    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.ITEM_NORMAL,R.layout.arrow_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
              switch (helper.getItemViewType()){
                  case 20:
                      helper.setText(R.id.tv_arrow_text, item.getText());
                      helper.setText(R.id.tv_arrow_value, item.getValue());
                      break;
                  default:
                      break;
              }

    }
}
