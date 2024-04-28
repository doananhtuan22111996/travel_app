package vn.travel.app.pages.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import vn.travel.app.databinding.FragmentPreviewBinding
import vn.travel.app.pages.main.RootViewModel

class PreviewFragment : Fragment() {
	
	private val sharedViewModel: RootViewModel by activityViewModel()
	private lateinit var navController: NavController
	private lateinit var viewBinding: FragmentPreviewBinding
	private lateinit var adapter: PreviewAdapter
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		viewBinding = FragmentPreviewBinding.inflate(inflater, container, false)
		return viewBinding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		navController = Navigation.findNavController(view)
		onInit()
		onBindViewModel()
	}
	
	private fun onInit() {
		viewBinding.btnClose.setOnClickListener {
			navController.popBackStack()
		}
	}
	
	private fun onBindViewModel() {
		sharedViewModel.detail.observe(viewLifecycleOwner) {
			if (it != null && it.images.isNotEmpty()) {
				adapter = PreviewAdapter(this, it.images)
				viewBinding.vPager.adapter = adapter
				viewBinding.dotIndicator.attachTo(viewBinding.vPager)
			}
		}
	}
}