package vn.travel.app.pages.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
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
import vn.travel.domain.model.AttractionModel

class FeedFragment : BaseFragment<RootViewModel, FeedViewModel, FragmentFeedBinding>() {
	
	private lateinit var adapter: FeedAdapter
	
	override val sharedViewModel: RootViewModel by activityViewModel()
	override val viewModel: FeedViewModel by viewModel()
	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFeedBinding =
		FragmentFeedBinding::inflate
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		adapter = FeedAdapter(this::onItemClickListener)
	}
	
	override fun onInit(view: View, savedInstanceState: Bundle?) {
		viewBinding.layoutHeader.header.apply {
			setTitle(getString(R.string.feed))
			navigationIcon = null
			menu[1].isVisible = false
			menu[2].isVisible = false
			setOnMenuItemClickListener {
				if (it.itemId == R.id.language) {
					onLanguages()
					true
				} else {
					false
				}
			}
		}
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
				val lang = Constants.languages[index].first.lowercase()
				viewModel.onLanguage(lang)
				viewBinding.rvFeed.scrollToPosition(0)
				val appLocale: LocaleListCompat =
					LocaleListCompat.forLanguageTags(lang.replace("id", "in"))
				// Call this on the main thread as it may require Activity.restart()
				AppCompatDelegate.setApplicationLocales(appLocale)
			}.show()
		
	}
	
	private fun onItemClickListener(model: AttractionModel?) {
		sharedViewModel.detail.value = model
		navController.navigate(R.id.pushToDetailFragment)
	}
}