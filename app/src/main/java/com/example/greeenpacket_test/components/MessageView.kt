package com.example.greeenpacket_test.components

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import com.example.greeenpacket_test.R
import kotlinx.android.synthetic.main.view_message.view.*

class MessageView :
    FrameLayout {

    constructor(
        context: Context
    ) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    init {
        addView(LayoutInflater.from(context).inflate(R.layout.view_message, this, false))
    }

    fun set(titleRes: Int, messageRes: Int) {
        textView_title.text = context.getString(titleRes)
        textView_message.text = context.getString(messageRes)
    }

    fun setTitle(titleRes: Int) {
        textView_title.text = context.getString(titleRes)
    }

    fun setMessage(messageRes: Int) {
        textView_message.text = context.getString(messageRes)
    }

    fun setOnRetryClickListener(onClickListener: OnClickListener){
        button_retry.setOnClickListener(onClickListener)
    }
}