package vn.travel.app.pages.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import coil.load
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.travel.app.R
import vn.travel.app.base.BaseFragment
import vn.travel.app.databinding.FragmentDetailBinding
import vn.travel.app.databinding.ItemSectionBinding
import vn.travel.app.pages.main.RootViewModel
import vn.travel.app.utils.visible

class DetailFragment : BaseFragment<RootViewModel, DetailViewModel, FragmentDetailBinding>() {
	
	override val sharedViewModel: RootViewModel by activityViewModel()
	
	override val viewModel: DetailViewModel by viewModel()
	
	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding =
		FragmentDetailBinding::inflate
	
	override fun onInit(view: View, savedInstanceState: Bundle?) {
		viewBinding.fragment = this
		viewBinding.header.apply {
			setNavigationIcon(R.drawable.baseline_arrow_back_24)
			setNavigationOnClickListener {
				navController.popBackStack()
			}
			menu[0].isVisible = false
			setOnMenuItemClickListener {
				when (it.itemId) {
					R.id.map -> {
						true
					}
					
					R.id.preview -> {
						true
					}
					
					else -> false
				}
			}
		}
		viewBinding.layoutCategory.tvTitle.text = getString(R.string.categories)
		viewBinding.layoutService.tvTitle.text = getString(R.string.services)
	}
	
	override fun bindViewModel() {
		super.bindViewModel()
		sharedViewModel.detail.observe(this) {
			viewBinding.header.apply {
				setTitle(it?.name ?: getString(R.string.detail))
			}
			it?.let {
				viewBinding.introduction = it.introduction
				viewBinding.address = it.address
				viewBinding.link = it.url
				viewModel.category.value = it.category
				viewModel.service.value = it.service
				if (it.images.isEmpty()) {
					viewBinding.ivImage.load(R.drawable.im_onboarding, builder = {
						crossfade(true)
					})
				} else {
					viewBinding.ivImage.load(it.images.first().src, builder = {
						crossfade(true)
						placeholder(R.drawable.im_onboarding)
						error(R.drawable.im_onboarding)
					})
				}
			}
		}
		viewModel.category.observe(this) {
			if (it.isEmpty()) {
				viewBinding.layoutCategory.root.visible(false)
			} else {
				it.forEach { model ->
					val binding = ItemSectionBinding.inflate(LayoutInflater.from(context))
					binding.tvName.text = model.name
					viewBinding.layoutCategory.linearLayout.addView(binding.root)
				}
			}
		}
		
		viewModel.service.observe(this) {
			if (it.isEmpty()) {
				viewBinding.layoutService.root.visible(false)
			} else {
				it.forEach { model ->
					val binding = ItemSectionBinding.inflate(LayoutInflater.from(context))
					binding.tvName.text = model.name
					viewBinding.layoutService.linearLayout.addView(binding.root)
				}
			}
			
		}
	}
	
	override fun onDestroy() {
		super.onDestroy()
		sharedViewModel.detail.value = null
	}
	
	fun onLink() = navController.navigate(R.id.pushToWebViewFragment)
	
}