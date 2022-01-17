package com.example.callmevideoapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {

  private val _usernameLiveData = MutableLiveData<String>()
  val usernameLiveData get() = _usernameLiveData

  private val _avatarLiveData = MutableLiveData<String>()
  val avatarLiveData get() = _avatarLiveData

  private val _selectedAvatarOnDialogLiveData = MutableLiveData<String>()
  val selectedAvatarOnDialogLiveData get() = _selectedAvatarOnDialogLiveData

}