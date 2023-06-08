package com.bashar.mychatapp.src.ui.fragments.splashFragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bashar.mychatapp.R
import com.bashar.mychatapp.databinding.FragmentSplashBinding
import com.bashar.mychatapp.src.ui.base.BaseFragment
import com.bashar.mychatapp.src.ui.fragments.usersFragment.UsersFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.time.Duration

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel,FragmentSplashBinding>() {


    override val viewModel: SplashViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_splash


    override fun initEvents() {

      dataBinding?.rootLayout?.setOnClickListener {

//          navigateTo(SplashFragmentDirections.actionSplashFragmentToUsersFragment())
//          findNavController().navigate(R.id.action_splashFragment_to_rootFragment)

      }
    }

    override fun initFragment(savedInstanceState: Bundle?) {

     viewModel.navigateLivedata.observe(parent!!){
         if(it){
             navigateTo(SplashFragmentDirections.actionSplashFragmentToUsersFragment())
         }
     }

    }

    override fun onBackPressed(): Boolean {
        return false
    }


}