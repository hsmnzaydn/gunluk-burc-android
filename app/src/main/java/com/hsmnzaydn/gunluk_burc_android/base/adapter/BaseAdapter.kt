package com.hsmnzaydn.gunluk_burc_android.base.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import iyzico.merchant.payment.base.model.BaseEntity
import kotlin.properties.Delegates

abstract class BaseAdapter<Data : BaseEntity> : ListAdapter<Data, BaseViewHolder<Data>>(
    DiffCallback<Data>()
) {

    private var onItemClick: ((Data, position: Int, layoutId: Int) -> Unit) = { _, _, _ -> }

    private var onViewClick: ((Data, View) -> Unit) = { _, _ -> }

    var recylerView: RecyclerView? = null

    var items: List<Data> by Delegates.observable(emptyList()) { prop, old, new ->
        this@BaseAdapter.recylerView?.let {
            it.layoutManager?.onRestoreInstanceState(it.layoutManager?.onSaveInstanceState())
        }
        this.submitList(new)
    }

    abstract override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Data>

    override fun onBindViewHolder(holder: BaseViewHolder<Data>, position: Int) {
        holder.setOnViewClick(onViewClick).bindItem(getItem(position), onItemClick)
    }

    override fun getItemViewType(position: Int) = getItem(position).layoutId


    fun onItemClick(onClick: ((Data, positionItem: Int, layoutId: Int) -> Unit)): BaseAdapter<Data> {
        this.onItemClick = onClick
        return this
    }

    fun changeData(position: Int) {
        notifyItemChanged(position)
    }

    fun removeItem(position: Int) {
        var tempList = items.toMutableList()
        tempList.removeAt(position)
        notifyItemRemoved(position)
        items = tempList
    }

    fun onViewClick(onClick: ((Data, View) -> Unit)): BaseAdapter<Data> {
        this.onViewClick = onClick
        return this
    }


    class DiffCallback<Data : BaseEntity> : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data) =
            oldItem.hashCode() == newItem.hashCode()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Data, newItem: Data) =
            oldItem == newItem
    }

}