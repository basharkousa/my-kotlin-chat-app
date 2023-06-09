package com.bashar.mychatapp.src.ui.fragments.chatsFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bashar.mychatapp.R
import com.bashar.mychatapp.databinding.FragmentChatsBinding
import com.bashar.mychatapp.databinding.FragmentUsersBinding
import com.bashar.mychatapp.src.data.models.Chat
import com.bashar.mychatapp.src.ui.activities.MainActivity
import com.bashar.mychatapp.src.ui.base.BaseFragment
import com.bashar.mychatapp.src.ui.base.GlobalAdapter
import com.bashar.mychatapp.src.ui.listeners.RvClickListener
import com.bashar.mychatapp.src.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsFragment : BaseFragment<ChatsViewModel, FragmentChatsBinding>() {


    override val viewModel: ChatsViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_chats

    override fun initEvents() {
//        dataBinding?.buttonSecond?.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        initRecycler()
        initListeners()
        dataBinding!!.itemclick = mainClickListener
        dataBinding!!.click = parent as MainActivity
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    private lateinit var mainClickListener: RvClickListener
    private fun initListeners() {
        mainClickListener = object : RvClickListener {
            override fun click(
                view: View,
                item: Any?,
                position: Int,
                adapter: GlobalAdapter<Any>,
            ) {
                when (item) {
                    is Chat -> {
                        when (view.id) {
                            R.id.card_chat -> {
                                item.sender = viewModel.user!!
                                navigateTo(ChatsFragmentDirections.actionChatsFragmentToConversationFragment(item))
                            }
//                            R.id.tv_artist_name -> {
//                                Toast.makeText(parent, "$position ${item.name}", Toast.LENGTH_SHORT)
//                                    .show()
//                            }
                        }
                    }
//                    is NewsCategory -> {
//                        when (view.id) {
//                            R.id.rv_news_main_cv -> {
//                                view as MaterialCardView
//                                view.background.setTint(Color.CYAN)
//                                Toast.makeText(this@MainActivity, "$position", Toast.LENGTH_SHORT)
//                                    .show()
//                            }
//                        }
//                    }
                }
            }
        }
    }

    lateinit var layoutManager: LinearLayoutManager
    private fun initRecycler() {
        println("initRecycler")
        layoutManager = LinearLayoutManager(parent!!.applicationContext)
        dataBinding?.recycler?.layoutManager = layoutManager

    }
}