package com.example.greeenpacket_test

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greeenpacket_test.models.User
import com.example.greeenpacket_test.viewmodels.UserListViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import kotlinx.android.synthetic.main.fragment_user_list.view.recycler_users
import java.lang.Exception

class UserListFragment : Fragment() {
    companion object {
        fun newInstance() = UserListFragment()
    }

    private lateinit var viewModel: UserListViewModel
    private lateinit var adapter: UsersRecyclerViewAdapter
    private lateinit var viewManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_user_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UsersRecyclerViewAdapter()
        viewManager = LinearLayoutManager(context)
        viewManager.orientation = LinearLayoutManager.VERTICAL
        recycler_users.setHasFixedSize(true)
        recycler_users.layoutManager = viewManager
        recycler_users.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        viewModel.listenUsers.observe(viewLifecycleOwner, Observer {


            activity?.runOnUiThread {
                Log.e("User Frag", it.size.toString())
                adapter.setUsers(it)
            }
        })
    }

}