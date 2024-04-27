package vn.travel.app.pages.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import vn.travel.app.R
import vn.travel.app.databinding.ItemFeedBinding
import vn.travel.domain.model.AttractionModel


class FeedAdapter :
    PagingDataAdapter<AttractionModel, FeedAdapter.FeedItemViewHolder>(differCallback) {
    companion object {
        val differCallback = object : DiffUtil.ItemCallback<AttractionModel>() {
            override fun areItemsTheSame(
                oldItem: AttractionModel, newItem: AttractionModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AttractionModel, newItem: AttractionModel
            ): Boolean {
                return oldItem.introduction == newItem.introduction && oldItem.name == newItem.name && oldItem.images.firstOrNull()?.src == newItem.images.firstOrNull()?.src
            }
        }
    }

    inner class FeedItemViewHolder(private val viewBinding: ItemFeedBinding) :
        ViewHolder(viewBinding.root) {

        fun bind(model: AttractionModel?) {
            viewBinding.name = model?.name
            viewBinding.introduction = model?.introduction
            if (model == null || model.images.isEmpty()) {
                viewBinding.ivFeed.load(R.drawable.im_onboarding, builder = {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(radius = 24f))
                })
            } else {
                viewBinding.ivFeed.load(model.images.first().src, builder = {
                    crossfade(true)
                    placeholder(R.drawable.im_onboarding)
                    error(R.drawable.im_onboarding)
                    transformations(RoundedCornersTransformation(radius = 24f))
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder {
        val viewBinding =
            ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}