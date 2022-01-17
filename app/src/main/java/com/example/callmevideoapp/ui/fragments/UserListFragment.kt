package com.example.callmevideoapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.callmevideoapp.R
import com.example.callmevideoapp.adapter.UserListAdapter
import com.example.callmevideoapp.databinding.FragmentUserListBinding
import com.example.callmevideoapp.models.AlertDialogUI
import com.example.callmevideoapp.models.User
import com.example.callmevideoapp.ui.dialogs.AlertDialogFragment
import com.example.callmevideoapp.ui.dialogs.CallDialogFragment
import com.example.callmevideoapp.ui.dialogs.ConnectDialogFragment
import com.example.callmevideoapp.utils.MockedData
import com.example.callmevideoapp.viewModels.UserListViewModel
import com.example.callmevideoapp.viewModels.UsersSocketViewModel
import java.util.*

class UserListFragment :
  Fragment(),
  UserListAdapter.OnClickListener,
  CallDialogFragment.CallDialogListener,
  AlertDialogFragment.AlertDialogListener,
  ConnectDialogFragment.ConnectDialogListener {

  private lateinit var binding: FragmentUserListBinding

  private val userListViewModel: UserListViewModel by activityViewModels()
  private val usersSocketViewModel: UsersSocketViewModel by activityViewModels()

  private val userAdapter = UserListAdapter(this)

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentUserListBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Initialize recycler views
    initUserList()

    // Create UI constants
    val searchBar: EditText = binding.etFragmentUserListSearch
    val userListRecyclerView: RecyclerView = binding.rvFragmentUserListUsers
    val emptyMessageContainer: LinearLayout = binding.llFragmentUserListEmptyContainer

    // Set listeners
    searchBar.doAfterTextChanged { currentText ->

      if (currentText == null) return@doAfterTextChanged

      if (currentText.length < 3) {
        userListViewModel.searchBarTextLiveData.postValue("")
      } else {
        userListViewModel.searchBarTextLiveData.postValue(currentText.toString())
      }
    }

    // Apply adapters into recycler views
    userListRecyclerView.apply {
      layoutManager =
        LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
      adapter = userAdapter
    }

    // Apply observers
    userListViewModel.userListLiveData.observe(viewLifecycleOwner, {
      // Force update to init data on observers, and then to update whenever the userList updates.
      userListViewModel.searchBarTextLiveData.postValue(
        userListViewModel.searchBarTextLiveData.value ?: ""
      )
    })

    userListViewModel.searchBarTextLiveData.observe(viewLifecycleOwner, { currentText ->
      val filteredUsersList = userListViewModel.userListLiveData.value?.filter { user ->
        user.username.lowercase(Locale.getDefault())
          .contains(currentText.lowercase(Locale.getDefault()))
      }?.toMutableList()

      userListViewModel.filteredUserListLiveData.postValue(filteredUsersList)
    })

    userListViewModel.filteredUserListLiveData.observe(viewLifecycleOwner, { filteredUserList ->
      val dontHaveUsersOnline = userListViewModel.userListLiveData.value.isNullOrEmpty()
      val isNotSearchingAndHaveUsersOnline =
        userListViewModel.searchBarTextLiveData.value.isNullOrEmpty()
            && !userListViewModel.userListLiveData.value.isNullOrEmpty()
      val isSearchingAndHaveUsersOnlineAndListIsNotEmpty =
        !userListViewModel.searchBarTextLiveData.value.isNullOrEmpty()
            && !userListViewModel.userListLiveData.value.isNullOrEmpty()
            && filteredUserList.isNotEmpty()
      val isSearchingAndHaveUsersOnlineAndListIsEmpty =
        !userListViewModel.searchBarTextLiveData.value.isNullOrEmpty()
            && !userListViewModel.userListLiveData.value.isNullOrEmpty()
            && filteredUserList.isEmpty()

      when {
        dontHaveUsersOnline -> {
          userListRecyclerView.visibility = View.GONE
          emptyMessageContainer.visibility = View.VISIBLE
        }

        isNotSearchingAndHaveUsersOnline -> {
          userAdapter.setData(userListViewModel.userListLiveData.value!!)
          userListRecyclerView.visibility = View.VISIBLE
          emptyMessageContainer.visibility = View.GONE
        }

        isSearchingAndHaveUsersOnlineAndListIsNotEmpty -> {
          userAdapter.setData(filteredUserList)
          userListRecyclerView.visibility = View.VISIBLE
          emptyMessageContainer.visibility = View.GONE
        }

        isSearchingAndHaveUsersOnlineAndListIsEmpty -> {
          userListRecyclerView.visibility = View.GONE
          emptyMessageContainer.visibility = View.VISIBLE
        }
      }
    })

    userListViewModel.selectedUserLiveData.observe(viewLifecycleOwner, { selectedUser ->
      if (selectedUser.isInCall) return@observe

      val callDialog = CallDialogFragment(this)
      callDialog.show(parentFragmentManager, "CallDialogFragment")
    })

    usersSocketViewModel.callingUserLiveData.observe(viewLifecycleOwner, {
      val connectDialog = ConnectDialogFragment(this)
      connectDialog.show(parentFragmentManager, "ConnectDialogFragment")
    })

  }

  override fun handleUserClick(user: User) {

    // Remove this part
    if (user.username == "Pablo Gritar") {
      usersSocketViewModel.callingUserLiveData.postValue(user)

      return
    }
    // ----------------

    if (user.isInCall) {
      val alertDialog = AlertDialogFragment(this)
      alertDialog.show(parentFragmentManager, "AlertDialogFragment")
    } else {
      userListViewModel.selectedUserLiveData.postValue(user)
    }
  }

  override fun onCallDialogCall(dialog: CallDialogFragment) {
    dialog.dismiss()
  }

  override fun onCallDialogCancel(dialog: CallDialogFragment) {
    dialog.dismiss()
  }

  override fun setCallDialogUser(): User {
    return userListViewModel.selectedUserLiveData.value
      ?: throw Exception("The selectedUserLiveData should not be null at this point")
  }

  override fun onAlertDialogConfirmClick(dialog: AlertDialogFragment) {
    dialog.dismiss()
  }

  override fun fillAlertDialogUI(): AlertDialogUI {
    return AlertDialogUI(
      titleText = "Oh no!",
      contentText = "It looks like this user is in call...",
      confirmBtnText = "I'll call later",
      iconId = R.raw.seeing_phone
    )
  }

  override fun onConnectDialogAnswer(dialog: ConnectDialogFragment) {
    dialog.dismiss()

    findNavController().navigate(R.id.action_userListFragment_to_videoCallFragment)
  }

  override fun onConnectDialogDecline(dialog: ConnectDialogFragment) {
    dialog.dismiss()
  }

  override fun setConnectDialogUser(): User {
    return usersSocketViewModel.callingUserLiveData.value
      ?: throw Exception("The callingUserLiveData should not be null at this point")
  }

  private fun initUserList() {
    userListViewModel.userListLiveData.postValue(MockedData.userList)
  }
}