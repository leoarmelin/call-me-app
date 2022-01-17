package com.example.callmevideoapp.utils

import com.example.callmevideoapp.R
import com.example.callmevideoapp.models.Avatar

object AvatarHandler {

  val avatarList = mutableListOf("meditating", "moshing", "plant", "unboxing")

  private const val meditatingAvatar = R.raw.meditating
  private const val moshingAvatar = R.raw.moshing
  private const val plantAvatar = R.raw.plant
  private const val unboxingAvatar = R.raw.unboxing

  fun getAvatarByName(name: String): Int {

    return when (name) {

      "meditating" -> meditatingAvatar
      "moshing" -> moshingAvatar
      "plant" -> plantAvatar
      "unboxing" -> unboxingAvatar

      else -> throw Exception("This avatar name is nor listed")

    }

  }

  fun getAvatarListByNames(nameList: MutableList<String>, selectedName: String?):
      MutableList<Avatar> {

    val newList = mutableListOf<Avatar>()

    for (name in nameList) {
      newList.add(
        Avatar(
          name,
          name == selectedName
        )
      )
    }

    return newList
  }
}