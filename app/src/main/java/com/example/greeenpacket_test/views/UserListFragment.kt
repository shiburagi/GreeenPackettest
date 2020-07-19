package com.example.greeenpacket_test.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.greeenpacket_test.R
import com.example.greeenpacket_test.adapters.UserListAdapter
import com.example.greeenpacket_test.constants.Status
import com.example.greeenpacket_test.databinding.FragmentUserListBinding
import com.example.greeenpacket_test.models.User
import com.example.greeenpacket_test.viewmodels.UserListViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*


/**
 * A [Fragment] subclass.
 * Use the [UserListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserListFragment : Fragment() {
    companion object {
        fun newInstance() =
            UserListFragment()
    }

    private val viewModel: UserListViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentUserListBinding>(
            inflater, R.layout.fragment_user_list, container, false
        ).apply {
            // Binding
            status = viewModel.getStatus()
            retry = View.OnClickListener {
                viewModel.loadUsers()
            }
            lifecycleOwner = this@UserListFragment
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter =
            UserListAdapter()
        recycler_users.apply {
            this.adapter = adapter
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }
        startListen(adapter)

    }

    /**
     * a method to listen any changes on [Status] and list of [User]
     */
    private fun startListen(adapter:UserListAdapter) {
        // Listen to list of user event
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

        })
    }


}