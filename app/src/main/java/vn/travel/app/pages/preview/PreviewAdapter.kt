package vn.travel.app.pages.preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.travel.app.utils.Constants
import vn.travel.domain.model.ImageModel

class PreviewAdapter(fragment: Fragment, private val items: List<ImageModel>) :
	FragmentStateAdapter(fragment) {
	override fun getItemCount(): Int = items.size
	
	override fun createFragment(position: Int): Fragment {
		val fragment = ImageFragment()
		fragment.arguments = Bundle().apply {
			putString(Constants.KEY_URL, items[position].src)
		}
		return fragment
	}
}