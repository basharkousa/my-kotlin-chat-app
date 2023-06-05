package com.bashar.mychatapp.src.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bashar.mychatapp.src.utils.SpacesItemDecoration
import com.bashar.mychatapp.R
import com.bashar.mychatapp.databinding.FragmentSecondBinding
import com.bashar.mychatapp.src.ui.activities.MainActivity
import com.bashar.mychatapp.src.ui.base.BaseFragment
import com.bashar.mychatapp.src.ui.base.GlobalAdapter
import com.bashar.mychatapp.src.ui.listeners.RvClickListener
import com.bashar.mychatapp.src.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : BaseFragment<UserViewModel, FragmentSecondBinding>() {


    override val viewModel: UserViewModel by activityViewModels()
    override val layoutRes: Int = R.layout.fragment_second

    override fun initEvents() {
        dataBinding?.buttonSecond?.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
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
                    is String -> {
                        when (view.id) {
                            R.id.card_text -> {
                                  parent?.showToastMessage(item)
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

    lateinit var layoutManager: GridLayoutManager
    private fun initRecycler() {
        println("initRecycler")
        /*todo
           If you want to consider this action just remove the 'orientation' from the configChanges in
           manifest file
        */

        layoutManager =
            if (parent!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) GridLayoutManager(
                parent!!.applicationContext, 2
            ) else GridLayoutManager(parent!!.applicationContext, 3)
        dataBinding!!.recycler.layoutManager = layoutManager
        dataBinding!!.recycler.addItemDecoration(
            SpacesItemDecoration(
                parent!!.resources.getDimension(R.dimen.d0_4)
                    .toInt()
            )
        )

        //init_listener();
//        if (adapter == null) {
//            dataBinding.recycler.setAdapter(adapter);
//            dataBinding.recycler.addItemDecoration(new SpacesItemDecoration((int) parent.getResources().getDimension(R.dimen.d0_4)));
//            // Open subject when click on it
//
//        }
    }
}