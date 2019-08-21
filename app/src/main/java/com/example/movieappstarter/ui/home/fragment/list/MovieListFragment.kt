package com.example.movieappstarter.ui.home.fragment.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappstarter.R
import com.example.movieappstarter.ui.home.fragment.list.paging.MovieListAdapter
import com.example.movieappstarter.utils.base.BaseFragment
import com.example.movieappstarter.utils.base.OnStartDrag
import com.example.movieappstarter.utils.base.TouchHelperCallback
import kotlinx.android.synthetic.main.fragment_movie_list.view.*

class MovieListFragment : BaseFragment<MovieListFragmentViewModel>() {


    lateinit var movieListAdapter: MovieListAdapter
    lateinit var itemTouchHelpercallback: ItemTouchHelper.Callback
    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_list, container, false)
        initView(rootView)
        setListener()
        return rootView
    }


    private fun initView(rootView: View?) {
        rootView!!.rc_movie_list.layoutManager = GridLayoutManager(context, 2)
        movieListAdapter = MovieListAdapter(object : OnStartDrag {
            override fun startDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }

        })
        rootView.rc_movie_list.adapter = movieListAdapter
        itemTouchHelpercallback = TouchHelperCallback(movieListAdapter)
        itemTouchHelper = ItemTouchHelper(itemTouchHelpercallback)

        itemTouchHelper.attachToRecyclerView(rootView.rc_movie_list)
    }

    private fun setListener() {

        viewModel.fetchMovieList().observe(this, Observer {
            movieListAdapter.submitList(it)
        })
    }


}
