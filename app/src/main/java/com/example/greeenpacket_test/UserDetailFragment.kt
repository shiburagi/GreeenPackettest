package com.example.greeenpacket_test

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.greeenpacket_test.models.User
import com.example.greeenpacket_test.viewmodels.UserListViewModel
import com.shiburagi.utility.load
import kotlinx.android.synthetic.main.fragment_user_detail.*

class UserDetailFragment : Fragment() {

    companion object {
        fun newInstance() = UserDetailFragment()
    }

    private val args: UserDetailFragmentArgs by navArgs()

    private lateinit var viewModel: UserDetailViewModel
    private val userListViewModel: UserListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)

        val user: User = args.user;
        val superiorUser: User? =
            userListViewModel.getUsers().value?.find { findUser -> findUser.isSuperiorFor(user) }
        imageView_avatar.load(user.profileImage ?: "", user.displayName);

        layout_team_lead.visibility = if (user.isTeamLead) View.VISIBLE else View.GONE;

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

        setField(textView_duties as TextView, viewModel.parseDuties(user.duties))
        setField(textView_report_to, superiorUser?.displayName)

    }

    private fun setField(view: EditText, text: String?) {
        view.inputType = InputType.TYPE_NULL
        setField(view as TextView, text)
    }

    private fun setField(view: TextView, text: String?) {
        if (text?.isEmpty() != false) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
            view.text = text
        }
    }


}