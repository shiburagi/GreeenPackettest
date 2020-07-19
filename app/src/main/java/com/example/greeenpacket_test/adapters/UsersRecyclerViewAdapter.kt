package com.example.greeenpacket_test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.greeenpacket_test.R
import com.example.greeenpacket_test.models.User
import com.example.greeenpacket_test.views.UserListFragmentDirections
import com.shiburagi.utility.loadAvatar
import kotlinx.android.synthetic.main.view_user.view.*

/**
 * a class to handle [RecyclerView] adapter
 */
class UsersRecyclerViewAdapter() :
    RecyclerView.Adapter<UsersRecyclerViewAdapter.UsersViewHolder>() {

    private val users: ArrayList<User> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_user, parent, false)
        return UsersViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    /**
     * assign value and event on view on given position
     */
    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user: User = users[position]
        val displayName: String = user.displayName
        holder.itemView.textView_name.text = displayName
        holder.itemView.textView_age.text = user.displayAge(holder.itemView.context)
        holder.itemView.imageView_avatar.loadAvatar(
            user.profileImage ?: "",
            displayName
        )

        holder.itemView.setOnClickListener {
            val action =
                UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(
                    user
                )
            holder.itemView.findNavController()
                .navigate(action)

        }
    }

    /**
     * Update [User] list and trigger notify
     */
    fun setUsers(users: List<User>) {
        val size: Int = this.users.size
        this.users.clear()
        notifyItemRangeRemoved(0, size)
        this.users.addAll(users)
        notifyItemRangeInserted(0, this.users.size)
    }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
