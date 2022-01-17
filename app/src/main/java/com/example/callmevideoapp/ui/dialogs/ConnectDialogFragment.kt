package com.example.callmevideoapp.ui.dialogs

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.callmevideoapp.R
import com.example.callmevideoapp.databinding.FragmentConnectDialogBinding
import com.example.callmevideoapp.models.User
import com.example.callmevideoapp.ui.MainActivity
import com.example.callmevideoapp.utils.AvatarHandler
import com.example.callmevideoapp.utils.UIHandler

class ConnectDialogFragment(
  private val dialogListener: ConnectDialogListener
) : DialogFragment() {

  private lateinit var binding: FragmentConnectDialogBinding
  private lateinit var callingUser: User

  interface ConnectDialogListener {
    fun onConnectDialogAnswer(dialog: ConnectDialogFragment)
    fun onConnectDialogDecline(dialog: ConnectDialogFragment)
    fun setConnectDialogUser(): User
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setStyle(STYLE_NO_FRAME, android.R.style.Theme)

    binding = FragmentConnectDialogBinding.inflate(inflater, container, false)

    callingUser = dialogListener.setConnectDialogUser()

    return binding.root
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Create UI constants
    val username: TextView = binding.tvFragmentConnectDialogUsername
    val userAvatar: ImageView = binding.ivFragmentConnectDialogIcon
    val acceptBtn: ImageButton = binding.btnFragmentConnectDialogAccept
    val declineBtn: ImageButton = binding.btnFragmentConnectDialogDecline

    // Apply data into UI
    username.text = callingUser.username
    userAvatar.setImageResource(AvatarHandler.getAvatarByName(callingUser.avatarName))

    // Set listeners
    acceptBtn.setOnTouchListener { _, event ->
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          UIHandler.updateResourceFromView(
            acceptBtn,
            R.drawable.background_btn_fragment_connect_dialog_accept_focused
          )
        }

        MotionEvent.ACTION_UP -> {
          UIHandler.updateResourceFromView(
            acceptBtn,
            R.drawable.background_btn_fragment_connect_dialog_accept
          )

          dialogListener.onConnectDialogAnswer(this)
        }

        MotionEvent.ACTION_MOVE -> {
          UIHandler.updateResourceFromView(
            acceptBtn,
            R.drawable.background_btn_fragment_connect_dialog_accept
          )
        }
      }

      return@setOnTouchListener true
    }

    declineBtn.setOnTouchListener { _, event ->
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          UIHandler.updateResourceFromView(
            declineBtn,
            R.drawable.background_btn_fragment_connect_dialog_decline_focused
          )
        }

        MotionEvent.ACTION_UP -> {
          UIHandler.updateResourceFromView(
            declineBtn,
            R.drawable.background_btn_fragment_connect_dialog_decline
          )

          dialogListener.onConnectDialogDecline(this)
        }

        MotionEvent.ACTION_MOVE -> {
          UIHandler.updateResourceFromView(
            declineBtn,
            R.drawable.background_btn_fragment_connect_dialog_decline
          )
        }
      }

      return@setOnTouchListener true
    }
  }
}