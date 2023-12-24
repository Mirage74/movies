package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.databinding.ReviewItemBinding
import com.example.movies.pojo.reviews.Review

private const val POSITIVE_REVIEW = "Позитивный"
private const val NEGATIVE_REVIEW = "Нейтральный"


class ReviewsAdapter : ListAdapter<Review, ReviewsAdapter.Holder>(Comparator()) {
    private lateinit var reviews: List<Review>

    fun setReviews(reviews: List<Review>) {
        //if (reviews.size > 0) {
            this.reviews = reviews
            this.submitList(reviews)
            notifyDataSetChanged()
        //}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val reviewInfo: Review = getItem(position)
        holder.bind(reviewInfo, holder)
    }


    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ReviewItemBinding.bind(view)

        private fun convertTypeReviewToInt(s: String): Int {
            var typeReview = 0
            if (s == POSITIVE_REVIEW) {
                typeReview = 1
            } else if (s == NEGATIVE_REVIEW) {
                typeReview = -1
            }
            return typeReview
        }


        fun bind(reviewInfo: Review, holder: Holder) = with(binding) {
            val reviewType = convertTypeReviewToInt(reviewInfo.type)
            textViewAuthor.text = reviewInfo.author
            textViewReview.text = reviewInfo.review
            var colorResId = android.R.color.holo_green_light
            if (reviewType == 0) {
                colorResId = android.R.color.holo_orange_light
            } else if (reviewType == -1) {
                android.R.color.holo_red_light
            }
            val color = ContextCompat.getColor(holder.itemView.context, colorResId)
            binding.linearLayoutContainer.setBackgroundColor(color)
        }
    }

        class Comparator : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }

    }