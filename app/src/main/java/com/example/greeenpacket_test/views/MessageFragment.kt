package com.example.greeenpacket_test.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.greeenpacket_test.R
import com.example.greeenpacket_test.viewmodels.UserListViewModel
import kotlinx.android.synthetic.main.fragment_message.*

private const val ARG_TITLE = "title"
private const val ARG_MESSAGE = "message"

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessageFragment : Fragment() {
    private var title: String? = null
    private var message: String? = null
    private val userListViewModel: UserListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            message = it.getString(ARG_MESSAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    private var onRetryClickListener: View.OnClickListener? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView_title.text = title
        textView_message.text = message
        button_retry.setOnClickListener {
            onRetryClickListener?.onClick(it)
        }
    }

    fun addOnRetryClickListener(onClickListener: View.OnClickListener) {
        this.onRetryClickListener = onClickListener
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title Parameter 1.
         * @param message Parameter 2.
         * @return A new instance of fragment MessageFragment.
         */
        @JvmStatic
        fun newInstance(title: String, message: String) =
            MessageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_MESSAGE, message)
                }
            }
    }
}