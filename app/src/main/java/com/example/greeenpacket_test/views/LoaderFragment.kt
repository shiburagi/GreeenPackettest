package com.example.greeenpacket_test.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.example.greeenpacket_test.R
import com.example.greeenpacket_test.viewmodels.UserListViewModel
import kotlinx.android.synthetic.main.fragment_message.*

private const val ARG_TITLE = "title"
private const val ARG_MESSAGE = "message"

/**
 * A [Fragment] subclass.
 * Use the [LoaderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loader, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            LoaderFragment()
    }
}