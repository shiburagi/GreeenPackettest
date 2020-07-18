package com.example.greeenpacket_test.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greeenpacket_test.R
import com.example.greeenpacket_test.adapters.UsersRecyclerViewAdapter
import com.example.greeenpacket_test.viewmodels.UserListViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*

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
//        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {


            activity?.runOnUiThread {
                Log.e("User Frag", it.size.toString())
                adapter.setUsers(it)
            }
        })
    }

}