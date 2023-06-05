package com.bashar.mychatapp.src.ui.base

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T>(private val mOnItemClickListener:(T) -> Unit):
    RecyclerView.Adapter<BaseAdapter<T>.MyViewHolder>() //        extends ListAdapter<T, BaseAdapter<T>.MyViewHolder>
{
    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    fun onClick(item: T) {
        Log.e("'AdapterClick'", "artist.getName()")
        mOnItemClickListener(item)
    }

    fun setOnClick(onclick:(T) -> Unit){
    }

    //    private final OnItemClickListener<T> itemClickListener;
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )
        val viewHolder: MyViewHolder = MyViewHolder(binding)
        viewHolder.bind(this)
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val item = getItemForPosition(position)
        //        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemClickListener.onItemClick(item);
//            }
//        });
        holder.bind(item)
        holder.itemView.setOnClickListener{
            mOnItemClickListener(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    private val DIFF_CALLBACK: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
//                    return oldItem.getMbid() == newItem.getMbid();
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }

    protected abstract fun getItemForPosition(position: Int): T

    protected abstract fun getLayoutIdForPosition(position: Int): Int

    inner class MyViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(item: T) {
//            binding.setVariable(BR.obj, item)
            //            binding.setVariable(BR.viewModel,);
//            binding.executePendingBindings()
        }

        fun bind(adapter: BaseAdapter<T>) {
//            binding.setVariable(BR.adapter, adapter)
//            binding.executePendingBindings()
        }
    }
}