package com.bashar.mychatapp.src.databinding

import android.annotation.SuppressLint
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bashar.mychatapp.BR
import com.bashar.mychatapp.src.ui.listeners.RvClickListener
import com.bashar.mychatapp.R
import com.bashar.mychatapp.src.data.models.Message
import com.bashar.mychatapp.src.data.models.base.NetworkResult
import com.bashar.mychatapp.src.ui.base.GlobalAdapter
import com.bashar.mychatapp.src.ui.fragments.conversationFragment.ConversationAdapter
import com.bashar.mychatapp.src.ui.fragments.conversationFragment.ConversationViewModel
import com.bashar.mychatapp.src.utils.AnimationUtils


import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.math.abs

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object BindingAdapterss {

    @BindingAdapter("hideOnLoading")
    @JvmStatic
    fun ViewGroup.hideOnLoading(responseState: NetworkResult<*>) {
        visibility = if (responseState is NetworkResult.Loading)
            View.GONE
        else
            View.VISIBLE
    }


//@JvmStatic

    @BindingAdapter("imgUrl", "circular")
    @JvmStatic
    fun loadImage(view: View, url: String, circular: Boolean = false) {
//        Log.e("OSA_", url[1].text)
        val imageView = view as ImageView
        if (circular)
            Glide.with(imageView.context).load(url).apply(RequestOptions.circleCropTransform())
                .into(imageView)
        else
            Glide.with(imageView.context).load(url).into(imageView)

    }

    @BindingAdapter("html")
    @JvmStatic
    fun setTextViewHTML(view: View, html: String?) {

        val textView = view as TextView

        if (!html.isNullOrEmpty()) {

            var validHtml = "<div>$html</div>"
            val sequence: CharSequence = Html.fromHtml(validHtml)
            val strBuilder = SpannableStringBuilder(sequence)
            val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
            for (span in urls) {
//                BasicTools.makeLinkClickable(strBuilder, span, textView.context as Activity)
            }
            textView.text = strBuilder
            textView.movementMethod = LinkMovementMethod.getInstance()
        } else {
            textView.setText(R.string.no_description)
        }

    }

    @BindingAdapter("android:visibility")
    @JvmStatic
    fun setVisibility(view: View, visible: Boolean) {
        if (visible) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @BindingAdapter("android:visibility")
    @JvmStatic
    fun setVisibility(view: View, str: String) {
        if (str.isNotEmpty()) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @BindingAdapter("android:visibility", "scaleAnim")
    @JvmStatic
    fun setVisibility(view: View, visible: Boolean, scaleAnim: Boolean) {
        if (visible) {
            view.animation = AnimationUtils.getScaleAnimation()
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("layout", "list", "click")
    fun <T> setRecyclerView(
        view: RecyclerView,
        layout: Int,
        list: MutableList<T>?,
        click: RvClickListener
    ) {
        println("newDataUpdated")
        if (list != null) {
            println("listSizeFromBindingAdapter${list.size}")
            val adapter: GlobalAdapter<*>
            if (view.adapter == null) {
                adapter = GlobalAdapter(
                    layout,
                    list.toMutableList(),
                    BR.model,
                    clickListener = click,
                    mapOf(BR.itemclick to click)
                )
                println("ListSize:${list.size}")
                view.adapter = adapter
            } else {
                if (view.adapter is GlobalAdapter<*>) {
                    (view.adapter as GlobalAdapter<T>).mutableList.clear()
                    (view.adapter as GlobalAdapter<T>).addItems(list.toMutableList())
                }
            }

        } else {
            println("listSizeFromBindingAdapterElse${list?.size}")
        }
    }

    @JvmStatic
    @BindingAdapter("bind_messages_list")
    fun bindMessagesList(listView: RecyclerView, items: List<Message>?) {
        items?.let {
            println("CURRENT_ADAPTER_LIST:${(listView.adapter as ConversationAdapter).currentList.size} ITEMS:${items.size}")
            (listView.adapter as ConversationAdapter).submitList(items)
            listView.scrollToPosition(items.size - 1)
        }
    }

    @JvmStatic
    @BindingAdapter("bind_message", "bind_message_viewModel")
    fun View.bindShouldMessageShowTimeText(message: Message, viewModel: ConversationViewModel) {
        val halfHourInMilli = 1800000
        val index = viewModel.messageList?.value!!.indexOf(message)

        if (index == 0) {
            this.visibility = View.VISIBLE
        } else {
            val messageBefore = viewModel.messageList?.value!![index - 1]

            if (abs(messageBefore!!.timestamp - message.timestamp) > halfHourInMilli) {
                this.visibility = View.VISIBLE
            } else {
                this.visibility = View.GONE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bind_disable_item_animator")
    fun bindDisableRecyclerViewItemAnimator(recyclerView: RecyclerView, disable: Boolean) {
        if (disable) {
            recyclerView.itemAnimator = null
        }

    }

    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("bind_epochTimeMsToDate_with_days_ago")
    fun TextView.bindEpochTimeMsToDateWithDaysAgo(epochTimeMs: Long) {
        val numOfDays = TimeUnit.MILLISECONDS.toDays(Date().time - epochTimeMs)

        this.text = when {
            numOfDays == 1.toLong() -> "Yesterday"
            numOfDays > 1.toLong() -> "$numOfDays days ago"
            else -> {
                val pat =
                    SimpleDateFormat().toLocalizedPattern().replace("\\W?[YyMd]+\\W?".toRegex(), " ")
                val formatter = SimpleDateFormat(pat, Locale.getDefault())
                formatter.format(Date(epochTimeMs))
            }
        }
    }

    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("bind_epochTimeMsToDate")
    fun TextView.bindEpochTimeMsToDate(epochTimeMs: Long) {
        if (epochTimeMs > 0) {
            val currentTimeMs = Date().time
            val numOfDays = TimeUnit.MILLISECONDS.toDays(currentTimeMs - epochTimeMs)

            val replacePattern = when {
                numOfDays >= 1.toLong() -> "Yy"
                else -> "YyMd"
            }
            val pat = SimpleDateFormat().toLocalizedPattern().replace("\\W?[$replacePattern]+\\W?".toRegex(), " ")
            val formatter = SimpleDateFormat(pat, Locale.getDefault())
            this.text = formatter.format(Date(epochTimeMs))
        }
    }


//    @JvmStatic
//    @BindingAdapter("refreshOnChange")
//    fun refreshOnChange(view: View, liveData: LiveData<*>) {
//        liveData.observe(view.viewLifecycleOwner), Observer {
//            view.invalidate()
//        })
//    }

//    @JvmStatic
//    @BindingAdapter("refreshOnChange")
//    fun refreshOnChange(view: View, liveData: LiveData<*>, lifecycleOwner: LifecycleOwner) {
//        liveData.observe(lifecycleOwner){
//            view.invalidate()
//        }
//    }


}