package com.example.greeenpacket_test

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greeenpacket_test.models.User
import kotlinx.android.synthetic.main.view_user.view.*

class UsersRecyclerViewAdapter() :
    RecyclerView.Adapter<UsersRecyclerViewAdapter.UsersViewHolder>() {

    private val users: ArrayList<User> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UsersRecyclerViewAdapter.UsersViewHolder, position: Int) {
        val user: User = users[position]
        holder.itemView.textView_name.text = user.firstName
    }

    fun setUsers(users: List<User>) {
        val size: Int = this.users.size;
        this.users.clear()
        notifyItemRangeRemoved(0, size)
        this.users.addAll(users)
        notifyItemRangeInserted(0, this.users.size)
    }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
