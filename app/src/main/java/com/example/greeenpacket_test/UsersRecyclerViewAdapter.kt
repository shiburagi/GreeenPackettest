package com.example.greeenpacket_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide
import com.example.greeenpacket_test.models.User
import com.shiburagi.utility.dp
import com.shiburagi.utility.px
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
        holder.itemView.textView_age.text = "${user.employeeAge} years old"
        val drawable = TextDrawable.builder().beginConfig().width(60.dp).height(60.dp).endConfig()
            .buildRound(
                user.firstName?.substring(0, 1) ?: "",
                ColorGenerator.MATERIAL.getColor(user.firstName)
            )
        Glide.with(holder.itemView.context).load(user.profileImage)
            .placeholder(drawable)
            .error(drawable)
            .into(holder.itemView.imageView_avatar);
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
