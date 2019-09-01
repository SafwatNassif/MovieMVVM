package com.example.movieappstarter.ui.home.fragment.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappstarter.R
import com.example.movieappstarter.data.model.Status
import com.example.movieappstarter.ui.home.fragment.list.paging.MovieListAdapter
import com.example.movieappstarter.ui.home.fragment.list.paging.PageListFooter
import com.example.movieappstarter.ui.home.fragment.list.paging.RetryListener
import com.example.movieappstarter.utils.base.BaseFragment
import com.example.movieappstarter.utils.base.OnStartDrag
import com.example.movieappstarter.utils.base.TouchHelperCallback
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.fragment_movie_list.view.*

class MovieListFragment : BaseFragment() {


    lateinit var viewModel: MovieListFragmentViewModel

    lateinit var movieListAdapter: MovieListAdapter
    lateinit var itemTouchHelperCallback: ItemTouchHelper.Callback
    lateinit var itemTouchHelper: ItemTouchHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_list, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListFragmentViewModel::class.java)
        initView(rootView)
        setListener()

        rootView.sw_refresh.setOnRefreshListener {
            viewModel.refresh()
        }

        return rootView
    }


    private fun initView(rootView: View?) {

        movieListAdapter = MovieListAdapter(
            object : OnStartDrag {
                override fun startDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }

            },
            object : RetryListener {
                override fun retry() {
                    viewModel.retry()
                }

            })


        rootView!!.rc_movie_list.adapter = movieListAdapter
        itemTouchHelperCallback = TouchHelperCallback(movieListAdapter)


        val layoutManager = GridLayoutManager(context, 2)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (movieListAdapter.getItemViewType(position)) {
                    (MovieListAdapter.FOOTER) -> {
                        2
                    }
                    MovieListAdapter.FOOTER_RETRY -> {
                        2
                    }
                    else -> {
                        1
                    }
                }
            }

        }

        rootView.rc_movie_list.layoutManager = layoutManager

        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)

        itemTouchHelper.attachToRecyclerView(rootView.rc_movie_list)
    }

    private fun setListener() {
        viewModel.fetchMovieList()
        viewModel.movieList.observe(this, Observer {
            sw_refresh.isRefreshing = false
            movieListAdapter.submitList(it)
        })


        viewModel.getProgressStatus().observe(this, Observer {
            when (it) {
                Status.LOADING -> {
                    if (movieListAdapter.itemCount == 0 || sw_refresh.isRefreshing) {
                        shimmer_list_loading.visibility = View.VISIBLE
//                        movie_load_more.visibility = View.GONE
                        movieListAdapter.setFooter(PageListFooter.NONE)

                    } else {
                        shimmer_list_loading.visibility = View.GONE
                        movieListAdapter.setFooter(PageListFooter.LOADING)
                    }
                }
                Status.LOADED -> {
                    shimmer_list_loading.visibility = View.GONE
                    movieListAdapter.setFooter(PageListFooter.NONE)
                    sw_refresh.isRefreshing = false
                    rc_movie_list.visibility = View.VISIBLE
                }
                Status.EMPTY_LIST -> {
                    shimmer_list_loading.visibility = View.GONE
                    movie_load_more.visibility = View.GONE
                    sw_refresh.isRefreshing = false
                    rc_movie_list.visibility = View.VISIBLE
                    // adding empty list view.
                }

            }
        })


        viewModel.error.observe(this, Observer {
            movieListAdapter.setFooter(PageListFooter.RETRY)
            sw_refresh.isRefreshing = false
            Alerter.create(activity)
                .setTitle(R.string.app_name)
                .setText(it.remoteError ?: context!!.getString(it.localError!!))
                .setBackgroundColorInt(context!!.getColor(R.color.red))
                .show()
        }
        )

    }

    override fun onStop() {
        Alerter.hide()
        super.onStop()
    }


}
