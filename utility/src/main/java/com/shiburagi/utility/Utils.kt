package com.shiburagi.utility

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.widget.ImageView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide
import kotlin.math.pow

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}


val Int.px: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


val Double.byte: Double
    get() = this * 1024.0.pow(2.0)


fun ImageView.load(url: String, placeholder: String) {
    val drawable = TextDrawable.builder().beginConfig().width(60.dp).height(60.dp).endConfig()
        .buildRound(
            placeholder.substring(0, 1) ?: "",
            ColorGenerator.MATERIAL.getColor(placeholder)
        )
    Glide.with(context).load(url)
        .placeholder(drawable)
        .error(drawable)
        .into(this);
}

