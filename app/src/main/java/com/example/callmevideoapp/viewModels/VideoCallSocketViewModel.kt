package com.example.callmevideoapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoCallSocketViewModel: ViewModel() {

  private val _isCameraOnLiveData = MutableLiveData<Boolean>()
  val isCameraOnLiveData get() = _isCameraOnLiveData

  private val _isMicOnLiveData = MutableLiveData<Boolean>()
  val isMicOnLiveData get() = _isMicOnLiveData
}