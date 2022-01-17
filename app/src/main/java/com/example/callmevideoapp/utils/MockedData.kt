package com.example.callmevideoapp.utils

import com.example.callmevideoapp.models.User

object MockedData {
  val userList = mutableListOf(
    User(
      id = "1",
      username = "Rebertson Fonseca",
      avatarName = "meditating",
      status = "online",
      isInCall = false
    ),
    User(
      id = "2",
      username = "Pablo Gritar",
      avatarName = "moshing",
      status = "away",
      isInCall = false
    ),
    User(
      id = "3",
      username = "Angelina Eu li",
      avatarName = "plant",
      status = "online",
      isInCall = false
    ),
    User(
      id = "4",
      username = "Pão piti",
      avatarName = "unboxing",
      status = "occupied",
      isInCall = true
    ),
    User(
      id = "5",
      username = "Criança Walking Stick",
      avatarName = "moshing",
      status = "online",
      isInCall = false
    ),
  )
}