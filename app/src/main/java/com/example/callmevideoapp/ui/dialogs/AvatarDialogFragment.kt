package com.example.callmevideoapp.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.callmevideoapp.adapter.AvatarAdapter
import com.example.callmevideoapp.databinding.FragmentAvatarDialogBinding
import com.example.callmevideoapp.utils.AvatarHandler
import com.example.callmevideoapp.viewModels.AuthViewModel

class AvatarDialogFragment(
  private val dialogListener: AvatarDialogListener
) : DialogFragment(), AvatarAdapter.OnItemClickListener {

  private lateinit var binding: FragmentAvatarDialogBinding

  private val authViewModel: AuthViewModel by activityViewModels()

  private val avatarAdapter = AvatarAdapter(this)

  interface AvatarDialogListener {
    fun onAvatarDialogConfirmClick(dialog: AvatarDialogFragment)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setStyle(STYLE_NO_FRAME, android.R.style.Theme)

    binding = FragmentAvatarDialogBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Create UI constants
    val avatarsRecyclerView: RecyclerView = binding.rvFragmentAvatarDialogAvatars
    val confirmBtn: Button = binding.btnFragmentAvatarDialogConfirm


    // Set listeners
    confirmBtn.setOnClickListener {
      dialogListener.onAvatarDialogConfirmClick(this)
    }

    // Apply adapters into recycler views
    avatarsRecyclerView.apply {
      layoutManager =
        GridLayoutManager(this.context, 2, GridLayoutManager.VERTICAL, false)
      adapter = avatarAdapter
    }

    // Set data into adapters
    avatarAdapter.setData(
      AvatarHandler.getAvatarListByNames(
        AvatarHandler.avatarList,
        authViewModel.avatarLiveData.value
      )
    )

    // Apply observers
    authViewModel.selectedAvatarOnDialogLiveData.observe(viewLifecycleOwner, { avatarName ->
      avatarAdapter.setData(
        AvatarHandler.getAvatarListByNames(
          AvatarHandler.avatarList,
          avatarName
        )
      )
    })
  }

  override fun handleAvatarClick(avatarName: String) {
    authViewModel.selectedAvatarOnDialogLiveData.postValue(avatarName)
  }
}