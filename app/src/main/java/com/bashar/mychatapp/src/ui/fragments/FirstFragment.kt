package com.bashar.mychatapp.src.ui.fragments

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bashar.mychatapp.R
import com.bashar.mychatapp.databinding.FragmentFirstBinding
import com.bashar.mychatapp.src.ui.base.BaseFragment
import com.bashar.mychatapp.src.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirstFragment : BaseFragment<UserViewModel,FragmentFirstBinding>() {


    override val viewModel: UserViewModel by activityViewModels()
    override val layoutRes: Int = R.layout.fragment_first

    override fun initEvents() {
        dataBinding?.buttonFirst?.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun initFragment(savedInstanceState: Bundle?) {

    }

    override fun onBackPressed(): Boolean {
       return false
    }


}