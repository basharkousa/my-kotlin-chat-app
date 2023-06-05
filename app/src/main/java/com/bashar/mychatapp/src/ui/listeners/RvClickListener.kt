package com.bashar.mychatapp.src.ui.listeners

import android.view.View
import com.bashar.mychatapp.src.ui.base.GlobalAdapter

interface RvClickListener {
    fun click(
        view: View,
        item: Any?,
        position: Int,
        adapter: GlobalAdapter<Any>
    )
}