package com.example.callmevideoapp.ui.dialogs

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.callmevideoapp.R
import com.example.callmevideoapp.databinding.FragmentCallDialogBinding
import com.example.callmevideoapp.models.User
import com.example.callmevideoapp.utils.AvatarHandler
import com.example.callmevideoapp.utils.UIHandler

class CallDialogFragment(
  private val dialogListener: CallDialogListener
) : DialogFragment() {

  private lateinit var binding: FragmentCallDialogBinding
  private lateinit var selectedUser: User

  interface CallDialogListener {
    fun onCallDialogCall(dialog: CallDialogFragment)
    fun onCallDialogCancel(dialog: CallDialogFragment)
    fun setCallDialogUser(): User
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setStyle(STYLE_NO_FRAME, android.R.style.Theme)

    binding = FragmentCallDialogBinding.inflate(inflater, container, false)

    selectedUser = dialogListener.setCallDialogUser()

    return binding.root
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Create UI constants
    val username: TextView = binding.tvFragmentCallDialogUsername
    val userAvatar: ImageView = binding.ivFragmentCallDialogIcon
    val callBtn: Button = binding.btnFragmentCallDialogCall
    val cancelBtn: Button = binding.btnFragmentCallDialogCancel

    // Apply data into UI
    username.text = selectedUser.username
    userAvatar.setImageResource(AvatarHandler.getAvatarByName(selectedUser.avatarName))

    // Set listeners
    callBtn.setOnTouchListener { _, event ->
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          UIHandler.updateBackgroundTintColor(callBtn, R.color.primaryPlusOne)
        }

        MotionEvent.ACTION_UP -> {
          UIHandler.updateBackgroundTintColor(callBtn, R.color.primary)
          dialogListener.onCallDialogCall(this)
        }

        MotionEvent.ACTION_MOVE -> {
          UIHandler.updateBackgroundTintColor(callBtn, R.color.primary)
        }
      }

      return@setOnTouchListener true
    }

    cancelBtn.setOnTouchListener { _, event ->
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          UIHandler.updateResourceFromView(cancelBtn, R.drawable.background_btn_fragment_call_dialog_cancel_focused)
          UIHandler.updateTextColor(cancelBtn, R.color.primaryPlusOne)
        }

        MotionEvent.ACTION_UP -> {
          UIHandler.updateResourceFromView(cancelBtn, R.drawable.background_btn_fragment_call_dialog_cancel)
          UIHandler.updateTextColor(cancelBtn, R.color.primary)
          dialogListener.onCallDialogCancel(this)
        }

        MotionEvent.ACTION_MOVE -> {
          UIHandler.updateResourceFromView(cancelBtn, R.drawable.background_btn_fragment_call_dialog_cancel)
          UIHandler.updateTextColor(cancelBtn, R.color.primary)
        }
      }

      return@setOnTouchListener true
    }
  }
}