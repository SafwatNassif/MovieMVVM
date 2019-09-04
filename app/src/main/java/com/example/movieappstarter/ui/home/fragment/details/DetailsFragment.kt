package com.example.movieappstarter.ui.home.fragment.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.movieappstarter.R
import kotlinx.android.synthetic.main.fragment_details.view.*


class DetailsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_details, container, false)

        arguments?.let {
            rootView.iv_movie_poster.transitionName = it.getString("transitionName")

            Glide.with(context!!)
                .load("http://image.tmdb.org/t/p/w500" + it.getString("poster_path"))
                .centerCrop()
                .into(rootView.iv_movie_poster)

        }

        return rootView
    }


}
