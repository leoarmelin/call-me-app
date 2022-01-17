package com.example.callmevideoapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.callmevideoapp.R
import com.example.callmevideoapp.databinding.AdapterFragmentAvatarDialogAvatarsBinding
import com.example.callmevideoapp.models.Avatar
import com.example.callmevideoapp.utils.AvatarHandler
import com.example.callmevideoapp.utils.UIHandler

class AvatarAdapter(
  private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {

  private var avatarList = mutableListOf<Avatar>()

  interface OnItemClickListener {
    fun handleAvatarClick(avatarName: String)
  }

  inner class ViewHolder(binding: AdapterFragmentAvatarDialogAvatarsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    // Create UI constants
    private val avatar: ImageView = binding.ivAdapterFragmentAvatarDialogAvatarsAvatar
    private val avatarContainer: CardView = binding.cvAdapterFragmentAvatarDialogAvatarsContainer

    fun bind(currentAvatar: Avatar) {
      // Apply data into UI
      avatar.setImageResource(AvatarHandler.getAvatarByName(currentAvatar.avatarName))

      if (currentAvatar.isPicked) {
        UIHandler.updateCardBackgroundColor(avatarContainer, R.color.primary)
      } else {
        UIHandler.updateCardBackgroundColor(avatarContainer, R.color.transparent)
      }

      // Set listeners
      avatarContainer.setOnClickListener {
        itemClickListener.handleAvatarClick(currentAvatar.avatarName)
      }
    }

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding =
      AdapterFragmentAvatarDialogAvatarsBinding.inflate(layoutInflater, parent, false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentAvatar = avatarList[position]

    holder.bind(currentAvatar)
  }

  override fun getItemCount() = avatarList.size

  @SuppressLint("NotifyDataSetChanged")
  fun setData(data: MutableList<Avatar>) {
    this.avatarList = data

    notifyDataSetChanged()
  }

}