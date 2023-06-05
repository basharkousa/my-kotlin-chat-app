package com.bashar.mychatapp.src.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.databinding.library.baseAdapters.BR


import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment<V : ViewModel, D : ViewDataBinding> : Fragment() {

    protected abstract val viewModel: V?
    protected var dataBinding: D? = null
    protected var parent: BaseActivity<*>? = null
    protected var snackbar: Snackbar? = null
    protected var backPressCallback: OnBackPressedCallback? = null
//    protected abstract fun getViewModel(): Class<V>

    // use it when your project doesn't use Dagger2
    //protected abstract ViewModelProvider.Factory getViewModelFactory();
    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parent = context as BaseActivity<*>
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this)[getViewModel()]
//        if(getViewModelFactory()==null)
//        viewModel = new ViewModelProvider(this,viewModelFactory).get(getViewModel());
//        else
//        viewModel = new ViewModelProvider(this, getViewModelFactory()).get(getViewModel());
//        viewModel = ViewModelProvider(this)[getViewModel()]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate<D>(inflater, layoutRes, container, false)
//        val listItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false)

        dataBinding!!.setVariable(BR.viewModel, viewModel)
        dataBinding!!.lifecycleOwner = this
        return dataBinding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
//        if(onBackPressed()){
            //this to make the nested backPress work

        // This callback will only be called when MyFragment is at least Started.
        backPressCallback = object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event

//                    if(NavHostFragment.findNavController(requireParentFragment()).backQueue.size ===>0){
//                        parent?.navController?.navigateUp()
//                    }else{
                        NavHostFragment.findNavController(requireParentFragment()).navigateUp()
//                    }
                }
            }
        if(onBackPressed()){
            (requireActivity() as OnBackPressedDispatcherOwner).onBackPressedDispatcher.addCallback(viewLifecycleOwner,backPressCallback!!)
        }

//            (requireActivity() as OnBackPressedDispatcherOwner).onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//                NavHostFragment.findNavController(requireParentFragment()).navigateUp()
////                parent?.navController?.popBackStack()
//            }

//        }
    }

    fun init(savedInstanceState: Bundle?) {
        initEvents()
        initFragment(savedInstanceState)
    }

    abstract fun initEvents()
    abstract fun initFragment(savedInstanceState: Bundle?)

    //we make it true if the fragment might be nested to enable the backPress listener
    abstract fun onBackPressed(): Boolean


    fun navigateTo(navDirections: NavDirections){
        parent?.navController?.navigate(navDirections)
    }

    fun navigateTo(navDirectionsId: Int){
        parent?.navController?.navigate(navDirectionsId)
    }

    fun navigateNestedTo(navDirections: NavDirections){
        //todo work here
//        (requireActivity() as OnBackPressedDispatcherOwner).onBackPressedDispatcher.addCallback(viewLifecycleOwner,backPressCallback)
        NavHostFragment.findNavController(requireParentFragment()).navigate(navDirections)
    }

    fun performBackPress(){
        NavHostFragment.findNavController(requireParentFragment()).popBackStack()
    }

    fun cancel() {
        removeSnackbar()
    }

    fun removeSnackbar() {
        if (snackbar != null && snackbar!!.isShown) snackbar!!.dismiss()
    }
}
