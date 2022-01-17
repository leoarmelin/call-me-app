package com.example.callmevideoapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.callmevideoapp.R
import com.example.callmevideoapp.databinding.FragmentEndedCallBinding

class EndedCallFragment : Fragment() {

  private lateinit var binding: FragmentEndedCallBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentEndedCallBinding.inflate(inflater, container, false)

    return binding.root
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Create UI constants
    val backButton: Button = binding.btnFragmentEndedCallBackText

    // Set listeners
    backButton.setOnTouchListener { _, event ->
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          backButton.alpha = 0.7f
        }

        MotionEvent.ACTION_UP -> {
          backButton.alpha = 1f
          findNavController().navigate(R.id.action_endedCallFragment_to_userListFragment)
        }
      }

      return@setOnTouchListener true
    }
  }
}