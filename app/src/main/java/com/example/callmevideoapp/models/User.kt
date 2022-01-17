package com.example.callmevideoapp.models

data class User(
  val id: String,
  val username: String,
  val avatarName: String,
  val status: String,
  val isInCall: Boolean
)
