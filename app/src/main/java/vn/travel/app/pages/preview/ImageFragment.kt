package vn.travel.app.pages.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import vn.travel.app.R
import vn.travel.app.databinding.ItemPreviewBinding
import vn.travel.app.utils.Constants

class ImageFragment : Fragment() {
	
	private lateinit var viewBinding: ItemPreviewBinding
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		viewBinding = ItemPreviewBinding.inflate(inflater, container, false)
		return viewBinding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val url = arguments?.getString(Constants.KEY_URL, "") ?: ""
		if (url.isEmpty()) {
			viewBinding.ivImage.load(R.drawable.im_onboarding, builder = {
				crossfade(true)
			})
		} else {
			viewBinding.ivImage.load(url, builder = {
				crossfade(true)
				placeholder(R.drawable.im_onboarding)
				error(R.drawable.im_onboarding)
			})
		}
	}
}