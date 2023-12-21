package com.example.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.pojo.Movie


class MoviesAdapter : ListAdapter<Movie, MoviesAdapter.Holder>(Comparator()) {

    private lateinit var onCardClickListener: OnCardClickListener
    private lateinit var movies: List<Movie>
    private lateinit var onReachEndListener: OnReachEndListener

    fun setOnCardClickListener(onCardClickListener: OnCardClickListener) {
        this.onCardClickListener = onCardClickListener
    }

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    fun setOnReachEndListener(onReachEndListener: OnReachEndListener) {
        this.onReachEndListener = onReachEndListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movieInfo: Movie = getItem(position)
        //Log.d("onBindViewHolder", "position: ${position}")
        holder.bind(movieInfo, holder)
        holder.itemView.setOnClickListener {
            onCardClickListener.onCardClick(movieInfo)
        }
        //Log.d("onBindViewHolder","position ${position}")
        //Log.d("onBindViewHolder","this.currentList.size minus odin ${this.currentList.size - 1}")


        //if (position == this.currentList.size - 1) {
        if (position >= this.currentList.size - 10) {
            onReachEndListener.onReachEnd()
        }
    }


    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = MovieItemBinding.bind(view)
        fun bind(movieInfo: Movie, holder: Holder) = with(binding) {
            val movieRating = movieInfo.rating.kp.toDouble()
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
            //Picasso.get().load(movieInfo.poster.url).into(imageViewPoster)
            Glide.with(holder.itemView)
                .load(movieInfo.poster.url)
                .into(imageViewPoster)

        }
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

    interface OnCardClickListener {
        fun onCardClick(movie: Movie)
    }

}