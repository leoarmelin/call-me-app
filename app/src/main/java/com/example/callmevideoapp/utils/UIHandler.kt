package com.example.callmevideoapp.utils

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

object UIHandler {

  fun <T> updateTextColor(view: T, color: Int) {
    (view as TextView).setTextColor(
      ContextCompat.getColor(
        view.context,
        color
      )
    )
  }

  fun <T> updateBackgroundTintColor(view: T, color: Int) {
    (view as View).backgroundTintList = ColorStateList.valueOf(
      ContextCompat.getColor(
        view.context,
        color
      )
    )
  }

  fun <T> updateResourceFromView(view: T, resource: Int) {
    (view as View).setBackgroundResource(resource)
  }

  fun updateCardBackgroundColor(view: CardView, color: Int) {
    view.setCardBackgroundColor(ColorStateList.valueOf(
      ContextCompat.getColor(
        view.context,
        color
      )
    ))
  }

}