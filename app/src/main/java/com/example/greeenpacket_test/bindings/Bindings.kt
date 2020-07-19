package com.example.greeenpacket_test.bindings

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.example.greeenpacket_test.R
import com.example.greeenpacket_test.components.MessageView
import com.example.greeenpacket_test.constants.Status
import com.shiburagi.utility.isOnline
import com.shiburagi.utility.loadAvatar


@BindingAdapter("url", "placeholder")
fun loadAvatar(view: ImageView, url: String, placeholder: String) {
    view.loadAvatar(url, placeholder)
}

@BindingAdapter("show")
fun hide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("status", "retry")
fun message(view: MessageView, status: Status?, retry: View.OnClickListener) {
    if (!isOnline(view.context))
        view.set(
            R.string.no_internet_connection,
            R.string.no_internet_connection_message
        )
    else if (status == Status.DENIED)
        view.set(
            R.string.dont_allow,
            R.string.dont_allow_message
        )
    else if (status == Status.FAILED)
        view.set(R.string.something_went_wrong, R.string.something_went_wrong_message)

    view.setOnRetryClickListener(retry)
}
