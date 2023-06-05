package com.bashar.mychatapp.src.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bashar.mychatapp.src.ui.listeners.RvClickListener
import androidx.databinding.library.baseAdapters.BR


class GlobalAdapter<T>(
    private val layoutId: Int,
    var mutableList: MutableList<T>,
    private val br: Int,
    private var clickListener: RvClickListener,
    private val brs: Map<Int, Any>
) : RecyclerView.Adapter<GlobalAdapter<T>.ViewHolder>() {

    private lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.setVariable(br, mutableList[holder.adapterPosition])
        brs.forEach {
            binding.setVariable(it.key, it.value)
        }
        holder.binding.setVariable(
            BR.click,
            View.OnClickListener { v ->
                clickListener.click(
                    v,
                    mutableList[holder.adapterPosition],
                    holder.adapterPosition,
                    this as GlobalAdapter<Any>
                )
            })
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = mutableList.size

    public fun addItems(newItems: MutableList<T>) {
        mutableList.addAll(mutableList.size, newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)
}