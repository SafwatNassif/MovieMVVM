package com.example.movieappstarter.ui.home.fragment.list


import android.app.usage.UsageEvents.Event.NONE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.movieappstarter.R
import com.example.movieappstarter.ui.home.fragment.list.mvi.MovieListIntent
import com.example.movieappstarter.ui.home.fragment.list.mvi.MovieListViewState
import com.example.movieappstarter.ui.home.fragment.list.paging.MovieListAdapter
import com.example.movieappstarter.utils.base.BaseFragment
import com.example.movieappstarter.utils.basemvi.MVIView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : BaseFragment(), MVIView<MovieListIntent, MovieListViewState> {


    private val viewModel: MovieListFragmentViewModel
            by lazy(NONE) {
                ViewModelProviders.of(this, viewModelFactory)
                    .get(MovieListFragmentViewModel::class.java)
            }

    private val disposeable = CompositeDisposable()
    lateinit var movieListAdapter: MovieListAdapter
    lateinit var itemTouchHelperCallback: ItemTouchHelper.Callback
    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        sw_refresh.setOnRefreshListener {
            viewModel.refresh()
        }

    }

    private fun bind() {
        disposeable.add(viewModel.state().subscribe(this::render))
        viewModel.processIntents(intents())
    }

    override fun intents(): Observable<MovieListIntent> {
        return Observable.merge(
            initialIntent(),
            navigateToDetails()
        )
    }

    override fun render(state: MovieListViewState) {
        //pass
        Toast.makeText(context, "done", Toast.LENGTH_LONG).show()
    }

    //    initial intent will load data.
    private fun initialIntent(): Observable<MovieListIntent.InitialIntent> {
        return Observable.just(MovieListIntent.InitialIntent)
    }

    private fun loadMoreIntent(): Observable<MovieListIntent.LoadMoreMovie> {
        return Observable.just(MovieListIntent.LoadMoreMovie)
    }

    private fun pullToRefreshIntent(): Observable<MovieListIntent.PullToRefresh> {
        return Observable.just(MovieListIntent.PullToRefresh)
    }

    private fun navigateToDetails(): Observable<MovieListIntent.NavigateToDetails> {
        return Observable.just(MovieListIntent.NavigateToDetails)
    }


    /*
//    private fun initView(rootView: View?) {
//
//        movieListAdapter = MovieListAdapter(
//            object : OnStartDrag {
//                override fun startDrag(viewHolder: RecyclerView.ViewHolder) {
//                    itemTouchHelper.startDrag(viewHolder)
//                }
//
//            },
//            object : RetryListener {
//                override fun retry() {
//                    viewModel.retry()
//                }
//
//            })
//
//
//        rootView!!.rc_movie_list.adapter = movieListAdapter
//        itemTouchHelperCallback = TouchHelperCallback(movieListAdapter)
//
//
//        val layoutManager = GridLayoutManager(context, 2)
//
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return when (movieListAdapter.getItemViewType(position)) {
//                    (MovieListAdapter.FOOTER) -> {
//                        2
//                    }
//                    MovieListAdapter.FOOTER_RETRY -> {
//                        2
//                    }
//                    else -> {
//                        1
//                    }
//                }
//            }
//
//        }
//
//        rootView.rc_movie_list.layoutManager = layoutManager
//
//        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
//
//        itemTouchHelper.attachToRecyclerView(rootView.rc_movie_list)
//    }
//
//    private fun setListener() {
//        viewModel.fetchMovieList()
//        viewModel.movieList.observe(this, Observer {
//            sw_refresh.isRefreshing = false
//            movieListAdapter.submitList(it)
//        })
//
//
//        viewModel.getProgressStatus().observe(this, Observer {
//            when (it) {
//                Status.LOADING -> {
//                    if (movieListAdapter.itemCount == 0 || sw_refresh.isRefreshing) {
//                        shimmer_list_loading.visibility = View.VISIBLE
////                        movie_load_more.visibility = View.GONE
//                        movieListAdapter.setFooter(PageListFooter.NONE)
//
//                    } else {
//                        shimmer_list_loading.visibility = View.GONE
//                        movieListAdapter.setFooter(PageListFooter.LOADING)
//                    }
//                }
//                Status.LOADED -> {
//                    shimmer_list_loading.visibility = View.GONE
//                    movieListAdapter.setFooter(PageListFooter.NONE)
//                    sw_refresh.isRefreshing = false
//                    rc_movie_list.visibility = View.VISIBLE
//                }
//                Status.EMPTY_LIST -> {
//                    shimmer_list_loading.visibility = View.GONE
//                    movie_load_more.visibility = View.GONE
//                    sw_refresh.isRefreshing = false
//                    rc_movie_list.visibility = View.VISIBLE
//                    // adding empty list view.
//                }
//
//            }
//        })
//
//
//        viewModel.error.observe(this, Observer {
//            movieListAdapter.setFooter(PageListFooter.RETRY)
//            sw_refresh.isRefreshing = false
//            Alerter.create(activity)
//                .setTitle(R.string.app_name)
//                .setText(it.remoteError ?: context!!.getString(it.localError!!))
//                .setBackgroundColorInt(context!!.getColor(R.color.red))
//                .show()
//        }
//        )
//
//    }
//
//    override fun onStop() {
//        Alerter.hide()
//        super.onStop()
//    }
*/

}
