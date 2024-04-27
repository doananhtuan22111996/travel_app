package vn.travel.app.pages.webView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import vn.travel.app.R
import vn.travel.app.databinding.FragmentWebViewBinding
import vn.travel.app.pages.main.RootViewModel
import vn.travel.app.utils.Constants

class WebViewFragment : Fragment() {
	private val sharedViewModel: RootViewModel by activityViewModel()
	
	private lateinit var viewBinding: FragmentWebViewBinding
	private lateinit var navController: NavController
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		viewBinding = FragmentWebViewBinding.inflate(inflater, container, false)
		return viewBinding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		navController = Navigation.findNavController(view)
		onInit()
	}
	
	private fun onInit() {
		viewBinding.layoutHeader.header.apply {
			menu.clear()
			setNavigationOnClickListener {
				navController.popBackStack()
			}
		}
		
		val name = arguments?.getString(Constants.KEY_TITLE, "") ?: getString(R.string.detail)
		viewBinding.layoutHeader.header.title = name
		val urlLoad = arguments?.getString(Constants.KEY_URL, "") ?: ""
		viewBinding.webView.apply {
			loadUrl(urlLoad)
			settings.javaScriptCanOpenWindowsAutomatically = true
			webViewClient = object : WebViewClient() {
				override fun onLoadResource(view: WebView?, url: String?) {
					super.onLoadResource(view, url)
					sharedViewModel.setLoadingOverlay(url == urlLoad)
				}
				
				override fun onPageFinished(view: WebView?, url: String?) {
					super.onPageFinished(view, url)
					sharedViewModel.setLoadingOverlay(false)
				}
			}
		}
	}
}