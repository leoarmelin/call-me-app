package com.example.callmevideoapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import com.example.callmevideoapp.R
import com.example.callmevideoapp.databinding.FragmentVideoCallBinding
import com.example.callmevideoapp.utils.UIHandler
import com.example.callmevideoapp.viewModels.VideoCallSocketViewModel
import java.lang.Exception

class VideoCallFragment : Fragment() {

  private lateinit var binding: FragmentVideoCallBinding

  private val videoCallSocketViewModel: VideoCallSocketViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentVideoCallBinding.inflate(inflater, container, false)

    return binding.root
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Create UI constants
    val micBtn: ImageButton = binding.btnFragmentVideoCallMic
    val camBtn: ImageButton = binding.btnFragmentVideoCallCam
    val hangUpBtn: ImageButton = binding.btnFragmentVideoCallHangup

    initButtons()

    // Set listeners
    micBtn.setOnTouchListener { _, event ->
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          micBtn.alpha = 0.7f
        }

        MotionEvent.ACTION_UP -> {
          micBtn.alpha = 1f

          val currentState = videoCallSocketViewModel.isMicOnLiveData.value ?: throw Exception("The isMicOnLiveData should not be null at this point")
          videoCallSocketViewModel.isMicOnLiveData.postValue(
            !currentState
          )
        }
      }

      return@setOnTouchListener true
    }

    camBtn.setOnTouchListener { _, event ->
      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          camBtn.alpha = 0.7f
        }

        MotionEvent.ACTION_UP -> {
          camBtn.alpha = 1f

          val currentState = videoCallSocketViewModel.isCameraOnLiveData.value ?: throw Exception("The isCameraOnLiveData should not be null at this point")
          videoCallSocketViewModel.isCameraOnLiveData.postValue(
            !currentState
          )
        }
      }

      return@setOnTouchListener true
    }

    hangUpBtn.setOnTouchListener { _, event ->

      when (event.action) {
        MotionEvent.ACTION_DOWN -> {
          hangUpBtn.alpha = 0.7f
        }

        MotionEvent.ACTION_UP -> {
          hangUpBtn.alpha = 1f
        }
      }

      return@setOnTouchListener true
    }

    // Apply observers
    videoCallSocketViewModel.isMicOnLiveData.observe(viewLifecycleOwner, { isMicOn ->
      micBtn.setImageResource(if (isMicOn) R.drawable.ic_mic else R.drawable.ic_mic_off)
      UIHandler.updateBackgroundTintColor(
        micBtn,
        if (isMicOn) R.color.backgroundMinusOne else R.color.text
      )
    })

    videoCallSocketViewModel.isCameraOnLiveData.observe(viewLifecycleOwner, { isCamOn ->
      camBtn.setImageResource(if (isCamOn) R.drawable.ic_camera else R.drawable.ic_camera_off)
      UIHandler.updateBackgroundTintColor(
        camBtn,
        if (isCamOn) R.color.backgroundMinusOne else R.color.text
      )
    })
  }

  private fun initButtons() {
    videoCallSocketViewModel.isMicOnLiveData.postValue(true)
    videoCallSocketViewModel.isCameraOnLiveData.postValue(true)
  }
}