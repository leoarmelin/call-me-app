package com.example.callmevideoapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.callmevideoapp.models.User

class UsersSocketViewModel: ViewModel() {

  private val _callingUserLiveData = MutableLiveData<User>()
  val callingUserLiveData get() = _callingUserLiveData

}