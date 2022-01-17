package com.example.callmevideoapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.callmevideoapp.R
import com.example.callmevideoapp.databinding.FragmentAuthBinding
import com.example.callmevideoapp.models.AlertDialogUI
import com.example.callmevideoapp.models.User
import com.example.callmevideoapp.ui.dialogs.AlertDialogFragment
import com.example.callmevideoapp.ui.dialogs.AvatarDialogFragment
import com.example.callmevideoapp.ui.dialogs.ConnectDialogFragment
import com.example.callmevideoapp.utils.AvatarHandler
import com.example.callmevideoapp.utils.UIHandler
import com.example.callmevideoapp.viewModels.AuthViewModel
import com.example.callmevideoapp.viewModels.UsersSocketViewModel

class AuthFragment :
  Fragment(),
  AvatarDialogFragment.AvatarDialogListener,
  AlertDialogFragment.AlertDialogListener {

  private lateinit var binding: FragmentAuthBinding

  private val authViewModel: AuthViewModel by activityViewModels()

  private var alertDialogErrorName = ""

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    binding = FragmentAuthBinding.inflate(inflater, container, false)

    return binding.root
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initAvatar()

    // Create UI constants
    val avatar: ImageView = binding.ivFragmentAuthAvatar
    val nameInput: EditText = binding.etFragmentAuthName
    val sendButton: Button = binding.btnFragmentAuthSend
    val avatarContainer: ConstraintLayout = binding.clFragmentAuthAvatarContainer

    // Apply listeners
    sendButton.setOnTouchListener { _, event ->
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          UIHandler.updateBackgroundTintColor(sendButton, R.color.primaryPlusOne)
        }

        MotionEvent.ACTION_UP -> {
          UIHandler.updateBackgroundTintColor(sendButton, R.color.primary)

          if (!nameInput.text.isNullOrEmpty()) {
            authViewModel.usernameLiveData.postValue(nameInput.text.toString())
          } else {
            alertDialogErrorName = "UsernameNotFound"

            val alertDialog = AlertDialogFragment(this)
            alertDialog.show(parentFragmentManager, "AlertDialogFragment")
          }
        }

        MotionEvent.ACTION_MOVE -> {
          UIHandler.updateBackgroundTintColor(sendButton, R.color.primary)
        }
      }

      return@setOnTouchListener true
    }

    nameInput.setOnFocusChangeListener { _, hasFocus ->
      if (hasFocus) {
        UIHandler.updateResourceFromView(
          nameInput,
          R.drawable.background_et_fragment_auth_name_focused
        )
      } else {
        UIHandler.updateResourceFromView(nameInput, R.drawable.background_et_fragment_auth_name)
      }
    }

    avatarContainer.setOnTouchListener { _, event ->
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          avatarContainer.alpha = 0.7f
        }

        MotionEvent.ACTION_UP -> {
          avatarContainer.alpha = 1f

          val dialog = AvatarDialogFragment(this)
          dialog.show(parentFragmentManager, "AvatarDialogFragment")
        }
      }


      return@setOnTouchListener true
    }

    // Apply observers
    authViewModel.avatarLiveData.observe(viewLifecycleOwner, { avatarName ->
      avatar.setImageResource(AvatarHandler.getAvatarByName(avatarName))
    })

    authViewModel.usernameLiveData.observe(viewLifecycleOwner, {
      connect()
    })
  }

  override fun onAvatarDialogConfirmClick(dialog: AvatarDialogFragment) {
    if (authViewModel.selectedAvatarOnDialogLiveData.value.isNullOrEmpty()) return

    authViewModel.avatarLiveData.postValue(
      authViewModel.selectedAvatarOnDialogLiveData.value
    )

    dialog.dismiss()
  }

  override fun onAlertDialogConfirmClick(dialog: AlertDialogFragment) {
    dialog.dismiss()
  }

  override fun fillAlertDialogUI(): AlertDialogUI {
    return when (alertDialogErrorName) {
      "UsernameNotFound" -> AlertDialogUI(
        titleText = "Oops",
        contentText = "Username not found, please fill the field before login.",
        confirmBtnText = "I understand",
        iconId = R.raw.coffee
      )

      else -> AlertDialogUI(
        titleText = "Oops",
        contentText = "An undefined error occurred. Please contact our dev team.",
        confirmBtnText = "Ok!",
        iconId = R.raw.coffee
      )
    }
  }

  private fun initAvatar() {
    authViewModel.avatarLiveData.postValue("meditating")
  }

  private fun connect() {
    if (authViewModel.avatarLiveData.value.isNullOrEmpty()
      || authViewModel.usernameLiveData.value.isNullOrEmpty()
    ) return

    // In future, change the navigation to connect into socket.
    findNavController().navigate(R.id.action_authFragment_to_userListFragment)
  }
}