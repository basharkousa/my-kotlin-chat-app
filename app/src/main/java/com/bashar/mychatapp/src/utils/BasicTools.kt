package com.bashar.mychatapp.src.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashar.mychatapp.src.ui.base.BaseAdapter
import com.bashar.mychatapp.src.ui.listeners.OnBottomReached
import java.util.*

object BasicTools {

    fun hideSoftKeyboardAdjust(activity: Activity) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }


    fun isConnected(context: Context): Boolean {
        val manager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return info != null && info.isConnectedOrConnecting
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun hasInternetConnection(context: Context?): Boolean {
        if (context == null)
            return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun hideKeyboard(activity: Activity) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideSoftKeyboard_adjust(activity: Activity) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }


    /*for long value*/
    private val suffixes: NavigableMap<Long, String> = TreeMap()

//    static
//    {
//        suffixes[1_000L] = "k"
//        suffixes[1_000_000L] = "M"
//        suffixes[1_000_000_000L] = "G"
//        suffixes[1_000_000_000_000L] = "T"
//        suffixes[1_000_000_000_000_000L] = "P"
//        suffixes[1_000_000_000_000_000_000L] = "E"
//    }

    fun format(value: Long): String {

        suffixes[1_000L] = "k"
        suffixes[1_000_000L] = "M"
        suffixes[1_000_000_000L] = "G"
        suffixes[1_000_000_000_000L] = "T"
        suffixes[1_000_000_000_000_000L] = "P"
        suffixes[1_000_000_000_000_000_000L] = "E"

        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1)
        if (value < 0) return "-" + format(-value)
        if (value < 1000) return java.lang.Long.toString(value) //deal with easy case
        val (divideBy, suffix) = suffixes.floorEntry(value)
        val truncated = value / (divideBy / 10) //the number part of the output times 10
        val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
        return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
    }
    /**/


    /**/
    fun formatSeconds(timeInSeconds: Int): String? {
        val hours = timeInSeconds / 3600
        val secondsLeft = timeInSeconds - hours * 3600
        val minutes = secondsLeft / 60
        val seconds = secondsLeft - minutes * 60
        var formattedTime = ""
        if (hours < 10) formattedTime += "0"
        formattedTime += "$hours:"
        if (minutes < 10) formattedTime += "0"
        formattedTime += "$minutes:"
        if (seconds < 10) formattedTime += "0"
        formattedTime += seconds
        return formattedTime
    }


    /* for html in textView */
     fun makeLinkClickable(
        strBuilder: SpannableStringBuilder,
        span: URLSpan,
        activity: Activity
    ) {
        val start = strBuilder.getSpanStart(span)
        val end = strBuilder.getSpanEnd(span)
        val flags = strBuilder.getSpanFlags(span)
        val clickable: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                open_website(span.url, activity)
                Log.e("DESCRIPTIOPN:", span.url)
            }
        }
        strBuilder.setSpan(clickable, start, end, flags)
        strBuilder.removeSpan(span)
    }

    fun setTextViewHTML(text: TextView, html: String?, activity: Activity) {
        val sequence: CharSequence = Html.fromHtml(html)
        val strBuilder = SpannableStringBuilder(sequence)
        val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
        for (span in urls) {
            makeLinkClickable(strBuilder, span, activity)
        }
        text.text = strBuilder
        text.movementMethod = LinkMovementMethod.getInstance()
    }
    /* */

    /* */
    fun open_website(url: String, activity: Activity) {
        try {
            var edited = url
            if (!url.startsWith("http")) edited = "http://$url"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(edited))
            activity.startActivity(browserIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun logMessage(key: String?, value: String?) {
        Log.e(key, value!!)
    }

    fun setBottomListener(
        layoutManager: LinearLayoutManager, recycler: RecyclerView, adapter: BaseAdapter<*>,
        listener: OnBottomReached
    ) {
        val scroll_listener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val visible_items = layoutManager.childCount
                        val total_items = layoutManager.itemCount
                        val past_visible_items = layoutManager.findFirstVisibleItemPosition()
                        if (visible_items + past_visible_items >= total_items) {
                            listener.onReachBottom()
                        } else {
                            listener.onScrolledUp()
                        }
                    } else {
                        listener.onScrolledUp()
                    }
                }
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recycler.clearOnScrollListeners()
            recycler.addOnScrollListener(scroll_listener)
        } else {
            recycler.setOnScrollListener(scroll_listener)
        }
    }

    fun setBottomListener(
        layoutManager: LinearLayoutManager, recycler: RecyclerView,
        listener: OnBottomReached
    ) {
        val scroll_listener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val visible_items = layoutManager.childCount
                        val total_items = layoutManager.itemCount
                        val past_visible_items = layoutManager.findFirstVisibleItemPosition()
                        if (visible_items + past_visible_items >= total_items && !recyclerView.canScrollVertically(1)) {
                            listener.onReachBottom()
                        } else {
                            listener.onScrolledUp()
                        }
                    } else {
                        listener.onScrolledUp()
                    }
                }
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recycler.clearOnScrollListeners()
            recycler.addOnScrollListener(scroll_listener)
        } else {
            recycler.setOnScrollListener(scroll_listener)
        }
    }

//    fun setBottomListener(
//        layoutManager: LinearLayoutManager, recycler: RecyclerView, adapter: BasePagedAdapter?,
//        listener: OnBottomReached
//    ) {
//        val scroll_listener: RecyclerView.OnScrollListener =
//            object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    if (dy > 0) {
//                        val visible_items = layoutManager.childCount
//                        val total_items = layoutManager.itemCount
//                        val past_visible_items = layoutManager.findFirstVisibleItemPosition()
//                        if (visible_items + past_visible_items >= total_items) {
//                            listener.onReachBottom()
//                        } else {
//                            listener.onScrolledUp()
//                        }
//                    } else {
//                        listener.onScrolledUp()
//                    }
//                }
//            }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            recycler.clearOnScrollListeners()
//            recycler.addOnScrollListener(scroll_listener)
//        } else {
//            recycler.setOnScrollListener(scroll_listener)
//        }
//    }
}