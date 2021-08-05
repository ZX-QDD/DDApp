package com.dongdong.ddapp.recycle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class SomeDataListV2Adapter extends BaseQuickAdapter<SomeData, BaseViewHolder> {

    public SomeDataListV2Adapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SomeData someData) {

    }
}
