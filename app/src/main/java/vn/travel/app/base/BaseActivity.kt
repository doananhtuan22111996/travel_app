package vn.travel.app.base

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import timber.log.Timber
import vn.travel.app.R
import vn.travel.app.databinding.ActivityBaseBinding
import vn.travel.domain.model.ResultModel
import vn.travel.app.utils.setupViewClickHideKeyBoard

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModel: VM

    protected lateinit var viewBinding: ActivityBaseBinding
        private set

    abstract fun onInit(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityBaseBinding.inflate(layoutInflater)
        super.setContentView(viewBinding.root)
        window?.setupViewClickHideKeyBoard(viewBinding.root)
        onInit(savedInstanceState)
        bindViewModel()
    }

    @CallSuper
    open fun bindViewModel() {
        viewModel.loadingOverlay.observe(this, this::executeLoadingOverlay)
        viewModel.exception.observe(this, this::executeAppException)
    }

    private fun executeAppException(appException: ResultModel.AppException?) {
        appException?.run {
            Timber.d("executeAppException: ${this.message}")
            Toast.makeText(this@BaseActivity, this.message ?: "", Toast.LENGTH_SHORT).show()
        }
    }

    private fun executeLoadingOverlay(isLoading: Boolean) {
        Timber.d("executeLoadingOverlay: $isLoading")
        viewBinding.layoutLoading.run {
            rlProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
            val imageLoader = ImageLoader.Builder(this@BaseActivity).components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()
            ivLoading.load(R.drawable.loading_medium, imageLoader)
        }
    }
}