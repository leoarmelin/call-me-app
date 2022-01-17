package com.example.callmevideoapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.callmevideoapp.models.User

class UserViewModel: ViewModel() {

  private val _userLiveData = MutableLiveData<User>()
  val userLiveData get() = _userLiveData

}