package com.dongdong.ddapp.recycle

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dongdong.ddapp.R


class SomeDataListAdapter : BaseQuickAdapter<SomeData, BaseViewHolder>(R.layout.item_recycle_some_data_list) {

    override fun convert(holder: BaseViewHolder, item: SomeData) {
        holder.setText(R.id.tv_someData, item.title)
    }

}