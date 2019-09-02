package com.example.movieappstarter.ui.home.fragment.list.paging

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieappstarter.R
import com.example.movieappstarter.data.model.Movie
import com.example.movieappstarter.utils.base.ItemTouchBackgroundViewHolderHelper
import com.example.movieappstarter.utils.base.OnStartDrag
import com.example.movieappstarter.utils.base.TouchHelperAdapter
import kotlinx.android.synthetic.main.item_movie_list.view.*
import kotlinx.android.synthetic.main.retry_loading_item.view.*

/**
 * Created by Safwat Nassif on 7/30/2019.
 */
class MovieListAdapter(
    val startDrage: OnStartDrag,
    val retryListener: RetryListener,
    val movieListenter: MovieItemListener
) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(DiffUtil),
    TouchHelperAdapter {


    companion object {

        val FOOTER = 1
        val FOOTER_RETRY = 2
        val ROW_ITEM = 3


        val DiffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    var pagedListFooter: PageListFooter = PageListFooter.NONE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ROW_ITEM) {
            MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_movie_list,
                    parent,
                    false
                )
            )
        } else if (viewType == FOOTER) {
            FooterLoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.load_more_indecator,
                    parent,
                    false
                )
            )
        } else {
            FooterRetryViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.retry_loading_item,
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            FOOTER -> {
                (holder as FooterLoadingViewHolder)
            }
            FOOTER_RETRY -> {
                (holder as FooterRetryViewHolder).bind(retryListener)
            }
            ROW_ITEM -> {
                (holder as MovieViewHolder).bind(getItem(position)!!, startDrage, movieListenter)
            }

        }
    }


    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
//        Collections.swap(currentList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount() && getItem(position) != null)
            ROW_ITEM
        else if (pagedListFooter == PageListFooter.RETRY)
            FOOTER_RETRY
        else
            FOOTER
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }


    fun setFooter(pageListFooter: PageListFooter) {
        if (this.pagedListFooter != pageListFooter) {
            this.pagedListFooter = pageListFooter
            notifyDataSetChanged()
        }
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 &&
                ((pagedListFooter is PageListFooter.LOADING) || (pagedListFooter is PageListFooter.RETRY))
    }

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view), ItemTouchBackgroundViewHolderHelper {

        fun bind(
            movieItem: Movie,
            startDrag: OnStartDrag,
            movieListener: MovieItemListener
        ) {
            movieItem.let {
                Glide.with(view.iv_movie_poster.context)
                    .load("http://image.tmdb.org/t/p/w500" + it.posterPath)
                    .centerCrop()
                    .into(view.iv_movie_poster)

                view.setOnLongClickListener {
                    startDrag.startDrag(this)
                    true
                }
                view.setOnClickListener {
                    movieListener.onMovieItemClick(movieItem.id, movieItem.posterPath, view)
                }
            }

        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

        override fun onItemSelect() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

    }


    class FooterLoadingViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    class FooterRetryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(retryListener: RetryListener) {
            view.reload.setOnClickListener {
                retryListener.retry()
            }
        }
    }

}

sealed class PageListFooter {
    object LOADING : PageListFooter()
    object RETRY : PageListFooter()
    object NONE : PageListFooter()

}

interface MovieItemListener {
    fun onMovieItemClick(movieId: Int, posterPath: String, view: View)
}