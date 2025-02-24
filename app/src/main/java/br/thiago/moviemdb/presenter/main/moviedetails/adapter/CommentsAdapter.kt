package br.thiago.moviemdb.presenter.main.moviedetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.thiago.moviemdb.R
import br.thiago.moviemdb.databinding.ItemCommentReviewBinding
import br.thiago.moviemdb.domain.model.movie.MovieReview
import com.bumptech.glide.Glide

class CommentsAdapter : ListAdapter<MovieReview, CommentsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieReview>() {
            override fun areItemsTheSame(
                oldItem: MovieReview,
                newItem: MovieReview
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieReview,
                newItem: MovieReview
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCommentReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)

        review.authorDetails?.avatarPath?.let { avatarPath ->
            Glide
                .with(holder.binding.root.context)
                .load("https://image.tmdb.org/t/p/w500$avatarPath")
                .into(holder.binding.imageUser)
        } ?: run {
            holder.binding.imageUser.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    R.drawable.person_comment_placeholder
                )
            )
        }

        holder.binding.textUsername.text = review.authorDetails?.username
        holder.binding.textComment.text = review.content
        holder.binding.textRating.text = review?.authorDetails?.rating?.toString() ?: "0"
       // holder.binding.textDate.text = formatCommentDate(review.createdAt)
    }

    inner class MyViewHolder(val binding: ItemCommentReviewBinding) :
        RecyclerView.ViewHolder(binding.root)

}