package com.example.callmevideoapp.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.callmevideoapp.databinding.FragmentAlertDialogBinding
import com.example.callmevideoapp.models.AlertDialogUI

class AlertDialogFragment(
  private val dialogListener: AlertDialogListener
) : DialogFragment() {

  private lateinit var binding: FragmentAlertDialogBinding
  private lateinit var dialogUIData: AlertDialogUI

  interface AlertDialogListener {
    fun onAlertDialogConfirmClick(dialog: AlertDialogFragment)
    fun fillAlertDialogUI(): AlertDialogUI
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setStyle(STYLE_NO_FRAME, android.R.style.Theme)

    binding = FragmentAlertDialogBinding.inflate(inflater, container, false)

    dialogUIData = dialogListener.fillAlertDialogUI()

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Create UI constants
    val title: TextView = binding.tvFragmentAlertDialogTitle
    val description: TextView = binding.tvFragmentAlertDialogDescription
    val icon: ImageView = binding.ivFragmentAlertDialogIcon
    val confirmBtn: Button = binding.btnFragmentAlertDialogConfirm

    // Apply data into UI
    title.text = dialogUIData.titleText
    description.text = dialogUIData.contentText
    confirmBtn.text = dialogUIData.confirmBtnText

    icon.setImageResource(dialogUIData.iconId)

    confirmBtn.setOnClickListener {
      dialogListener.onAlertDialogConfirmClick(this)
    }
  }

}