package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.databinding.TrailerItemBinding
import com.example.movies.pojo.trailers.Trailer

class TrailersAdapter: ListAdapter<Trailer, TrailersAdapter.Holder>(Comparator()) {
    private lateinit var onCardClickListener: OnCardClickListener
    private lateinit var trailers: List<Trailer>


    fun setOnCardClickListener(onCardClickListener: OnCardClickListener) {
        this.onCardClickListener = onCardClickListener
    }

    fun setTrailers(trailers: List<Trailer>) {
        this.trailers = trailers
        this.submitList(trailers)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trailer_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val trailerInfo: Trailer = getItem(position)
        holder.bind(trailerInfo)
        holder.itemView.setOnClickListener {
            onCardClickListener.onCardClick(trailerInfo)
        }
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TrailerItemBinding.bind(view)
        fun bind(trailerInfo: Trailer) = with(binding) {
            textViewTrailerName.text = trailerInfo.name
        }
    }

    class Comparator : DiffUtil.ItemCallback<Trailer>() {
        override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem == newItem
        }
    }


    interface OnCardClickListener {
        fun onCardClick(trailer: Trailer)
    }

}