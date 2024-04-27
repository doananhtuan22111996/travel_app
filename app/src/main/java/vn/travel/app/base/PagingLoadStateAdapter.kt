package vn.travel.app.base

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import vn.travel.app.R
import vn.travel.app.databinding.ItemPagingStateBinding
import vn.travel.app.utils.visible

class PagingLoadStateAdapter(private val retryFunc: (() -> Unit)? = null) :
    LoadStateAdapter<PagingLoadStateAdapter.PagingStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ): PagingStateViewHolder {
        return PagingStateViewHolder(
            ItemPagingStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PagingStateViewHolder, loadState: LoadState) {
        holder.bind()
    }

    inner class PagingStateViewHolder(private val viewBinding: ItemPagingStateBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind() {
            val imageLoader = ImageLoader.Builder(viewBinding.root.context).components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()
            viewBinding.ivLoading.apply {
                visible(loadState is LoadState.Loading)
                load(R.drawable.loading_medium, imageLoader)
            }

            viewBinding.layoutRetry.retry.visible(loadState is LoadState.Error)
            if (loadState is LoadState.Error) {
                viewBinding.layoutRetry.let {
                    it.tvError.text = (loadState as LoadState.Error).error.message
                    it.btnRetry.setOnClickListener {
                        retryFunc?.invoke()
                    }
                }
            }

        }
    }
}


