package com.bashar.mychatapp.src.databinding

import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bashar.mychatapp.BR
import com.bashar.mychatapp.src.ui.listeners.RvClickListener
import com.bashar.mychatapp.R
import com.bashar.mychatapp.src.data.models.base.NetworkResult
import com.bashar.mychatapp.src.ui.base.GlobalAdapter
import com.bashar.mychatapp.src.utils.AnimationUtils


import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


object BindingAdapterss {

    @BindingAdapter("showOnSuccess")
    @JvmStatic
    fun ViewGroup.showOnSuccess(responseState: NetworkResult<*>) {
        println("showOnSuccess")
        visibility = if (responseState is NetworkResult.Success)
            View.VISIBLE
        else
            View.GONE
    }

    @BindingAdapter("showOnLoading")
    @JvmStatic
    fun ViewGroup.showOnLoading(responseState: NetworkResult<*>) {
        visibility = if (responseState is NetworkResult.Loading)
            View.VISIBLE
        else
            View.GONE
    }

    @BindingAdapter("showOnError")
    @JvmStatic
    fun ViewGroup.showOnError(responseState: NetworkResult<*>) {
        visibility = if (responseState is NetworkResult.Error)
            View.VISIBLE
        else
            View.GONE
    }

    @BindingAdapter("hideOnLoading")
    @JvmStatic
    fun ViewGroup.hideOnLoading(responseState: NetworkResult<*>) {
        visibility = if (responseState is NetworkResult.Loading)
            View.GONE
        else
            View.VISIBLE
    }

//    @BindingAdapter("showOnLoading")
//    fun ProgressBar.showOnLoading(responseState: NetworkResult<*>) {
//        visibility = if (responseState is NetworkResult.Loading)
//            View.VISIBLE
//        else
//            View.GONE
//    }

//    @BindingAdapter("showOnError")
//    fun TextView.showError(responseState: NetworkResult<*>) {
//        visibility = if (responseState is NetworkResult.Error)
//            View.VISIBLE
//        else
//            View.GONE
//        text = (responseState as NetworkResult.Error).message
//    }


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
//        Log.e("OSA_", url[1].text)
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


//    @JvmStatic
//    @BindingAdapter("profileImage")
//    fun loadImage(view: ImageView, profileImage: String) {
//        Glide.with(view.context)
//            .load(profileImage)
//            .into(view)
//    }

//    @BindingAdapter("duration_value")
//    @JvmStatic
//    fun formatSecondd(view: View, duration: Int) {
//        val textView = view as TextView
//        val durationInt = duration.toInt()
//        textView.setText(BasicTools.formatSeconds(durationInt))
//    }


    @BindingAdapter("android:visibility")
    @JvmStatic
    fun setVisibility(view: View, visible: Boolean) {
        if (visible) {
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


}

