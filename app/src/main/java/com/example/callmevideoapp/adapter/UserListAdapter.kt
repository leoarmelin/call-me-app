package com.example.callmevideoapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.callmevideoapp.R
import com.example.callmevideoapp.databinding.AdapterFragmentUserListUsersBinding
import com.example.callmevideoapp.models.User
import com.example.callmevideoapp.utils.AvatarHandler
import com.example.callmevideoapp.utils.UIHandler
import java.lang.Exception

class UserListAdapter(
  private val itemClickListener: OnClickListener
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

  private var userList = mutableListOf<User>()

  interface OnClickListener {
    fun handleUserClick(user: User)
  }

  inner class ViewHolder(binding: AdapterFragmentUserListUsersBinding) :
    RecyclerView.ViewHolder(binding.root) {

    // Create UI constants
    private val userName: TextView = binding.tvAdapterFragmentUserListUsersUsername
    private val userAvatar: ImageView = binding.ivAdapterFragmentUserListUsersAvatar
    private val userActivity: CardView = binding.cvAdapterFragmentUserListUsersActivityValue
    private val itemContainer: ConstraintLayout = binding.clAdapterFragmentUserListContainer

    @SuppressLint("ClickableViewAccessibility")
    fun bind(currentUser: User) {
      // Apply data into UI
      userName.text = currentUser.username
      userAvatar.setImageResource(
        AvatarHandler.getAvatarByName(currentUser.avatarName)
      )

      UIHandler.updateCardBackgroundColor(
        userActivity,
        when (currentUser.status) {
          "online" -> R.color.success
          "away" -> R.color.primary
          "occupied" -> R.color.danger

          else -> throw Exception("currentUser.status is not listed on UserListAdapter")
        }
      )

      // Set listeners
      itemContainer.setOnClickListener {
        itemClickListener.handleUserClick(currentUser)
      }

      itemContainer.setOnTouchListener { _, event ->
        when (event.action) {
          MotionEvent.ACTION_DOWN -> {
            UIHandler.updateBackgroundTintColor(itemContainer, R.color.backgroundMinusOne)
          }

          MotionEvent.ACTION_UP -> {
            UIHandler.updateBackgroundTintColor(itemContainer, R.color.background)
            itemClickListener.handleUserClick(currentUser)
          }

          MotionEvent.ACTION_MOVE -> {
            UIHandler.updateBackgroundTintColor(itemContainer, R.color.background)
          }
        }

        return@setOnTouchListener true
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding =
      AdapterFragmentUserListUsersBinding.inflate(layoutInflater, parent, false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentUser = userList[position]

    holder.bind(currentUser)
  }

  override fun getItemCount() = userList.size

  @SuppressLint("NotifyDataSetChanged")
  fun setData(data: MutableList<User>) {
    this.userList = data

    notifyDataSetChanged()
  }

}