package vn.travel.app.utils

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["htmlText"], requireAll = false)
    fun setHtmlText(textView: TextView, htmlText: String?) {
        textView.text = HtmlCompat.fromHtml(htmlText ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

fun View.visible(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}
