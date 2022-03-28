package com.devm7mdibrahim.presentation.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<MODEL, VIEW_BINDING : ViewBinding> constructor(viewBinding: VIEW_BINDING) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private var item: MODEL? = null

    fun doBindings(data: MODEL?) {
        this.item = data
    }

    abstract fun bind()

    fun getRowItem(): MODEL? {
        return item
    }
}