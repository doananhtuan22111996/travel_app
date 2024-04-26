package vn.travel.app.pages.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import vn.travel.app.R
import vn.travel.app.databinding.FragmentOnboardingBinding

class OnBoardingFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewBinding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    fun onNext() = navController.navigate(R.id.pushToFeedFragment)
}
