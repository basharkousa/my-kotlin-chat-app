package com.bashar.mychatapp.src.ui.fragments.conversationFragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashar.mychatapp.R
import com.bashar.mychatapp.databinding.FragmentChatsBinding
import com.bashar.mychatapp.databinding.FragmentConversationBinding
import com.bashar.mychatapp.src.ui.activities.MainActivity
import com.bashar.mychatapp.src.ui.base.BaseFragment
import com.bashar.mychatapp.src.ui.base.GlobalAdapter
import com.bashar.mychatapp.src.ui.fragments.chatsFragment.ChatsViewModel
import com.bashar.mychatapp.src.ui.listeners.RvClickListener
import com.bashar.mychatapp.src.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ConversationFragment : BaseFragment<ConversationViewModel, FragmentConversationBinding>() {

    override val viewModel: ConversationViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_conversation
    val userViewModel: UserViewModel by activityViewModels()


    private lateinit var listAdapter: ConversationAdapter
    private lateinit var listAdapterObserver: RecyclerView.AdapterDataObserver

    override fun initEvents() {

        dataBinding?.btnBack?.setOnClickListener {
            parent?.navController?.navigateUp()
        }

        dataBinding?.recordBtnParent?.setOnClickListener {

            if (ContextCompat.checkSelfPermission(parent!!, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    parent!!,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    1
                )
            } else {
                viewModel.startRecording(parent?.externalCacheDir)
            }
        }

        dataBinding?.stopRecordBtnParent?.setOnClickListener{
            viewModel.stopRecording()
        }

    }

    override fun initFragment(savedInstanceState: Bundle?) {
        initRecycler()
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    private fun initRecycler() {
        println("initRecycler")

        listAdapterObserver = (object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                dataBinding?.recycler?.scrollToPosition(positionStart)
            }
        })
        listAdapter =
            ConversationAdapter(viewModel, userViewModel.currentUser!!.id)
        listAdapter.registerAdapterDataObserver(listAdapterObserver)
        dataBinding?.recycler?.adapter = listAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        listAdapter.unregisterAdapterDataObserver(listAdapterObserver)
    }


//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            viewModel.stopRecording()
//        }
//    }

}