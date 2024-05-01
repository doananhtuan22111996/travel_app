package vn.travel.app.pages.main

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.travel.app.R
import vn.travel.app.base.BaseActivity

class MainActivity : BaseActivity<RootViewModel>() {
	
	override val viewModel: RootViewModel by viewModel()
	
	private lateinit var navController: NavController
	
	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()
		super.onCreate(savedInstanceState)
	}
	
	override fun onInit(savedInstanceState: Bundle?) {
		setupRootNavigation()
	}
	
	private fun setupRootNavigation() {
		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.root_host_fragment) as NavHostFragment
		navController = navHostFragment.navController
		val navInflater = navController.navInflater
		val graph = navInflater.inflate(R.navigation.nav_root)
		// This is where you pass the bundle data from Activity to StartDestination
		// This is where you change start Destination
		val destination = R.id.onBoardingFragment
		graph.setStartDestination(destination)
		navHostFragment.navController.graph = graph
	}
}
