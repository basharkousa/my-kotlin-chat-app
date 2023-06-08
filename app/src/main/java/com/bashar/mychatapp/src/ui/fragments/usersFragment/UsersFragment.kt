package com.bashar.mychatapp.src.ui.fragments.usersFragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bashar.mychatapp.R
import com.bashar.mychatapp.databinding.FragmentUsersBinding
import com.bashar.mychatapp.src.data.models.User
import com.bashar.mychatapp.src.ui.activities.MainActivity
import com.bashar.mychatapp.src.ui.base.BaseFragment
import com.bashar.mychatapp.src.ui.base.GlobalAdapter
import com.bashar.mychatapp.src.ui.listeners.RvClickListener
import com.bashar.mychatapp.src.utils.SpacesItemDecoration
import com.bashar.mychatapp.src.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class UsersFragment : BaseFragment<UserViewModel, FragmentUsersBinding>() {


    override val viewModel: UserViewModel by activityViewModels()
    override val layoutRes: Int = R.layout.fragment_users

    override fun initEvents() {

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
                    is User -> {
                        when (view.id) {
                            R.id.card_user -> {
                                navigateTo(UsersFragmentDirections.actionUsersFragmentToChatsFragment(item))
                                viewModel.currentUser = item
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

//        dataBinding!!.recycler.addOnScrollListener(object : RecyclerViewPaginator(dataBinding!!.recycler) {
//
//            override val isLastPage: Boolean
//                get() = viewModel.isLastPage()
//
//            override fun loadMore(start: Long, count: Long) {
//                println("start: ${start} count: ${count}")
//
//                viewModel.getNewPage()
////                Toast.makeText(parent,"endOfScroll",Toast.LENGTH_SHORT).show()
//
//            }
//        })


//        dataBinding!!.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//
//                if (!recyclerView.canScrollVertically(1) ) {
//                    Toast.makeText(parent,"endOfScroll",Toast.LENGTH_LONG).show()
//                    recyclerView.adapter.
//                    //Implement button dim here. Example:
//                    //mybutton.setAlpha(0.5f);
//                }
//
//            }
//        })
    }

    lateinit var layoutManager: LinearLayoutManager
    private fun initRecycler() {
        println("initRecycler")
        /*todo
           If you want to consider this action just remove the 'orientation' from the configChanges in
           manifest file
        */
        layoutManager = LinearLayoutManager(parent!!.applicationContext)
        dataBinding?.recycler?.layoutManager = layoutManager

    }
}