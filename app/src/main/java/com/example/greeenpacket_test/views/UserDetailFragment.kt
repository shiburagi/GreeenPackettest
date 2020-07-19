package com.example.greeenpacket_test.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.greeenpacket_test.R
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

    private lateinit var user: User
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
        user = args.user

        val view = inflater.inflate(R.layout.fragment_user_detail, container, false)
        ViewCompat.setTransitionName(view.imageView_avatar, user.userId)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        superiorUser = userListViewModel.getSuperiorFor(user)
        populateData()
        initializeInteraction()
    }

    /**
     * a method to assign value on all textField and editText
     */
    private fun populateData() {
        imageView_avatar.loadAvatar(user.profileImage ?: "", user.displayName)
        layout_team_lead.visibility = if (user.isTeamLead) View.VISIBLE else View.GONE

        textView_name.text = user.firstName
        textView_age.text = user.displayAge(requireContext())
        setField(textView_job_title, user.jobTitleName)
        setField(textView_first_name, user.firstName)
        setField(textView_last_name, user.lastName)
        setField(textView_email, user.emailAddress)
        setField(textView_employee_code, user.employeeCode?.toString())
        setField(textView_phone_no, user.phoneNumber)
        setField(textView_region, user.region)
        setField(textView_user_id, user.userId)

        setField(textView_duties, user.duties?.toString())
        setField(textView_report_to, superiorUser?.displayName)

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