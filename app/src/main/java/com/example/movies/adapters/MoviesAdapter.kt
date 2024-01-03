package com.example.movies.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.pojo.movies.Movie


private val TAG = "MoviesAdapter"

class MoviesAdapter : ListAdapter<Movie, MoviesAdapter.Holder>(Comparator()) {

    var onCardClickListener: ((Movie) -> Unit)? = null

    private lateinit var movies: List<Movie>
    private lateinit var onReachEndListener: OnReachEndListener


    fun setOnReachEndListener(onReachEndListener: OnReachEndListener) {
        this.onReachEndListener = onReachEndListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movieInfo = getItem(position)
        holder.view.setOnClickListener {
            onCardClickListener?.invoke(movieInfo)
        }


        val movieRating = movieInfo.rating.kp.toDouble()
        with(holder) {
            textViewRating.text = movieInfo.rating.kp
            textViewRating.text =
                movieInfo.rating.kp.substring(0, movieInfo.rating.kp.indexOf('.') + 2)
            if (movieRating > 7) {
                textViewRating.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.circle_green)
            } else if (movieRating > 4.9) {
                textViewRating.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.circle_yellow)
            } else {
                textViewRating.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.circle_red)
            }
            Glide.with(holder.itemView)
                .load(movieInfo.poster.url)
                .into(imageViewPoster)
        }
        if (position >= this.currentList.size - 10) {
            onReachEndListener.onReachEnd()
        }
    }


    class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewRating = view.findViewById<TextView>(R.id.textViewRating)
        val imageViewPoster = view.findViewById<ImageView>(R.id.imageViewPoster)

    }

    class Comparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }


}