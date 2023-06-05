package com.bashar.mychatapp.src.ui.fragments.splashFragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.bashar.mychatapp.R
import com.bashar.mychatapp.databinding.FragmentSplashBinding
import com.bashar.mychatapp.src.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel,FragmentSplashBinding>() {


    override val viewModel: SplashViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_splash

    override fun initEvents() {
      dataBinding?.rootLayout?.setOnClickListener {

//          parent?.showToastMessage("HEy")
//          parent?.navController?.navigate(SplashFragmentDirections.actionSplashFragmentToRootFragment())

          navigateTo(SplashFragmentDirections.actionSplashFragmentToFirst())
//          findNavController().navigate(R.id.action_splashFragment_to_rootFragment)

      }
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        
    }

    override fun onBackPressed(): Boolean {
        return false
    }


}