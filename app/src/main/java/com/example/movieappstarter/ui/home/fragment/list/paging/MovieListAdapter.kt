package com.example.movieappstarter.ui.home.fragment.list.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieappstarter.R
import com.example.movieappstarter.data.local.model.Movie
import kotlinx.android.synthetic.main.item_movie_list.view.*

/**
 * Created by Safwat Nassif on 7/30/2019.
 */
class MovieListAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(DiffUtil) {

    companion object {
        val DiffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieView = holder as MovieViewHolder
        movieView.bind(getItem(position)!!)
    }

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        fun bind(movie: Movie) {
            movie.let {
                Glide.with(view.iv_poster.context)
                    .load("http://image.tmdb.org/t/p/w500" + it.posterPath)
                    .centerCrop()
                    .into(view.iv_poster)
            }

        }

    }

}