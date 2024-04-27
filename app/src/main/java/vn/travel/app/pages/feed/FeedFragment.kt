package vn.travel.app.pages.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import vn.travel.app.R
import vn.travel.app.base.BaseFragment
import vn.travel.app.base.PagingLoadStateAdapter
import vn.travel.app.databinding.FragmentFeedBinding
import vn.travel.app.pages.main.RootViewModel
import vn.travel.app.utils.Constants
import vn.travel.app.utils.visible

class FeedFragment : BaseFragment<RootViewModel, FeedViewModel, FragmentFeedBinding>() {

    private lateinit var adapter: FeedAdapter

    override val sharedViewModel: RootViewModel by activityViewModel()
    override val viewModel: FeedViewModel by viewModel()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFeedBinding =
        FragmentFeedBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FeedAdapter()
    }

    override fun onInit(view: View, savedInstanceState: Bundle?) {
        viewBinding.header.ivBack.apply {
            visibility = View.INVISIBLE
        }

        viewBinding.header.ivRight.apply {
            visible(true)
            (this as MaterialButton).setIconResource(R.drawable.baseline_language_24)
            setOnClickListener {
                onLanguages()
            }
        }
        viewBinding.header.tvHeader.text = getString(R.string.feed)
        viewBinding.rvFeed.adapter = adapter.withLoadStateFooter(
            footer = PagingLoadStateAdapter(retryFunc = this::onRetry)
        )
        viewBinding.swRefresh.setOnRefreshListener {
            adapter.refresh()
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.paging.collectLatest(adapter::submitData)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.distinctUntilChangedBy { it.source.refresh }.map { it.refresh }
                .collect { loadStates ->
                    Timber.d("loadStateFlow $loadStates")
                    viewBinding.swRefresh.isRefreshing =
                        adapter.itemCount != 0 && loadStates is LoadState.Loading
                    sharedViewModel.setLoadingOverlay(adapter.itemCount == 0 && loadStates is LoadState.Loading)
                    if (adapter.itemCount == 0) {
                        setPagingInitialState(loadStates)
                    }
                }
        }
    }

    private fun setPagingInitialState(loadStates: LoadState) {
        viewBinding.layoutRetry.retry.visible(loadStates is LoadState.Error)
        if (loadStates is LoadState.Error) {
            viewBinding.layoutRetry.let {
                it.tvError.text = loadStates.error.message
                it.btnRetry.setOnClickListener {
                    onRetry()
                }
            }
        }
    }

    private fun onRetry() = adapter.retry()
    private fun onLanguages() {
        MaterialAlertDialogBuilder(requireContext()).setTitle(getString(R.string.support_languages))
            .setItems(Constants.languages.map { "${it.first} - ${it.second}" }
                .toTypedArray()) { _, index ->
                viewModel.onLanguage(Constants.languages[index].first)
                viewBinding.rvFeed.scrollToPosition(0)
            }.show()

    }
}