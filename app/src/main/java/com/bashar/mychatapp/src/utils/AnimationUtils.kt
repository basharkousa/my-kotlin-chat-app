package com.bashar.mychatapp.src.utils

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bashar.mychatapp.R

object AnimationUtils {

//    val TRANSITION_POP = 0
//    val TRANSITION_FADE_IN_OUT = 1
//    val TRANSITION_SLIDE_LEFT_RIGHT = 2
//    val TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT = 3
//    val TRANSITION_NONE = 4
//
//
//    @IntDef(
//        TRANSITION_POP,
//        TRANSITION_FADE_IN_OUT,
//        TRANSITION_SLIDE_LEFT_RIGHT,
//        TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT,
//        TRANSITION_NONE
//    )
//    internal annotation class FragmentAnimation


    fun getScaleAnimation(): ScaleAnimation {
        val animation = ScaleAnimation(
            0f, 1f, 0f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        animation.duration = 165
        return animation
    }

    fun runLayoutAnimation(recyclerView: RecyclerView, item: AnimationItem) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, item.resourceId)
        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    fun getAnimationRecyclerItemsHorizontal(): Array<AnimationItem> {
        return arrayOf<AnimationItem>(
            AnimationItem("Fall down", R.anim.layout_animation_fall_down),
            AnimationItem("Slide from right", R.anim.layout_animation_from_right),
            AnimationItem("Slide from left", R.anim.layout_animation_from_left),
            AnimationItem("Slide from bottom", R.anim.layout_animation_from_bottom)
        )
    }

    fun getAnimationRecyclerItemsVertical(): Array<AnimationItem> {
        return arrayOf<AnimationItem>(
            AnimationItem("Slide from bottom", R.anim.grid_layout_animation_from_bottom),
            AnimationItem("Scale", R.anim.grid_layout_animation_scale),
            AnimationItem("Scale random", R.anim.grid_layout_animation_scale_random)
        )
    }


    class AnimationItem(val name: String, val resourceId: Int)


}

