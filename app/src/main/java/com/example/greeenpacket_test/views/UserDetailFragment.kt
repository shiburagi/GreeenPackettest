package com.example.greeenpacket_test.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.greeenpacket_test.R
import com.example.greeenpacket_test.databinding.FragmentUserDetailBinding
import com.example.greeenpacket_test.models.User
import com.example.greeenpacket_test.viewmodels.UserListViewModel
import com.shiburagi.utility.loadAvatar
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.fragment_user_detail.view.*


/**
 * A [Fragment] subclass.
 * Use the [UserDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserDetailFragment : Fragment() {

    companion object {
        fun newInstance() =
            UserDetailFragment()
    }

    private val args: UserDetailFragmentArgs by navArgs()
    private val userListViewModel: UserListViewModel by activityViewModels()

    private var superiorUser: User? = null

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val user = args.user
        superiorUser = userListViewModel.getSuperiorFor(user)
        val binding =
            DataBindingUtil.inflate<FragmentUserDetailBinding>(
                inflater,
                R.layout.fragment_user_detail,
                container,
                false
            ).apply {
                this.user = user
                this.superior = superiorUser

            }

        ViewCompat.setTransitionName(binding.root.imageView_avatar, user.userId)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeInteraction()
    }

    /**
     * a method to provide interface/listener on click event
     */
    private fun initializeInteraction() {
        if (superiorUser != null)
            textView_report_to.setOnClickListener {
                val action =
                    UserDetailFragmentDirections.actionUserDetailFragmentToUserDetailFragment(
                        superiorUser!!
                    )
                findNavController()
                    .navigate(action)

            }
    }

    /**
     * a method to set [EditText]'s text and hide the view if the given text is empty
     * @param view [EditText] component
     * @param text String to assign as text value on [EditText]
     */
    private fun setField(view: EditText, text: String?) {
        if (text?.isEmpty() != false) {
            (view.parent as View).visibility = View.GONE
        } else {
            (view.parent as View).visibility = View.VISIBLE
            (view as TextView).text = text
        }
    }

    /**
     * a method to set [TextView]'s text and hide the view if the given text is empty
     * @param view EditText component
     * @param text String to assign as text value on [TextView]
     */
    private fun setField(view: TextView, text: String?) {
        if (text?.isEmpty() != false) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
            view.text = text
        }
    }


}