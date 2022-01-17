package com.example.callmevideoapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.callmevideoapp.models.User

class UserListViewModel: ViewModel() {

  private val _userListLiveData = MutableLiveData<MutableList<User>>()
  val userListLiveData get() = _userListLiveData

  private val _filteredUserListLiveData = MutableLiveData<MutableList<User>>()
  val filteredUserListLiveData get() = _filteredUserListLiveData

  private val _searchBarTextLiveData = MutableLiveData<String>()
  val searchBarTextLiveData get() = _searchBarTextLiveData

  private val _selectedUserLiveData = MutableLiveData<User>()
  val selectedUserLiveData get() = _selectedUserLiveData

}