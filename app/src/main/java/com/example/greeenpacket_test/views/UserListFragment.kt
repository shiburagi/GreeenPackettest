package com.example.greeenpacket_test.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greeenpacket_test.R
import com.example.greeenpacket_test.adapters.UsersRecyclerViewAdapter
import com.example.greeenpacket_test.constants.Status
import com.example.greeenpacket_test.models.User
import com.example.greeenpacket_test.viewmodels.UserListViewModel
import com.shiburagi.utility.isOnline
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

    private lateinit var adapter: UsersRecyclerViewAdapter
    private lateinit var viewManager: LinearLayoutManager
    private val viewModel: UserListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter =
            UsersRecyclerViewAdapter()
        viewManager = LinearLayoutManager(context)
        viewManager.orientation = LinearLayoutManager.VERTICAL
        recycler_users.setHasFixedSize(true)
        recycler_users.layoutManager = viewManager
        recycler_users.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        startListen()
    }

    /**
     * a method to listen any changes on [Status] and list of [User]
     */
    private fun startListen() {
        showLoader()
        // Listen to list of user event
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            adapter.setUsers(it)
        })
        // Listen to status event
        viewModel.getStatus().observe(viewLifecycleOwner, Observer {
            hideLoader()
            if (!isOnline(requireContext())) { // no internet connection
                showMessage(
                    R.string.no_internet_connection,
                    R.string.no_internet_connection_message
                )
            } else if (it == Status.FAILED) { // fail to complete the http request
                showMessage(
                    R.string.something_went_wrong,
                    R.string.something_went_wrong_message
                )
            } else if (it == Status.DENIED) { // http status code not return 200
                showMessage(
                    R.string.dont_allow,
                    R.string.dont_allow_message
                )
            }
        })
    }

    private var messageFragment: MessageFragment? = null
    private fun showMessage(titleRes: Int, messageRes: Int) {
        if (messageFragment != null)
            return
        messageFragment = MessageFragment.newInstance(
            getString(titleRes),
            getString(messageRes)
        )
        messageFragment?.addOnRetryClickListener(View.OnClickListener {
            childFragmentManager.beginTransaction().remove(messageFragment!!).commit()
            messageFragment = null
            showLoader()
            viewModel.loadUsers()
        })
        childFragmentManager.beginTransaction()
            .add(
                R.id.layout_fragment_user_list,
                messageFragment!!
            )
            .commit()
    }

    private var loader: LoaderFragment? = null
    private fun showLoader() {
        loader = LoaderFragment.newInstance(
        )

        childFragmentManager.beginTransaction()
            .add(
                R.id.layout_fragment_user_list,
                loader!!
            )
            .commit()

    }

    private fun hideLoader() {
        if (loader != null)
            childFragmentManager.beginTransaction().remove(loader!!).commit()
        loader = null
    }


}