package com.bashar.mychatapp.src.ui.base


import android.content.Intent

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivity<D : ViewDataBinding>:AppCompatActivity()
{
    var dataBinding: D? = null
    var navController: NavController? = null

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected abstract val navigationHostRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutRes)

        if(navigationHostRes != null){
            if (navigationHostRes!! > 0) {
                // Get the navigation host fragment from this Activity
                val navHostFragment = supportFragmentManager.findFragmentById(
                    navigationHostRes!!
                ) as NavHostFragment
                // Instantiate the navController using the NavHostFragment
                navController = navHostFragment.navController
            }
        }

//        set_fragment_place()
        initEvents()
        initActivity()
    }

    abstract fun initActivity()
    abstract fun initEvents()
    abstract fun setFragmentPlace()
//    override fun onBackPressed() {
//
//    }

    private fun doubleClickToExit() {
//        if (back_pressed + 2000 > System.currentTimeMillis()) finish() else showToastMessageShort(R.string.double_click)
//        back_pressed = System.currentTimeMillis()
    }

    fun navigateTo(fragmentId: Int, args: Bundle) {
//        current_fragment = (BaseFragment) navController.getContext(). ;
//        NavBackStackEntry navBackStackEntry;
//        navBackStackEntry.
//        navController.getCurrentBackStackEntry().
        navController!!.navigate(fragmentId, args)
    }

    fun showActivity(toActivity: Class<*>, clearStack: Boolean, withAnimation: Boolean) {
        val intent = Intent(this@BaseActivity, toActivity)
        if (clearStack) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
//        if (withAnimation) overridePendingTransition(R.anim.enter, R.anim.exit)
    }

    fun showActivityWithIntent(intent: Intent, clearStack: Boolean, withAnimation: Boolean) {
        val intentt = intent
        if (clearStack) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
//        if (withAnimation) overridePendingTransition(R.anim.enter, R.anim.exit)
    }

    fun showToastMessage(msg: Int) {
        val toast = Toast.makeText(applicationContext, resources.getString(msg), Toast.LENGTH_LONG)
        val view = toast.view
        toast.show()
    }
    fun showToastMessage(msg: String) {
        val toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG)
        val view = toast.view
        toast.show()
    }


    companion object {
        private var back_pressed: Long = 0
    }
}

